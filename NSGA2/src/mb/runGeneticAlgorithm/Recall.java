/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mb.MyIndexReader;
import mb.MyIndexSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class Recall extends ObjectiveFunction {

    // private static final String AXIS_TITLE = "pow(x, 2)";
    private static final String AXIS_TITLE = "recall";
   
    //@Override
    public double objectiveFunction(Query query) {
        
        ///System.out.println("recall");
        double recall = 0.0;
        
        ArrayList<Document> relevantRetrivedDocumnets=getRelevantRetrievedDocuments(query.getRetrievedDocuments(),query.getTopic_num(),Configuration.getSearcher());
        
       
            
            recall = (double)  relevantRetrivedDocumnets.size() / Configuration.getAllRelevatnDocuments().size();
                   // int rank = j;
             MyIndexReader indexreader= new MyIndexReader(query.getTopic_num());
            //  indexreader.addTermsToMutaionPool(relevantRetrivedDocumnets);
         
        return recall;
        
    }
    
    @Override
    public double objectiveFunction(ParetoObject paretoObject) {
     
        return objectiveFunction(paretoObject.getChromosome());
    }
    
    @Override
    public String getAxisTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
    
}
