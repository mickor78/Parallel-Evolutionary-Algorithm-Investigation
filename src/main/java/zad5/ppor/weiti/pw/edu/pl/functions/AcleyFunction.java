package zad5.ppor.weiti.pw.edu.pl.functions;



import java.util.function.Function;

import static java.lang.Math.*;

public class AcleyFunction implements Function<double[],Double> {
    @Override
    public Double apply(double[] x) {
        double product = -1.0;
        double sum1 = 0.0;
        double sum2 = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum1 += x[i] * x[i];
            sum2 += cos(2 * PI * x[i]);
        }

        sum1 = -0.2 * sqrt(sum1 / x.length);
        sum2 /= x.length;

        return -20 * exp(sum1) - exp(sum2) + 20 + E;
    }

}
