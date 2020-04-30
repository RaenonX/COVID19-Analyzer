import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final String APP_TITLE = "COVID-19 Analyzer (CS400 / AT87)";

    private static DataHolder mainData;

    /**
     * Load the necessary data files.
     *
     * @param config config object of the application
     * @throws IOException thrown if any of the resource file does not exist
     */
    private void loadFile(Config config) throws IOException {
                StateNameConverter converter = new StateNameConverter(config.getUsStatesPath());
        PopulationDataParser.loadUsPopFile(config.getUsPopsPath(), converter);

        try {
            mainData = DataHolder.parseFile(config.getDataPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Config config = Config.parse(Config.PATH_CONFIG);

        try {
            loadFile(config);
            new MainLayout(primaryStage, config, APP_TITLE, 1500, 800, mainData).applyAndShow();
        } catch (IOException e) {
            FileLoadFailedLayout.displayAlert(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}