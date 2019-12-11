package zad5.ppor.weiti.pw.edu.pl.algorithm;

import javafx.util.Pair;
import org.apache.commons.lang3.ArrayUtils;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EvolutionaryAlgorithmStepsStepsDefaultImpl implements EvolutionaryAlgorithmSteps {
    FunctionToOptimize functionObject;

    public EvolutionaryAlgorithmStepsStepsDefaultImpl(FunctionToOptimize functionObject) {
        this.functionObject = functionObject;
    }

    @Override
    public Population selection(final Population inputPopulation) {
        return new Population(new ArrayList<>(
                inputPopulation.getPopulation()
                        .parallelStream()
                        .map(genotype -> genotype.withRate(functionObject.function(genotype.getGenotype())))
                        .sorted()
                        .limit(1000)
                        .parallel()
                        .collect(Collectors.toList())));
    }

    @Override
    public Population reproduction(final Population inputPopulation) {
        int powerLambda = 400;

        ArrayList<Genotype> parentGenotype = inputPopulation.getPopulation();
        Collections.shuffle(parentGenotype);
        Population T = new Population(new ArrayList<>(parentGenotype.subList(0, powerLambda)));

        ArrayList<Genotype> childGenotype;
        if (new Random().nextInt() % 50 == 0) {
            childGenotype = mutation(crossover(T)).getPopulation();
        } else {
            childGenotype = crossover(T).getPopulation();
        }

        childGenotype.addAll(parentGenotype);
        return new Population(childGenotype);
    }

    @Override
    public Population crossover(final Population inputPopulation) {
        int populationSize = inputPopulation.getPopulation().size();
        ArrayList<Genotype> T = inputPopulation.getPopulation();
        Random rnd = new Random();

        return new Population(new ArrayList<>(
                IntStream.range(0, populationSize - 1)
                        .parallel()
                        .mapToObj(i -> new Pair<>(rnd.nextInt(populationSize - 1), rnd.nextInt(populationSize - 1)))
                        .map(pair -> crossoverVectors(
                                T.get(pair.getKey()).getGenotype(),
                                T.get(pair.getValue()).getGenotype()
                        ))
                        .flatMap(Function.identity())
                        .map(Genotype::new)
                        .collect(Collectors.toList())));
    }

    @Override
    public Population mutation(final Population inputPopulation) {
        return new Population(new ArrayList<Genotype>(
                inputPopulation.getPopulation()
                        .parallelStream()
                        .map(genotype -> {
                            Double[] genotypeWrapped = ArrayUtils.toObject(genotype.getGenotype());

                            for (int i = 0; i < Math.abs(new Random().nextInt() % 500); i++) {
                                genotypeWrapped[Math.abs(new Random().nextInt() % genotypeWrapped.length)] = new Random().nextDouble() * 80 - 40;
                            }
                            return genotypeWrapped;
                        })
                        .parallel()
                        .map(Genotype::new)
                        .collect(Collectors.toList())
        ));
    }

    @Override
    public Genotype bestGenotype(final Population inputPopulation) {
        ArrayList<Genotype> genotypes = inputPopulation.getPopulation();
        return genotypes.parallelStream()
                .map(genotype -> genotype.withRate(functionObject.function(genotype.getGenotype())))
                .sorted()
                .findFirst()
                .get();
    }

    public Stream<Double[]> crossoverVectors(double[] g1, double[] g2) {
        List<Double[]> result = new ArrayList<>();

        int splitIndex = new Random().nextInt(g1.length - 2);
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
}
