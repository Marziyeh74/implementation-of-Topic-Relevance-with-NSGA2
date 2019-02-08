/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class PercisionTotall  extends ObjectiveFunction{
     private static final String AXIS_TITLE = "percision";
    
    @Override
    public double objectiveFunction(Query query) {

        //System.out.println("Percison");
       
        List<String> queryTerms = query.getWords();
        StringBuilder query_str = new StringBuilder();
        String index_dir;
        
        double percisionTotall = 0.0;

        int relevant_retrieved = 0;
        
        TopDocs hits= query.getRetrievedDocuments();
        
       
            for (int i = 0; i < hits.scoreDocs.length; i++) {
            try {
                Document doc=null;
              if(i<hits.scoreDocs.length){
                     doc = Configuration.getSearcher().getIndexSearcher().doc(hits.scoreDocs[i].doc);
                if (Configuration.getAllRelevatnDocuments().contains(doc.get("docName"))) {
                    relevant_retrieved++;                    
                }
              }
              else{
                  System.out.println("**********");
              }
            } catch (IOException ex) {
                Logger.getLogger(Percision.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            percisionTotall = (double) relevant_retrieved / hits.scoreDocs.length;
                   // int rank = j;
            // j++;
            //buffer.write("EA"+(i+1) + "\t" + "Q0 \t" + doc.get("doc_num") + "\t" + rank + "\t" + scoreDoc.score + "\t fam \n");

       
        
        return percisionTotall;

    }

    @Override
    public double objectiveFunction(ParetoObject paretoObject) {
     
        return objectiveFunction(paretoObject.getChromosome());
    }

    @Override
    public String getAxisTitle() {
        return AXIS_TITLE;
    }

    
}
