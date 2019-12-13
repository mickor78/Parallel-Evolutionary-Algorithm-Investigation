package zad5.ppor.weiti.pw.edu.pl.model;

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
        else return Math.abs(rate) > Math.abs(anotherGenotype.getRate()) ? 1 : -1;
    }
}