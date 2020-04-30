import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Layout class to be called if failed to load any file of the program.
 */
public class ExportPreviewLayout extends LayoutBase {
    private static final int PREVIEW_LEN_LIMIT = 30000;

    private final StringBuilder stringBuilder;

    /**
     * @param stage owner stage of the layout
     * @param appConfig application config object
     * @param title title of the layout
     * @param width width of the layout in pixels (px)
     * @param height height of the layout in pixels (px)
     * @param stringBuilder {@code StringBuilder} to be used when previewing and exporting the data file
     */
    public ExportPreviewLayout(Stage stage, Config appConfig, String title, int width, int height, StringBuilder stringBuilder) {
        super(stage, appConfig, title, width, height, true);

        this.stringBuilder = stringBuilder;
    }

    /**
     * Method to be called on "Export" being clicked.
     */
    private void onExportClick() {
        String path = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").format(LocalDateTime.now()) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)))) {
            writer.append(stringBuilder);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("File exporting completed");
            alert.setHeaderText("File exported.");
            alert.setContentText(String.format("The summary has been exported to `%s`.", path));
            alert.show();

            this.stage.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("File exporting failed");
            alert.setHeaderText("Unable to export the file.");
            alert.setContentText(String.format("Unable to export the file to %s.", path));
            alert.show();
        }
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
        String preview = stringBuilder.length() > PREVIEW_LEN_LIMIT ?
                stringBuilder.substring(0, PREVIEW_LEN_LIMIT) : stringBuilder.toString();

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.getStyleClass().add("export-preview");
        textArea.setText(preview);
        textArea.setPrefHeight(height);

        return textArea;
    }

    /**
     * Footer of the export preview layout.
     *
     * @return footer GUI element
     */
    private HBox footer() {
        return new HBox() {{
            getChildren().add(new Button() {{
                setText("Export");
                setOnAction(e -> onExportClick());
                setId("export");
            }});
            setAlignment(Pos.CENTER_RIGHT);
        }};
    }

    /**
     * Get a {@code GridPane} containing the preview info.
     *
     * @return a prepared {@code GridPane} containing the preview info
     */
    private GridPane exportPreviewPane() {
        return new GridPane() {{
            setPadding(new Insets(10));
            setVgap(10);
            getColumnConstraints().add(0, new ColumnConstraints() {{
                setPrefWidth(width);
                setPercentWidth(100);
                setHgrow(Priority.ALWAYS);
            }});
            addColumn(0, previewTitleLabel(), previewTextArea(), footer());
        }};
    }

    /**
     * {@inheritDoc}
     */
    public BorderPane layout() {
        BorderPane main = new BorderPane();

        main.setCenter(exportPreviewPane());

        return main;
    }
}
