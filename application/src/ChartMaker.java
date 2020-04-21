import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ChartMaker {
	/**
	 * A {@code BorderPane} which contains the sample chart GUI.
	 *
	 * @return prepared chart GUI element
	 */
	public static Pane sampleChart() {
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

		return root;
	}

	/**
	 * Creates a chart series.
	 *
	 * @param date date of the data (X Axis)
	 * @param cases cases number (Y Axis)
	 * @return series ready to be used in the chart
	 */
	public static XYChart.Series<Number,Number> makeSeries(List<Number> date, List<Number> cases) {
		XYChart.Series<Number,Number> series = new XYChart.Series<>();
		for(int i = 0; i < date.size();i++) {
			series.getData().add(new XYChart.Data<>(date.get(i), cases.get(i)));
		}
		return series;
	}

	/**
	 * Make a chart with the given data series.
	 *
	 * @param series series to be put into the chart
	 * @return {@code LineChart} ready to be used in the GUI
	 */
	public static LineChart<Number, Number> makeChart(List<XYChart.Series<Number, Number>> series) {
		NumberAxis xAxis = new NumberAxis(); 
		NumberAxis yAxis = new NumberAxis(); 
		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.getData().addAll(series);

		return lineChart;
	}
}
