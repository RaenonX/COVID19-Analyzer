import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainLayout extends LayoutBase {
    private final SummarySection summarySection;
    private final FilterSection filterSection;

    private final MainFooter footer;

    public MainLayout(Stage stage, String title, int width, int height, DataHolder defaultHolder) {
        super(stage, title, width, height, true);

        // Store & set layout helping GUI elements
        VBox.setVgrow(Utils.growRegion, Priority.ALWAYS);
        HBox.setHgrow(Utils.growRegion, Priority.ALWAYS);

        // Data layout
        this.summarySection = new SummarySection(width, defaultHolder);
        this.filterSection = new FilterSection(width, defaultHolder);

        this.footer = new MainFooter(stage, filterSection::getCurrentHolder);
        this.filterSection.onStatusUpdate(this.footer::updateStatus);
    }

    /**
     * {@inheritDoc}
     */
    public BorderPane layout() {
        BorderPane main = new BorderPane();

        // Main part
        VBox vBox = new VBox() {{
            getChildren().addAll(
                    summarySection.getGuiElement(),
                    filterSection.getGuiElement()
            );
        }};

        // Status bar
        main.setCenter(vBox);
        main.setBottom(footer.getGuiElement());

        return main;
    }
}
