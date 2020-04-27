import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CaseSection implements IGuiUnit {
    private CaseUnit confirmed;
    private CaseUnit fatal;

    private final VBox box;

    public CaseSection(int width, String sectionTitle) {
        this(width, sectionTitle, CaseUnit.DEFAULT_TEXT, CaseUnit.DEFAULT_TEXT);
    }

    public CaseSection(int width, String sectionTitle, String defaultConfirmed, String defaultFatal) {
        Label title = new Label(sectionTitle);

        this.confirmed = new CaseUnit("Confirmed Cases", "confirmed", defaultConfirmed);
        this.fatal = new CaseUnit("Fatal Cases", "fatal", defaultFatal);

        GridPane gp = Utils.generateHGridPane(width, confirmed.getGuiElement(), fatal.getGuiElement());

        this.box = new VBox();
        this.box.getStyleClass().addAll("summary-section", "section");
        this.box.getChildren().addAll(title, gp);
    }

    /**
     * Update the confirmed case count.
     *
     * @param val value to replace
     */
    public void updateConfirmed(String val) {
        confirmed.updateCount(val);
    }

    /**
     * Update the fatal case count.
     *
     * @param val value to replace
     */
    public void updateFatal(String val) {
        fatal.updateCount(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getGuiElement() {
        return box;
    }
}
