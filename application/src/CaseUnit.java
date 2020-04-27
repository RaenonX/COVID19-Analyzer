import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A class represents a case unit which contains a title and the case count.
 */
public class CaseUnit implements IGuiUnit {
    public static final String DEFAULT_TEXT = "-";

    private final VBox box;
    private final Label count;

    public CaseUnit(String titleText, String styleClass, String defaultCountValue) {
        Label title = new Label(titleText) {{
            getStyleClass().addAll("summary-type", styleClass);
        }};

        this.count = new Label(defaultCountValue) {{
            getStyleClass().addAll("summary-count", styleClass);
        }};

        this.box = new VBox();
        this.box.getStyleClass().add("case");
        this.box.getChildren().addAll(title, this.count);
    }

    /**
     * {@inheritDoc}
     */
    public VBox getGuiElement() {
        return box;
    }

    /**
     * Update the count {@code Label}.
     *
     * @param val new value to replace
     */
    public void updateCount(String val) {
        count.setText(val);
    }
}
