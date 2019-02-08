/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author Azade
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

        //"126kjhjkh" is not suitable
        //"wwwjkjhk" is suitable
        //"www.kjhjkh.kjk is suitable
        String str="www.kjhjkh.kjk"; 
       if(isSuitableWord(str)){
            System.out.println("is suitable");
       }
       else{
           System.out.println("is not suitable");
       }
        
        
        
      
     /* String training_index="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\training_index";
        String testing_index="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\testing_index";
      
        String topic_desc_dir="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\topic description\\topic description" ;
        String topic_desc_indexes="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\topic description\\topic_description_indexes";
        MyIndex index = new MyIndex(training_index,testing_index);
        index.createTopicIndexes(topic_desc_dir, topic_desc_indexes);*/
       /* index.indexDirecotryOfTopics("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\parsed\\Parsed");
        index.close();

        */
        
        
        
    //  MyIndexReader reader = new MyIndexReader("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\topic description\\topic_description_indexes\\index_1");
      //reader.getSelectedTermsForQuery(32);
//  MyIndexSearcher s = new MyIndexSearcher(choice);
       // if(choice == 5)

       //g s.test(s.readQuery("D:\\Courses of uni\\Term8\\InformaionRetrieval\\project\\dataset\\dataset\\cran.qry"));
        //else 
       //     s.result(s.readQuery("D:\\Courses of uni\\Term8\\InformaionRetrieval\\project\\dataset\\dataset\\cran.qry"));
       
    }
    
    public static boolean stringIsNumber(String str){
        return str.matches("\\d+");
    }
    
    public static boolean englishOrDot(String str){
        //"^[a-zA-Z0-9]+$" accepts english or numberic
        return str.matches("^[a-zA-Z\\.]+$");
    }
    
    public static boolean isSuitableWord(String str){
        if(stringIsNumber(str))
            return false;
        else{
            if(englishOrDot(str))
                return true;
            else
                return false;
        }
    }

   // }

}
