import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SummarySection implements IGuiUnit {
    private static final String TITLE_OVERALL = "Overall";
    private static final String TITLE_7DAYS_DIFF = "7 Days Difference";

    private final CaseSection summaryOverall;
    private final CaseSection summary7dDiff;

    private final VBox box;

    private void updateCaseSections(DataHolder defaultDataHolder) {
        DailyCaseCounts latestCounts = defaultDataHolder.getDailyCaseStats().getLatest();
        DailyCaseCounts last7thDay = defaultDataHolder.getDailyCaseStats().getLatestCountsNdays(7);

        if (latestCounts != null) {
            this.summaryOverall.updateConfirmed(StringUtils.simplifyNumber(latestCounts.getConfirmed()));
            this.summaryOverall.updateFatal(StringUtils.simplifyNumber(latestCounts.getFatal()));
            this.summaryOverall.updateTitle(String.format("%s - %s", TITLE_OVERALL, latestCounts.getDate().toString()));

            if (last7thDay != null) {
                this.summary7dDiff.updateConfirmed(
                        StringUtils.simplifyNumber(
                                latestCounts.getConfirmed() - last7thDay.getConfirmed(),
                                true, true, true, 0));
                this.summary7dDiff.updateFatal(
                        StringUtils.simplifyNumber(
                                latestCounts.getFatal() - last7thDay.getFatal(),
                                true, true, true, 0));
                this.summary7dDiff.updateTitle(String.format("%s - %s", TITLE_7DAYS_DIFF, last7thDay.getDate().toString()));
            }
        }
    }

    /**
     * {@code HBox} containing the data in the summary section.
     */
    private HBox summaryDataSection() {
        return new HBox() {{
            getChildren().addAll(
                    summaryOverall.getGuiElement(),
                    summary7dDiff.getGuiElement()
            );
            getStyleClass().add("section");
        }};
    }

    /**
     * Summary section.
     */
    private VBox summarySection() {
        return new VBox() {{
            getStyleClass().add("section");
            getChildren().addAll(
                    Utils.sectionTitle("Summary"),
                    summaryDataSection()
            );
        }};
    }

    public SummarySection(int width, DataHolder defaultDataHolder) {
        this.summaryOverall = new CaseSection(width, TITLE_OVERALL);
        this.summary7dDiff = new CaseSection(width, TITLE_7DAYS_DIFF);

        this.updateCaseSections(defaultDataHolder);

        this.box = summarySection();
    }

    @Override
    public Pane getGuiElement() {
        return box;
    }
}
