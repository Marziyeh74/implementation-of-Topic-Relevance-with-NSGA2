/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mb.MyIndexSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class Percision extends ObjectiveFunction {

    // private static final String AXIS_TITLE = "pow(x, 2)";
    private static final String AXIS_TITLE = "percision@10";
    
    @Override
    public double objectiveFunction(Query query) {

        //System.out.println("Percison");
       
        List<String> queryTerms = query.getWords();
        StringBuilder query_str = new StringBuilder();
        String index_dir;
        
        double percisionAt10 = 0.0;

        int relevant_retrieved = 0;
        
        TopDocs hits= query.getRetrievedDocuments();
        
       
            for (int i = 0; i < 10; i++) {
            try {
                Document doc=null;
              if(i<hits.scoreDocs.length){
                     doc = Configuration.getSearcher().getIndexSearcher().doc(hits.scoreDocs[i].doc);
                       String topic_num_part= doc.get("docName").split("_")[0];
                if(Integer.parseInt(topic_num_part)==query.getTopic_num()){
                     String doc_num = doc.get("docName").split("_")[1];
                if (Configuration.getAllRelevatnDocuments().contains(doc_num)) {
                    relevant_retrieved++;                    
                }
              }
              }
              else{
                  System.out.println("**********");
              }
            } catch (IOException ex) {
                Logger.getLogger(Percision.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            percisionAt10 = (double) relevant_retrieved / 10;
                   // int rank = j;
            // j++;
            //buffer.write("EA"+(i+1) + "\t" + "Q0 \t" + doc.get("doc_num") + "\t" + rank + "\t" + scoreDoc.score + "\t fam \n");

       
        
        return percisionAt10;

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
