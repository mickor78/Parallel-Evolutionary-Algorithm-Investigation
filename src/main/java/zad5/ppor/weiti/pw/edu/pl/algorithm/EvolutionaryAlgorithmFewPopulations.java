package zad5.ppor.weiti.pw.edu.pl.algorithm;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import javafx.util.Pair;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvolutionaryAlgorithmFewPopulations {

    public final Function<double[], Double> functionToOptimize;
    public final int numberOfThreedsToFork;
    public final int populationToDivide;

    public EvolutionaryAlgorithmFewPopulations(Function<double[], Double> functionToOptimize, int numberOfThreedsToFork, int populationToDivide) {
        this.functionToOptimize = functionToOptimize;
        this.numberOfThreedsToFork = numberOfThreedsToFork;
        this.populationToDivide = populationToDivide;
    }

    public Genotype findBestGenotype(final Population populations, double epsilon, long seconds) {
        Population temp = populations;
        double[] x;
        EvolutionaryAlgorithmSteps.Static.calculateFunction(
                temp, functionToOptimize, numberOfThreedsToFork);
        if (Constants.Presentation.MODE) {
            showFirstPlot(populations, temp);
        }


        Population[] population = new Population[populationToDivide];

        for (int i = 0; i < populationToDivide; i++) {
            int sizeOfOne = populations.getPopulation().size() / populationToDivide;
            population[i] = new Population(new ArrayList<>(populations
                    .getPopulation()
                    .subList(i * sizeOfOne, (i + 1) * sizeOfOne)));
        }


        double result = Double.POSITIVE_INFINITY;
        List<Population> populationsList = new ArrayList<>(Arrays.asList(population));
        boolean nextIteration = true;


        long startTime = System.nanoTime();
        while (true) {

            if((System.nanoTime()-startTime)> seconds*Constants.Algorothm.NANOSEC_PER_SEC) break;
            populationsList =
                    useAlgorithmOnPopulations(epsilon, populationsList);
            if(Constants.Presentation.MODE)
            {
                showPlotForParallel(populationsList);
            }

            nextIteration = populationsList.parallelStream()
                    .anyMatch(pop -> Math.abs(EvolutionaryAlgorithmSteps.Static.bestGenotype(pop).getRate()) < epsilon);
            if (nextIteration) break;

            final List<Population> tempPopulation2 = new ArrayList<>(populationsList);
            ForkJoinPool forkJoinPool = null;
            try {
                forkJoinPool = new ForkJoinPool(numberOfThreedsToFork);
                populationsList = forkJoinPool.submit(() ->
                        crossoverPopulations(tempPopulation2)
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

    private void showFirstPlot(Population populations, Population temp) {
        double[] x;
        x = IntStream.range(0, populations.getPopulation().size())
                .mapToDouble(i -> (double) i)
                .toArray();
        EvolutionaryAlgorithmSteps.Static.showPlot(temp, x);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPlotForParallel(List<Population> populationsList) {
        Population temp = new Population (new ArrayList<Genotype>(populationsList
                .stream()
                .map(Population::getPopulation)
                .flatMap(ArrayList::stream)
                .collect(Collectors.toList()))
        );
        double [] x = IntStream.range(0, temp.getPopulation().size())
                .mapToDouble(i -> (double) i)
                .toArray();
        EvolutionaryAlgorithmSteps.Static.showPlot(temp,x);
    }

    private List<Population> useAlgorithmOnPopulations(double epsilon, final List<Population> populationsList) {
        return populationsList
                .parallelStream()
                .map(pop -> Static.doAlgorithm(pop, epsilon, functionToOptimize, numberOfThreedsToFork))
                .peek(pop -> {
                            if (Constants.Presentation.MODE)
                                System.out.println(EvolutionaryAlgorithmSteps.Static.bestGenotype(pop).getRate());
                        }
                )
                .parallel()
                .collect(Collectors.toList());
    }

    private List<Population> crossoverPopulations(List<Population> populationsList) {
        int size = populationsList.size();
        final List<Population> temp = new ArrayList<>(populationsList);
        List<Population> result = new ArrayList<>();

        Collections.shuffle(populationsList);

        result.addAll(populationsList.subList(0, (int) (populationsList.size() * Constants.Algorothm.NUMBER_OF_POPULATION_TO_CROSSOVER)));

        List<Integer> indexes = new ArrayList<>();
        indexes = new ArrayList<Integer>(IntStream.range(0,result.size())
                .mapToObj(Integer::new)
                .collect(Collectors.toList()));
        Collections.shuffle(indexes);

        final List<Integer> indexes1 = indexes.subList(0, indexes.size() / 2);
        final List<Integer> indexes2 = indexes.subList(indexes.size() / 2, indexes.size());

        result = IntStream.range(0,Math.min(indexes1.size(),indexes2.size()))
                .mapToObj(i->new Pair<> (indexes1.get(i),indexes2.get(i)))
                .map(pair -> EvolutionaryAlgorithmSteps.Static.crossoverPopulations(
                        temp.get(pair.getKey()),
                        temp.get(pair.getValue()))
                )
                .flatMap(Function.identity())
                .limit(size)
                .collect(Collectors.toList());

        result.addAll(populationsList.subList((int) (populationsList.size() * Constants.Algorothm.NUMBER_OF_POPULATION_TO_CROSSOVER),populationsList.size()));

        return result;
    }

    public static class Static {
        public static Population doAlgorithm(final Population population, double epsilon, Function<double[], Double> functionToOptimize, int numberOfThreedsToFork) {

            Population temp = population;


            int iterations = 0;
            double result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
            while (Math.abs(result) > epsilon) {


                iterations++;
                if (iterations % Constants.Algorothm.NUMBER_OF_ITERATION_BEFORE_CROSOVER == 0) {
                    break;
                }
                temp = EvolutionaryAlgorithmSteps.Static.reproduction(temp, numberOfThreedsToFork);
                temp = EvolutionaryAlgorithmSteps.Static.calculateFunction(temp, functionToOptimize, numberOfThreedsToFork);
                temp = EvolutionaryAlgorithmSteps.Static.selectionWithThreeds(temp, population.getPopulation().size(), numberOfThreedsToFork);
                result = 0 - EvolutionaryAlgorithmSteps.Static.bestGenotype(temp).getRate();
            }
            return temp;
        }
    }
}
