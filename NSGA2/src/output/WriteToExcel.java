/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mb.runGeneticAlgorithm.Performance;
import mb.runGeneticAlgorithm.Query;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

/**
 *
 * @author Marziyeh
 */
public class WriteToExcel {
    
    //store best query of each generation
   public static void writeXLSFile_bestQueriesOfGenerations(String excelFileName,List<Query> queries) {
		
		//String excelFileName = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < queries.size(); r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			//for (int c=0;c < 5; c++ )
			{
                                //generation_num
				HSSFCell generation_num = row.createCell(0);
				
				generation_num.setCellValue(r+1);
                                
                                //query_id
                                HSSFCell query_id = row.createCell(1);
				
				query_id.setCellValue(queries.get(r).getUniqueID());
                                
                                //query string
                                HSSFCell query = row.createCell(2);
				
				query.setCellValue(getStringOfQuery(queries.get(r).getWords()));
                                
                                //persicion
                                 HSSFCell percision = row.createCell(3);
				
				percision.setCellValue(queries.get(r).getPercision());
                                
                                //recall
                                  HSSFCell recall = row.createCell(4);
				
				recall.setCellValue(queries.get(r).getRecall());
                                
                                //fitness
                                 HSSFCell fitness = row.createCell(5);
				
				fitness.setCellValue(queries.get(r).getFitness());
                                
                                //dominantrank
                                
                                 HSSFCell dominantrank = row.createCell(6);
				
				dominantrank.setCellValue(queries.get(r).getDominationRank());
                                
                                  //size
                                
                                 HSSFCell size = row.createCell(7);
				
				size.setCellValue(queries.get(r).getSize());
                                
                               
                                
			}
		}
		
                try{
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
                } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
                
	}
   
   public static String getStringOfQuery(List<String> query)
   {
       StringBuilder query_str= new StringBuilder();
       
        
            for (int i = 0; i < query.size(); i++) {
                query_str.append(" " + query.get(i));
                
            }
            return query_str.toString();
            
   }
   
   public static void writeXLSFile_childs(String excelFileName,List<Query> queries) throws IOException {
		
		//String excelFileName = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < queries.size(); r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			//for (int c=0;c < 5; c++ )
			{
                                //invidiual number
				HSSFCell generation_num = row.createCell(0);
				
				generation_num.setCellValue(r+1);
                                
                                //query_id
                                HSSFCell query_id = row.createCell(1);
				
				query_id.setCellValue(queries.get(r).getUniqueID());
                                
                                //query string
                                HSSFCell query = row.createCell(2);
				
				query.setCellValue(getStringOfQuery(queries.get(r).getWords()));
                                
                                //persicion
                                 HSSFCell percision = row.createCell(3);
				
				percision.setCellValue(queries.get(r).getPercision());
                                
                                //recall
                                  HSSFCell recall = row.createCell(4);
				
				recall.setCellValue(queries.get(r).getRecall());
                                
                                //fitness
                                 HSSFCell fitness = row.createCell(5);
				
				fitness.setCellValue(queries.get(r).getFitness());
                                
                                //dominantrank
                                
                                 HSSFCell dominantrank = row.createCell(6);
				
				dominantrank.setCellValue(queries.get(r).getDominationRank());
                                
                                //size
                                
                                 HSSFCell size = row.createCell(7);
				
				size.setCellValue(queries.get(r).getSize());
                                
                               
                                
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
   
   
      public static void writeXLSFile_meanPerformance(String excelFileName,List<Performance> performances) throws IOException {
		
		//String excelFileName = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < performances.size(); r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			//for (int c=0;c < 5; c++ )
			{
                                //invidiual number
				HSSFCell generation_num = row.createCell(0);
				
				generation_num.setCellValue(r+1);
                                
                               
                                //persicion
                                 HSSFCell percision = row.createCell(3);
				
				percision.setCellValue(performances.get(r).percision);
                                
                                //recall
                                  HSSFCell recall = row.createCell(4);
				
				recall.setCellValue(performances.get(r).recall);
                                
                                //fitness
                                 HSSFCell fitness = row.createCell(5);
				
				fitness.setCellValue(performances.get(r).f_measure);
                                
                               
                               
                                
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
   
   
   
   
   
}
