import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;


public class FilterSyntaxDocGUI {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;

    /**
     * Load the documentation HTML to a GUI element and return it.
     *
     * @return GUI element which contains the documentation of {@code FilterSyntax}
     */
    private static Node loadDocHtml() {
        // TODO: Handle the case where the doc html file does not exist

        String htmlPath;
        try {
            htmlPath = new File(".res/filterdoc.html").toURI().toURL().toString();
        } catch (MalformedURLException e) {
            return new Label("Unable to load the documentation file.");
        }

        WebView main = new WebView();
        main.getEngine().load(htmlPath);

        return main;
    }

    /**
     * A {@code Stage} which contains the documentation.
     *
     * @param ownerStage {@code Stage} which opens this popup
     * @return popup {@code Stage}
     */
    public static Stage documentationPopup(Stage ownerStage) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(ownerStage);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setCenter(loadDocHtml());

        Scene dialogScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        dialog.setTitle("Filter Syntax Documentation");
        dialog.setScene(dialogScene);

        return dialog;
    }
}
