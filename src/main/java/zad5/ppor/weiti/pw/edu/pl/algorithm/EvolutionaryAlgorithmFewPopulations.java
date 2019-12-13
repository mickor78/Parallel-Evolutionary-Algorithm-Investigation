package zad5.ppor.weiti.pw.edu.pl.algorithm;

import javafx.util.Pair;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.model.Genotype;
import zad5.ppor.weiti.pw.edu.pl.model.Population;
import zad5.ppor.weiti.pw.edu.pl.utilities.PrintSolution;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvolutionaryAlgorithmFewPopulations {

    private final Function<double[], Double> functionToOptimize;
    private final int numberOfThreedsToFork;
    private final int populationToDivide;
    private final EvolutionaryAlgorithmStepsStreamsImp algSteps = new EvolutionaryAlgorithmStepsStreamsImp();

    
    public EvolutionaryAlgorithmFewPopulations(Function<double[], Double> functionToOptimize, int numberOfThreedsToFork, int populationToDivide) {
        this.functionToOptimize = functionToOptimize;
        this.numberOfThreedsToFork = numberOfThreedsToFork;
        this.populationToDivide = populationToDivide;
    }

    public Genotype findBestGenotype(final Population populations, double epsilon, long seconds) {
        Population temp = populations;
        double[] x;
        algSteps.calculateFunction(
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
                new PrintSolution().showPlotForParallel(populationsList);
            }

            nextIteration = populationsList.parallelStream()
                    .anyMatch(pop -> Math.abs(algSteps.bestGenotype(pop).getRate()) < epsilon);
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
                .map(algSteps::bestGenotype)
                .sorted()
                .findFirst()
                .get();
    }

    private void showFirstPlot(Population populations, Population temp) {
        double[] x;
        x = IntStream.range(0, populations.getPopulation().size())
                .mapToDouble(i -> (double) i)
                .toArray();
        new PrintSolution().showPlot(temp, x);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private List<Population> useAlgorithmOnPopulations(double epsilon, final List<Population> populationsList) {
        return populationsList
                .parallelStream()
                .map(pop -> doAlgorithm(pop, epsilon, functionToOptimize, numberOfThreedsToFork))
                .peek(pop -> {
                            if (Constants.Presentation.MODE)
                                System.out.println(algSteps.bestGenotype(pop).getRate());
                        }
                )
                .parallel()
                .collect(Collectors.toList());
    }

    private List<Population> crossoverPopulations(List<Population> populationsList) {
        int size = populationsList.size();
        final List<Population> temp = new ArrayList<>(populationsList);

        Collections.shuffle(populationsList);

        List<Population> result = new ArrayList<>(populationsList.subList(0, (int) (populationsList.size() * Constants.Algorothm.NUMBER_OF_POPULATION_TO_CROSSOVER)));

        List<Integer> indexes = new ArrayList<>();
        indexes = new ArrayList<Integer>(IntStream.range(0,result.size())
                .boxed()
                .collect(Collectors.toList()));
        Collections.shuffle(indexes);

        final List<Integer> indexes1 = indexes.subList(0, indexes.size() / 2);
        final List<Integer> indexes2 = indexes.subList(indexes.size() / 2, indexes.size());

        result = IntStream.range(0,Math.min(indexes1.size(),indexes2.size()))
                .mapToObj(i->new Pair<> (indexes1.get(i),indexes2.get(i)))
                .map(pair -> algSteps.crossoverPopulations(
                        temp.get(pair.getKey()),
                        temp.get(pair.getValue()))
                )
                .flatMap(Function.identity())
                .limit(size)
                .collect(Collectors.toList());

        result.addAll(populationsList.subList((int) (populationsList.size() * Constants.Algorothm.NUMBER_OF_POPULATION_TO_CROSSOVER),populationsList.size()));

        return result;
    }

   
        public  Population doAlgorithm(final Population population, double epsilon, Function<double[], Double> functionToOptimize, int numberOfThreedsToFork) {

            Population temp = population;


            int iterations = 0;
            double result = 0 - algSteps.bestGenotype(temp).getRate();
            while (Math.abs(result) > epsilon) {


                iterations++;
                if (iterations % Constants.Algorothm.NUMBER_OF_ITERATION_BEFORE_CROSOVER == 0) {
                    break;
                }
                temp = algSteps.reproduction(temp, numberOfThreedsToFork);
                temp = algSteps.calculateFunction(temp, functionToOptimize, numberOfThreedsToFork);
                temp = algSteps.selection(temp, population.getPopulation().size(), numberOfThreedsToFork);
                result = 0 - algSteps.bestGenotype(temp).getRate();
            }
            return temp;
        }
    
}
