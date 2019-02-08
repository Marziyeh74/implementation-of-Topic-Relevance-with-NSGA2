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
public class Performance {
    
    public double percision;
    public double recall;
    public double f_measure;
    public double percisionAt10;
    
    public Performance(double percision,double recall,double f_measure){
        this.f_measure=f_measure;
        this.percision=percision;
        this.recall=recall;
        //this.percisionAt10=percisionAt10;
    }
    
}
