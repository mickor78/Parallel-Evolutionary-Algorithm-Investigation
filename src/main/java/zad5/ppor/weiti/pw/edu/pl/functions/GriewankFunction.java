package zad5.ppor.weiti.pw.edu.pl.functions;

import java.util.function.Function;

import static java.lang.Math.cos;

public class GriewankFunction implements Function<double[],Double> {

    @Override
    public Double apply(double[] x) {
        double product = -1.0;
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * x[i];
            product *= cos(x[i] / (i+1));
        }
        return sum / 40 + 1 - product;
    }
}
