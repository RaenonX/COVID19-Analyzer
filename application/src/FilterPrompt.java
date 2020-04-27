import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Filter Prompt GUI section class.
 */
public class FilterPrompt implements IGuiUnit {
    private final VBox box;

    private final TextField textField;
    private final Label messageLabel;

    public FilterPrompt(int width, EventHandler<ActionEvent> eventHandler) {
        // Error label
        this.messageLabel = new Label();
        this.messageLabel.managedProperty().bind(this.messageLabel.visibleProperty());
        this.messageLabel.setVisible(false);
        this.messageLabel.getStyleClass().add("error");

        // Text input
        this.textField = new TextField() {{
            setPromptText("Input filter query...");
            messageLabel.setVisible(false);
            setOnAction(eventHandler);
        }};
        Pane pane = Utils.generateHGridPane(width, this.textField);
        pane.getStyleClass().add("section");

        // Main layout element
        this.box = new VBox();
        this.box.getChildren().addAll(pane, this.messageLabel);
    }

    /**
     * Hide the error message label.
     */
    public void hideErrorMessage() {
        this.messageLabel.setVisible(false);
    }

    /**
     * Update the error message and display it.
     *
     * @param errorMessage error message
     */
    public void updateErrorMessage(String errorMessage) {
        this.messageLabel.setText(String.format("Error: %s", errorMessage));
        this.messageLabel.setVisible(true);
    }

    /**
     * Get the text input.
     *
     * @return text input
     */
    public String getTextInput() {
        return this.textField.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getGuiElement() {
        return box;
    }
}
