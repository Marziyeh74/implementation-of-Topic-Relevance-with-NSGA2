/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mb.MyIndexReader;
import mb.MyIndexSearcher;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class Synthesis {
    
     private static final Random LOCAL_RANDOM = new Random();
    
    /**
     * depending on the settings available in the Configuration.java file, this method synthesizes a
     * random population of chromosomes with pseudo-randomly generated genetic code for each chromosome.
     * 
     * @return  a randomly generated population
     */
    public static Population syntesizePopulation(int topic_num) {
        
        List<Query> populace = new ArrayList<>();
        
        /**
         * the number of chromosomes in the population is received from the Configuration.java file
         */
        /**
         * Every choromosome has random size between 1 and 32, and terms of a query selected randomly from
         * the mutaion pool
         * 
         */
        for(int i = 0; i < Configuration.getPOPULATION_SIZE(); i++) {
            
            
            Query chromosome = new Query();
            
            chromosome.setSize((int) (Math.random() * 31 + 1));
            int query_size=chromosome.getSize();
            MyIndexReader indexreader= new MyIndexReader(topic_num);
           chromosome.setTopic_num(topic_num);
           chromosome.setUniqueID("gen1_"+i);
           chromosome.setWords(indexreader.getSelectedTermsForQuery(query_size));
           
           
            //MyIndexSearcher searcher =new MyIndexSearcher();
            TopDocs  hits = Configuration.getSearcher().getRetrievedDocuments(chromosome,Configuration.getAllRelevatnDocuments().size());
            if(hits==null){
                i = i-1;
                chromosome=null;
                continue;
            }
            chromosome.setRetrievedDocuments(hits);
            ObjectiveFunction percision= new Percision();
           ObjectiveFunction recall = new Recall();
         
         //  chromosome.setRecall(recall.objectiveFunction(chromosome, null,hits,searcher));
           
           chromosome.setRecall(recall.objectiveFunction(chromosome));
           
          chromosome.setPercision(percision.objectiveFunction(chromosome));
           
        
           chromosome.setFitness();
          System.out.println("query "+chromosome.getUniqueID()+" ,persicionAt10="+chromosome.getPercision()+" , recall="+chromosome.getRecall());
           
          // System.out.println("query "+chromosome.getUniqueID()+",percision="+chromosome.getPercision()+",recall="+chromosome.getRecall());
           //done in recall class
          //indexreader.addTermsToMutaionPool(recall.getRelevantRetrievedDocuments(hits));
           
           // chromosome.setGeneticCode(synthesizeGeneticCode(main.java.io.onclave.nsga.ii.configuration.Configuration.getCHROMOSOME_LENGTH()));
            populace.add(chromosome);
        }
        
     
        return new Population(populace);
    }
    
    /**
     * a child population of the same size as the parent is synthesized from the parent population
     * 
     * @param   parent  the parent population object
     * @return          a child population synthesized from the parent population
     */
    public static Population synthesizeChild(Population parent,int topic_num,int generation_num) {
        
      Population child = new Population();
        List<Query> populace = new ArrayList<>();
        
        /**
         * child chromosomes undergo crossover and mutation.
         * the child chromosomes are selected using binary tournament selection.
         * crossover returns an array of exactly two child chromosomes synthesized from two parent
         * chromosomes.
         */
        int count=0;
        while(populace.size() < Configuration.getPOPULATION_SIZE()){
           // for(Query chromosome : crossover(binaryTournamentSelection(parent), binaryTournamentSelection(parent),topic_num,generation_num))
               // populace.add(mutation(chromosome));
            Query[] chromosomes=crossover(binaryTournamentSelection(parent), binaryTournamentSelection(parent),topic_num);
            if(chromosomes[0].getUniqueID()==null){
            chromosomes[0].setUniqueID("gen"+generation_num+"_"+count);
            count++;
            }
            if(chromosomes[1].getUniqueID()==null){
            chromosomes[1].setUniqueID("gen"+generation_num+"_"+count);
            count++;
            }
            populace.add(mutation(chromosomes[0]));
            populace.add(mutation(chromosomes[1]));
                    
            
        }
        
        child.setPopulace(populace);
        child.setTopicNum(topic_num);
        
        //add coded
        for (int i = 0; i < populace.size(); i++) {
            Query chromosome = populace.get(i);
            if(chromosome.getRetrievedDocuments()==null){
        
        MyIndexSearcher searcher =new MyIndexSearcher();
            TopDocs  hits = searcher.getRetrievedDocuments(chromosome,Configuration.getAllRelevatnDocuments().size());
             if(hits==null){
                i = i-1;
                chromosome=null;
                continue;
            }
             
            chromosome.setRetrievedDocuments(hits);
            
            ObjectiveFunction percision= new Percision();
           ObjectiveFunction recall = new Recall();
         
           chromosome.setPercision(percision.objectiveFunction(chromosome));
           chromosome.setRecall(recall.objectiveFunction(chromosome));
        
           //System.out.println("query "+chromosome.getUniqueID()+" ,persicion@10="+chromosome.getPercision()+" , recall="+chromosome.getRecall());
           chromosome.setFitness();
           
            }
            System.out.println("query "+chromosome.getUniqueID()
                   + ",percision@10="+chromosome.getPercision()+",recall="+chromosome.getRecall());
          
          
        
        }
        return child;
    }
    
    /**
     * this is an implementation of basic binary tournament selection.
     * for a tournament of size t, select t individuals (randomly) from population and determine winner of
     * tournament with the highest fitness value.
     * in case of binary tournament selection, t = 2.
     * 
     * refer [https://stackoverflow.com/questions/36989783/binary-tournament-selection] for more information.
     * 
     * @param   population  the population from which a child chromosome is to be selected
     * @return              the selected child chromosome
     */
    private static Query binaryTournamentSelection(Population population) {
        
        Query individual1 = population.getPopulation().get(LOCAL_RANDOM.nextInt(population.getPopulation().size()));
        Query individual2 = population.getPopulation().get(LOCAL_RANDOM.nextInt(population.getPopulation().size()));
        
        if(individual1.getFitness() > individual2.getFitness()) return individual1; else return individual2;
    }
    
    /**
     * this is a basic implementation of uniform crossover where the crossover/break point is the middle
     * of the chromosomes. The genetic code of both the parent chromosomes are broken from the middle
     * and crossover is done to create two child chromosomes.
     * crossover probability is considered.
     * 
     * @param   chromosome1     the first parent chromosome taking part in crossover
     * @param   chromosome2     the second parent chromosome taking part in crossover
     * @return                  an array of exactly two child chromosomes synthesized from two parent chromosomes.
     */
    public static Query[] crossover_old(Query chromosome1, Query chromosome2) {
        
         Query[] childChromosomes = {new Query(), new Query()};
        if(LOCAL_RANDOM.nextFloat() >= Configuration.getCROSSOVER_PROBABILITY()){
            
             childChromosomes[0] = chromosome1;
            childChromosomes[1] = chromosome2;
        }
        else{
             /**
         * generating a new random float value and if this value is less than equal to the
         * crossover probability mentioned in the Configuration file, then crossover occurs,
         * otherwise the parents themselves are copied as child chromosomes.
         */
            
            int breakPoint = (int) (Math.random() * (Math.min(chromosome1.getSize(),chromosome2.getSize())));
            childChromosomes[0].setSize((int) (Math.random() * (Math.max(chromosome1.getSize(), chromosome2.getSize()))));
        
            childChromosomes[1].setSize((int) (Math.random() * (Math.max(chromosome1.getSize(), chromosome2.getSize()))));
            List<String> geneticCode1= new ArrayList<String>();
            List<String> geneticCode2= new ArrayList<String>();
            
            for(int i=0; i<childChromosomes[0].getSize();i++)
            {
                if(i <= breakPoint) {
                    geneticCode1.add(chromosome1.getWords().get(i));
                   
                } else {
                   
                    if(chromosome2.getSize()<=breakPoint){
                    geneticCode1.add(chromosome2.getWords().get(chromosome2.getSize()-1));
                    }
                    else{
                         geneticCode1.add(chromosome2.getWords().get(i));
                    }
                }
            }
            
              for(int i=0; i<childChromosomes[1].getSize();i++)
            {
                if(i <= breakPoint) {
                    geneticCode2.add(chromosome2.getWords().get(i));
                   
                } else {
                    if(chromosome1.getSize()==breakPoint){
                    geneticCode2.add(chromosome1.getWords().get(chromosome1.getSize()-1));
                    }
                    else{
                         geneticCode2.add(chromosome1.getWords().get(i));
                    }
                }
            }
        
              chromosome1.setWords(geneticCode1);
              chromosome2.setWords(geneticCode2);
              
        }
       
        
        return childChromosomes;
    }
    
    public static Query[] crossover(Query chromosome1, Query chromosome2,int topic_num) {
        
         Query[] childChromosomes = {new Query(), new Query()};
         
        if(LOCAL_RANDOM.nextFloat() >= Configuration.getCROSSOVER_PROBABILITY()){
            
            childChromosomes[0] = chromosome1;
            childChromosomes[1] = chromosome2;
        }
        else{
             /**
         * generating a new random float value and if this value is less than equal to the
         * crossover probability mentioned in the Configuration file, then crossover occurs,
         * otherwise the parents themselves are copied as child chromosomes.
         */
            childChromosomes[0].setSize((int) (Math.random() * 31 + 1));
            childChromosomes[1].setSize((int) (Math.random() * 31 + 1));
            int breakPoint = (int) (Math.random() * 31 + 1);
            List<String> geneticCode1= new ArrayList<String>();
            List<String> geneticCode2= new ArrayList<String>();
            
            for(int i=0; i<childChromosomes[0].getSize();i++)
            {
                if(i <= breakPoint ) {
                     int j=i%chromosome1.getSize();
                   
                      //System.out.println("j="+j+" ,size="+chromosome1.getSize()+", b="+breakPoint);
                        geneticCode1.add(chromosome1.getWords().get(j));
                    
                        
                   
                } else {
                   
                    int j=i%chromosome2.getSize();
                   
                     //System.out.println("j="+j+" ,size="+chromosome2.getSize()+", b="+breakPoint);
                     try{
                         geneticCode1.add(chromosome2.getWords().get(j));
                     }
                     catch(java.lang.IndexOutOfBoundsException e){
                         System.out.println("j="+j+" ,size_p1="+chromosome1.getSize()+
                                 ",size_p2="+chromosome2.getSize()
                                  + ",size_ch1="+childChromosomes[0].getSize()
                                 +"b="+breakPoint);
                     }
                    
                }
            }
            
              for(int i=0; i<childChromosomes[1].getSize();i++)
            {
                if(i <= breakPoint) {
                     int j=i%chromosome2.getSize();
                     // System.out.println("j="+j+" ,size="+chromosome2.getSize()+", b="+breakPoint);
                    geneticCode2.add(chromosome2.getWords().get(j));
                   
                } else {
                   
                      int j=i%chromosome1.getSize();
                      //System.out.println("j="+j+" ,size="+chromosome1.getSize()+", b="+breakPoint);
                      try{
                         geneticCode2.add(chromosome1.getWords().get(j));
                      }catch(java.lang.IndexOutOfBoundsException e)
                      {
                          System.out.println("j="+j+" ,size_p1="+chromosome1.getSize()+
                                 ",size_p2="+chromosome2.getSize()
                                  + ",size_ch2="+childChromosomes[1].getSize()
                                 +"b="+breakPoint);
                      }
                    
                }
            }
        
              childChromosomes[0].setWords(geneticCode1);
              childChromosomes[1].setWords(geneticCode2);
              
        }
       
        childChromosomes[0].setTopic_num(topic_num);
        childChromosomes[1].setTopic_num(topic_num);
        
        return childChromosomes;
    }
    
    /**
     * in this mutation operation implementation, a random bit-flip takes place.
     * a random float value is generated and if this value is less than equal to the mutation
     * probability defined in Configuration, then mutation takes place, otherwise the original
     * chromosome is returned.
     * 
     * @param   chromosome  the chromosome over which the mutation takes place
     * @return              the mutated chromosome
     */
    private static Query mutation(Query chromosome) {
        
        if(LOCAL_RANDOM.nextFloat() <Configuration.getMUTATION_PROBABILITY()) {
            
            System.out.println("mutaion, topic="+chromosome.getTopic_num() );
            String random_word= new MyIndexReader(chromosome.getTopic_num()).getRandomTermRomIndex();
            List<String> genetic_code=chromosome.getWords();
            genetic_code.set(LOCAL_RANDOM.nextInt(chromosome.getSize()), random_word);
            chromosome.setWords(genetic_code);
            
            
        }
        
        return chromosome;
    }
    
   
    
}
