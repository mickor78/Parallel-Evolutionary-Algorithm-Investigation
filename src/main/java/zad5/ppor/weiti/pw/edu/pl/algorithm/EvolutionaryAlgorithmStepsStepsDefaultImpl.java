package zad5.ppor.weiti.pw.edu.pl.algorithm;

import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.ArrayList;

public class EvolutionaryAlgorithmStepsDefaultImpl implements EvolutionaryAlgorithm {
    FunctionToOptimize functionObject;

    public EvolutionaryAlgorithmStepsDefaultImpl(FunctionToOptimize functionObject) {
        this.functionObject = functionObject;
    }

    @Override
    public Population selection(Population inputPopulation) {
        return new Population (
                inputPopulation.getPopulation()
                .parallelStream()
                .map(genotype -> genotype.withRate(functionObject.function(genotype.getGenotype())))
                .sorted()
                .collect();
    ;}

    @Override
    public Population reproduction(Population inputPopulation) {
        return null;
    }

    @Override
    public Population crossover(Population inputPopulation) {
        return null;
    }

    @Override
    public Population mutation(Population inputPopulation) {
        return null;
    }
}
