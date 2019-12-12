//package zad5.ppor.weiti.pw.edu.pl.tests;
//
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.infra.Blackhole;
//import zad5.ppor.weiti.pw.edu.pl.Constants;
//import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmOnePopulation;
//import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
//import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
//import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmFewPopulations;
//import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
//import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
//import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunctionStream;
//import zad5.ppor.weiti.pw.edu.pl.utilities.Population;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.TimeUnit;
//
//public class ManyPopulationsStreamTest {
//
//    @State(Scope.Benchmark)
//    public static class BeforeState {
//        public Population[] populations;
//        FunctionToOptimize fto;
//        EvolutionaryAlgorithmSteps steps;
//        EvolutionaryAlgorithmFewPopulations mu_lambda;
//
//
//        @Setup
//        public void init() {
//            fto = new GriewankFunction();
//            steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
//            mu_lambda = new EvolutionaryAlgorithmFewPopulations(steps);
//            populations = new Population[50];
//            for (int i = 0; i < populations.length; i++) {
//                populations[i] = new Population(
//                        Constants.Population.POPULATION_SIZE_1000,
//                        Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE);
//                while (steps.calculateFunction(populations[i]).getRate() > 2000 || steps.calculateFunction(populations[i]).getRate() < 400) {
//                    populations[i] = new Population(
//                            Constants.Population.POPULATION_SIZE_1000,
//                            Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE
//                    );
//                }
//            }
//        }
//    }
//
//
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(1)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test(BeforeState state, Blackhole blackhole) {
//        final int parallelism = 1;
//        ForkJoinPool forkJoinPool = null;
//        try {
//            forkJoinPool = new ForkJoinPool(parallelism);
//            final double rank = forkJoinPool.submit(() ->
//                    state.mu_lambda.calculateFunction(state.populations).getRate()
//            ).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (forkJoinPool != null) {
//                forkJoinPool.shutdown();
//            }
//        }
//
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(1)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test4(BeforeState state, Blackhole blackhole) {
//        final int parallelism = 4;
//        ForkJoinPool forkJoinPool = null;
//        try {
//            forkJoinPool = new ForkJoinPool(parallelism);
//            final double rank = forkJoinPool.submit(() ->
//                    state.mu_lambda.calculateFunction(state.populations).getRate()
//            ).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (forkJoinPool != null) {
//                forkJoinPool.shutdown();
//            }
//        }
//
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(1)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test8(BeforeState state, Blackhole blackhole) {
//        final int parallelism = 8;
//        ForkJoinPool forkJoinPool = null;
//        try {
//            forkJoinPool = new ForkJoinPool(parallelism);
//            final double rank = forkJoinPool.submit(() ->
//                    state.mu_lambda.calculateFunction(state.populations).getRate()
//            ).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (forkJoinPool != null) {
//                forkJoinPool.shutdown();
//            }
//        }
//
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(1)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test12(BeforeState state, Blackhole blackhole) {
//        final int parallelism = 12;
//        ForkJoinPool forkJoinPool = null;
//        try {
//            forkJoinPool = new ForkJoinPool(parallelism);
//            final double rank = forkJoinPool.submit(() ->
//                    state.mu_lambda.calculateFunction(state.populations).getRate()
//            ).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (forkJoinPool != null) {
//                forkJoinPool.shutdown();
//            }
//        }
//
//    }
//
////    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(2)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test_threads2(BeforeState state, Blackhole blackhole) {
//        blackhole.consume(state.mu_lambda.calculateFunction(state.populations));
//    }
//
////    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(4)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test_threads4(BeforeState state, Blackhole blackhole) {
//        blackhole.consume(state.mu_lambda.calculateFunction(state.populations));
//    }
//
////    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(24)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test_threads24(BeforeState state, Blackhole blackhole) {
//        blackhole.consume(state.mu_lambda.calculateFunction(state.populations));
//    }
//
////    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Threads(Threads.MAX)
//    @Fork(value = 1)
//    @Warmup(iterations = 1, time = 1)
//    @Measurement(iterations = 3, time = 2)
//    public static void stream_test_threads_max(BeforeState state, Blackhole blackhole) {
//        blackhole.consume(state.mu_lambda.calculateFunction(state.populations));
//    }
//
//}
