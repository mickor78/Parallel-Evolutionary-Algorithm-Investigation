package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.model.Genotype;
import zad5.ppor.weiti.pw.edu.pl.model.Population;
import zad5.ppor.weiti.pw.edu.pl.utilities.PrintSolution;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.IntStream;


public class EvolutionaryAlgorithmOnePopulation {

    private final Function<double[], Double> functionToOptimize;
    private final int numOfThreedsToFork;
    private final PrintSolution printer = new PrintSolution();


    public EvolutionaryAlgorithmOnePopulation(int numOfThreedsToFork, Function<double[], Double> functionToOptimize) {
        this.numOfThreedsToFork = numOfThreedsToFork;
        this.functionToOptimize = functionToOptimize;
    }

    public Genotype findBestGenotype(final Population population, double epsilon, long seconds) {

        Population temp = population;
        double result;
        int iterations = 0;
        EvolutionaryAlgorithmStepsStreamsImp algSteps = new EvolutionaryAlgorithmStepsStreamsImp();
        temp = algSteps.calculateFunction(temp, functionToOptimize, numOfThreedsToFork);
        result = 0 - algSteps.bestGenotype(temp).getRate();
        if(Constants.Presentation.MODE) System.out.println(result);

        double[] x;
        double[] y;
        if (Constants.Presentation.MODE) {
            x = IntStream.range(0, population.getPopulation().size())
                    .mapToDouble(i -> (double) i)
                    .toArray();
            printer.showPlot(temp, x);
            try {
                System.out.println("press key");
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        long startTime = System.nanoTime();

        while (Math.abs(result) > epsilon) {
            if (Constants.Presentation.MODE) printer.showPlot(temp, x);
            if((System.nanoTime()-startTime)> seconds*Constants.Algorothm.NANOSEC_PER_SEC) break;


            iterations++;
            temp = algSteps.reproduction(temp, numOfThreedsToFork);
            temp = algSteps.calculateFunction(temp, functionToOptimize, numOfThreedsToFork);
            temp = algSteps.selection(temp, population.getPopulation().size(), numOfThreedsToFork);
            result = 0 - algSteps.bestGenotype(temp).getRate();
            if(Constants.Presentation.MODE)System.out.println(result);
        }
        return algSteps.bestGenotype(temp);
    }

}
