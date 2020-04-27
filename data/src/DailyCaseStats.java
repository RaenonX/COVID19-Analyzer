import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

/**
 * A class which holds multiple {@code DailyCaseCounts}.
 */
public class DailyCaseStats implements IGUITableDataCollection<DailyCaseCounts> {
    private final List<DailyCaseCounts> caseCounts;

    public DailyCaseStats(List<DailyCaseCounts> caseCounts) {
        this.caseCounts = caseCounts;
    }

    /**
     * Get the latest {@code DailyCaseCount}. Return {@code null} if no stats.
     *
     * @return latest {@code DailyCaseCount}
     */
    public DailyCaseCounts getLatest() {
        return this.caseCounts
                .stream()
                .max(DailyCaseCounts.cmpDate)
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DailyCaseCounts> getTableDataEntry() {
        return caseCounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTableColumns(TableView<DailyCaseCounts> table) {
        new TableColumn<DailyCaseCounts, LocalDate>("Date") {{
            setCellValueFactory(new PropertyValueFactory<>("date"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("Confirmed") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmed"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("+/-") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmedDiff"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("Fatal") {{
            setCellValueFactory(new PropertyValueFactory<>("fatal"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("+/-") {{
            setCellValueFactory(new PropertyValueFactory<>("fatalDiff"));
            table.getColumns().add(this);
        }};

        Callback<TableColumn<DailyCaseCounts, Double>, TableCell<DailyCaseCounts, Double>> factory =
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

        new TableColumn<DailyCaseCounts, Double>("Confirmed / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmedPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Double>("Fatal / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("fatalPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        table.getColumns().forEach(x -> x.setReorderable(false));
    }
}
