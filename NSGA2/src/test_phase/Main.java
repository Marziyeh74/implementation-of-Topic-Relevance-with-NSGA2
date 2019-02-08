/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_phase;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mb.runGeneticAlgorithm.Configuration;

/**
 *
 * @author Marziyeh
 */
public class Main {

    public static void main(String[] args) {
        try {
            int topic_num = 439;
            Configuration.setTopic_num(topic_num);
            Configuration.setAllRelevantDocumnets();
            Configuration.setSearcher();
           // ReadFromExcel.readXLSFile_bestQueriesOfGenerations("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\295_50gen_50query\\bestQueries_Normal.xls", topic_num);
            //paretoFront_child_Normal
            //ReadFromExcel.readXLSFile_paretoFront("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\295_50gen_50query\\paretoFront_child_Normal.xls", topic_num);
        
           //ReadFromExcel.readXLSFile_paretoFront("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\295_50gen_50query\\paretoFront_nextChild_Normal.xls", topic_num);
        ////////////////////////////////////////////////////
            //ReadFromExcel.readXLSFile_bestQueriesOfGenerations("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\295_100gen_100query\\bestQueries_Normal_ 1.xls", topic_num);
            //paretoFront_child_Normal
            //ReadFromExcel.readXLSFile_paretoFront("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\295_100gen_100query\\paretoFront_nextChild_Normal_ 1.xls", topic_num);
        ///////////////////439////////////////////
           // ReadFromExcel.readXLSFile_bestQueriesOfGenerations("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\439_50gen_50individul\\bestQueries_Normal.xls", topic_num);
            //paretoFront_child_Normal
            ReadFromExcel.readXLSFile_paretoFront("D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\439_50gen_50individul\\paretoFront_child_Normal.xls", topic_num);
        
           
        
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
