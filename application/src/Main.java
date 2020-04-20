import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 300;
    private static final String APP_TITLE = "LINE GRAPH";

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        // Test Chart
 /*
        Number date1 = 1;
        Number date2 = 3;
        Number date3 = 5;

        ArrayList<Number> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        dates.add(date3);

        ArrayList<Number> cases = new ArrayList<>();
        cases.add(1);
        cases.add(43);
        cases.add(32);


        XYChart.Series<Number, Number> s1 = graph.makeSeries(dates,cases);
        s1.setName("Data Series 1");

        ArrayList<XYChart.Series<Number,Number>> seriesList = new ArrayList<>();
        seriesList.add(s1);
        LineChart<Number, Number> chart = graph.makeChart(seriesList);

        primaryStage.setTitle(APP_TITLE);
        root.setCenter(chart);

		Scene scene = new Scene(root,200,100);
		primaryStage.setScene(scene);
		*/

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