package sdd.mapoverlay.frontend.Components;

import java.net.URI;
import java.net.URL;
import java.util.Set;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class CustomLineChart extends LineChart<Number, Number> {
    private XYChart.Series<Number, Number> sweeplineSeries;
    private XYChart.Series<Number, Number> intersectionSeries;

    public CustomLineChart() {
        super(new NumberAxis(), new NumberAxis());
        this.setCreateSymbols(true);
        this.setLegendVisible(false);
        URL cssPath = getClass().getResource("LineChart.css");
        getStylesheets().addAll(cssPath.toString());
        this.sweeplineSeries = new XYChart.Series<>();
        this.intersectionSeries = new XYChart.Series<>();

        // End point at
        // the top
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
        sweeplineSeries.getData().clear();
        intersectionSeries.getData().clear();

    }

    public void deactivateSweepline() {
        getData().remove(sweeplineSeries);
    }

    public void activateSweepline(double maxX, double maxY) {
        getData().remove(sweeplineSeries);
        sweeplineSeries.getData().clear();
        XYChart.Data<Number, Number> start = new XYChart.Data<>(0, maxY + 5); // Start point at the top

        XYChart.Data<Number, Number> end = new XYChart.Data<>(maxX + 5, maxY + 5);

        sweeplineSeries.getData().addAll(start, end); // Start point at the top
        // left

        getData().add(sweeplineSeries);
        start.getNode().getStyleClass().add("sweepline-point");
        end.getNode().getStyleClass().add("sweepline-point");
        sweeplineSeries.getNode().getStyleClass().add("sweepline-series");

    }

    public void playSweepAnimation() {
        // Duration of the animation
        Duration duration = Duration.millis(5000);

        // Create TranslateTransitions for the sweepline and its data points
        TranslateTransition sweeplineTransition = new TranslateTransition(duration, sweeplineSeries.getNode());
        TranslateTransition startTransition = new TranslateTransition(duration,
                sweeplineSeries.getData().get(0).getNode());
        TranslateTransition endTransition = new TranslateTransition(duration,
                sweeplineSeries.getData().get(1).getNode());

        // Set the target Y coordinates for the sweepline and its data points
        double targetY = getHeight(); // Move to the bottom of the chart
        sweeplineTransition.setToY(targetY);
        startTransition.setToY(targetY);
        endTransition.setToY(targetY);

        // Play the transitions
        sweeplineTransition.play();
        startTransition.play();
        endTransition.play();

    }

    public void showIntersection(double x, double y) {
        this.intersectionSeries = new XYChart.Series<>();
        XYChart.Data<Number, Number> point = new XYChart.Data<>(x, y);
        intersectionSeries.getData().add(point);
        getData().add(intersectionSeries);

    }

}
