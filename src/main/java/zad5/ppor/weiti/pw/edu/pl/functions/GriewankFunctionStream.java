package zad5.ppor.weiti.pw.edu.pl.functions;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.cos;

public class GriewankFunctionStream implements FunctionToOptimize{
    @Override
    public double function(double[] x) {
        double product = IntStream.range(1, x.length)
                .parallel()
                .mapToDouble(i->cos(x[i] / (i+1)))
                .reduce((d1,d2)->d1*d2)
                .getAsDouble();

        double sum = IntStream.range(1,x.length)
                .parallel()
                .mapToDouble(i -> x[i]*x[i])
                .sum();

        return sum / 40 + 1 - product;
    }
}
