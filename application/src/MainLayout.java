import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Main layout GUI class of the application.
 */
public class MainLayout extends LayoutBase {
    private final SummarySection summarySection;
    private final FilterSection filterSection;

    private final MainFooter footer;

    /**
     * @param stage owner stage of the layout
     * @param appConfig application config object
     * @param title title of the layout
     * @param width width of the layout in pixels (px)
     * @param height height of the layout in pixels (px)
     * @param defaultHolder the data holder to be used when initializing thi layout
     */
    public MainLayout(Stage stage, Config appConfig, String title, int width, int height, DataHolder defaultHolder) {
        super(stage, appConfig, title, width, height, true);

        // Store & set layout helping GUI elements
        VBox.setVgrow(Utils.growRegion, Priority.ALWAYS);
        HBox.setHgrow(Utils.growRegion, Priority.ALWAYS);

        // Data layout
        this.summarySection = new SummarySection(width, defaultHolder);
        this.filterSection = new FilterSection(width, defaultHolder);

        this.footer = new MainFooter(stage, appConfig, filterSection::getCurrentHolder);
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
