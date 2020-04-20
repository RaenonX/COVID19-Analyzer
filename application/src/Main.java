import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 300;
    private static final String APP_TITLE = "Hello World!";

    @Override
    public void start(Stage primaryStage) {
        // Test Table
        /*
        TempDataHolder tmc = new TempDataHolder();
        primaryStage.setScene(new Scene(new BorderPane(TableMaker.makeTable(tmc)), WINDOW_WIDTH, WINDOW_HEIGHT));
         */

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}