package zad5.ppor.weiti.pw.edu.pl.utilities;

import zad5.ppor.weiti.pw.edu.pl.Constants;

public class Population {
    private double[] population;

    public Population(double[] population) {
        this.population = population;
    }

    public Population(int n) {
        this.population = new java.util.Random()
                .doubles(
                        n,
                        Constants.Range.MIN_ELEMENT_IN_POPULATION,
                        Constants.Range.MAX_ELEMENT_IN_POPULATION)
                .toArray();
    }

    public double[] getPopulation() {
        return population;
    }

    public void setPopulation(double[] population) {
        this.population = population;
    }
}
