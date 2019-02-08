/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;


/**
 *
 * @author Marziyeh
 */
public class ParetoObject {
   private Query chromosome = null;
    private double crowdingDistance = -1f;
    private boolean crowdingDistanceSorted = false;
    
    public ParetoObject(Query chromosome) {
        this(chromosome, -1f);
    }
    
    public ParetoObject(Query chromosome, float crowdingDistance) {
        this.chromosome = chromosome;
        this.crowdingDistance = crowdingDistance;
    }

    public Query getChromosome() {
        return chromosome;
    }

    public void setChromosome(Query chromosome) {
        this.chromosome = chromosome;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public boolean isCrowdingDistanceSorted() {
        return crowdingDistanceSorted;
    }

    public void setCrowdingDistanceSorted(boolean crowdingDistanceSorted) {
        this.crowdingDistanceSorted = crowdingDistanceSorted;
    } 
}
