package sdd.mapoverlay.frontend.Components;

import java.net.URI;
import java.net.URL;
import java.util.Set;

import javafx.animation.TranslateTransition;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

public class CustomLineChart extends LineChart<Number, Number> {
    private XYChart.Series<Number, Number> sweeplineSeries;

    public CustomLineChart() {
        super(new NumberAxis(), new NumberAxis());
        this.setCreateSymbols(true);
        this.setLegendVisible(false);
        URL cssPath = getClass().getResource("LineChart.css");
        getStylesheets().addAll(cssPath.toString());
        sweeplineSeries = new XYChart.Series<>();
        sweeplineSeries.getData().add(new XYChart.Data<>(0, 0)); // Start point at the top left
        sweeplineSeries.getData().add(new XYChart.Data<>(getBoundsInLocal().getWidth(), 0)); // End point at the top
                                                                                             // right

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

    public void activateSweepline() {
        getData().add(sweeplineSeries);
        TranslateTransition showTransition = new TranslateTransition(Duration.millis(1000),
                this.sweeplineSeries.getNode());
        showTransition.setFromY(-getBoundsInLocal().getHeight()); // Move from the left
        showTransition.setToY(0); // Move to the bottom of the chart

        showTransition.play();
    }

}
