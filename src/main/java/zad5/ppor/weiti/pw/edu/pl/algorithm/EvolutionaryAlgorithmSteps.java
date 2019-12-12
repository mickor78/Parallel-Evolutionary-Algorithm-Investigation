package zad5.ppor.weiti.pw.edu.pl.algorithm;

import javafx.util.Pair;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EvolutionaryAlgorithmSteps {
    public static class Static {

        /**
         * Crossover two genotypes parent vector and produces Stream of crossovered child
         *
         * @param g1 parent one
         * @param g2 parent second
         * @return {@link Stream} of child genotypes
         */
        public static Stream<Double[]> crossoverVectors(double[] g1, double[] g2) {
            List<Double[]> result = new ArrayList<>();

            int splitIndex = new Random().nextInt(g1.length - 2) + 1;
            int size = g1.length;

            List<Double> tempG1 = Arrays.asList(ArrayUtils.toObject(g1));
            List<Double> tempG2 = Arrays.asList(ArrayUtils.toObject(g2));
            ArrayList<Double> temp;

            temp = new ArrayList<>(tempG1.subList(0, splitIndex));
            temp.addAll(tempG2.subList(splitIndex, size));

            result.add(temp.toArray(new Double[]{}));

            temp = new ArrayList<>(tempG2.subList(0, splitIndex));
            temp.addAll(tempG1.subList(splitIndex, size));

            result.add(temp.toArray(new Double[]{}));

            return result.stream();
        }


        public static Population calculateFunction(final Population inputPopulation, Function<double[], Double> functionToOptimize, int numberOfThreedsToFork) {
            ArrayList<Genotype> genotypes = inputPopulation.getPopulation();

            ForkJoinPool forkJoinPool = null;
            Population result;
            try {
                forkJoinPool = new ForkJoinPool(numberOfThreedsToFork);
                result  = forkJoinPool.submit(() ->
                       new Population(new ArrayList<>(
                               genotypes.parallelStream()
                                .map(genotype -> genotype.withRate(functionToOptimize.apply(genotype.getGenotype())))
                                .collect(Collectors.toList())))
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            } finally {
                if (forkJoinPool != null) {
                    forkJoinPool.shutdown();
                }
            }

            return result;
        }

        public static Genotype bestGenotype(final Population inputPopulation) {
            ArrayList<Genotype> genotypes = inputPopulation.getPopulation();
            return genotypes.parallelStream()
                    .sorted()
                    .findFirst()
                    .get();
        }

        public static Population selection(final Population inputPopulation, int limit) {
            return new Population(new ArrayList<>(
                    inputPopulation.getPopulation()
                            .parallelStream()
                            .sorted()
                            .limit(limit)
                            .parallel()
                            .collect(Collectors.toList())));
        }

        public static Population selectionWithThreeds(final Population inputPopulation, int limit, int numberOfThreedsToFork) {
            ForkJoinPool forkJoinPool = null;
            Population result;
            try {
                forkJoinPool = new ForkJoinPool(numberOfThreedsToFork);
                result  = forkJoinPool.submit(() -> new Population(new ArrayList<>(
                                inputPopulation.getPopulation()
                                        .parallelStream()
                                        .sorted()
                                        .limit(limit)
                                        .parallel()
                                        .collect(Collectors.toList())))
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            } finally {
                if (forkJoinPool != null) {
                    forkJoinPool.shutdown();
                }
            }
            return result;
        }

        /**
         * Takes part of input populations crossover it and then add to rest of population.
         * Mutation takes place with some probability.
         *
         * @param inputPopulation
         * @return
         */
        public static Population reproduction(final Population inputPopulation, int numberOfThreedsToFork) {
            int powerLambda = (int) (Constants.Algorothm.NUMBER_OF_LAMBDA_POPULATION * inputPopulation.getPopulation().size());

            ArrayList<Genotype> parentGenotype = inputPopulation.getPopulation();
            Collections.shuffle(parentGenotype);
            Population T = new Population(new ArrayList<>(parentGenotype.subList(0, powerLambda)));

            ArrayList<Genotype> childGenotype;

            ForkJoinPool forkJoinPool = null;
            double rank;
            try {
                forkJoinPool = new ForkJoinPool(numberOfThreedsToFork);
                childGenotype = forkJoinPool.submit(mutation(crossover(T))::getPopulation
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            } finally {
                if (forkJoinPool != null) {
                    forkJoinPool.shutdown();
                }
            }

            childGenotype.addAll(parentGenotype);
            return new Population(childGenotype);
        }

        /**
         * Crossovers random elements from populations.
         *
         * @param inputPopulation
         * @return
         */
        public static Population crossover(final Population inputPopulation) {




            int populationSize = inputPopulation.getPopulation().size();
            ArrayList<Genotype> T = inputPopulation.getPopulation();
            Random rnd = new Random();

            return new Population(new ArrayList<>(
                    IntStream.range(0, populationSize-1)
                            .parallel()
                            .mapToObj(i -> {
                                int x = rnd.nextInt(populationSize);
                                int y = rnd.nextInt(populationSize);
                                while(x==y)
                                    y = rnd.nextInt(populationSize);
                                return new Pair<>(x, y);
                            })
                            .map(pair -> crossoverVectors(
                                    T.get(pair.getKey()).getGenotype(),
                                    T.get(pair.getValue()).getGenotype()
                            ))
                            .flatMap(Function.identity())
                            .map(Genotype::new)
                            .collect(Collectors.toList())));
        }

        public static Population mutation(final Population inputPopulation) {
            return new Population(new ArrayList<Genotype>(
                    inputPopulation.getPopulation()
                            .parallelStream()
                            .map(genotype -> {
                                Double[] genotypeWrapped = ArrayUtils.toObject(genotype.getGenotype());

                                for (int i = 0; i < genotypeWrapped.length; i++) {
                                    genotypeWrapped[i] = new Random().nextDouble() < Constants.Algorothm.PROBABILITY_OF_GENE_MUTATION
                                            ? new Random().nextDouble() * 80 - 40
                                            : genotypeWrapped[i];
                                }
                                return genotypeWrapped;
                            })
                            .parallel()
                            .map(Genotype::new)
                            .collect(Collectors.toList())
            ));
        }

        public static Stream<Population> crossoverPopulations(final Population p1, final Population p2) {
            List<Population> result = new ArrayList<>();

            ArrayList<Genotype> result1 = selection(p2, (int) (Constants.Algorothm.NUMBER_OF_GENOTYPES_TO_EXCHANGE * p1.getPopulation().size())).getPopulation();
            result1.addAll(p1.getPopulation());
            ArrayList<Genotype> result2 = selection(p1, (int) (Constants.Algorothm.NUMBER_OF_GENOTYPES_TO_EXCHANGE * p2.getPopulation().size())).getPopulation();
            result2.addAll(p2.getPopulation());

            Population pop1 = selection(new Population(result1), p1.getPopulation().size());
            Population pop2 = selection(new Population(result2), p2.getPopulation().size());

            result.add(pop1);
            result.add(pop2);

            return result.stream();
        }

        public static void showPlot(Population temp, double[] x) {
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
    }

}
