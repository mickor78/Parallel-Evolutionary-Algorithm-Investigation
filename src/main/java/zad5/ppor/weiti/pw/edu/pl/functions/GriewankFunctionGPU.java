package zad5.ppor.weiti.pw.edu.pl.functions;
import java.util.function.Function;

import com.aparapi.Kernel;
import com.aparapi.Range;
import java.util.stream.DoubleStream;
import static java.lang.Math.*;

public class GriewankFunctionGPU implements Function<double[],Double> {
    @Override
    public Double apply(double[] x) {
        double[] productResult = new double[x.length];
        double[] sumResult = new double[x.length];

        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                sumResult[i] = x[i] * x[i];
                productResult[i] = cos(x[i] / (i+1));
            }
        };

        Range range = Range.create(x.length);
        kernel.execute(range);
        double sum = DoubleStream.of(sumResult).sum();
        double product = DoubleStream.of(productResult).reduce(1, (a, b) -> a * b);
        return sum / 40 + 1 - product;
    }
}
