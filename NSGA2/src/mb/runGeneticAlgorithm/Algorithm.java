/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.runGeneticAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*import main.java.io.onclave.nsga.ii.api.GraphPlot;
 import main.java.io.onclave.nsga.ii.api.Reporter;
 import main.java.io.onclave.nsga.ii.datastructure.Chromosome;*/
import org.jfree.ui.RefineryUtilities;
import output.WriteToExcel;

/**
 *
 * @author Marziyeh
 */
public class Algorithm {

    List<Query> bestQueries = new ArrayList<Query>();
    List<Performance> meanValues = new ArrayList<Performance>();

    public void run(int topic_num, String setting) throws IOException {

        int partno = 1;

        /* prepares the objectives [See Configuration.java file for more information.] */
        Configuration.buildObjectives();
        Configuration.setTopic_num(topic_num);
        Configuration.setAllRelevantDocumnets();
        Configuration.setSearcher();
        // GraphPlot multiPlotGraph = new GraphPlot();

        /**
         * a new random population is synthesized and sorted using non-dominated
         * population sort to get a sorted list of parent chromosomes at
         * generation 0. child population generated from parent population.
         */
        Population parent = Service.nonDominatedPopulationSort(Synthesis.syntesizePopulation(topic_num));
        parent.setTopicNum(topic_num);
        bestQueries.add(calculateBestQueryOfGeneration(parent.getPopulation()));
        meanValues.add(calculateMeanPerformance(parent.getPopulation()));

        Population child = Synthesis.synthesizeChild(parent, topic_num, 2);

        /**
         * a loop is run that iterates as new generations are created (new child
         * population is created from previous parent population. the number of
         * generations to be simulated are defined in the Configuration.java
         * file.
         */
        Population nextChildPopulation = null;
        List<Query> childPopulace = null;

        for (int i = 2; i <= Configuration.getGENERATIONS(); i++) {

            System.out.println("GENERATION : " + i);
            try {
               // bestQueries.add(calculateBestQueryOfGeneration(child.getPopulation()));
                 bestQueries.add(calculateBestQueryOfGeneration(Service.createCombinedPopulation(parent, child).getPopulation()));
                //meanValues.add(calculateMeanPerformance(child.getPopulation()));
                 meanValues.add(calculateMeanPerformance(Service.createCombinedPopulation(parent, child).getPopulation()));

                /**
                 * a combined population of both latest parent and child is
                 * created to ensure elitism. the combined population created is
                 * then sorted using fast non-dominated sorting algorithm, to
                 * create rank wise divisions [chromosomes with rank 1
                 * (non-dominated), chromosomes with rank 2 (dominated by 1
                 * chromosome), etc.] this information is stored in a HashMap
                 * data-structure that maps one integer value to one list of
                 * chromosomes. The integer refers to the rank number while the
                 * list refers to the chromosomes that belong to that rank.
                 */
                HashMap<Integer, List<Query>> rankedFronts = Service.fastNonDominatedSort(Service.createCombinedPopulation(parent, child));

                nextChildPopulation = new Population();
                childPopulace = new ArrayList<>();

                /**
                 * an iteration is carried over the HashMap to go through each
                 * rank of chromosomes, and the most desired chromosomes (higher
                 * ranks) are included into the child population of the next
                 * generation.
                 */
                for (int j = 1; j <= rankedFronts.size(); j++) {

                    /**
                     * during iteration, the current ranked list of chromosomes
                     * is chosen and the amount of free space (to accommodate
                     * chromosomes) of the current child population is
                     * calculated to check whether chromosomes from this rank
                     * can be fit into the new child population.
                     */
                    List<Query> singularFront = rankedFronts.get(j);
                    int usableSpace = Configuration.getPOPULATION_SIZE() - childPopulace.size();

                    /**
                     * if the new list of chromosomes is not null and if the
                     * child population has free usable space, then an attempt
                     * to include some or all of the chromosomes is made
                     * otherwise, there is no more space in the child population
                     * and hence no more rank/chromosome checks are done and the
                     * program breaks out from the inner for-loop.
                     */
                    if (singularFront != null && !singularFront.isEmpty() && usableSpace > 0) {

                        /**
                         * if the amount of usable space is more than or equal
                         * to the number of chromosomes in the clot, the whole
                         * clot of chromosomes is added to the child
                         * population/populace, otherwise, only the number of
                         * chromosomes that can be fit within the usable space
                         * is chosen according to the crowding distance of the
                         * chromosomes.
                         */
                        if (usableSpace >= singularFront.size()) {
                            childPopulace.addAll(singularFront);
                        } else {

                            /**
                             * a crowd comparison sort is carried over the
                             * present clot of chromosomes after assigning them
                             * a crowding distance (to preserve diversity) and
                             * hence a list of ParetoObjects are prepared.
                             * [refer ParetoObject.java for more information]
                             */
                            List<ParetoObject> latestFront = Service.crowdComparisonSort(Service.crowdingDistanceAssignment(singularFront));

                            for (int k = 0; k < usableSpace; k++) {
                                if (k < latestFront.size()) {
                                    childPopulace.add(latestFront.get(k).getChromosome());
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }

                /**
                 * the new populace is added to the new child population
                 */
                nextChildPopulation.setPopulace(childPopulace);
                System.out.println("parent_sieze" + parent.getPopulation().size()
                        + " , child_size=" + child.getPopulation().size()
                        + ", nextChildPopulaion=" + nextChildPopulation.getPopulation().size());

                if (i %25==0) {
                    String bestQueries_file = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                            + topic_num + "\\bestQueries_" + setting +"_ "+partno+".xls";
                    String meanValues_file = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                            + topic_num + "\\meanValues_" + setting +"_ "+partno+".xls";
                    String paretorFront_child = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                            + topic_num + "\\paretoFront_child_" + setting +"_ "+partno+ ".xls";
                    String paretorFront_nextChild = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                            + topic_num + "\\paretoFront_nextChild_" + setting +"_ "+partno+ ".xls";

                    WriteToExcel.writeXLSFile_bestQueriesOfGenerations(bestQueries_file, bestQueries);
                    WriteToExcel.writeXLSFile_childs(paretorFront_child, child.getPopulation());
                    WriteToExcel.writeXLSFile_childs(paretorFront_nextChild, nextChildPopulation.getPopulation());
                    WriteToExcel.writeXLSFile_meanPerformance(meanValues_file, meanValues);
                    partno++;

                }
                /**
                 * if this iteration is not the last generation, the new child
                 * created is made the parent for the next generation, and a new
                 * child is synthesized from this new parent for the next
                 * generation. this is the new parent and child for the next
                 * generation. if this is the last generation, no new
                 * parent/child combination is created, instead the Pareto Front
                 * is plotted and rendered as the latest created child is the
                 * actual Pareto Front.
                 */
                if (i < Configuration.getGENERATIONS()) {
                    parent = child;
                    child = Synthesis.synthesizeChild(nextChildPopulation, topic_num, i + 1);
                    System.out.println("if");
                } //else 
                //Reporter.render2DGraph(child);

                /**
                 * this adds the child of each generation to the plotting to
                 * render the front of all the generations.
                 */
                // multiPlotGraph.prepareMultipleDataset(child, i, "generation " + i);
            } catch (Exception e) {
                System.out.println("error");
                e.printStackTrace();
                break;
            }
        }

        System.out.println("\n\n----CHECK PARETO FRONT OUTPUT----\n\n");

        /*System.out.println("best queries..........");
         for (int i = 0; i < bestQueries.size(); i++) {
         Query get = childPopulace.get(i);
         System.out.println("query "+get.getUniqueID()+", percision="+get.getPercision()+", recall="+get.getRecall());
            
         }*/
        //wirte resluts
        String bestQueries_file = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                + topic_num + "\\bestQueries_" + setting + ".xls";
        String meanValues_file = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                + topic_num + "\\meanValues_" + setting + ".xls";
        String paretorFront_child = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                + topic_num + "\\paretoFront_child_" + setting + ".xls";
        String paretorFront_nextChild = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\results\\"
                + topic_num + "\\paretoFront_nextChild_" + setting + ".xls";

        WriteToExcel.writeXLSFile_bestQueriesOfGenerations(bestQueries_file, bestQueries);
        WriteToExcel.writeXLSFile_childs(paretorFront_child, child.getPopulation());
        WriteToExcel.writeXLSFile_childs(paretorFront_nextChild, nextChildPopulation.getPopulation());
        WriteToExcel.writeXLSFile_meanPerformance(meanValues_file, meanValues);

        /**
         * the plotted and rendered chart/graph is viewed to the user.
         */
       // multiPlotGraph.configureMultiplePlotter(main.java.io.onclave.nsga.ii.configuration.Configuration.getXaxisTitle(), main.java.io.onclave.nsga.ii.configuration.Configuration.getYaxisTitle(), "All Pareto");
        // multiPlotGraph.pack();
        //RefineryUtilities.centerFrameOnScreen(multiPlotGraph);
        //multiPlotGraph.setVisible(true);
    }

    /* public Query calculateMeanQuery(Population queries)
     {
        
        
     }*/
    public Query calculateBestQueryOfGeneration(List<Query> population) {

        double max_fitness = 0;
        Query bestQuery = new Query();

        bestQuery = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            Query q = population.get(i);
            //if(q.getFitness()>max_fitness){
            if (q.getPercision() > bestQuery.getPercision() || q.getRecall() > bestQuery.getRecall()) {
                bestQuery.setDominatedChromosomes(q.getDominatedChromosomes());
                bestQuery.setDominationRank(q.getDominationRank());
                bestQuery.setPercision(q.getPercision());
                bestQuery.setRecall(q.getRecall());
                bestQuery.setRetrievedDocuments(q.getRetrievedDocuments());
                bestQuery.setSize(q.getSize());
                bestQuery.setTopic_num(q.getTopic_num());
                bestQuery.setUniqueID(q.getUniqueID());
                bestQuery.setWords(q.getWords());

                bestQuery.setFitness();

            }

        }
        return bestQuery;
    }

    public Performance calculateMeanPerformance(List<Query> population) {
        double percison = 0.0;
        double recall = 0.0;
        double f_measure = 0.0;
        for (int i = 0; i < population.size(); i++) {
            Query q = new Query();

            percison += q.getPercision();
            recall += q.getRecall();
            f_measure += q.getFitness();

        }

        percison = percison / population.size();
        recall = recall / population.size();
        f_measure = f_measure / population.size();

        Performance p = new Performance(percison, recall, f_measure);
        return p;
    }

}
