import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Layout class to be called if failed to load any file of the program.
 */
public class FileLoadFailedLayout extends LayoutBase {
    private final IOException ioe;

    public FileLoadFailedLayout(Stage stage, String title, int width, int height, IOException e) {
        super(stage, title, width, height, true);

        this.ioe = e;
    }

    /**
     * Get the stack trace string of the exception.
     *
     * @return {@code String} of the stack trace of the exception
     */
    private String exceptionStackTraceToString() {
        StringWriter sw = new StringWriter();
        this.ioe.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * Title GUI {@code Label} of the exception which contains the exception message.
     *
     * @return GUI {@code Label} of the exception
     */
    private Label exceptionTitleLabel() {
        Label title = new Label();
        title.setText(String.format("Failed to load the file: %s", this.ioe.getMessage()));

        return title;
    }

    /**
     * Exception stack trace text GUI element.
     *
     * @return GUI {@code TextArea} containing stack trace
     */
    private TextArea exceptionStackTraceText() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setStyle("-fx-opacity: 1;");
        textArea.setText(exceptionStackTraceToString());
        textArea.setPrefHeight(height);

        return textArea;
    }

    /**
     * Get a {@code GridPane} containing the exception info.
     *
     * @return a prepared {@code GridPane} containing the exception info
     */
    private GridPane exceptionInfoPane() {
        return new GridPane() {{
            setPadding(new Insets(10));
            getColumnConstraints().add(0, new ColumnConstraints() {{
                setPrefWidth(width);
                setPercentWidth(100);
                setHgrow(Priority.ALWAYS);
            }});
            addColumn(0, exceptionTitleLabel(), exceptionStackTraceText());
        }};
    }

    /**
     * {@inheritDoc}
     */
    public BorderPane layout() {
        BorderPane main = new BorderPane();

        main.setCenter(exceptionInfoPane());

        return main;
    }
}
