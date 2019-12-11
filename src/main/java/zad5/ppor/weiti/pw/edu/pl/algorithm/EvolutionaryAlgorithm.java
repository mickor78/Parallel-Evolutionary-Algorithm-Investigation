package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.stream.DoubleStream;

public class EvolutionaryAlgorithm {

    private final EvolutionaryAlgorithmSteps algorithmSteps;

    public EvolutionaryAlgorithm(EvolutionaryAlgorithmSteps algorithmSteps) {
        this.algorithmSteps = algorithmSteps;
    }

    public Genotype findBestGenotype(final Population population) {
        Population temp = population;
        System.out.println();
        System.out.println(algorithmSteps.bestGenotype(temp).getRate());
        double result = Double.POSITIVE_INFINITY;
        double epsilon = 5;
        int iterations = 0;
        while (Math.abs(result) > epsilon) {
            iterations++;
            result = 0 - algorithmSteps.bestGenotype(temp).getRate();
            temp = algorithmSteps.reproduction(temp);
            temp = algorithmSteps.selection(temp);
        }
        System.out.println(iterations);
        return algorithmSteps.bestGenotype(temp);
    }
}
