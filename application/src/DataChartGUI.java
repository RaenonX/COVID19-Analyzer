import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * A class represents a data chart GUI element.
 *
 * @param <T> class to be used for this {@code DataChartGUI}.
 */
public class DataChartGUI<T extends IGUIChartSeries<String, Number>> implements IGuiUnit {
    private final LineChart<String, Number> chart;

    public DataChartGUI(T defaultData) {
        this.chart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        this.chart.setCreateSymbols(false);
        updateChartData(defaultData);
    }

    /**
     * Update the chart's data
     *
     * @param data data to update
     */
    public void updateChartData(T data) {
        chart.getData().clear();
        chart.getData().addAll(data.getSeries());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getGuiElement() {
        return new BorderPane() {{ setCenter(chart); }};
    }
}
