package zad5.ppor.weiti.pw.edu.pl.algorithm;

import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class EvolutionaryAlgorithmOnePopulation {

    public final Function<double[], Double> functionToOptimize;
    private final int numOfThreedsToFork;

    public EvolutionaryAlgorithmOnePopulation(int numOfThreedsToFork, Function<double[], Double> functionToOptimize) {
        this.numOfThreedsToFork = numOfThreedsToFork;
        this.functionToOptimize = functionToOptimize;
    }

    public Genotype findBestGenotype(final Population population, double epsilon, long seconds) {

        Population temp = population;
        double result;
        int iterations = 0;
        temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp, functionToOptimize, numOfThreedsToFork);
        result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
        if(Constants.Presentation.MODE) System.out.println(result);

        double[] x;
        double[] y;
        if (Constants.Presentation.MODE) {
            x = IntStream.range(0, population.getPopulation().size())
                    .mapToDouble(i -> (double) i)
                    .toArray();
            EvolutionaryAlgorithmSteps.Static.showPlot(temp, x);
            try {
                System.out.println("press key");
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        long startTime = System.nanoTime();

        while (Math.abs(result) > epsilon) {
            if (Constants.Presentation.MODE) EvolutionaryAlgorithmSteps.Static.showPlot(temp, x);
            if((System.nanoTime()-startTime)> seconds*Constants.Algorothm.NANOSEC_PER_SEC) break;


            iterations++;
            temp = EvolutionaryAlgorithmSteps.Static.reproduction(temp, numOfThreedsToFork);
            temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp, functionToOptimize, numOfThreedsToFork);
            temp = EvolutionaryAlgorithmSteps.Static.selectionWithThreeds(temp, population.getPopulation().size(), numOfThreedsToFork);
            result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
            if(Constants.Presentation.MODE)System.out.println(result);
        }
        return EvolutionaryAlgorithmSteps.Static.bestGenotype(temp);
    }

}
