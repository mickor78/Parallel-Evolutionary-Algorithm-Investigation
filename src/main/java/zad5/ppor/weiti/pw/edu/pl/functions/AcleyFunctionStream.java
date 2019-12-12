package zad5.ppor.weiti.pw.edu.pl.functions;

import java.util.function.Function;
import java.util.stream.DoubleStream;

import static java.lang.Math.*;
import static java.lang.Math.exp;

public class AcleyFunctionStream implements Function<double[], Double> {
    @Override
    public Double apply(double[] x) {
        double product = -1.0;
        double sum1 = DoubleStream.of(x)
                .parallel()
                .map(y -> y * y)
                .sum();
        double sum2 = DoubleStream.of(x)
                .parallel()
                .map(y -> cos(2 * PI * y))
                .sum();
        sum1 = -0.2 * sqrt(sum1 / x.length);
        sum2 /= x.length;

        return -20 * exp(sum1) - exp(sum2) + 20 + E;

    }
}
