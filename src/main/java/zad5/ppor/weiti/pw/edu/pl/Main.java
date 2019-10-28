package zad5.ppor.weiti.pw.edu.pl;


import zad5.ppor.weiti.pw.edu.pl.expressions.GriewankFunctionSimpleLoopOnArray;
import zad5.ppor.weiti.pw.edu.pl.utilities.Population;

public class Main {
    public static void main(String[] args) {
        Population p20 = new Population(Constants.Cases.ELEMENTS_NUM_IN_20_EL_POPULATION);
        System.out.println(new GriewankFunctionSimpleLoopOnArray()
                .griewankFunction(p20.getPopulation())
        );
    }
}
