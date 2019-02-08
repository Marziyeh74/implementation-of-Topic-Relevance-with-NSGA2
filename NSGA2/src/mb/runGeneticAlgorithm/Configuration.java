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
import mb.MyIndexSearcher;


/**
 *
 * @author Marziyeh
 */
public class Configuration {
    
     private static final int POPULATION_SIZE = 50;
    private static final int GENERATIONS = 50;
    private static final int CHROMOSOME_MAX_LENGTH = 32;
    private static final float CROSSOVER_PROBABILITY = 0.7f;
    private static final float MUTATION_PROBABILITY = 0.7f;
    private static List<IObjectiveFunction> objectives = null;
   
    
    //Other paramerter of our problem:
    private static final String type_index="testing";
     private static int TOPIC_NUM;
      private static String topic_files_dir="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\parsed\\Parsed\\";
  
      private static   ArrayList<String> relevantDocs;
      private static MyIndexSearcher searcher;
   
    
 /*   public static final double ACTUAL_MIN = 0;
    public static final double ACTUAL_MAX = Math.pow(2, CHROMOSOME_LENGTH) - 1;
    public static final double NORMALIZED_MIN = 0;
    public static final double NORMALIZED_MAX = 2;
    public static final String DEFAULT_X_AXIS_TITLE = "x-axis";
    public static final String DEFAULT_Y_AXIS_TITLE = "y-axis";*/

    public static int getPOPULATION_SIZE() {
        return POPULATION_SIZE;
    }
    
    

    public static int getGENERATIONS() {
        return GENERATIONS;
    }

    public static int getCHROMOSOME_LENGTH() {
        return CHROMOSOME_MAX_LENGTH;
    }
    
   
    public static void buildObjectives() {
        
        List<IObjectiveFunction> newObjectives = new ArrayList<>();
        
        newObjectives.add(new Percision());
        newObjectives.add(new Recall());
        
        setObjectives(newObjectives);
    }

    public static List<IObjectiveFunction> getObjectives() {
        return objectives;
    }

    public static void setObjectives(List<IObjectiveFunction> objectives) {
        Configuration.objectives = objectives;
    }
    
    public static void setSearcher()
    {
        searcher= new MyIndexSearcher();
    }
    public static MyIndexSearcher getSearcher()
    {
        return searcher;
    }

    public static float getMUTATION_PROBABILITY() {
        return MUTATION_PROBABILITY;
    }

    public static float getCROSSOVER_PROBABILITY() {
        return CROSSOVER_PROBABILITY;
    }
    
    public static String getTypeIndex()
    {
        return type_index;
    }
    
    public static void setTopic_num(int topic_num)
    {
        TOPIC_NUM=topic_num;
    }
    
    public static  void setAllRelevantDocumnets() throws IOException
    {
        
      System.out.println("getAllRelevantDocumnets");
         relevantDocs = new ArrayList<String>();
        File file= new File(topic_files_dir+TOPIC_NUM+"_"+type_index+".txt");
        
        FileReader fr = null;
                BufferedReader br = null;

          try {
              fr = new FileReader(file);
               br = new BufferedReader(fr);
                String sCurrentLine = br.readLine();
                while (sCurrentLine != null) {
                    //System.out.println(sCurrentLine);
                    if(sCurrentLine.contains("t_"))
                    {
                        String str=sCurrentLine.split("t_")[1];
                       // str=str.split(".t")[0];
                        relevantDocs.add(str);
                       System.out.println(str);
                    }
                    sCurrentLine = br.readLine();
                }
          } catch (FileNotFoundException ex) {
              Logger.getLogger(Percision.class.getName()).log(Level.SEVERE, null, ex);
          }
               
        
       
        
    }
    
    public static ArrayList<String> getAllRelevatnDocuments()
    {
        return  relevantDocs;
    }
   /* public static String getXaxisTitle() {
        return getObjectives().size() > 2 ? DEFAULT_X_AXIS_TITLE : getObjectives().get(0).getAxisTitle();
    }
    
    public static String getYaxisTitle() {
        return getObjectives().size() > 2 ? DEFAULT_Y_AXIS_TITLE : getObjectives().get(1).getAxisTitle();
    }*/

}
