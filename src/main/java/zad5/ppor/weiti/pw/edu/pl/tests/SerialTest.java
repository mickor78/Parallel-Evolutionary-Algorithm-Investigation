package zad5.ppor.weiti.pw.edu.pl.tests;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithm;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStream;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunctionStream;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class SerialTest {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void test_thread4(BeforeState state, Blackhole blackhole) {
        final int parallelism = 4;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void test_thread8(BeforeState state, Blackhole blackhole) {
        final int parallelism = 8;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void test(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void grienwank_stream_test(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda_stream.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void grienwank_stream_test_4_threads(BeforeState state, Blackhole blackhole) {

        final int parallelism = 4;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda_stream.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void grienwank_stream_test_18_threads(BeforeState state, Blackhole blackhole) {

        final int parallelism = 18;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda_stream.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(1)
    @Fork(value = 1)
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 3, time = 2)
    public static void grienwank_stream_test_8_threads(BeforeState state, Blackhole blackhole) {

        final int parallelism = 8;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final double rank = forkJoinPool.submit(() ->
                    state.mu_lambda_stream.findBestGenotype(state.population).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }

    @State(Scope.Benchmark)
    public static class BeforeState {
        public Population population;
        FunctionToOptimize fto;
        FunctionToOptimize fto_stream;
        EvolutionaryAlgorithmSteps steps;
        EvolutionaryAlgorithmSteps steps_stream;
        EvolutionaryAlgorithm mu_lambda;
        EvolutionaryAlgorithm mu_lambda_stream;

        @Setup
        public void init() {
            population = new Population(
                    100000,
                    1000
            );
            fto = new GriewankFunction();
            fto_stream = new GriewankFunctionStream();
            steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
            steps_stream = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto_stream);
            mu_lambda = new EvolutionaryAlgorithm(steps);
            mu_lambda_stream = new EvolutionaryAlgorithm(steps_stream);
            while (steps.bestGenotype(population).getRate() > 500 || steps.bestGenotype(population).getRate() < 400) {
                population = new Population(
                        Constants.Population.POPULATION_SIZE_1000,
                        Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE
                );
            }
        }
    }


}
