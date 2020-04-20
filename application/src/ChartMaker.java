import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartMaker {
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
