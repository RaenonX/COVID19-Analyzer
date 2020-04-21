import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Filter extends Application {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "COVID-19 Analyzer";

    @Override
    public void start(Stage primaryStage) {


        //Separate line
        HBox empty = new HBox();
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(WINDOW_WIDTH);
        line.setStyle("-fx-stroke: lightgray");
        empty.getChildren().add(line);


        HBox h205 = new HBox();
        Label label205 = new Label("Filter");
        label205.setStyle("-fx-text-fill:purple; -fx-font-size: 24;");
        h205.getChildren().addAll(label205);
        h205.setPadding(new Insets(0, 10, 0, 10));


        // Textfield
        TextField name = new TextField();
        name.setPromptText("Filter syntx here:");

        // Filter Boutton
        Button button = new Button("Filter");
        button.setStyle("-fx-background-color:teal");

        HBox h21 = new HBox();
        VBox filter = new VBox(name);
        filter.setPrefWidth(WINDOW_WIDTH * 0.94);
        VBox filter_button = new VBox(button);
        filter_button.setPrefWidth(WINDOW_WIDTH * 0.04);
        h21.getChildren().addAll(filter, filter_button);
        h21.setPadding(new Insets(10, 10, 0, 10));


        String confirmed_style = "-fx-text-alignment: left; -fx-font-size: 20; -fx-text-fill: navy;";
        String recovered_style = "-fx-text-alignment: left; -fx-font-size: 20; -fx-text-fill: green;";
        String death_style = "-fx-text-alignment: left; -fx-font-size: 20; -fx-text-fill: red;";

        String confirmed_data = "-fx-text-alignment: center; -fx-font-size: 16; -fx-text-fill: navy;";
        String recovered_data = "-fx-text-alignment: center; -fx-font-size: 16; -fx-text-fill: green;";
        String death_data = "-fx-text-alignment: center; -fx-font-size: 16; -fx-text-fill: red;";

        // creating HBoxes that stores VBoxes
        HBox h221_left = new HBox();

        // label 1 after filter
        Label lf1 = new Label("Confirmed");
        lf1.setStyle(confirmed_style);
        lf1.setPadding(new Insets(5, 5, 5, 5));
        Label lf1d = new Label("----");
        lf1d.setStyle(confirmed_data);
        lf1d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
        lf1d.setAlignment(Pos.CENTER);
        VBox confirmed_left2 = new VBox(lf1, lf1d);
        confirmed_left2.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
        confirmed_left2.setPrefWidth(WINDOW_WIDTH / 6);
        confirmed_left2.setAlignment(Pos.CENTER);

        // label 2 after filter
        Label lf2 = new Label("Recovered");
        lf2.setStyle(recovered_style);
        lf2.setPadding(new Insets(5, 5, 5, 5));
        Label lf2d = new Label("----");
        lf2d.setStyle(recovered_data);
        lf2d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
        lf2d.setAlignment(Pos.CENTER);
        VBox recovered_left2 = new VBox(lf2, lf2d);
        recovered_left2.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
        recovered_left2.setPrefWidth(WINDOW_WIDTH / 6);
        recovered_left2.setAlignment(Pos.CENTER);

        // label 3 after filter
        Label lf3 = new Label("Death");
        lf3.setStyle(death_style);
        lf3.setPadding(new Insets(5, 5, 5, 5));
        Label lf3d = new Label("----");
        lf3d.setStyle(death_data); // TODO: need summary data here
        lf3d.setPadding(new Insets(5, 5, 5, 5));
        lf3d.setAlignment(Pos.CENTER);
        VBox death_left2 = new VBox(lf3, lf3d);
        death_left2.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
        death_left2.setPrefWidth(WINDOW_WIDTH / 6);
        death_left2.setAlignment(Pos.CENTER);

        h221_left.getChildren().addAll(confirmed_left2, recovered_left2, death_left2);

        HBox h221_right = new HBox();
        // label 4 after filter
        Label lf4 = new Label("Confirmed/100K");
        lf4.setStyle(confirmed_style);
        lf4.setPadding(new Insets(5, 5, 5, 5));
        Label lf4d = new Label("----");
        lf4d.setStyle(confirmed_data); // TODO: need summary data here
        lf4d.setPadding(new Insets(5, 5, 5, 5));
        lf4d.setAlignment(Pos.CENTER);
        VBox confirmed_right2 = new VBox(lf4, lf4d);
        confirmed_right2.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
        confirmed_right2.setPrefWidth(WINDOW_WIDTH / 6);
        confirmed_right2.setAlignment(Pos.CENTER);

        // label 5 after filter
        Label lf5 = new Label("Recovered/100K");
        lf5.setStyle(recovered_style);
        lf5.setPadding(new Insets(5, 5, 5, 5));
        Label lf5d = new Label("----");
        lf5d.setStyle(recovered_data);
        lf5d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
        lf5d.setAlignment(Pos.CENTER);
        VBox recovered_right2 = new VBox(lf5, lf5d);
        recovered_right2.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
        recovered_right2.setPrefWidth(WINDOW_WIDTH / 6);
        recovered_right2.setAlignment(Pos.CENTER);

        // label 6 after filter
        Label lf6 = new Label("Death/100K");
        lf6.setStyle(death_style);
        lf6.setPadding(new Insets(5, 5, 5, 5));
        Label lf6d = new Label("----");
        lf6d.setStyle(death_data);
        lf6d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
        lf6d.setAlignment(Pos.CENTER);

        VBox death_right2 = new VBox(lf6, lf6d);
        death_right2.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
        death_right2.setPrefWidth(WINDOW_WIDTH / 6);
        death_right2.setAlignment(Pos.CENTER);
        h221_right.getChildren().addAll(confirmed_right2, recovered_right2, death_right2);


        // HBox for filter data
        HBox h22 = new HBox();
        VBox v221 = new VBox();
        v221.getChildren().addAll(new VBox(h221_left));
        VBox v222 = new VBox();
        v222.getChildren().addAll(new VBox(h221_right));

        VBox separate2 = new VBox();
        separate2.setPadding(new Insets(8, 5, 8, 5));

        h22.getChildren().addAll(v221, separate2, v222);
        h22.setPadding(new Insets(8, 8, 8, 8));

        VBox v1 = new VBox();
        VBox.setVgrow(new Region(), Priority.ALWAYS);
        v1.getChildren().addAll(empty, h205, h21, h22);

        // BoarderPane that has v1 at top and v2 at center
        BorderPane root = new BorderPane();
        root.setTop(v1);


        /* end of Layout Manager */


        // place layout manager in scenes
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle(APP_TITLE);

        // place scenes in stages
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
