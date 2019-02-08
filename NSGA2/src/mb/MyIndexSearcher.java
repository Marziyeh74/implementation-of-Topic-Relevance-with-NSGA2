/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mb.analyzer.MyStandardAnalyzer;
import mb.runGeneticAlgorithm.Configuration;
import mb.runGeneticAlgorithm.Recall;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Azade
 */
public class MyIndexSearcher {

    private IndexSearcher indexSearcher = null;
    
     private String index = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\";
   
    MyStandardAnalyzer analyzer = null;
    

    public MyIndexSearcher() {
        
    }

    public IndexSearcher getIndexSearcher(){
        return this.indexSearcher;
    }
    
    public TopDocs search(String indexDir, String q,int num_retreived_docs)
            throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

       // System.out.println("**********search");
        IndexReader rdr = DirectoryReader.open(FSDirectory.open(
                                Paths.get(indexDir)));
        indexSearcher = new IndexSearcher(rdr);
        
       analyzer = new MyStandardAnalyzer(new StandardAnalyzer().getStopwordSet());
       
        QueryParser parser
                = new QueryParser("content", analyzer);

        Query query =null;
        TopDocs hits=null;
        try{
         query = parser.parse(q);
          hits = indexSearcher.search(query, num_retreived_docs);
        
        }
        catch(Exception queryNotParsedE){
            hits=null;
        }

        
       
        return hits;

    }

     public TopDocs getRetrievedDocuments(mb.runGeneticAlgorithm.Query query,int size)
    {
        
      //  System.out.println("getRetrievedDocuments");
         
        List<String> queryTerms = query.getWords();
        StringBuilder query_str = new StringBuilder();
        String index_dir;
          if(Configuration.getTypeIndex().equalsIgnoreCase("traing"))
        {
            index_dir=index+"traing_index";
        }
        else{
            index_dir= index+"testing_index";
        }

       
      
            
            for (int i = 0; i < queryTerms.size(); i++) {
                query_str.append(" " + queryTerms.get(i));
                
            }
            
            TopDocs hits = null;
            System.out.println("query "+query.getUniqueID()+", terms="+query_str.toString());
        try {
            hits = search(index_dir, query_str.toString(), size);
        } catch (IOException ex) {
            Logger.getLogger(Recall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Recall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            Logger.getLogger(Recall.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return hits;
            
       
    }
  
  
}
