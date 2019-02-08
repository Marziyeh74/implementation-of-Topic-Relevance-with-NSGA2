/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import output.WriteToExcel;

/**
 *
 * @author Marziyeh
 */
public class ExecuteTraining {
    
    public static void main(String[] args)
    {
      
            Algorithm algorithm= new Algorithm();
            try {
            //295:Body painting
           algorithm.run(295,"HyperM");
            //439:Bioinformatics Normal
               // algorithm.run(439, "Normal");
            } catch (IOException ex) {
            Logger.getLogger(ExecuteTraining.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
        
    }
    
    
    // we should run NSGA2 for each topic and save the results
    
    
}
