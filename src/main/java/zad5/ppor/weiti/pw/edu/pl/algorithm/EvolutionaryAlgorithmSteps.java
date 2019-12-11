package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

public interface EvolutionaryAlgorithm {

    Population selection(Population inputPopulation);

    Population reproduction(Population inputPopulation);

    Population crossover(Population inputPopulation);

    Population mutation(Population inputPopulation);

}
