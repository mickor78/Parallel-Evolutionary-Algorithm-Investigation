package zad5.ppor.weiti.pw.edu.pl;

import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmFewPopulations;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmOnePopulation;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.functions.AcleyFunction;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        Population x = new Population(
                1000,
                100
        );

        Population[] y = new Population[4];
        for (int i = 0; i < y.length; i++) {
            y[i] = new Population(1250, 100);
        }

        AcleyFunction af = new AcleyFunction();
        GriewankFunction gf = new GriewankFunction();

//        EvolutionaryAlgorithmSteps.Static.calculateFunction(x,gf,8);
//        while (EvolutionaryAlgorithmSteps.Static.bestGenotype(x).getRate() > 500 || EvolutionaryAlgorithmSteps.Static.bestGenotype(x).getRate() < 400) {
//            x = new Population(
//                    100,
//                    100
//                );
//            EvolutionaryAlgorithmSteps.Static.calculateFunction(x,gf,8);
//            System.out.println(EvolutionaryAlgorithmSteps.Static.bestGenotype(x).getRate());
//        }

        EvolutionaryAlgorithmOnePopulation eaop = new EvolutionaryAlgorithmOnePopulation(
                20, af
        );

        EvolutionaryAlgorithmFewPopulations eafp = new EvolutionaryAlgorithmFewPopulations(
                af, 4
        );


        eaop.findBestGenotype(x,2).getRate();

        ForkJoinPool forkJoinPool = null;
        double rank;
        try {
            forkJoinPool = new ForkJoinPool(4);
            rank = forkJoinPool.submit(()->
                    eafp.findBestGenotype(y,2).getRate()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }
}
