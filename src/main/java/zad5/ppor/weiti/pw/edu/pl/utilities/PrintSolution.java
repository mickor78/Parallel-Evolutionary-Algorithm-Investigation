package zad5.ppor.weiti.pw.edu.pl.utilities;

import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import zad5.ppor.weiti.pw.edu.pl.model.Genotype;
import zad5.ppor.weiti.pw.edu.pl.model.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrintSolution {
    public final void showPlot(Population temp, double[] x) {
        double[] y;
        y = ArrayUtils.toPrimitive(temp.getPopulation()
                .stream()
                .map(Genotype::getRate)
                .collect(Collectors.toList())
                .toArray(new Double[]{}));

        XYChart chart;
        chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", x, y);
        new SwingWrapper(chart).displayChart();

    }

    public final void showPlotForParallel(List<Population> populationsList) {
        Population temp = new Population (new ArrayList<Genotype>(populationsList
                .stream()
                .map(Population::getPopulation)
                .flatMap(ArrayList::stream)
                .collect(Collectors.toList()))
        );
        double [] x = IntStream.range(0, temp.getPopulation().size())
                .mapToDouble(i -> (double) i)
                .toArray();
        showPlot(temp,x);
    }
}
