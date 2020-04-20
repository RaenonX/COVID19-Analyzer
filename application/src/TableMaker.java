import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class TableMaker {
    /**
     * Same as {@code makeTable} with an additional empty table placeholder parameter.
     *
     * @param emptyTablePlaceholder text to be displayed if empty
     */
    public static <T extends IGUITableDataCollection<S>, S extends IGUITableEntry> TableView<S> makeTable(
            T data, String emptyTablePlaceholder) {
        TableView<S> table = new TableView<>();

        table.getItems().addAll(data.getTableDataEntry());

        // Placeholder
        table.setPlaceholder(new Label(emptyTablePlaceholder));

        // Fill column
        data.setTableColumns(table);

        return table;
    }

    /**
     * Create a GUI table element.
     *
     * @param data an entry collection class instance
     * @param <T> class which holds all entries ({@code S})
     * @param <S> class which represents a data entry
     * @return prepared GUI table view
     */
    public static <T extends IGUITableDataCollection<S>, S extends IGUITableEntry> TableView<S> makeTable(T data) {
        return makeTable(data, "NO DATA");
    }
}
