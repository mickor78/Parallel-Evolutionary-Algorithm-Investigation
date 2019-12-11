package zad5.ppor.weiti.pw.edu.pl.tests;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithm;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.concurrent.TimeUnit;

public class StreamTest {

    @State(Scope.Benchmark)
    public static class BeforeState {
        public Population population;
        FunctionToOptimize fto;
        EvolutionaryAlgorithmSteps steps;
        EvolutionaryAlgorithm mu_lambda;


        @Setup
        public void init() {
            population = new Population(
                    Constants.Genotype.FEATURES_NUM_IN_1000_FT_GENOTYPE,
                    Constants.Population.POPULATION_SIZE_100);
            fto = new GriewankFunction();
            steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
            mu_lambda = new EvolutionaryAlgorithm(steps);
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2)
    public static void test(BeforeState state, Blackhole blackhole) {
       state.mu_lambda.findBestGenotype(state.population);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(4)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2)
    public static void test_threads4(BeforeState state, Blackhole blackhole) {
        state.mu_lambda.findBestGenotype(state.population);
    }

}
