import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 300;
    private static final String APP_TITLE = "COVID-19 Analyzer";

    private void testChart(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        ArrayList<Number> dates = new ArrayList<>();
        dates.add(1);
        dates.add(3);
        dates.add(5);

        ArrayList<Number> cases = new ArrayList<>();
        cases.add(1);
        cases.add(43);
        cases.add(32);

        XYChart.Series<Number, Number> s1 = ChartMaker.makeSeries(dates,cases);
        s1.setName("Data Series 1");

        ArrayList<XYChart.Series<Number,Number>> seriesList = new ArrayList<>();
        seriesList.add(s1);
        LineChart<Number, Number> chart = ChartMaker.makeChart(seriesList);

        root.setCenter(chart);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
    }

    private void testTable(Stage primaryStage) {
        TempDataHolder tmc = new TempDataHolder();
        primaryStage.setScene(new Scene(new BorderPane(TableMaker.makeTable(tmc)), WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    @Override
    public void start(Stage primaryStage) {
        // Test Chart
        // testChart(primaryStage);

        // Test Table
        // testTable(primaryStage);

        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}