package zad5.ppor.weiti.pw.edu.pl.functions;

import static java.lang.Math.cos;

public class FunctionToOptimizeSimpleLoopOnArray implements FunctionToOptimize {
    public double griewankFunction(double[] x) {
        double product = -1.0;
        double summary = 0.0;
        for (int i = 0; i < x.length; i++) {
            summary += x[i] * x[i];
            product *= cos(x[i] / (i+1));
        }
        return summary / 40 + 1 - product;

    }
}
