/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class Test {
    
    public static void main(String[] args)
    {
        ArrayList<String> arr = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            arr.add("f_"+i);
            
        }
        if (arr.contains("f_0"))
                {
                    System.out.println("found");
                }
        
        //MyIndexReader reader = new MyIndexReader(295);
        //reader.getTermsOfTrainingIndex( "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\traing_index");
        
       // reader.getTermsOFIndex();
       
        //topic=295
        MyIndexSearcher searcher = new MyIndexSearcher();
        String indexdir="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\traing_index";
        String q="stencil us appli bodypaint utensil";
        int num_retreived_docs=113;
        TopDocs hits=null;
        try {
           hits= searcher.search(indexdir, q, num_retreived_docs);
           
            for (int i=0; i<hits.scoreDocs.length;i++) {
                Document doc = searcher.getIndexSearcher().doc(hits.scoreDocs[i].doc);
                System.out.println(doc.get("docName")+" ,score"+hits.scoreDocs[i].score);

            }
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
       
    }
    
}
