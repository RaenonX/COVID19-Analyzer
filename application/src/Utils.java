import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    public static final Region growRegion = new Region();

    /**
     * Generate a {@code GridPane} which can fit horizontally to the window
     * with the {@code items} equally distributed in terms of width.
     *
     * @param items GUI elements to be used
     * @return processed {@code GridPane}
     */
    public static GridPane generateHGridPane(int width, Node... items) {
        GridPane gp = new GridPane();
        int count = items.length;

        gp.getColumnConstraints().addAll(
                IntStream
                        .range(0, count)
                        .mapToObj(x -> new ColumnConstraints() {{
                            setPrefWidth(width / (double) count);
                            setPercentWidth(100 / (double) count);
                            setHgrow(Priority.ALWAYS);
                        }})
                        .collect(Collectors.toList()));
        gp.addRow(0, items);

        return gp;
    }

    /**
     * Creates a label for the section title.
     *
     * @param titleText text of the title
     * @return section title label
     */
    public static Label sectionTitle(String titleText) {
        Label title = new Label(titleText);
        title.getStyleClass().add("section-title");

        return title;
    }
}
