package zad5.ppor.weiti.pw.edu.pl.utilities;

import org.apache.commons.lang3.ArrayUtils;
import zad5.ppor.weiti.pw.edu.pl.Constants;

import java.util.Random;

public class Genotype implements Comparable<Genotype> {
    private double[] genotype;
    private Double rate;

    public Genotype(double[] genotype) {
        this.genotype = genotype;
    }
    public Genotype(Double[] genotype) {
        this.genotype = ArrayUtils.toPrimitive(genotype);
    }

    public Genotype(int n) {
        double s = new Random().nextGaussian();
        this.genotype = new java.util.Random()
                .doubles(
                        n,
                        Constants.Range.MIN_FEATURE_IN_GENOTYPE,
                        Constants.Range.MAX_FEATURE_IN_GENOTYPE)
                .toArray();
    }

    public double[] getGenotype() {
        return genotype;
    }

    public void setGenotype(double[] genotype) {
        this.genotype = genotype;
    }

    public double getRate() {
        return rate;
    }

    public Genotype withRate(double rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public int compareTo(Genotype anotherGenotype) {
        if (rate == anotherGenotype.getRate()) return 0;
        else return rate.compareTo(anotherGenotype.getRate());
    }
}


/*
final int parallelism = 4;
ForkJoinPool forkJoinPool = null;
try {
    forkJoinPool = new ForkJoinPool(parallelism);
    final List<Integer> primes = forkJoinPool.submit(() ->
        // Parallel task here, for example
        IntStream.range(1, 1_000_000).parallel()
                .filter(PrimesPrint::isPrime)
                .boxed().collect(Collectors.toList())
    ).get();
    System.out.println(primes);
} catch (InterruptedException | ExecutionException e) {
    throw new RuntimeException(e);
} finally {
    if (forkJoinPool != null) {
        forkJoinPool.shutdown();
    }
}


 //Parallel Sorting slower for lesser elements
        Arrays.parallelSort(intArray);


        ArrayList<String> list = Collections.synchronizedList(new ArrayList<String>());
 */
