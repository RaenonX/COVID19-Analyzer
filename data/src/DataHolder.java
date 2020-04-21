import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataHolder implements IGUITableDataCollection<DataEntry> {
    private List<DataEntry> entries;  // TODO: temporary, will be removed

    public DataHolder(List<DataEntry> entries) {
        this.entries = entries;
        // FIXME: Data Reader
        // FIXME: Sort and insert data here (#6)
    }

    public static DataHolder sampleData() {
        County dane = new County("Dane", 45, 120);
        State wi = new State("WI", "Wisconsin", new ArrayList<>() {{ add(dane); }});

        return new DataHolder(Arrays.asList(
                new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 17),
                        wi, dane, 359, 17, 439553),
                new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 18),
                        wi, dane, 360, 17, 439553),
                new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 19),
                        wi, dane, 361, 19, 439553)));
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

    public static DataHolder parseFile() {
        // TODO: pass in the file path and parse it to be the main data holder
        return new DataHolder(new ArrayList<>());
    }
}
