package zad5.ppor.weiti.pw.edu.pl.algorithm;

import javafx.util.Pair;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvolutionaryAlgorithmFewPopulations {

    public final Function<double[],Double> functionToOptimize;
    public final int numberOfThreedsToFork;

    public EvolutionaryAlgorithmFewPopulations(Function<double[], Double> functionToOptimize, int numberOfThreedsToFork) {
        this.functionToOptimize = functionToOptimize;
        this.numberOfThreedsToFork = numberOfThreedsToFork;
    }

    public Genotype findBestGenotype(final Population[] population, double epsilon) {
        double result = Double.POSITIVE_INFINITY;
        List<Population> populationsList = new ArrayList<>(Arrays.asList(population));
        boolean nextIteration = true;

        Random rnd = new Random();
        while (true) {

            populationsList =
                    useAlgorithmOnPopulations(epsilon, populationsList);

            nextIteration = populationsList.parallelStream()
                    .anyMatch(pop -> Math.abs(EvolutionaryAlgorithmSteps.Static.bestGenotype(pop).getRate()) < epsilon);
            if(nextIteration) break;

            final List<Population> tempPopulation2 = new ArrayList<>(populationsList);
            ForkJoinPool forkJoinPool = null;
            try {
                forkJoinPool = new ForkJoinPool(numberOfThreedsToFork);
                populationsList = forkJoinPool.submit(() ->
                        crossoverPopulations(tempPopulation2, rnd)
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            } finally {
                if (forkJoinPool != null) {
                    forkJoinPool.shutdown();
                }
            }

        }
        return populationsList.parallelStream()
                .map(EvolutionaryAlgorithmSteps.Static::bestGenotype)
                .sorted()
                .findFirst()
                .get();
    }

    private List<Population> useAlgorithmOnPopulations(double epsilon, final List<Population> populationsList) {
        return populationsList
                .parallelStream()
                .map(pop -> Static.doAlgorithm(pop, epsilon, functionToOptimize, numberOfThreedsToFork))
                .peek(pop ->
                        System.out.println(EvolutionaryAlgorithmSteps.Static.bestGenotype(pop).getRate())
                )
                .parallel()
                .collect(Collectors.toList());
    }

    private List<Population> crossoverPopulations(List<Population> populationsList, Random rnd) {
        int size = populationsList.size();
        final List<Population> temp = new ArrayList<>(populationsList);
        populationsList = IntStream.range(0, (int) (populationsList.size() * Constants.Algorothm.NUMBER_OF_POPULATION_TO_CROSSOVER))
                .parallel()
                .mapToObj(i -> {
                    int x = rnd.nextInt(size);
                    int y = rnd.nextInt(size);
                    while (y==x) y = rnd.nextInt(size);
                    return new Pair<>(x, y);
                })
                .map(pair -> EvolutionaryAlgorithmSteps.Static.crossoverPopulations(
                        temp.get(pair.getKey()),
                        temp.get(pair.getValue()))
                )
                .flatMap(Function.identity())
                .limit(size)
                .collect(Collectors.toList());
        return populationsList;
    }

    public static class Static {
        public static Population doAlgorithm(final Population population, double epsilon, Function<double[],Double> functionToOptimize, int numberOfThreedsToFork) {
            Population temp = population;
            int iterations = 0;
            temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp,functionToOptimize,numberOfThreedsToFork);
            double result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
            while (Math.abs(result) > epsilon) {
                iterations++;
                if (iterations % Constants.Algorothm.NUMBER_OF_ITERATION_BEFORE_CROSOVER == 0) { break; }
                temp = EvolutionaryAlgorithmSteps.Static.reproduction(temp, numberOfThreedsToFork);
                temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp,functionToOptimize,numberOfThreedsToFork);
                temp = EvolutionaryAlgorithmSteps.Static.selectionWithThreeds(temp,population.getPopulation().size(), numberOfThreedsToFork);
                result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
            }
            return temp;
        }
    }
}
