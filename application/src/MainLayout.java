import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainLayout extends LayoutBase {
    // region GUI-related elements
    private final CaseSection summaryOverall;
    private final CaseSection summary7dDiff;

    private final FilterSection filterSection;

    private final MainFooter footer;
    // endregion

    public MainLayout(Stage stage, String title, int width, int height, DataHolder defaultHolder) {
        super(stage, title, width, height, true);

        // Store & set layout helping GUI elements
        VBox.setVgrow(Utils.growRegion, Priority.ALWAYS);
        HBox.setHgrow(Utils.growRegion, Priority.ALWAYS);

        // Data layout
        this.filterSection = new FilterSection(width, defaultHolder);

        // region Summary
        this.summaryOverall = new CaseSection(width, "Overall");
        this.summary7dDiff = new CaseSection(width, "7 Days Difference");

        DailyCaseCounts latestCounts = defaultHolder.getDailyCaseStats().getLatest();
        DailyCaseCounts last7thDay = defaultHolder.getDailyCaseStats().getLatestCountsNdays(7);

        if (latestCounts != null) {
            this.summaryOverall.updateConfirmed(StringUtils.simplifyNumber(latestCounts.getConfirmed()));
            this.summaryOverall.updateFatal(StringUtils.simplifyNumber(latestCounts.getFatal()));
            this.summaryOverall.updateTitle(String.format("Overall - %s", latestCounts.getDate().toString()));

            if (last7thDay != null) {
                this.summary7dDiff.updateConfirmed(
                        StringUtils.simplifyNumber(
                                latestCounts.getConfirmed() - last7thDay.getConfirmed(),
                                true, true, true));
                this.summary7dDiff.updateFatal(
                        StringUtils.simplifyNumber(
                                latestCounts.getFatal() - last7thDay.getFatal(),
                                true, true, true));
                this.summary7dDiff.updateTitle(String.format("7 Days Difference - %s", last7thDay.getDate().toString()));
            }
        }
        // endregion

        this.footer = new MainFooter(stage, filterSection::getCurrentHolder);
    }

    // region GUI element construction
    /**
     * {@code HBox} containing the data in the summary section.
     */
    private HBox summaryDataSection() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(
                this.summaryOverall.getGuiElement(),
                this.summary7dDiff.getGuiElement()
        );
        hBox.getStyleClass().add("section");

        return hBox;
    }

    /**
     * Summary section.
     */
    private Pane summarySection() {
        VBox main = new VBox() {{
            getStyleClass().add("section");
        }};

        main.getChildren().addAll(
                Utils.sectionTitle("Summary"),
                summaryDataSection()
        );

        return main;
    }
    // endregion

    /**
     * {@inheritDoc}
     */
    public BorderPane layout() {
        BorderPane main = new BorderPane();

        // Main part
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                summarySection(),
                filterSection.getGuiElement()
        );

        // Status bar
        main.setCenter(vBox);
        main.setBottom(footer.getGuiElement());

        return main;
    }
}
