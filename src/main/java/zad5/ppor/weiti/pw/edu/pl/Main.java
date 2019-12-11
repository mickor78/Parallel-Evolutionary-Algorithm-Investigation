package zad5.ppor.weiti.pw.edu.pl;

import javafx.print.PageOrientation;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithm;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmSteps;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStepsStepsDefaultImpl;
import zad5.ppor.weiti.pw.edu.pl.algorithm.EvolutionaryAlgorithmStream;
import zad5.ppor.weiti.pw.edu.pl.functions.FunctionToOptimize;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunction;
import zad5.ppor.weiti.pw.edu.pl.functions.GriewankFunctionStream;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

public class Main {
    public static void main(String[] args) {
        Population x = new Population(10000, 1000);
        Population[] y = new Population[10];
        for (int i = 0; i < y.length; i++) {
            y[i] = new Population(100, 50);
        }

        FunctionToOptimize fto = new GriewankFunctionStream();
//        FunctionToOptimize fto = new GriewankFunction();
        EvolutionaryAlgorithmSteps steps = new EvolutionaryAlgorithmStepsStepsDefaultImpl(fto);
        EvolutionaryAlgorithmStream mu_lambda = new EvolutionaryAlgorithmStream(steps);

        while(steps.bestGenotype(x).getRate()>500 || steps.bestGenotype(x).getRate()<300){
            x = new Population(
                    Constants.Population.POPULATION_SIZE_1000,
                    Constants.Genotype.FEATURES_NUM_IN_50_FT_GENOTYPE
            );
        }


        EvolutionaryAlgorithm mu_lambda_1 = new EvolutionaryAlgorithm(steps);
        System.out.println("please wait...");
//        System.out.println(mu_lambda.findBestGenotype(y).getRate());
        System.out.println("done");
        System.out.println(mu_lambda_1.findBestGenotype(x).getRate());
        System.out.println("done");


    }
}
