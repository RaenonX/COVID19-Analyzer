import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A class represents a data chart GUI element.
 *
 * @param <T> class to be used for this {@code DataChartGUI}.
 */
public class DataChartGUI<T extends IGUIChartSeriesCollection<String, Number>> implements IGuiUnit {
    private final LineChart<String, Number> chart;
    private List<ChartSeriesData<String, Number>> chartSeries;

    public DataChartGUI(T defaultData) {
        this.chart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        this.chart.setCreateSymbols(false);
        this.chart.setLegendVisible(false);
        this.chart.setAnimated(false);
        updateChartData(defaultData);
    }

    /**
     * Update the chart's data
     *
     * @param data data to update
     */
    public void updateChartData(T data) {
        chartSeries = data.getSeriesCollection();

        chart.getData().clear();
        chartSeries.forEach(series -> series.applyChartSeries(chart));
    }

    public HBox legendHBox() {
        return new HBox() {{
            setSpacing(3);
            getChildren().addAll(
                    chartSeries
                            .stream()
                            .map(chartSeries -> {
                                Button btn = new Button(chartSeries.getSeriesName());

                                btn.setMaxWidth(Double.MAX_VALUE);
                                btn.getStyleClass().add(chartSeries.getStyleIdentifier());
                                btn.setOnAction(e -> chartSeries.toggleChartSeries(chart));

                                HBox.setHgrow(btn, Priority.ALWAYS);

                                return btn;
                            })
                            .collect(Collectors.toList()));
        }};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pane getGuiElement() {
        return new BorderPane() {{
            setCenter(chart);
            setBottom(legendHBox());
        }};
    }
}
