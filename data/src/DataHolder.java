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

public class DataHolder implements IGUITableDataCollection<DataEntry> {
    private List<DataEntry> entries;

    public DataHolder(Stream<DataEntry> entries) {
        this.entries = entries.collect(Collectors.toList());
    }

    public static DataHolder sampleData() {
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

    @Override
    public List<DataEntry> getTableDataEntry() {
        return this.entries;
    }

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

    public static DataHolder parseFile(String path) throws IOException {
        return new DataHolder(
                Files.lines(Paths.get(path))
                        .map(x -> x.split(","))
                        .map(DataEntryParser::parse));
    }
}
