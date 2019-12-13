package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.model.Genotype;
import zad5.ppor.weiti.pw.edu.pl.model.Population;

import java.util.function.Function;
import java.util.stream.Stream;

public interface EvolutionaryAlgorithmSteps {

    Population calculateFunction(Population inputPopulation, Function<double[], Double> functionToOptimize, int numberOfThreedsToFork);

    Genotype bestGenotype(Population inputPopulation);

    Population selection(Population inputPopulation, int limit);

    Population selection(Population inputPopulation, int limit, int numberOfThreedsToFork);

    Population reproduction(Population inputPopulation, int numberOfThreedsToFork);

    Population crossover(Population inputPopulation);

    Population mutation(Population inputPopulation);

    Stream<Population> crossoverPopulations(Population p1, Population p2);
}
