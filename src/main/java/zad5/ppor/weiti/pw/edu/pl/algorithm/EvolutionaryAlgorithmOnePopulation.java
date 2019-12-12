package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.function.Function;


public class EvolutionaryAlgorithmOnePopulation {

    private final int numOfThreedsToFork;

    public final Function<double[],Double> functionToOptimize;

    public EvolutionaryAlgorithmOnePopulation(int numOfThreedsToFork, Function<double[], Double> functionToOptimize) {
        this.numOfThreedsToFork = numOfThreedsToFork;
        this.functionToOptimize = functionToOptimize;
    }

    public Genotype findBestGenotype(final Population population, double epsilon) {

        Population temp = population;
        double result;
        int iterations = 0;
        temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp,functionToOptimize,numOfThreedsToFork);
        result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
        System.out.println(result);
        while (Math.abs(result) > epsilon) {
            iterations++;
            temp = EvolutionaryAlgorithmSteps.Static.reproduction(temp,numOfThreedsToFork);
            temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp,functionToOptimize,numOfThreedsToFork);
            temp = EvolutionaryAlgorithmSteps.Static.selectionWithThreeds(temp,population.getPopulation().size(),numOfThreedsToFork);
            result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
            System.out.println(result);
        }
        System.out.println("Iterations: " + iterations);
        return EvolutionaryAlgorithmSteps.Static.bestGenotype(temp);
    }
}
