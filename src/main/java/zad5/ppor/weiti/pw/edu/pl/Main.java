package zad5.ppor.weiti.pw.edu.pl;

import javafx.print.PageOrientation;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithm;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStream;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

public class Main {
    public static void main(String[] args) {
        Population x = new Population(10000, 50);
        Population[] y = new Population[10];
        for (int i = 0; i < y.length; i++) {
            y[i] = new Population(100, 50);
        }

        FunctionToOptimize fto = new GriewankFunction();
        EvolutionaryAlgorithmSteps steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
        EvolutionaryAlgorithmStream mu_lambda = new EvolutionaryAlgorithmStream(steps);

        EvolutionaryAlgorithm mu_lambda_1 = new EvolutionaryAlgorithm(steps);
        System.out.println("please wait...");
//        System.out.println(mu_lambda.findBestGenotype(y).getRate());
        System.out.println("done");
        System.out.println(mu_lambda_1.findBestGenotype(x).getRate());
        System.out.println("done");


    }
}
