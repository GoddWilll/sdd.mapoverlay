package sdd.mapoverlay.frontend.Components;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CustomLineChart extends LineChart<Number, Number> {

    public CustomLineChart() {
        super(new NumberAxis(), new NumberAxis());

    }

    public void addSeries(double[] segment) {
        this.setAnimated(true);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(segment[0], segment[1]));
        series.getData().add(new XYChart.Data<>(segment[2], segment[3]));

        getData().add(series);
    }

    public void clearLineChart() {
        this.setAnimated(false);
        getData().clear();

    }

}
