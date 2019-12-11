package zad5.ppor.weiti.pw.edu.pl.tests;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithm;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunctionStream;
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
                    Constants.Population.POPULATION_SIZE_1000,
                    Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE
            );
            fto = new GriewankFunctionStream();
            steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
            mu_lambda = new EvolutionaryAlgorithm(steps);
            while(steps.bestGenotype(population).getRate()>500){
                population = new Population(
                        Constants.Population.POPULATION_SIZE_1000,
                        Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE
                );
            }
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void grienwank_stream_test(BeforeState state, Blackhole blackhole) {
       state.mu_lambda.findBestGenotype(state.population);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(2)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void grienwank_stream_test_threads2(BeforeState state, Blackhole blackhole) {
        state.mu_lambda.findBestGenotype(state.population);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(4)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void grienwank_stream_test_threads4(BeforeState state, Blackhole blackhole) {
        state.mu_lambda.findBestGenotype(state.population);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(24)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void grienwank_stream_test_threads24(BeforeState state, Blackhole blackhole) {
        state.mu_lambda.findBestGenotype(state.population);
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(Threads.MAX)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void stream_test_threads_max(BeforeState state, Blackhole blackhole) {
        state.mu_lambda.findBestGenotype(state.population);
    }

}
