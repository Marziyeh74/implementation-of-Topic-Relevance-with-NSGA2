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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marziyeh
 */
public class Population {
    
     public Population() {
        this(null);
    }
    
    public Population(final List<Query> queries) {
        this.queries = queries;
    }
    
    private List<Query> queries;
   // private String topic_files_dir="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\parsed\\Parsed\\";
  

    private int topic_num;
    public List<Query> getPopulation() {
        return queries;
    }

    public void setPopulace(List<Query> queries) {
        this.queries = queries;
    }
    
    public int getTopicNum()
    {
        return topic_num;
    }
    public void setTopicNum(int topic_num){
        this.topic_num=topic_num;
    }
    
     
}
