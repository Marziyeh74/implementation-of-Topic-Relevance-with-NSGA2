/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mb.analyzer.MyStandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.StoredFieldVisitor;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

/**
 *
 * @author Marziyeh
 */
public class MyIndexReader {
    private IndexReader reader;
    private String directory_index;
    private  ArrayList<String> words= new ArrayList<String>();
    private int topic_num;
    public MyIndexReader(int topic_num) 
    {
        this.topic_num=topic_num;
        
          
    
}
    
    public  void getTermsOFIndex()
    {
         this.directory_index="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\"
                    + "datases\\topic description\\topic_description_indexes\\index_"+topic_num;
 
            
        try {
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(directory_index)));
           
        } catch (IOException ex) {
            Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
           
            Fields fields=MultiFields.getFields(reader);
            for(String field:fields)
            {
               if(field.equalsIgnoreCase("content")){
                Terms terms= fields.terms(field);
                long size= terms.size();
               
                TermsEnum termsEnum=terms.iterator();
                int i=0;
                
                //BytesRef term=termsEnum.next();
                while(termsEnum.next()!=null)
                {
                   // System.out.print("count="+i);
                    
                 // System.out.println(" "+termsEnum.term().utf8ToString());
                    i++;
                    words.add(termsEnum.term().utf8ToString());
                    
                }
                
                
                
                System.out.println("count="+i);
               }
            }
            // TermsEnum listTerm = reader.
        } catch (IOException ex) {
            Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
        
      
    }
    
    public List<String> getSelectedTermsForQuery(int query_size)
    {
        List<String> query_words=new ArrayList<String>();
        getTermsOFIndex();
       for(int i=0; i<query_size;i++)
       {
           int rand=(int) (Math.random() * words.size());
           String word=this.words.get(rand);
           while(!isSuitableWord(word)){
               rand=(int) (Math.random() * words.size());
            word=this.words.get(rand); 
           }
           query_words.add(word);
       }
       return query_words;
    }
    
  public  boolean stringIsNumber(String str){
        return str.matches("\\d+");
    }
    
    public  boolean englishOrDot(String str){
        //"^[a-zA-Z0-9]+$" accepts english or numberic
        return str.matches("^[a-zA-Z\\.]+$");
    }
    
    public  boolean isSuitableWord(String str){
        if(stringIsNumber(str))
            return false;
        else{
            if(englishOrDot(str))
                return true;
            else
                return false;
        }
    }
    public String getRandomTermRomIndex()
    {
        getTermsOFIndex();
         int rand=(int) (Math.random() * (words.size()-1));
         return this.words.get(rand);
    }
    
    
    
    
    public void addTermsToMutaionPool(ArrayList<Document> relevantRetrievedDocs){
        
          this.directory_index="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\"
                    + "datases\\topic description\\topic_description_indexes\\index_"+topic_num;
 
            
        try {
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(directory_index)));
           
        } catch (IOException ex) {
            Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FSDirectory index_dirctory;
            index_dirctory = FSDirectory.open(Paths.get(this.directory_index));
            MyStandardAnalyzer analyzer = new MyStandardAnalyzer(new StandardAnalyzer().getStopwordSet());
            
            IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
            cfg.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
            
            IndexWriter writer = new IndexWriter(index_dirctory, cfg);
            
            for (int i = 0; i < relevantRetrievedDocs.size(); i++) {
                Document doc = relevantRetrievedDocs.get(i);
                 Document ldoc = new Document();
                //ldoc.add(new StringField("docName",doc.get("docName") , Field.Store.YES));
                FieldType type = new FieldType();
                type.setStored(true);
                type.setStoreTermVectors(true);
                type.setTokenized(true);
                type.setStoreTermVectorPositions(true);
                type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
                ldoc.add(new Field("content",doc.get("content"), type));


                writer.addDocument(ldoc);
                
            }
            writer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    public void getTermsOfTrainingIndex(String dir)
    {
        try {
            System.out.println("getTerms of traing_index:");
            this.directory_index=dir;
            
            File training_words = new File("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\training_words_index.txt");
            
            BufferedWriter buffer = null;
            
            if (!training_words.exists()) {
                training_words.createNewFile();

            }
            buffer = new BufferedWriter(new FileWriter(training_words));
            
            
            try {
                reader = DirectoryReader.open(FSDirectory.open(Paths.get(directory_index)));
                
            } catch (IOException ex) {
                Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                
                Fields fields=MultiFields.getFields(reader);
                for(String field:fields)
                {
                    if(field.equalsIgnoreCase("content")){
                        Terms terms= fields.terms(field);
                        long size= terms.size();
                        
                        TermsEnum termsEnum=terms.iterator();
                        int i=0;
                        
                        //BytesRef term=termsEnum.next();
                        while(termsEnum.next()!=null)
                        {
                            System.out.print("count="+i);
                            
                            System.out.println(" "+termsEnum.term().utf8ToString());
                            
                            i++;
                            words.add(termsEnum.term().utf8ToString());
                            buffer.write("count ="+i+ " "+termsEnum.term().utf8ToString()+"\n");
                          
                        }
                        
                        
                        
                        System.out.println("count="+i);
                        buffer.close();
                    }
                }
                // TermsEnum listTerm = reader.
            } catch (IOException ex) {
                Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(MyIndexReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
}
