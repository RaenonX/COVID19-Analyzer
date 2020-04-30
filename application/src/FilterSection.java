import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public class FilterSection implements IGuiUnit {
    private static final String TITLE_OVERALL = "Latest Overall";
    private static final String TITLE_PER_100K = "Latest Per 100K residents";

    // Data
    private final DataHolder defaultHolder;
    private DataHolder currentHolder;

    private Consumer<String> onStatusUpdate;

    // GUI
    private final CaseSection overall;
    private final CaseSection per100K;

    private final FilterPrompt prompt;

    private final DataTableGUI<DataHolder, DataEntry> dataEntryGUI;
    private final DataTableGUI<DailyCaseStats, DailyCaseCounts> dailyCaseGUI;
    private final DataChartGUI<DailyCaseStats> chartGUI;

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
        this.overall = new CaseSection(width, TITLE_OVERALL);
        this.per100K = new CaseSection(width, TITLE_PER_100K);

        this.prompt = new FilterPrompt(width, e -> onFilterEntered());

        this.dataEntryGUI = new DataTableGUI<>(defaultDataHolder);
        this.dailyCaseGUI = new DataTableGUI<>(defaultDataHolder.getDailyCaseStats());
        this.chartGUI = new DataChartGUI<>(defaultDataHolder.getDailyCaseStats());

        Pane gp = Utils.generateHGridPane(
                width,
                new VBox() {{
                    getStyleClass().add("section");
                    getChildren().addAll(
                            filterDataSection(),
                            chartGUI.getGuiElement()
                    );
                }},
                new TabPane() {{
                    getTabs().addAll(
                            new Tab("Data Entry") {{
                                setContent(dataEntryGUI.getGuiElement());
                                setClosable(false);
                            }},
                            new Tab("Daily Case") {{
                                setContent(dailyCaseGUI.getGuiElement());
                                setClosable(false);
                            }});
                }}
        );

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

    // region Events

    /**
     * Method to be called when `Enter` is clicked in filter prompt.
     */
    private void onFilterEntered() {
        try {
            FilterCondition condition = FilterQueryParser.parse(prompt.getTextInput());
            updateHolder(defaultHolder.filterData(condition));
            prompt.hideErrorMessage();

            updateStatus("Filter successfully applied.");
        } catch (FilterSyntaxError filterSyntaxError) {
            prompt.updateErrorMessage(filterSyntaxError.getMessage());

            updateStatus("Failed to apply the filter.");
        }
    }

    public void onStatusUpdate(Consumer<String> action) {
        this.onStatusUpdate = action;
    }
    // endregion

    /**
     * Execute {@code onStatusUpdate} with the given new status {@code String}.
     *
     * @param status new status
     */
    private void updateStatus(String status) {
        if (onStatusUpdate != null) {
            onStatusUpdate.accept(status);
        }
    }

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

        if (latestCounts != null) {
            this.overall.updateConfirmed(
                    StringUtils.simplifyNumber(latestCounts.getConfirmed(), false, false, false, 0));
            this.overall.updateFatal(
                    StringUtils.simplifyNumber(latestCounts.getFatal(), false, false, false, 0));
            this.overall.updateTitle(String.format("%s - %s", TITLE_OVERALL, latestCounts.getDate().toString()));

            this.per100K.updateConfirmed(
                    StringUtils.simplifyNumber(latestCounts.getConfirmedPer100K(), false, false, false, 2));
            this.per100K.updateFatal(
                    StringUtils.simplifyNumber(latestCounts.getFatalPer100K(), false, false, false, 2));
            this.per100K.updateTitle(String.format("%s - %s", TITLE_PER_100K, latestCounts.getDate().toString()));
        } else {
            this.overall.updateConfirmed("-");
            this.overall.updateFatal("-");
            this.overall.updateTitle(TITLE_OVERALL);

            this.per100K.updateConfirmed("-");
            this.per100K.updateFatal("-");
            this.per100K.updateTitle(TITLE_PER_100K);
        }
    }

    /**
     * Update the data tables with the current data.
     */
    private void updateTables() {
        dataEntryGUI.updateItems(currentHolder);
        dailyCaseGUI.updateItems(currentHolder.getDailyCaseStats());
    }

    /**
     * Update the charts with the current data.
     */
    private void updateCharts() {
        chartGUI.updateChartData(currentHolder.getDailyCaseStats());
    }

    /**
     * Update all layout elements.
     */
    private void updateLayout() {
        updateLayoutData();
        updateTables();
        updateCharts();
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
