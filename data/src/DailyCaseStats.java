import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A class which holds multiple {@code DailyCaseCounts}.
 */
public class DailyCaseStats implements IGUITableDataCollection<DailyCaseCounts>, IGUIChartSeriesCollection<String, Number> {
    private final List<DailyCaseCounts> caseCounts;

    public DailyCaseStats(List<DailyCaseCounts> caseCounts) {
        this.caseCounts = caseCounts.stream()
                .sorted(DailyCaseCounts.cmpDate)
                .collect(Collectors.toList());
    }

    /**
     * Get the latest {@code DailyCaseCount}.
     * Return {@code null} if no corresponding {@code DailyCaseCount}.
     *
     * @return latest {@code DailyCaseCount}
     */
    public DailyCaseCounts getLatest() {
        return this.caseCounts
                .stream()
                .max(DailyCaseCounts.cmpDate)
                .orElse(null);
    }

    /**
     * Get the {@code n}th-latest {@code DailyCaseCount}.
     * Return {@code null} if no corresponding {@code DailyCaseCount}.
     *
     * @param n days counting from the latest
     * @return {@code n}th-latest {@code DailyCaseCount}
     */
    public DailyCaseCounts getLatestCountsNdays(int n) {
        return this.caseCounts
                .stream()
                .sorted(DailyCaseCounts.cmpDateRev)
                .limit(n + 1)
                .min(DailyCaseCounts.cmpDate)
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DailyCaseCounts> getTableDataEntry() {
        return caseCounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTableColumns(TableView<DailyCaseCounts> table) {
        new TableColumn<DailyCaseCounts, LocalDate>("Date") {{
            setCellValueFactory(new PropertyValueFactory<>("date"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("Confirmed") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmed"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("+/-") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmedDiff"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("Fatal") {{
            setCellValueFactory(new PropertyValueFactory<>("fatal"));
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Integer>("+/-") {{
            setCellValueFactory(new PropertyValueFactory<>("fatalDiff"));
            table.getColumns().add(this);
        }};

        Callback<TableColumn<DailyCaseCounts, Double>, TableCell<DailyCaseCounts, Double>> factory =
                tc -> new TableCell<>() {
                    @Override
                    protected void updateItem(Double confirmed, boolean empty) {
                        super.updateItem(confirmed, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.format("%.2f", confirmed));
                        }
                    }
                };

        new TableColumn<DailyCaseCounts, Double>("Confirmed / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("confirmedPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        new TableColumn<DailyCaseCounts, Double>("Fatal / 100K") {{
            setCellValueFactory(new PropertyValueFactory<>("fatalPer100K"));
            setCellFactory(factory);
            table.getColumns().add(this);
        }};

        table.getColumns().forEach(x -> x.setReorderable(false));
    }

    /**
     * Make a chart series with the given name and a function to get the number for Y axis.
     *
     * @param name series name
     * @param styleClass style class to be applied to the chart series
     * @param numberFunction function to get the number for Y axis
     * @return a data-filled {@code XYChart.Series}
     */
    private ChartSeriesData<String, Number> makeSeries(
            String name, String styleClass, Function<DailyCaseCounts, Number> numberFunction) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // https://stackoverflow.com/a/47954566/11571888
        series.getData().addAll(caseCounts
                .stream()
                .map(x -> new XYChart.Data<>(x.getDate().toString(), numberFunction.apply(x)))
                .collect(Collectors.toList()));
        series.setName(name);

        return new ChartSeriesData<>(series, styleClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ChartSeriesData<String, Number>> getSeriesCollection() {
        List<ChartSeriesData<String, Number>> series = new ArrayList<>();

        series.add(makeSeries("Confirmed Case", "line-confirmed", DailyCaseCounts::getConfirmed));
        series.add(makeSeries("Fatal Case", "line-fatal", DailyCaseCounts::getFatal));
        series.add(makeSeries("Confirmed +/-", "line-confirmed-diff", DailyCaseCounts::getConfirmedDiff));
        series.add(makeSeries("Fatal +/-", "line-fatal-diff", DailyCaseCounts::getFatalDiff));
        series.add(makeSeries("Confirmed / 100K", "line-confirmed-100k", DailyCaseCounts::getConfirmedPer100K));
        series.add(makeSeries("Fatal / 100K", "line-fatal-100k", DailyCaseCounts::getFatalPer100K));

        return series;
    }
}
