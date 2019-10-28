package zad5.ppor.weiti.pw.edu.pl.expressions;

import static java.lang.Math.cos;

public class GriewankFunctionSimpleLoopOnArray implements GriewanFunction {
    public double griewankFunction(double[] x) {
        double multiplication = -1.0;
        double summary = 0.0;
        for (int i = 0; i < x.length; i++) {
            summary += x[i] * x[i];
            multiplication *= cos(x[i] / (i+1));
        }
        return summary / 40 + 1 - multiplication;

    }
}
