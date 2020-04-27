import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class holding multiple {@code DataEntry}.
 */
public class DataHolder implements IGUITableDataCollection<DataEntry> {
    private final List<DataEntry> entries;
    private final FilterCondition condition;

    private final DailyCaseStats dailyCaseStats;

    public DataHolder(Stream<DataEntry> entries) {
        this(entries, new FilterCondition());
    }

    public DataHolder(Stream<DataEntry> entries, FilterCondition condition) {
        this.entries = entries.filter(Objects::nonNull).collect(Collectors.toList());
        this.condition = condition;

        this.dailyCaseStats = prepareDailyStats();
    }

    private DailyCaseStats prepareDailyStats() {
        int totalPop = getPopulation();
        List<LocalDate> dates = new ArrayList<>();
        Map<LocalDate, Integer> confirmedCount = new TreeMap<>();
        Map<LocalDate, Integer> fatalCount = new TreeMap<>();

        this.entries.forEach(x -> {
            LocalDate date = x.getDate();
            if (!dates.contains(date)) {
                dates.add(date);
            }
            confirmedCount.put(date, confirmedCount.getOrDefault(date, 0) + x.getConfirmed());
            fatalCount.put(date, fatalCount.getOrDefault(date, 0) + x.getFatal());
        });

        List<DailyCaseCounts> counts = dates.stream()
                .map(x -> {
                    int confirmed = confirmedCount.getOrDefault(x, 0);
                    int confirmedDiff = confirmed - confirmedCount.getOrDefault(x.minusDays(1), 0);
                    int fatal = fatalCount.getOrDefault(x, 0);
                    int fatalDiff = fatal - fatalCount.getOrDefault(x.minusDays(1), 0);
                    return new DailyCaseCounts(x, confirmed, confirmedDiff, fatal, fatalDiff, totalPop);
                })
                .collect(Collectors.toList());

        return new DailyCaseStats(counts);
    }

    /**
     * Returns a {@code DataHolder} which contains some sample data.
     *
     * @return {@code DataHolder} which contains some sample data
     */
    public static DataHolder sampleData() {
        County dane = new County("Dane", 45, 120, 435337, new ArrayList<>());
        State wi = new State("WI", "Wisconsin", new ArrayList<>() {{
            add(dane);
        }});

        return new DataHolder(Stream.of(
                new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 17),
                        wi, dane, 359, 17),
                new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 18),
                        wi, dane, 360, 17),
                new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 19),
                        wi, dane, 361, 19)));
    }

    /**
     * Filter the data using the given {@code condition}
     * and return a {@code DataHolder} which contains the filtered data.
     *
     * @param condition condition to be used to filter the data
     * @return a {@code DataHolder} containing the filtered data
     */
    public DataHolder filterData(FilterCondition condition) {
        // TODO: Filter the data
        return null;
    }

    public int getDataCount() {
        return this.entries.size();
    }

    public DailyCaseStats getDailyCaseStats() {
        return dailyCaseStats;
    }

    /**
     * Get the total population of all entries' location.
     *
     * @return total population of all entries' location
     */
    public int getPopulation() {
        return this.entries
                .stream()
                .map(DataEntry::getState)
                .distinct()
                .mapToInt(State::getPopulation)
                .sum();
    }

    /**
     * Returns a {@code StringBuilder} containing the summary string of the data in this {@code DataHolder}.
     * <br>
     * The summary string contains the following data:
     * <ul>
     *     <li>Condition used to filter the data</li>
     *     <li>Confirmed / Fatal Case Count</li>
     *     <li>Confirmed / Fatal Case Count per 100K residents</li>
     *     <li>Data entries in this {@code DataHolder}</li>
     * </ul>
     *
     * @return {@code StringBuilder} containing the summary string of the data
     */
    public StringBuilder summaryString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "Condition: %s\n", this.condition.toString()));
        sb.append("\n");
        sb.append("# Daily Case Counts\n");
        sb.append(DailyStatsTableMaker.tableString(getDailyCaseStats())).append("\n");
        sb.append("\n");
        sb.append("# Data Entries\n");
        sb.append(DataEntryFileProcessor.tableHeader()).append("\n");
        sb.append(this.entries.stream().map(DataEntry::toTableEntry).collect(Collectors.joining("\n")));

        return sb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataEntry> getTableDataEntry() {
        return this.entries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTableColumns(TableView<DataEntry> table) {
        new TableColumn<DataEntry, LocalDate>("Date") {{
            setCellValueFactory(new PropertyValueFactory<>("date"));
            table.getColumns().add(this);
        }};

        new TableColumn<DataEntry, String>("State") {{
            setCellValueFactory(new PropertyValueFactory<>("state"));
            table.getColumns().add(this);
        }};

        new TableColumn<DataEntry, LocalDate>("County") {{
            setCellValueFactory(new PropertyValueFactory<>("county"));
            setMinWidth(100);
            table.getColumns().add(this);
        }};

        new TableColumn<DataEntry, Integer>("Confirmed") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmed"));
            table.getColumns().add(this);
        }};

        new TableColumn<DataEntry, Integer>("Fatal") {{
            setCellValueFactory(new PropertyValueFactory<>("fatal"));
            table.getColumns().add(this);
        }};

        Callback<TableColumn<DataEntry, Double>, TableCell<DataEntry, Double>> factory =
                tc -> new TableCell<>() {
                    @Override
                    protected void updateItem(Double confirmed, boolean empty) {
                        super.updateItem(confirmed, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.format("%.2f", confirmed));
                        }
                    }
                };

        new TableColumn<DataEntry, Double>("Confirmed / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmedPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        new TableColumn<DataEntry, Double>("Fatal / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("fatalPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        table.getColumns().forEach(x -> x.setReorderable(false));
    }

    /**
     * Parse the data file into a single {@code DataHolder}.
     *
     * @param path path of the data file
     * @return parsed {@code DataHolder}
     * @throws IOException thrown if file does not exist or occupied
     */
    public static DataHolder parseFile(String path) throws IOException {
        return new DataHolder(
                Files.lines(Paths.get(path))
                        .map(x -> x.split(","))
                        .map(DataEntryFileProcessor::parse));
    }
}
