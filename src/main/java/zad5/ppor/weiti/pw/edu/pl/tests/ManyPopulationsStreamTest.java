package zad5.ppor.weiti.pw.edu.pl.tests;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithm;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStream;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunctionStream;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.concurrent.TimeUnit;

public class ManyPopulationsStreamTest {

    @State(Scope.Benchmark)
    public static class BeforeState {
        public Population[] populations;
        FunctionToOptimize fto;
        EvolutionaryAlgorithmSteps steps;
        EvolutionaryAlgorithmStream mu_lambda;


        @Setup
        public void init() {
            populations = new Population[50];
            for (int i = 0; i < populations.length; i++) {
                populations[i] = new Population(
                        1000,
                        Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE
                );
            }
            fto = new GriewankFunctionStream();
            steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
            mu_lambda = new EvolutionaryAlgorithmStream(steps);
        }
    }


//    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void stream_test(BeforeState state, Blackhole blackhole) {
        blackhole.consume(state.mu_lambda.findBestGenotype(state.populations));
    }

//    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(2)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void stream_test_threads2(BeforeState state, Blackhole blackhole) {
        blackhole.consume(state.mu_lambda.findBestGenotype(state.populations));
    }

//    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(4)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void stream_test_threads4(BeforeState state, Blackhole blackhole) {
        blackhole.consume(state.mu_lambda.findBestGenotype(state.populations));
    }

//    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(24)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void stream_test_threads24(BeforeState state, Blackhole blackhole) {
        blackhole.consume(state.mu_lambda.findBestGenotype(state.populations));
    }

//    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(Threads.MAX)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 4)
    public static void stream_test_threads_max(BeforeState state, Blackhole blackhole) {
        blackhole.consume(state.mu_lambda.findBestGenotype(state.populations));
    }

}
