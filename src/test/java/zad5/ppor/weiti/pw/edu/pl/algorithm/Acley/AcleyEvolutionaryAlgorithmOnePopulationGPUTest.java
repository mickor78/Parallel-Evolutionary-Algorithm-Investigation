package zad5.ppor.weiti.pw.edu.pl.algorithm.Acley;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmOnePopulation;
import zad5.ppor.weiti.pw.edu.pl.functions.AcleyFunctionGPU;
import zad5.ppor.weiti.pw.edu.pl.model.Population;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

@RunWith(Parameterized.class)

public class AcleyEvolutionaryAlgorithmOnePopulationTest {


    Population x;
    Function<double[],Double> function;
    int numberOfThreedToFork;
    long executionTime;


    @BeforeClass
    public static void before(){
        System.out.println("pop num\t\tfeat\t\ttthread\t\tmean\t\tvariance");
    }
    @Before
    public void init() {
        executionTime = 30;
        function = new AcleyFunctionGPU();
    }

    public AcleyEvolutionaryAlgorithmOnePopulationTest(Integer numberOfThreedToFork, Population x) {
        this.x = x;
        this.numberOfThreedToFork = numberOfThreedToFork;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        Population pop_10000_100 = new Population(10000, 100);

        Population pop_10000_10 = new Population(10000, 10);
        Population pop_10000_50 = new Population(10000, 50);
        return Arrays.asList(new Object[][] {
                { 1, pop_10000_100},
//                { 2, pop_10000_100},
//                { 4, pop_10000_100},
//                { 8, pop_10000_100},
//                { 12, pop_10000_100},
//                { 16, pop_10000_100},
//                { 20, pop_10000_100},
                { 1, pop_10000_10},
//                { 2, pop_10000_10},
//                { 4, pop_10000_10},
//                { 8, pop_10000_10},
//                { 12, pop_10000_10},
//                { 16, pop_10000_10},
//                { 20, pop_10000_10},
                { 1, pop_10000_50},
//                { 2, pop_10000_50},
//                { 4, pop_10000_50},
//                { 8, pop_10000_50},
//                { 12, pop_10000_50},
//                { 16, pop_10000_50},
//                { 20, pop_10000_50}
        });
    }

    @Test
    public void findBestGenotype() {
        EvolutionaryAlgorithmOnePopulation eaop = new EvolutionaryAlgorithmOnePopulation(
                numberOfThreedToFork, function
        );
        double [] result = new double[5];
        double mean = 0;
        double variance = 0;

        for (int i = 0; i < result.length; i++) {
            result[i] = eaop.findBestGenotype(x, Double.NEGATIVE_INFINITY, executionTime).getRate();
            mean += result[i];
        }

        mean/=result.length;

        for (int i = 0; i < result.length; i++) {
            variance += (result[i]-mean)*(result[i]-mean);
        }
        variance/=result.length;


        System.out.println(
                x.getPopulation().size() + "\t" +
                        x.getPopulation().get(0).getGenotype().length + "\t" +
                        numberOfThreedToFork + "\t" +
                        mean+ "\t" +variance);
    }
}