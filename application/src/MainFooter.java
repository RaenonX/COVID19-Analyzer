import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class MainFooter implements IGuiUnit {
    private static final String DEFAULT_STATUS_MSG = "Ready";

    // region GUI elements
    private final HBox box;
    private final Label status;

    private final Supplier<DataHolder> fnGetHolder;
    // endregion

    /**
     * Flag to indicate if the export dialog has been called
     * to display to prevent the user from opening multiple export dialogs.
     */
    private boolean exportDialogOpened;

    public MainFooter(Stage mainStage, Supplier<DataHolder> fnGetHolder) {
        this.fnGetHolder = fnGetHolder;

        this.box = new HBox() {{
            getStyleClass().add("section");
        }};

        this.status = new Label() {{
            setId("status");
        }};
        updateStatus(DEFAULT_STATUS_MSG);

        Button b1_doc = new Button("Filter Syntax Manual") {{
            setId("doc");
            setOnAction(e -> FilterSyntaxDocGUI.documentationPopup(mainStage).show());
        }};
        Button b2_export = new Button("Export Result") {{
            setId("export");
            setOnAction(e -> onExportClicked());
        }};

        this.box.getChildren().addAll(status, Utils.growRegion, b1_doc, b2_export);
    }

    // region On event
    /**
     * Function to be called when the export button is clicked.
     */
    private void onExportClicked() {
        if (!exportDialogOpened) {
            Stage stage = new Stage();
            stage.setOnHiding(event -> exportDialogOpened = false);

            LayoutBase layoutBase = new ExportPreviewLayout(
                    stage, "Export Preview", 1000, 600, fnGetHolder.get().summaryString());

            layoutBase.applyAndShow();
            exportDialogOpened = true;
        }
    }
    // endregion

    /**
     * Update the status.
     *
     * @param status status string to replace
     */
    public void updateStatus(String status) {
        this.status.setText(
                String.format("%s: %s",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),
                        status));
    }

    @Override
    public Pane getGuiElement() {
        return box;
    }
}
