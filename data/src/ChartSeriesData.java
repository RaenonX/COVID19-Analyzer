import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * A class holding the properties of a {@code XYChart.Series<>}
 * which would be applied after {@code XYChart.Chart<>} constructions.
 */
public class ChartSeriesData<X, Y> {
    private final String styleClass;
    private final XYChart.Series<X, Y> chartSeries;

    public ChartSeriesData(XYChart.Series<X, Y> chartSeries, String styleClass) {
        this.styleClass = styleClass;
        this.chartSeries = chartSeries;
    }

    /**
     * Apply this chart series to {@code chart} and set its style class.
     *
     * @param chart chart to apply this {@code ChartSeriesData}
     */
    public void applyChartSeries(LineChart<X, Y> chart) {
        XYChart.Series<X, Y> series = getChartSeries();
        chart.getData().add(series);
        series.getNode().setId(getStyleIdentifier());
    }

    /**
     * If {@code chart} does not contain this series, add it. Otherwise, remove it.
     *
     * @param chart chart to toggle this {@code ChartSeriesData}
     */
    public void toggleChartSeries(LineChart<X, Y> chart) {
        if (chart.getData().contains(getChartSeries())) {
            chart.getData().remove(getChartSeries());
        } else {
            applyChartSeries(chart);
        }
    }

    public XYChart.Series<X, Y> getChartSeries() {
        return chartSeries;
    }

    public String getStyleIdentifier() {
        return styleClass;
    }

    public String getSeriesName() {
        return this.chartSeries.getName();
    }
}
