package zad5.ppor.weiti.pw.edu.pl.tests;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunctionStream;
import zad5.ppor.weiti.pw.edu.pl.model.Genotype;
import zad5.ppor.weiti.pw.edu.pl.model.Population;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Threads(1)
@Fork(value = 1)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 3, time = 1)
public class GriewankFunctionTest {

    @State(Scope.Benchmark)
    public static class BeforeState {
        public Genotype gen1;
        public Genotype gen2;
        public GriewankFunction gf;
        public GriewankFunctionStream gfs;


        @Setup
        public void init() {
            gf = new GriewankFunction();
            gfs = new GriewankFunctionStream();


            gen1 = new Population(1,1000).getPopulation().get(0);
            gen2 = new Population(1,1000000).getPopulation().get(0);

        }
    }

    @Benchmark
    public static void griewankfunction__1000_8(BeforeState state, Blackhole blackhole) {
        final int parallelism = 8;
        calculateValue(blackhole, parallelism, state.gen1.getGenotype(), state.gf);
    }

    @Benchmark
    public static void griewankfunction__1000_1(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        calculateValue(blackhole, parallelism, state.gen1.getGenotype(), state.gf);
    }

    @Benchmark
    
    public static void griewankfunction__1000_2(BeforeState state, Blackhole blackhole) {
        final int parallelism = 2;
        calculateValue(blackhole, parallelism, state.gen1.getGenotype(), state.gf);
    }


    @Benchmark
    
    public static void griewankfunction__1M_8(BeforeState state, Blackhole blackhole) {
        final int parallelism = 8;
        calculateValue(blackhole, parallelism, state.gen2.getGenotype(), state.gf);
    }

    @Benchmark
    
    public static void griewankfunction__1M_2(BeforeState state, Blackhole blackhole) {
        final int parallelism = 2;
        calculateValue(blackhole, parallelism, state.gen2.getGenotype(), state.gf);
    }

    @Benchmark
    
    public static void griewankfunction__1M_1(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        calculateValue(blackhole, parallelism, state.gen2.getGenotype(), state.gf);
    }



    @Benchmark
    
    public static void griewankfunction_stream_1000_1(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        calculateValue(blackhole, parallelism, state.gen1.getGenotype(), state.gfs);
    }



    @Benchmark
    
    public static void griewankfunction_stream_1000_2(BeforeState state, Blackhole blackhole) {
        final int parallelism = 2;
        calculateValue(blackhole, parallelism, state.gen1.getGenotype(), state.gfs);
    }

    @Benchmark
    
    public static void griewankfunction_stream_1000_8(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        calculateValue(blackhole, parallelism, state.gen1.getGenotype(), state.gfs);
    }



    @Benchmark
    
    public static void griewankfunction_stream_1M_8(BeforeState state, Blackhole blackhole) {
        final int parallelism = 8;
        calculateValue(blackhole, parallelism, state.gen2.getGenotype(), state.gfs);
    }

    @Benchmark
    
    public static void griewankfunction_stream_1M_1(BeforeState state, Blackhole blackhole) {
        final int parallelism = 1;
        calculateValue(blackhole, parallelism, state.gen2.getGenotype(), state.gfs);
    }

    @Benchmark
    
    public static void griewankfunction_stream_1M_2(BeforeState state, Blackhole blackhole) {
        final int parallelism = 2;
        calculateValue(blackhole, parallelism, state.gen2.getGenotype(), state.gfs);
    }


    private static void calculateValue(Blackhole blackhole, int parallelism, double[] g, Function<double[], Double> f) {
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            blackhole.consume(forkJoinPool.submit(() ->
                    f.apply(g)
            ).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }


}
