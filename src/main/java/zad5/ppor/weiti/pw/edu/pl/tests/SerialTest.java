package zad5.ppor.weiti.pw.edu.pl.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import zad5.ppor.weiti.pw.edu.pl.Constants;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.utilities.Genotype;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.concurrent.TimeUnit;

public class SerialTest {

    @State(Scope.Benchmark)
    public static class BeforeState {
        public Population population;
        public GriewankFunction griewankFunction;

        @Setup
        public void init() {
            population = new Population(
                    Constants.Genotype.FEATURES_NUM_IN_1000_FT_GENOTYPE,
                    Constants.Population.POPULATION_SIZE_10);
//                     100000,
//            10);
            griewankFunction = new GriewankFunction();
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(2)
    @Fork(value = 1)
    @Warmup(iterations = 2, time = 2)
    @Measurement(iterations = 5)
    public static void test(BeforeState state, Blackhole blackhole) {
        for (Genotype g : state.population.getGenotypes()
        ) {
            blackhole.consume(state.griewankFunction.function(g.getGenotype()));
        }
    }


}
