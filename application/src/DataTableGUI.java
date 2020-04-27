import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * A data table GUI element.
 *
 * @param <T> class which holds all entries ({@code S})
 * @param <S> class which represents a data entry
 */
public class DataTableGUI<T extends IGUITableDataCollection<S>, S extends IGUITableEntry> implements IGuiUnit {
    private final TableView<S> table;

    /**
     * Construct a {@code DataTableGUI}.
     *
     * @param data an entry collection class instance
     */
    public DataTableGUI(T data) {
        this(data, "NO DATA");
    }

    /**
     * Same as {@code DataTableGUI(T data)} with an additional empty table placeholder parameter.
     *
     * @param emptyTablePlaceholder text to be displayed if empty
     */
    public DataTableGUI(T data, String emptyTablePlaceholder) {
        this.table = new TableView<>();
        this.table.setPlaceholder(new Label(emptyTablePlaceholder));

        // Fill column
        data.setTableColumns(table);

        updateItems(data);
    }

    /**
     * Update the items of this table.
     *
     * @param data data to be used to update the table
     */
    public void updateItems(T data) {
        table.getItems().clear();
        table.getItems().addAll(data.getTableDataEntry());
    }

    @Override
    public Pane getGuiElement() {
        return new BorderPane() {{ setCenter(table); }};
    }
}
