import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 300;
    private static final String APP_TITLE = "Hello World!";

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}