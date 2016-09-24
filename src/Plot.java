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
        chart.getStyleManager().setLegendPosition(StyleManager.LegendPosition.InsideNW);
        chart.setXAxisTitle(XAsis);
        chart.setYAxisTitle(YAsis);
    }

    Plot addGraphic(List<Flat> points, String graphicName) {
        if (!points.isEmpty()) {
            List<Double> xData = new LinkedList<>();
            List<Double> yData = new LinkedList<>();
            for (Flat point : points) {
                xData.add((double)point.area);
                yData.add((double)point.price);
            }
            chart.addSeries(graphicName, xData, yData).setLineColor(new Color(255, 255, 255, 0)).setMarker(SeriesMarker.CIRCLE);
        }
        return this;
    }

    void show() {
        new SwingWrapper(chart).displayChart();
    }
}
