package zad5.ppor.weiti.pw.edu.pl.algorithm.Acley;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmFewPopulations;
import zad5.ppor.weiti.pw.edu.pl.functions.AcleyFunction;
import zad5.ppor.weiti.pw.edu.pl.model.Population;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

@RunWith(Parameterized.class)

public class AcleyEvolutionaryAlgorithmFewPopulationTest {


    Population x;
    Function<double[], Double> function;
    int numberOfThreedToForkForAlgOperations;
    long executionTime;
    int numberOfThreedAimingByPopulations;
    int numOfDivide;


    public AcleyEvolutionaryAlgorithmFewPopulationTest(int numberOfThreedToForkForAlgOperations, int numberOfThreedAimingByPopulations, int numOfDivide, Population x) {
        this.numberOfThreedToForkForAlgOperations = numberOfThreedToForkForAlgOperations;
        this.numberOfThreedAimingByPopulations = numberOfThreedAimingByPopulations;
        this.numOfDivide = numOfDivide;
        this.x = x;
    }

    @BeforeClass
    public static void before() {
        System.out.println("pop num\t\tfeat\t\tthread_alg\t\tthread_pop\t\tnum_of_div\t\tmean\t\tvariance");

    }

    @Parameterized.Parameters
    public static Collection parameters() {
        Population pop_10000_100 = new Population(10000, 100);

        Population pop_10000_10 = new Population(10000, 10);
        Population pop_10000_50 = new Population(10000, 50);
        return Arrays.asList(new Object[][]{

                {1,     1,  4, pop_10000_10},
                {1,     4,  4, pop_10000_10},
                {4,     1,  4, pop_10000_10},
                {2,     2,  4, pop_10000_10},
                {2,     4,  4, pop_10000_10},
                {4,     4,  4, pop_10000_10},
                {4,     8,  4, pop_10000_10},
                {8,     8,  4, pop_10000_10},


                {1,     1,  8, pop_10000_10},
                {2,     4,  8, pop_10000_10},
                {4,     2,  8, pop_10000_10},
                {8,     4,  8, pop_10000_10},

                {1,     1,  10, pop_10000_10},
                {2,     4,  10, pop_10000_10},
                {4,     2,  10, pop_10000_10},
                {8,     4,  10, pop_10000_10},


                {1,     1,  4, pop_10000_50},
                {1,     4,  4, pop_10000_50},
                {4,     1,  4, pop_10000_50},
                {2,     2,  4, pop_10000_50},
                {2,     4,  4, pop_10000_50},
                {4,     4,  4, pop_10000_50},
                {4,     8,  4, pop_10000_50},
                {8,     8,  4, pop_10000_50},


                {1,     1,  8, pop_10000_50},
                {2,     4,  8, pop_10000_50},
                {4,     2,  8, pop_10000_50},
                {8,     4,  8, pop_10000_50},

                {1,     1,  10, pop_10000_50},
                {2,     4,  10, pop_10000_50},
                {4,     2,  10, pop_10000_50},
                {8,     4,  10, pop_10000_50},

                {1,     1,  4, pop_10000_100},
                {1,     4,  4, pop_10000_100},
                {4,     1,  4, pop_10000_100},
                {2,     2,  4, pop_10000_100},
                {2,     4,  4, pop_10000_100},
                {4,     4,  4, pop_10000_100},
                {4,     8,  4, pop_10000_100},
                {8,     8,  4, pop_10000_100},


                {1,     1,  8, pop_10000_100},
                {2,     4,  8, pop_10000_100},
                {4,     2,  8, pop_10000_100},
                {8,     4,  8, pop_10000_100},

                {1,     1,  10, pop_10000_100},
                {2,     4,  10, pop_10000_100},
                {4,     2,  10, pop_10000_100},
                {8,     4,  10, pop_10000_100}

        });
    }

    @Before
    public void init() {
        x = new Population(
                10000,
                100
        );
        executionTime = 30;
        function = new AcleyFunction();
    }

    @Test
    public void findBestGenotype() {
        EvolutionaryAlgorithmFewPopulations eafp = new EvolutionaryAlgorithmFewPopulations(
                function, numberOfThreedToForkForAlgOperations, numOfDivide
        );

        double [] result = new double[3];
        double mean = 0;
        double variance = 0;

        for (int i = 0; i < result.length; i++) {
            result[i] = eafp.findBestGenotype(x, Double.NEGATIVE_INFINITY, executionTime).getRate();
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
                        numberOfThreedToForkForAlgOperations + "\t" +
                        numberOfThreedAimingByPopulations+"\t"+
                        numOfDivide+"\t"+
                        mean+ "\t" +variance);
    }
}