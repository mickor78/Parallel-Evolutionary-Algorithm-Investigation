package zad5.ppor.weiti.pw.edu.pl.functions;

import org.apache.spark.api.java.JavaRDD;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.lang.Math.*;
import static zad5.ppor.weiti.pw.edu.pl.Constants.SparkConf.sc;

public class AcleyFunctionSpark implements Function<double[],Double> {

    @Override
    public Double apply(double[] x) {

        List<Double> y = DoubleStream.of(x).boxed().collect(Collectors.toList());

        JavaRDD<Double> rdd = sc.parallelize(y, x.length);

        JavaRDD<Double> squares = rdd.map(i -> i*i);
        JavaRDD<Double> cosinuses = rdd.map(i -> cos(2* PI * i));
        List<Double> resultSquares = squares.collect();
        List<Double> resultCosinuses = cosinuses.collect();

        double sum1 = resultSquares.stream().mapToDouble(f -> f).sum();
        double sum2 = resultCosinuses.stream().mapToDouble(f -> f).sum();

        sum1 = -0.2 * sqrt(sum1 / x.length);
        sum2 /= x.length;
        return -20 * exp(sum1) - exp(sum2) + 20 + E;
    }
}
