import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "COVID-19 Analyzer (CS400 / AT87)";

    /**
     * Load the CSS style sheet file to the scene.
     */
    private void loadStylesheet(Scene scene) {
        String cssPath;
        try {
            cssPath = new File(".res/main.css").toURI().toURL().toString();
        } catch (MalformedURLException e) {
            System.out.println("Unable to load the css file.");
            return;
        }

        scene.getStylesheets().clear();
        scene.getStylesheets().add(cssPath);
    }

    private void configurePrimaryStage(Scene mainScene, Stage primaryStage) {
        loadStylesheet(mainScene);

        // Set properties
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setResizable(true);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.setScene(mainScene);
    }

    @Override
    public void start(Stage primaryStage) {
        MainLayout ml = new MainLayout(WINDOW_WIDTH);

        configurePrimaryStage(new Scene(ml.layout()), primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}