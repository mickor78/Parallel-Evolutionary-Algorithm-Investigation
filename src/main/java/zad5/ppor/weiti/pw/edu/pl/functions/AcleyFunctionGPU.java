package zad5.ppor.weiti.pw.edu.pl.functions;

import com.aparapi.Kernel;
import com.aparapi.Range;
import java.util.function.Function;
import java.util.stream.DoubleStream;

import static java.lang.Math.*;

public class AcleyFunctionGPU implements Function<double[],Double> {
    @Override
    public Double apply(double[] x) {
        double product = -1.0;
        double[] result1 = new double[x.length];
        double[] result2 = new double[x.length];

        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                result1[i] = x[i] * x[i];
                result2[i] = cos(2 * PI * x[i]);
            }
        };

        Range range = Range.create(x.length);
        kernel.execute(range);
        double sum1 = DoubleStream.of(result1).sum();
        double sum2 = DoubleStream.of(result2).sum();
        sum1 = -0.2 * sqrt(sum1 / x.length);
        sum2 /= x.length;

        return -20 * exp(sum1) - exp(sum2) + 20 + E;
    }

}
