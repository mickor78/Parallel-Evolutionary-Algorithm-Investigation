package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.stream.Stream;

public class EvolutionaryAlgorithmStream {
    private final EvolutionaryAlgorithmSteps algorithmSteps;

    public EvolutionaryAlgorithmStream(EvolutionaryAlgorithmSteps algorithmSteps) {
        this.algorithmSteps = algorithmSteps;
    }

    public Genotype findBestGenotype(final Population[] population){
        return Stream.of(population)
                .map(pop -> new EvolutionaryAlgorithm(algorithmSteps).findBestGenotype(pop)
                )
                .parallel()
                .findFirst()
                .get();
    }
}
