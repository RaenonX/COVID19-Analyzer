import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Layout class to be called if failed to load any file of the program.
 */
public class ExportPreviewLayout extends LayoutBase {
    private static final int PREVIEW_LEN_LIMIT = 10000;

    private final StringBuilder stringBuilder;

    public ExportPreviewLayout(Stage stage, String title, int width, int height, StringBuilder stringBuilder) {
        super(stage, title, width, height, true);

        this.stringBuilder = stringBuilder;
    }

    /**
     * Title GUI {@code Label} for the preview pane.
     *
     * @return GUI {@code Label} title
     */
    private Label previewTitleLabel() {
        Label title = new Label();
        title.setText(
                String.format(
                        "Preview of the text content to be exported.\nOnly displaying %d characters.",
                        PREVIEW_LEN_LIMIT));

        return title;
    }

    /**
     * Preview text GUI element.
     *
     * @return GUI {@code TextArea} containing the text to be exported
     */
    private TextArea previewTextArea() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.getStyleClass().add("export-preview");
        textArea.setText(stringBuilder.substring(0, PREVIEW_LEN_LIMIT));
        textArea.setPrefHeight(height);

        return textArea;
    }

    /**
     * Get a {@code GridPane} containing the preview info.
     *
     * @return a prepared {@code GridPane} containing the preview info
     */
    private GridPane exportPreviewPane() {
        return new GridPane() {{
            setPadding(new Insets(10));
            getColumnConstraints().add(0, new ColumnConstraints() {{
                setPrefWidth(width);
                setPercentWidth(100);
                setHgrow(Priority.ALWAYS);
            }});
            addColumn(0, previewTitleLabel(), previewTextArea());
        }};
    }

    // TODO: Add button to export the file

    /**
     * {@inheritDoc}
     */
    public BorderPane layout() {
        BorderPane main = new BorderPane();

        main.setCenter(exportPreviewPane());

        return main;
    }
}
