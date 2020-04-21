import javafx.scene.control.TableView;

import java.util.List;

/**
 * Interface of the data collection class.
 *
 * @param <T> Type of the data model
 */
public interface IGUITableDataCollection<T extends IGUITableEntry> {
    /**
     * Get the data table entries.
     *
     * @return data entries
     */
    List<T> getTableDataEntry();

    /**
     * Get the list of {@code javafx.scene.control.TableColumn}.
     * <br>
     * Column needs to be configured first.
     * <br>
     * In order to make the column work, cell value factory needs to be set.
     * <ul>
     *     <li>
     *         {@code ColumnName} will be used and displayed on GUI.
     *     </li>
     *     <li>
     *         {@code FieldName} is the field name of {@code T}.
     *         A {@code public} accessor ({@code getFieldName()}) is required.
     *     </li>
     * </ul>
     * Example:
     * <pre>{@code
     * TableColumn<T, Integer> column = new TableColumn<>("ColumnName");
     * column.setCellValueFactory(new PropertyValueFactory<>("FieldName"));
     * table.getColumns.add(column);
     * }</pre>
     */
    void setTableColumns(TableView<T> table);
}
