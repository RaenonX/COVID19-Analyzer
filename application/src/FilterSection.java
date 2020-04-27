import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FilterSection implements IGuiUnit {
    // Data
    private final DataHolder defaultHolder;
    private DataHolder currentHolder;

    // GUI
    private final CaseSection overall;
    private final CaseSection per100K;

    private final FilterPrompt prompt;

    private final VBox box;

    // region Class/GUI constructions
    /**
     * {@code HBox} containing the data in the filter section.
     */
    private HBox filterDataSection() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(
                this.overall.getGuiElement(),
                this.per100K.getGuiElement()
        );
        hBox.getStyleClass().add("section");

        return hBox;
    }

    public FilterSection(int width, DataHolder defaultDataHolder) {
        // Store data
        this.defaultHolder = defaultDataHolder;
        this.currentHolder = defaultDataHolder;

        // Initialize GUI elements
        this.overall = new CaseSection(width, "Latest Overall");
        this.per100K = new CaseSection(width, "Latest Per 100K residents");

        this.prompt = new FilterPrompt(width, x -> onFilterEntered());

        GridPane gp = Utils.generateHGridPane(
                width,
                new VBox() {{
                    getStyleClass().add("section");
                    getChildren().addAll(
                            filterDataSection(),
                            ChartMaker.sampleChart()
                    );
                }},
                TableMaker.makeTable(this.currentHolder)
        );
        gp.getStyleClass().add("section");

        // Main GUI element
        this.box = new VBox() {{
            getStyleClass().add("section");
            getChildren().addAll(
                    Utils.sectionTitle("Filtered"),
                    prompt.getGuiElement(),
                    gp
            );
        }};

        // Refresh
        updateLayoutData();
    }
    // endregion

    /**
     * Method to be called when `Enter` is clicked in filter prompt.
     */
    // region Events
    private void onFilterEntered() {
        try {
            FilterCondition condition = FilterQueryParser.parse(prompt.getTextInput());
            updateHolder(defaultHolder.filterData(condition));
            prompt.hideErrorMessage();
        } catch (FilterSyntaxError filterSyntaxError) {
            prompt.updateErrorMessage(filterSyntaxError.getMessage());
        }
    }
    // endregion

    /**
     * Update the {@code DataHolder} of the filtered data, triggering all layout updates.
     *
     * @param holder new {@code DataHolder}
     */
    public void updateHolder(DataHolder holder) {
        this.currentHolder = holder;
        this.updateLayout();
    }

    /**
     * Update the data layouts.
     */
    private void updateLayoutData() {
        DailyCaseCounts latestCounts = currentHolder.getDailyCaseStats().getLatest();

        // TODO: Update filter section label

        if (latestCounts != null) {
            this.overall.updateConfirmed(
                    StringUtils.simplifyNumber(latestCounts.getConfirmed(), false, false, false, 0));
            this.overall.updateFatal(
                    StringUtils.simplifyNumber(latestCounts.getFatal(), false, false, false, 0));

            this.per100K.updateConfirmed(
                    StringUtils.simplifyNumber(latestCounts.getConfirmedPer100K(), false, false, false, 2));
            this.per100K.updateFatal(
                    StringUtils.simplifyNumber(latestCounts.getFatalPer100K(), false, false, false, 2));
        } else {
            this.overall.updateConfirmed("-");
            this.overall.updateFatal("-");

            this.per100K.updateConfirmed("-");
            this.per100K.updateFatal("-");
        }
    }

    /**
     * Update all layout elements.
     */
    private void updateLayout() {
        updateLayoutData();
    }

    public DataHolder getCurrentHolder() {
        return currentHolder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getGuiElement() {
        return box;
    }
}
