import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Interface to be able to use the class for GUI chart.
 *
 * @param <X> X axis type
 * @param <Y> Y axis type
 */
public interface IGUIChartSeries<X, Y> {
    /**
     * Get the series for GUI chart.
     *
     * @return series for GUI chart
     */
    List<XYChart.Series<X, Y>> getSeries();
}
