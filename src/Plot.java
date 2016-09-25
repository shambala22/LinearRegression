/**
 * Created by shambala on 25/09/16.
 */

import com.xeiam.xchart.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ilya239 on 17.09.2016.
 */
class Plot {

    private Chart chart;

    Plot(String XAsis, String YAsis) {
        chart = new ChartBuilder().width(1280).height(960).build();
        chart.getStyleManager().setChartType(StyleManager.ChartType.Scatter);
        chart.setXAxisTitle(XAsis);
        chart.setYAxisTitle(YAsis);
    }

    Plot addGraphic(List<Flat> points, String graphicName) {
        if (!points.isEmpty()) {
            List<Double> xData = new LinkedList<>();
            List<Double> yData = new LinkedList<>();
            for (Flat point : points) {
                xData.add((double) point.area);
                yData.add((double) point.price);
            }
            chart.addSeries(graphicName, xData, yData).setLineColor(new Color(255, 255, 255, 0)).setMarker(SeriesMarker.CIRCLE);
        }
        return this;
    }

    Plot addLine(Flat begin, Flat end, String lineName) {
        List<Double> xData = new LinkedList<>();
        List<Double> yData = new LinkedList<>();
        xData.add(begin.area);
        xData.add(end.area);
        yData.add(begin.price);
        yData.add(end.price);
        chart.addSeries(lineName, xData, yData).setMarker(SeriesMarker.CIRCLE);
        return this;
    }

    void show() {
        new SwingWrapper(chart).displayChart();
    }
}
