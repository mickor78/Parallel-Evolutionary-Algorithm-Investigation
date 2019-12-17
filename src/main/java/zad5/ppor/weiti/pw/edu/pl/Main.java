package zad5.ppor.weiti.pw.edu.pl;

import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmFewPopulations;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmOnePopulation;
import zad5.ppor.weiti.pw.edu.pl.functions.AcleyFunction;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.model.Population;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        Population x = new Population(
                10000,
                100
        );
        Population y = new Population(
                10000,
                100
        );

        AcleyFunction af = new AcleyFunction();
        GriewankFunction gf = new GriewankFunction();


        EvolutionaryAlgorithmOnePopulation eaop = new EvolutionaryAlgorithmOnePopulation(
                4, af
        );

        EvolutionaryAlgorithmFewPopulations eafp = new EvolutionaryAlgorithmFewPopulations(
                af, 4,4
        );


//        System.out.println(eaop.findBestGenotype(x, Double.NEGATIVE_INFINITY, 120).getRate());

        ForkJoinPool forkJoinPool = null;
        double rank;
        try {
            forkJoinPool = new ForkJoinPool(8);
            rank = forkJoinPool.submit(()->
                    eafp.findBestGenotype(y,Double.NEGATIVE_INFINITY,120).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

        System.out.println(rank);

    }
}
