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
import mb.MyIndexSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class ObjectiveFunction implements IObjectiveFunction {

    protected MyIndexSearcher searcher;
    protected String index = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\";

    @Override
    public double objectiveFunction(Query query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double objectiveFunction(ParetoObject paretoObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAxisTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Document> getRelevantRetrievedDocuments(TopDocs retrivedDocs, int topic_num,MyIndexSearcher searcher) {
        ArrayList<Document> relevantRetrievedDocs = new ArrayList<Document>();

        try {
            /*for (int i = 0; i < retrivedDocs.scoreDocs.length; i++) {
      
             Document doc = searcher.getIndexSearcher().doc(retrivedDocs.scoreDocs[i].doc);
             if (Configuration.getAllRelevatnDocuments().contains(doc.get("docName"))) {
             relevantRetrievedDocs.add(doc);
             }
             }*/

            for (ScoreDoc scoreDoc : retrivedDocs.scoreDocs) {
                Document doc = searcher.getIndexSearcher().doc(scoreDoc.doc);

                 String doc_num = doc.get("docName").split("_")[1];
               //  System.out.println("docName=" + doc.get("docName") + ", doc_num=" + doc_num);

                String topic_num_part= doc.get("docName").split("_")[0];
                if(Integer.parseInt(topic_num_part)==topic_num){
               
                
                if (Configuration.getAllRelevatnDocuments().contains(doc_num)) {
                    relevantRetrievedDocs.add(doc);
                  //  System.out.println("relevant");
                }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ObjectiveFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return relevantRetrievedDocs;
    }

}
