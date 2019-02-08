/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;

import java.util.List;

import org.apache.lucene.index.Terms;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Marziyeh
 */
public class Query {
    
   /* private Allele[] geneticCode;
    private double fitness;
    private String extraInfo;
    private int dominationRank = 0;
    private String uniqueID;
    private List<Chromosome> dominatedChromosomes;*/
    
    private double fitness;
    private double percision;
    
    private double recall;
    private String uniqueID;
    private int dominationRank = 0;
    private List<String> words;
    private String terms;
    private int size;
    private int topic_num;
    private List<Query> dominatedChromosomes;
    private TopDocs retrievedDocuments;
    
    public Query()
    {
        retrievedDocuments=null;
       // setSize();
        
    }
    

    public Query(double fitness,String uniqueID,List<String> words,int topic_num,int size )
    {
        setFitness();
        setUniqueID(uniqueID);
        setTopic_num(topic_num);
        setWords(words);
        setSize(size);
        
    }
    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness() {
        // calculate f-measure
        this.fitness= (2* this.percision*this.recall)/(this.percision + this.recall);
        if ((this.percision==0) && (this.recall==0)){
            this.fitness=0;
        }
        //this.fitness = fitness;
    }

    /**
     * @return the uniqueID
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * @param uniqueID the uniqueID to set
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     * @return the dominationRank
     */
    public int getDominationRank() {
        return dominationRank;
    }

    /**
     * @param dominationRank the dominationRank to set
     */
    public void setDominationRank(int dominationRank) {
        this.dominationRank = dominationRank;
    }

    /**
     * @return the words
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(List<String> words) {
        this.words = words;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        
        this.size = size;
    }

    /**
     * @return the topic_num
     */
    public int getTopic_num() {
        return topic_num;
    }

    /**
     * @param topic_num the topic_num to set
     */
    public void setTopic_num(int topic_num) {
        this.topic_num = topic_num;
    }
    
    public List<Query> getDominatedChromosomes() {
        return dominatedChromosomes;
    }

    public void setDominatedChromosomes(List<Query> dominatedChromosomes) {
        this.dominatedChromosomes = dominatedChromosomes;
    }

    /**
     * @return the percision
     */
    public double getPercision() {
        return percision;
    }

    /**
     * @param percision the percision to set
     */
    public void setPercision(double percision) {
        this.percision = percision;
    }

    /**
     * @return the recall
     */
    public double getRecall() {
        return recall;
    }

    /**
     * @param recall the recall to set
     */
    public void setRecall(double recall) {
        this.recall = recall;
    }
    
    public void setRetrievedDocuments(TopDocs hits)
    {
        this.retrievedDocuments=hits;
    }
    
    public TopDocs getRetrievedDocuments()
    {
        return this.retrievedDocuments;
        
    }
    
    public void setTerms(String terms){
        this.terms=terms;
    }
    public String getTerms(){
        return this.terms;
    }
    
    
    
}
