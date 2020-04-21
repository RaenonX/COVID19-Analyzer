package Application;

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
    // first half of the interface (blue)
    VBox v1 = new VBox(); 
    Region vregion1 = new Region();
    VBox.setVgrow(vregion1, Priority.ALWAYS);
    
    /* to h11 */
    Label title = new Label("COVID-19 Analyzer");
    title.setStyle("-fx-text-fill: black; -fx-font-size: 13;");
    Button b1_doc = new Button("Doc");
    b1_doc.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");
    Button b2_export = new Button("Export");
    b2_export.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-size: 14;");
    Button b3_exit = new Button("X");
    b3_exit.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14;");

    // inner part of the first half
    // first inner part
    HBox h11 = new HBox(); // (light blue)
    Region region1 = new Region();
    HBox.setHgrow(region1, Priority.ALWAYS);
    h11.getChildren().addAll(title, region1, b1_doc, b2_export, b3_exit);
    h11.setPadding(new Insets(10, 10, 10, 10));

    /* end of h11 */
    HBox h115 = new HBox();
    Label label115 = new Label("##Summary"); // header 1
    label115.setStyle("-fx-text-fill:purple; -fx-font-size: 24;");
    h115.getChildren().addAll(label115);
    h115.setPadding(new Insets(0,10,0,10));

    
    /* h12 */

    // header
    
    Label label121 = new Label("Full Time"); // header 1
    label121.setStyle("-fx-text-fill: black; -fx-font-size: 13;");
    label121.setPadding(new Insets(2, 2, 2, 2));
    Label label122 = new Label("Compare to these 7 days period (Mar 29th ~ Apr 5th)"); // header 2
    label122.setStyle("-fx-text-fill: black; -fx-font-size: 13;");
    label122.setPadding(new Insets(2, 2, 2, 2));

    // labels setting for [confirmed, recovered, death]
    String confirmed_style = "-fx-text-alignment: left; -fx-font-size: 20; -fx-text-fill: navy;";
    String recovered_style = "-fx-text-alignment: left; -fx-font-size: 20; -fx-text-fill: green;";
    String death_style = "-fx-text-alignment: left; -fx-font-size: 20; -fx-text-fill: red;";

    String confirmed_data = "-fx-text-alignment: center; -fx-font-size: 16; -fx-text-fill: navy;";
    String recovered_data = "-fx-text-alignment: center; -fx-font-size: 16; -fx-text-fill: green;";
    String death_data = "-fx-text-alignment: center; -fx-font-size: 16; -fx-text-fill: red;";

    // creating Label Objects and settings for Labels
    Label left_text1 = new Label("Confirmed");
    left_text1.setStyle(confirmed_style);
    left_text1.setPadding(new Insets(5, 5, 5, 5));
    Label left_text1d = new Label("data_field");
    left_text1d.setStyle(confirmed_data); // TODO: need summary data here
    left_text1d.setPadding(new Insets(5, 5, 5, 5));
    // left_text1d.setAlignment(Pos.CENTER);


    Label left_text2 = new Label("Recovered");
    left_text2.setStyle(recovered_style);
    left_text2.setPadding(new Insets(5, 5, 5, 5));
    Label left_text2d = new Label("data_field");
    left_text2d.setStyle(recovered_data);
    left_text2d.setPadding(new Insets(5, 5, 5, 5));
    left_text2d.setAlignment(Pos.CENTER);


    Label left_text3 = new Label("Death");
    left_text3.setStyle(death_style);
    left_text3.setPadding(new Insets(5, 5, 5, 5));
    Label left_text3d = new Label("data_field");
    left_text3d.setStyle(death_data);
    left_text3d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
    left_text3d.setAlignment(Pos.CENTER);


    Label right_text1 = new Label("Confirmed");
    right_text1.setStyle(confirmed_style);
    right_text1.setPadding(new Insets(5, 5, 5, 5));
    Label right_text1d = new Label("data_field");
    right_text1d.setPadding(new Insets(5, 5, 5, 5));
    right_text1d.setStyle(confirmed_data); // TODO: need summary data here
    right_text1d.setAlignment(Pos.CENTER);


    Label right_text2 = new Label("Recovered");
    right_text2.setStyle(recovered_style);
    right_text2.setPadding(new Insets(5, 5, 5, 5));
    Label right_text2d = new Label("data_field");
    right_text2d.setStyle(recovered_data);
    right_text2d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
    right_text2d.setAlignment(Pos.CENTER);

    Label right_text3 = new Label("Death");
    right_text3.setStyle(death_style);
    right_text3.setPadding(new Insets(5, 5, 5, 5));
    Label right_text3d = new Label("data_field");
    right_text3d.setStyle(death_data);
    right_text3d.setPadding(new Insets(5, 5, 5, 5));// TODO: need summary data here
    right_text3d.setAlignment(Pos.CENTER);


    // creating HBoxes that stores VBoxes
    HBox h121_left = new HBox();
    VBox confirmed_left = new VBox(left_text1, left_text1d);
    confirmed_left.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
    confirmed_left.setPrefWidth(WINDOW_WIDTH / 6);
    confirmed_left.setAlignment(Pos.CENTER);

    VBox recovered_left = new VBox(left_text2, left_text2d);
    recovered_left.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
    recovered_left.setPrefWidth(WINDOW_WIDTH / 6);
    recovered_left.setAlignment(Pos.CENTER);

    VBox death_left = new VBox(left_text3, left_text3d);
    death_left.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
    death_left.setPrefWidth(WINDOW_WIDTH / 6);
    death_left.setAlignment(Pos.CENTER);
    
    h121_left.getChildren().addAll(confirmed_left, recovered_left, death_left);


    HBox h121_right = new HBox();
    VBox confirmed_right = new VBox(right_text1, right_text1d);
    confirmed_right.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
    confirmed_right.setPrefWidth(WINDOW_WIDTH / 6);
    confirmed_right.setAlignment(Pos.CENTER);

    VBox recovered_right = new VBox(right_text2, right_text2d);
    recovered_right.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
    recovered_right.setPrefWidth(WINDOW_WIDTH / 6);
    recovered_right.setAlignment(Pos.CENTER);

    VBox death_right = new VBox(right_text3, right_text3d);
    death_right.setStyle("-fx-border-color:lightgray; -fx-border-width:2");
    death_right.setPrefWidth(WINDOW_WIDTH / 6);
    death_right.setAlignment(Pos.CENTER);

    h121_right.getChildren().addAll(confirmed_right, recovered_right, death_right);


    
    
    
    
    
    
    // second inner part
    HBox h12 = new HBox(); // (light blue)
    VBox v121 = new VBox(); // (orange)
    v121.getChildren().addAll(new VBox(label121, h121_left));
    VBox v122 = new VBox(); // (orange)
    v122.getChildren().addAll(new VBox(label122, h121_right));
    VBox separate = new VBox();
    separate.setPadding(new Insets(10,4, 10, 4));
    h12.getChildren().addAll(v121,separate,v122);
    h12.setPadding(new Insets(10, 10, 10, 10));
    
   //Separate line
    HBox empty = new HBox();
    Line line = new Line();
    line.setStartX(0);
    line.setEndX(WINDOW_WIDTH);
    line.setStyle("-fx-stroke: lightgray");
    empty.getChildren().add(line);

    
    
    HBox h205 = new HBox();
    Label label205 = new Label("##Filter"); 
    label205.setStyle("-fx-text-fill:purple; -fx-font-size: 24;");
    h205.getChildren().addAll(label205);
    h205.setPadding(new Insets(0,10,0,10));
    
    
    


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
    separate2.setPadding(new Insets(8,5, 8, 5));
    
    h22.getChildren().addAll(v221,separate2,v222);
    h22.setPadding(new Insets(8,8,8,8));

    v1.getChildren().addAll(h11, h115,h12, empty,h205, h21, h22);

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
