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
    private List<DataEntry> entries;
    private FilterCondition condition;

    public DataHolder(Stream<DataEntry> entries) {
        this(entries, new FilterCondition());
    }

    public DataHolder(Stream<DataEntry> entries, FilterCondition condition) {
        this.entries = entries.collect(Collectors.toList());
        this.condition = condition;
    }

    /**
     * Returns a {@code DataHolder} which contains some sample data.
     *
     * @return {@code DataHolder} which contains some sample data
     * @throws InvalidPopulationCount 
     * @throws InvalidLongitudeException 
     * @throws InvalidLatitudeException 
     * @throws InvalidCountyNameException 
     */
    public static DataHolder sampleData() throws InvalidCountyNameException, InvalidLatitudeException, InvalidLongitudeException, InvalidPopulationCount {
        County dane = new County("Dane", 45, 120, 435337, new ArrayList<>());
        State wi = new State("WI", "Wisconsin", new ArrayList<>() {{ add(dane); }});

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

    public int getConfirmedCaseCount() {
        return this.entries
                .stream()
                .mapToInt(DataEntry::getConfirmed)
                .sum();
    }

    public int getFatalCaseCount() {
        return this.entries
                .stream()
                .mapToInt(DataEntry::getFatal)
                .sum();
    }

    public int getPopulation() {
        return this.entries
                .stream()
                .map(DataEntry::getState)
                .distinct()
                .mapToInt(State::getPopulation)
                .sum();
    }

    public double getConfirmedCasePer100K() {
        return this.getConfirmedCaseCount() / (double)this.getPopulation() * 100000;
    }

    public double getFatalCasePer100K() {
        return this.getFatalCaseCount() / (double)this.getPopulation() * 100000;
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
        sb.append(String.format(
                "Confirmed / Fatal case count: %d / %d\n",
                this.getConfirmedCaseCount(), this.getFatalCaseCount()));
        sb.append(String.format(
                "Confirmed / Fatal case per 100K residents: %.2f / %.2f\n",
                this.getConfirmedCasePer100K(), this.getFatalCasePer100K()));
        sb.append("\n");
        sb.append("Data Entries:\n");
        sb.append(DataEntryFileProcessor.tableHeader());
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
