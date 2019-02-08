/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_phase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mb.MyIndexReader;
import mb.runGeneticAlgorithm.Configuration;
import mb.runGeneticAlgorithm.ObjectiveFunction;
import mb.runGeneticAlgorithm.Percision;
import mb.runGeneticAlgorithm.Query;
import mb.runGeneticAlgorithm.Recall;
import org.apache.lucene.search.TopDocs;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import output.WriteToExcel;

/**
 *
 * @author Marziyeh
 */
public class ReadFromExcel {
    public static void readXLSFile_bestQueriesOfGenerations(String excelFile,int topic_num) throws IOException
	{
            
        List<Query> queries = new ArrayList<Query>();
		InputStream ExcelFileToRead = new FileInputStream(excelFile);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
                        Query q = new Query();
			Iterator cells = row.cellIterator();
			int count=1;
			while (cells.hasNext())
			{
                            
				cell=(HSSFCell) cells.next();
		
                                //gen_no
                                if(count==1){
				
					System.out.print(cell.getNumericCellValue()+" ");
				}
                                if(count==2){
                                    q.setUniqueID(cell.getStringCellValue());
                                    System.out.print(cell.getStringCellValue()+" ");
                                    
                                }
                                if(count==3){
                                    System.out.print(cell.getStringCellValue()+" ");
                                    String[] words = cell.getStringCellValue().split(" ");
                                    List<String> terms = new ArrayList<String>();
                                    for(int i=0; i<words.length;i++)
                                    {
                                        
                                        terms.add(words[i]);
                                        
                                    }
                                    q.setWords(terms);
                                    q.setTerms(cell.getStringCellValue());
                                }
                                
                                 if(count==4){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setPercision(cell.getNumericCellValue());
                                }
                                 
                                 if(count==5){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setRecall(cell.getNumericCellValue());
                                }
                                 
                                 if(count==6){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setFitness();
                                }
                                 
                                  if(count==7){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setDominationRank((int) cell.getNumericCellValue());
                                }
                                  
                                    
                                  if(count==8){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setSize((int) cell.getNumericCellValue());
                                }
                                  count++;
                                 
                                  
				
			}
                         queries.add(q);
			
		}
                System.out.println("finish");
                
                
                for (int i = 0; i < queries.size(); i++) {
                Query chromosome = queries.get(i);
                 
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
              
        chromosome.setTopic_num(topic_num);
           chromosome.setRecall(recall.objectiveFunction(chromosome));
           
          chromosome.setPercision(percision.objectiveFunction(chromosome));
        
           chromosome.setFitness();
          
                
            }
                String writeExcel="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\"
                        + "datases\\results\\439_50gen_50individul\\test\\"+"bestQueries_Normal_test.xls";
                WriteToExcel.writeXLSFile_bestQueriesOfGenerations(writeExcel, queries);
	
	}
    
     public static void readXLSFile_paretoFront(String excelFile,int topic_num) throws IOException
	{
            
        List<Query> queries = new ArrayList<Query>();
		InputStream ExcelFileToRead = new FileInputStream(excelFile);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
                        Query q = new Query();
			Iterator cells = row.cellIterator();
			int count=1;
			while (cells.hasNext())
			{
                            
				cell=(HSSFCell) cells.next();
		
                                //number
                                if(count==1){
				
					System.out.print(cell.getNumericCellValue()+" ");
				}
                                if(count==2){
                                    q.setUniqueID(cell.getStringCellValue());
                                    System.out.print(cell.getStringCellValue()+" ");
                                    
                                }
                                if(count==3){
                                    System.out.print(cell.getStringCellValue()+" ");
                                    String[] words = cell.getStringCellValue().split(" ");
                                    List<String> terms = new ArrayList<String>();
                                    for(int i=0; i<words.length;i++)
                                    {
                                        
                                        terms.add(words[i]);
                                        
                                    }
                                    q.setWords(terms);
                                    q.setTerms(cell.getStringCellValue());
                                }
                                
                                 if(count==4){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setPercision(cell.getNumericCellValue());
                                }
                                 
                                 if(count==5){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setRecall(cell.getNumericCellValue());
                                }
                                 
                                 if(count==6){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setFitness();
                                }
                                 
                                  if(count==7){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setDominationRank((int) cell.getNumericCellValue());
                                }
                                  
                                    
                                  if(count==8){
                                    System.out.print(cell.getNumericCellValue()+" ");
                                    q.setSize((int) cell.getNumericCellValue());
                                }
                                  count++;
                                 
                                  
				
			}
                         queries.add(q);
			System.out.println("finish");
		}
                
                for (int i = 0; i < queries.size(); i++) {
                Query chromosome = queries.get(i);
                 //MyIndexReader indexreader= new MyIndexReader(topic_num);
                 TopDocs  hits = Configuration.getSearcher().getRetrievedDocuments(chromosome,Configuration.getAllRelevatnDocuments().size());
            if(hits==null){
                i = i-1;
                chromosome=null;
                continue;
            }
            chromosome.setRetrievedDocuments(hits);
            ObjectiveFunction percision= new Percision();
           ObjectiveFunction recall = new Recall();
          chromosome.setTopic_num(topic_num);
         //  chromosome.setRecall(recall.objectiveFunction(chromosome, null,hits,searcher));
           
           chromosome.setRecall(recall.objectiveFunction(chromosome));
           
          chromosome.setPercision(percision.objectiveFunction(chromosome));
           
        
           chromosome.setFitness();
          
                
            }
                String writeExcel="D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\"
                        + "datases\\results\\439_50gen_50individul\\test\\"+"paretoFront_child_Normal.xls";
                WriteToExcel.writeXLSFile_childs(writeExcel, queries);
	
	}
    
    
}
