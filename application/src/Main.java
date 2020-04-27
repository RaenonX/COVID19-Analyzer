import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final String APP_TITLE = "COVID-19 Analyzer (CS400 / AT87)";

    private static DataHolder mainData;

    /**
     * Load the necessary data files.
     *
     * @throws IOException thrown if any of the resource file does not exist
     */
    private void loadFile() throws IOException {
        StateNameConverter converter = new StateNameConverter(".res/data/usstates.csv");
        PopulationDataParser.loadUsPopFile(".res/data/uspops.csv", converter);

        try {
          mainData = DataHolder.parseFile(".res/data/data.csv");
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        
    }

    @Override
    public void start(Stage primaryStage) {
        LayoutBase ml;

        try {
            loadFile();
            ml = new MainLayout(primaryStage, APP_TITLE, 1500, 1000, mainData);
        } catch (IOException e) {
            ml = new FileLoadFailedLayout(primaryStage, APP_TITLE, 600, 600, e);
        } 
        ml.applyAndShow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}