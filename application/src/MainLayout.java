import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainLayout {
    // TODO: needs a local variable storing the case data label to update the data
    //  Mechanism of how the layout needs to be changed to accomplish the above
    private static final String DEFAULT_STATUS_MSG = "Ready";
    private static final String DEFAULT_TEXT = "30.3K";

    private final int presetWidth;

    private final Region growRegion;

    public MainLayout(int presetWidth) {
        this.presetWidth = presetWidth;

        this.growRegion = new Region();

        VBox.setVgrow(growRegion, Priority.ALWAYS);
        HBox.setHgrow(growRegion, Priority.ALWAYS);
    }

    /**
     * Generate a {@code GridPane} which can fit horizontally to the window
     * with the {@code items} equally distributed in terms of width.
     *
     * @param items GUI elements to be used
     * @return processed {@code GridPane}
     */
    private GridPane generateHGridPane(Node... items) {
        GridPane gp = new GridPane();
        int count = items.length;

        gp.getColumnConstraints().addAll(
                IntStream
                        .range(0, count)
                        .mapToObj(x -> new ColumnConstraints() {{
                            setPrefWidth(presetWidth / (double) count);
                            setPercentWidth(100 / (double) count);
                            setHgrow(Priority.ALWAYS);
                        }})
                        .collect(Collectors.toList()));
        gp.addRow(0, items);

        return gp;
    }

    /**
     * Bottom part of the main layout.
     *
     * @return GUI element of the layout
     */
    public Pane bottomPart() {
        HBox hBox = new HBox() {{
            getStyleClass().add("section");
        }};

        Label status = new Label(DEFAULT_STATUS_MSG) {{
            setId("status");
        }};
        Button b1_doc = new Button("Filter Syntax Manual") {{
            setId("doc");
        }};
        Button b2_export = new Button("Export Result") {{
            setId("export");
        }};

        hBox.getChildren().addAll(status, growRegion, b1_doc, b2_export);

        return hBox;
    }

    /**
     * Creates a label for the section title.
     *
     * @param titleText text of the title
     * @return section title label
     */
    private Label sectionTitle(String titleText) {
        Label title = new Label(titleText);
        title.getStyleClass().add("section-title");

        return title;
    }

    /**
     * A {@code VBox} unit containing the case type and the case data.
     *
     * @param titleText case type title text
     * @param styleClass css class to style
     * @return a prepared {@code VBox} unit
     */
    private VBox caseUnit(String titleText, String styleClass) {
        Label title = new Label(titleText) {{
            getStyleClass().addAll("summary-type", styleClass);
        }};
        Label data = new Label(DEFAULT_TEXT) {{
            getStyleClass().addAll("summary-count", styleClass);
        }};
        VBox box = new VBox();
        box.getStyleClass().add("case");
        box.getChildren().addAll(title, data);

        return box;
    }

    /**
     * A {@code Pane} containing various type of case units.
     *
     * @param titleText title text
     * @return a {@code Pane} containing various type of case units
     */
    public Pane caseSection(String titleText) {
        Label title = new Label(titleText);

        VBox confirmed = caseUnit("Confirmed Cases", "confirmed");
        VBox fatal = caseUnit("Fatal Cases", "fatal");

        GridPane gp = generateHGridPane(confirmed, fatal);

        VBox main = new VBox();
        main.getStyleClass().addAll("summary-section", "section");
        main.getChildren().addAll(title, gp);

        return main;
    }

    /**
     * {@code HBox} containing the data in the summary section.
     */
    public HBox summaryDataSection() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(
                caseSection("Overall"),
                caseSection("Difference in 7 Days")
        );
        hBox.getStyleClass().add("section");

        return hBox;
    }

    /**
     * Summary section.
     */
    public Pane summarySection() {
        VBox main = new VBox() {{
            getStyleClass().add("section");
        }};

        main.getChildren().addAll(
                sectionTitle("Summary"),
                summaryDataSection()
        );

        return main;
    }

    /**
     * {@code HBox} containing the data in the filter section.
     */
    public HBox filterDataSection() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(
                caseSection("Overall"),
                caseSection("Per 100K residents")
        );
        hBox.getStyleClass().add("section");

        return hBox;
    }

    /**
     * A {@code Pane} containing the input section of the filter section.
     */
    public Pane filterPrompt() {
        GridPane pane = generateHGridPane(new TextField() {{
            setPromptText("Input filter query...");
        }});
        pane.getStyleClass().add("section");

        return pane;
    }

    /**
     * Filter section.
     */
    public Pane filterSection() {
        VBox main = new VBox() {{
            getStyleClass().add("section");
        }};

        main.getChildren().addAll(
                sectionTitle("Filtered"),
                filterPrompt(),
                filterDataSection()
        );

        return main;
    }

    /**
     * Constructed GUI layout.
     */
    public BorderPane layout() {
        BorderPane main = new BorderPane();

        // Main part
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                summarySection(),
                filterSection(),
                ChartMaker.sampleChart(),
                TableMaker.sampleTable()
        );

        // Status bar
        main.setCenter(vBox);
        main.setBottom(bottomPart());

        return main;
    }
}
