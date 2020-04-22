import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "COVID-19 Analyzer (CS400 / AT87)";

    private static DataHolder mainData;

    private void loadFile() throws IOException {
        StateNameConverter converter = new StateNameConverter(".res/data/usstates.csv");
        PopulationDataParser.loadUsPopFile(".res/data/uspops.csv", converter);

        mainData = DataHolder.parseFile(".res/data/data.csv");
    }

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

    /**
     * Configure the primary stage.
     */
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
    public void start(Stage primaryStage) throws IOException {
        loadFile(); // TODO: Handle the case where the file does not exist

        MainLayout ml = new MainLayout(primaryStage, WINDOW_WIDTH);

        configurePrimaryStage(new Scene(ml.layout(mainData)), primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}