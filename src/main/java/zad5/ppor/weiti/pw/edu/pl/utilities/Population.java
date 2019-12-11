package zad5.ppor.weiti.pw.edu.pl.utilities;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
    private ArrayList<Genotype> population;

    public Population(Genotype[] population) {
        this.population = new ArrayList<Genotype>();
        Collections.addAll(this.population, population);
    }

    public Population(ArrayList<Genotype> population) {
        this.population = population;
    }

    public Population(int populationSize, int numberOfFeatures) {
        this.population = new ArrayList<Genotype>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Genotype(numberOfFeatures));
        }
    }

    public ArrayList<Genotype> getPopulation() {
        return population;
    }

    public void setPopulation(Genotype[] population) {
        this.population.clear();
        Collections.addAll(this.population, population);
    }
}
