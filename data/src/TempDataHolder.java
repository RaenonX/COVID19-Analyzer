import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class TempDataHolder implements IGUITableDataCollection<TempDataEntry> {
    @Override
    public List<TempDataEntry> getTableDataEntry() {
        return Arrays.asList(
                new TempDataEntry(
                        LocalDate.of(2020, Month.APRIL, 17),
                        "WI", "Dane", 359, 17, 439553),
                new TempDataEntry(
                        LocalDate.of(2020, Month.APRIL, 18),
                        "WI", "Dane", 360, 17, 439553),
                new TempDataEntry(
                        LocalDate.of(2020, Month.APRIL, 19),
                        "WI", "Dane", 361, 19, 439553));
    }

    @Override
    public void setTableColumns(TableView<TempDataEntry> table) {
        new TableColumn<TempDataEntry, LocalDate>("Date") {{
            setCellValueFactory(new PropertyValueFactory<>("date"));
            table.getColumns().add(this);
        }};

        new TableColumn<TempDataEntry, String>("State") {{
            setCellValueFactory(new PropertyValueFactory<>("state"));
            table.getColumns().add(this);
        }};

        new TableColumn<TempDataEntry, LocalDate>("County") {{
            setCellValueFactory(new PropertyValueFactory<>("county"));
            table.getColumns().add(this);
        }};

        new TableColumn<TempDataEntry, Integer>("Confirmed") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmed"));
            table.getColumns().add(this);
        }};

        new TableColumn<TempDataEntry, Integer>("Fatal") {{
            setCellValueFactory(new PropertyValueFactory<>("fatal"));
            table.getColumns().add(this);
        }};

        Callback<TableColumn<TempDataEntry, Double>, TableCell<TempDataEntry, Double>> factory =
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

        new TableColumn<TempDataEntry, Double>("Confirmed / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmedPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        new TableColumn<TempDataEntry, Double>("Fatal / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("fatalPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        table.getColumns().forEach(x -> x.setReorderable(false));
    }
}
