/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;


import java.util.ArrayList;
import mb.MyIndexSearcher;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public interface IObjectiveFunction {
    
    // public double objectiveFunction(double geneVaue);
    public double objectiveFunction(Query query);
    public double objectiveFunction(ParetoObject paretoObject);
    public String getAxisTitle();
}
