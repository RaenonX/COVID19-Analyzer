import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CaseSection implements IGuiUnit {
    private final Label title;

    private final CaseUnit confirmed;
    private final CaseUnit fatal;

    private final VBox box;

    public CaseSection(int width, String sectionTitle) {
        this(width, sectionTitle, CaseUnit.DEFAULT_TEXT, CaseUnit.DEFAULT_TEXT);
    }

    public CaseSection(int width, String sectionTitle, String defaultConfirmed, String defaultFatal) {
        this.title = new Label(sectionTitle);

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
     * Update the title.
     *
     * @param titleText title to replace
     */
    public void updateTitle(String titleText) {
        title.setText(titleText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getGuiElement() {
        return box;
    }
}
