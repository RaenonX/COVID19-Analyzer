
import java.util.ArrayList;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class graph {

	//final static CategoryAxis xAxis = new CategoryAxis();
	//final static NumberAxis yAxis = new NumberAxis();

	//final static LineChart<String,Number> lineChart = 
	//        new LineChart<String,Number>(xAxis,yAxis);

	public static XYChart.Series<Number,Number> makeSeries(ArrayList<Number> date, ArrayList<Number> cases) {

		XYChart.Series<Number,Number> series = new XYChart.Series<>();
		for(int i = 0; i < date.size();i++) {
			series.getData().add(new XYChart.Data<Number,Number>((Number)date.get(i), (Number)cases.get(i))); 
		}
		return series;

	}
	//make chart, take series into chart

	@SuppressWarnings("unchecked")
	public static LineChart<Number, Number> makeChart(ArrayList<XYChart.Series<Number,Number>> series) {
		NumberAxis xAxis = new NumberAxis(); 
		NumberAxis yAxis = new NumberAxis(); 
		LineChart<Number, Number> linechart = new LineChart<Number, Number>(xAxis, yAxis); 

		
		for(int i = 0; i < series.size();i++) {
			linechart.getData().addAll(series.get(i));
		}
		return linechart;
	}
}
