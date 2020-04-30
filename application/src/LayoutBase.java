import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public abstract class LayoutBase {
    /**
     * {@code Stage} for the layout to attach.
     */
    protected final Stage stage;
    protected final Config appConfig;
    protected final String title;
    protected final int width;
    protected final int height;
    protected final boolean resizable;

    /**
     * @param stage owner stage of the layout
     * @param appConfig application config object
     * @param title title of the layout
     * @param width width of the layout in pixels (px)
     * @param height height of the layout in pixels (px)
     * @param resizable if this layout should be resizable
     */
    protected LayoutBase(Stage stage, Config appConfig, String title, int width, int height, boolean resizable) {
        this.stage = stage;
        this.appConfig = appConfig;
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;
    }

    /**
     * Get the layout of the class.
     *
     * @return prepared GUI layout
     */
    abstract BorderPane layout();

    /**
     * Load the CSS style sheet file to the scene.
     *
     * Show a dialog if failed to load.
     */
    private void loadStylesheet(Scene scene) {
        String cssPath;
        try {
            cssPath = new File(appConfig.getStylePath()).toURI().toURL().toString();
        } catch (MalformedURLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("CSS Loading failed");
            alert.setHeaderText("Unable to find the CSS stylesheet file.");
            alert.setContentText(
                    "Unable to load the CSS stylesheet. " +
                    "Make sure that it is located in `.res/main.css`.");
            alert.show();
            return;
        }

        scene.getStylesheets().clear();
        scene.getStylesheets().add(cssPath);
    }

    /**
     * Apply the configurations and the layout to {@code stage}.
     */
    private void applyLayout() {
        Scene scene = new Scene(layout());
        loadStylesheet(scene);

        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setWidth(width);
        stage.setHeight(height);

        stage.setScene(scene);
    }

    /**
     * Apply the layout to {@code stage} and show it.
     */
    public void applyAndShow() {
        applyLayout();
        stage.show();
    }
}
