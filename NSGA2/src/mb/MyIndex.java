/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

/**
 *
 * @author Azade
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import mb.analyzer.MyStandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class MyIndex {

    private IndexWriter training_writer;
    private IndexWriter testing_writer;

    private ArrayList<MyDocument> docs = new ArrayList<MyDocument>();
    //private String indexDir;

    public MyIndex(String training_dir, String testing_dir) throws IOException {
        FSDirectory training_directory = FSDirectory.open(Paths.get(training_dir));
        MyStandardAnalyzer train_analyzer = new MyStandardAnalyzer(new StandardAnalyzer().getStopwordSet());

        //System.out.println(analyzer.getStopwordSet().toString())
        IndexWriterConfig train_cfg = new IndexWriterConfig(train_analyzer);
        System.out.println(train_cfg.getSimilarity().toString());
        train_cfg.setOpenMode(OpenMode.CREATE);

        training_writer = new IndexWriter(training_directory, train_cfg);

        FSDirectory testing_directory = FSDirectory.open(Paths.get(testing_dir));
        MyStandardAnalyzer test_analyzer = new MyStandardAnalyzer(new StandardAnalyzer().getStopwordSet());

        //System.out.println(analyzer.getStopwordSet().toString())
     
        IndexWriterConfig test_cfg = new IndexWriterConfig(test_analyzer);
        System.out.println(test_cfg.getSimilarity().toString());
        test_cfg.setOpenMode(OpenMode.CREATE);

        testing_writer = new IndexWriter(testing_directory, test_cfg);

        // testing_writer = new IndexWriter(directory, cfg);
    }

    public void indexDirecotryOfTopics(String parsed_dir) {
        if (checkDirecotry(new File(parsed_dir))) {
            ArrayList<File> topic_folders = new ArrayList<File>();
            File parsed_folder = new File(parsed_dir);
            for (final File fileEntry : parsed_folder.listFiles()) {
                if (fileEntry.isDirectory()) {

                    topic_folders.add(fileEntry);
                }
            }

            // get files of each topic and index them into two indexed
            for (int i = 0; i < topic_folders.size(); i++) {
                ArrayList<File> topic_files = new ArrayList<File>();
                System.out.print("index: " + i);
                System.out.println(" , topic : " + topic_folders.get(i).getName());

                int count_files = 0;
                for (final File fileEntry : topic_folders.get(i).listFiles()) {
                    if (fileEntry.isFile()) {

                        topic_files.add(fileEntry);
                        count_files++;
                    }
                }

                createIndexForEachTopic(topic_files, i + 1, topic_folders.get(i).getName());

            }

        }

    }

    //make a  training index for 2/3 of parsed pages of all topics ,and a testing index for 1/3 of remaind pages of topics
    private void createIndexForEachTopic(ArrayList<File> topic_files, int topic_index, String topic_num) {

        String training_files_name = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\parsed\\Parsed\\" + topic_num + "_traing.txt";
        String testing_files_name = "D:\\Courses of uni -arshad\\Term3\\RayaneshTakamoli\\homework\\mini-project\\datases\\parsed\\Parsed\\" + topic_num + "_testing.txt";

        File traing_description = new File(training_files_name);
        File testing_description = new File(testing_files_name);

        Map<Integer, File> training_files = new HashMap<Integer, File>();
        try {

            BufferedWriter buffer = null;

            if (!traing_description.exists()) {
                traing_description.createNewFile();

            }
            buffer = new BufferedWriter(new FileWriter(traing_description));

            // Map<Integer, File> testing_files = new HashMap<Integer, File>();
            int initial_size = 2 * topic_files.size() / 3;
            int i = 0;
            for (i = 0; i < initial_size; i++) {
                int random = (int) (Math.random() * topic_files.size() + 0);
                while (training_files.containsKey(random)) {
                    random = (int) (Math.random() * topic_files.size() + 0);
                }
                if (!training_files.containsKey(random)) {
                    training_files.put(random, topic_files.get(random));
                    buffer.write(topic_files.get(random).getName() + "\n");

                    //topic_files.remove(random);
                }
            }
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(MyIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            if (!testing_description.exists()) {

                testing_description.createNewFile();
            }

            BufferedWriter buffer = null;

            buffer = new BufferedWriter(new FileWriter(testing_description));

            for (int i = 0; i < topic_files.size(); i++) {

                if (!training_files.containsKey(i)) {
                    buffer.write(topic_files.get(i).getName() + "\n");
                }
            }

            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(MyIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            createTrainingIndex(training_files, topic_num);
            createTestingIndex(training_files.keySet(), topic_num, topic_files);
        } catch (IOException ex) {
            Logger.getLogger(MyIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createTrainingIndex(Map<Integer, File> training_files, String topic_num) throws FileNotFoundException, IOException {
        System.out.println("training_index\n");
        for (Map.Entry mapElement : training_files.entrySet()) {
            Integer key = (Integer) mapElement.getKey();

            File file;
            file = (File) mapElement.getValue();

            FileReader fr = null;
            BufferedReader br = null;

            fr = new FileReader(file);
            br = new BufferedReader(fr);
               // MyDocument doc = new MyDocument();

            // doc.setName(docfile.getName());
            StringBuilder content = new StringBuilder();
            String sCurrentLine = br.readLine();
            while (sCurrentLine != null) {
                content.append(sCurrentLine);
                sCurrentLine = br.readLine();
            }
            String file_name;
            if (file.getName().contains("t_")) {
                file_name = topic_num + "_" + file.getName().split("t_")[1];
            } else {
                file_name = topic_num + "_" + file.getName();
            }
           // System.out.println(file_name);
            Document ldoc = new Document();
            ldoc.add(new StringField("docName", file_name, Field.Store.YES));
            FieldType type = new FieldType();
            type.setStored(true);
           
            type.setStoreTermVectors(true);
            type.setTokenized(true);
            type.setStoreTermVectorPositions(true);
            type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
            ldoc.add(new Field("content", content.toString(), type));

            training_writer.addDocument(ldoc);

        }
    }

    public void createTestingIndex(Set<Integer> training_files_keys, String topic_num, ArrayList<File> topic_files) throws FileNotFoundException, IOException {
        System.out.println("testing index\n");
        for (int i = 0; i < topic_files.size(); i++) {
            if (!training_files_keys.contains(i)) {

                FileReader fr = null;
                BufferedReader br = null;

                fr = new FileReader(topic_files.get(i));
                br = new BufferedReader(fr);
               // MyDocument doc = new MyDocument();

                // doc.setName(docfile.getName());
                StringBuilder content = new StringBuilder();
                String sCurrentLine = br.readLine();
                while (sCurrentLine != null) {
                    content.append(sCurrentLine);
                    sCurrentLine = br.readLine();
                }
                String file_name;
                if (topic_files.get(i).getName().contains("t_")) {
                    file_name = topic_num + "_" + topic_files.get(i).getName().split("t_")[1];
                } else {
                    file_name = topic_num + "_" + topic_files.get(i).getName();
                }
                System.out.println(file_name);
                Document ldoc = new Document();
                ldoc.add(new StringField("docName", file_name, Field.Store.YES));
                FieldType type = new FieldType();
                type.setStored(true);
                type.setStoreTermVectors(true);
                type.setTokenized(true);
                type.setStoreTermVectorPositions(true);
                type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
                ldoc.add(new Field("content", content.toString(), type));

                testing_writer.addDocument(ldoc);
            }

        }
    }

    public void indexFileOrDirectory_initial(String directoryname) throws IOException {

        if (checkDirecotry(new File(directoryname))) {

            int originalNumDocs = training_writer.numDocs();

            System.out.println("reading docs");
            ArrayList<File> files = listFilesForFolder(new File(directoryname));
            for (File docfile : files) {

                FileReader fr = null;
                BufferedReader br = null;

                fr = new FileReader(docfile);
                br = new BufferedReader(fr);
                MyDocument doc = new MyDocument();

                doc.setName(docfile.getName());
                StringBuilder content = new StringBuilder();
                String sCurrentLine = br.readLine();
                while (sCurrentLine != null) {
                    content.append(sCurrentLine);
                    sCurrentLine = br.readLine();
                }
                doc.setContent(content.toString());
                docs.add(doc);
            }

            for (int i = 0; i < docs.size(); i++) {
                Document ldoc = new Document();
                ldoc.add(new StringField("docName", docs.get(i).getName(), Field.Store.YES));
                FieldType type = new FieldType();
                type.setStored(true);
                type.setStoreTermVectors(true);
                type.setTokenized(true);
                type.setStoreTermVectorPositions(true);
                type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
                ldoc.add(new Field("content", docs.get(i).getContent(), type));

                training_writer.addDocument(ldoc);

            }

            System.out.println("num=" + training_writer.numDocs());

        }

        this.training_writer.close();

    }

    public ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> files = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {

                files.add(fileEntry);
                //System.out.println(fileEntry.getName());

            }
        }
        return files;

    }

    private boolean checkDirecotry(File directory) {

        if (!directory.exists()) {
            System.out.println(directory + " does not exist.");
            return false;
        }
        return true;

    }

    private void checkfile(File file) {

        if (!file.exists()) {
            System.out.println(file + " does not exist.");
        }

    }

    public void close() throws IOException {
        training_writer.close();
        testing_writer.close();
    }

    // make index for each topic description
    public void createTopicIndexes(String topic_desc_dir, String topic_desc_indexes) throws IOException {
        if (checkDirecotry(new File(topic_desc_dir))) {
            ArrayList<File> topic_desc_files = new ArrayList<File>();
            File topic_description = new File(topic_desc_dir);
            for (final File fileEntry : topic_description.listFiles()) {
                if (!fileEntry.isDirectory()) {

                    topic_desc_files.add(fileEntry);
                }
            }

            for (int i = 0; i < topic_desc_files.size(); i++) {
                System.out.println(topic_desc_files.get(i).getName());
                String[] str=topic_desc_files.get(i).getName().split(".t");
                String index_name_dir = topic_desc_indexes + "\\index_" +str[0];
                new File(index_name_dir).mkdir();
                FSDirectory index_dirctory = FSDirectory.open(Paths.get(index_name_dir));
                MyStandardAnalyzer analyzer = new MyStandardAnalyzer(new StandardAnalyzer().getStopwordSet());

                IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
                cfg.setOpenMode(OpenMode.CREATE);

                IndexWriter writer = new IndexWriter(index_dirctory, cfg);
                
                 FileReader fr = null;
                BufferedReader br = null;

                fr = new FileReader(topic_desc_files.get(i));
                br = new BufferedReader(fr);
               // MyDocument doc = new MyDocument();

                // doc.setName(docfile.getName());
                StringBuilder content = new StringBuilder();
                String sCurrentLine = br.readLine();
                while (sCurrentLine != null) {
                    content.append(sCurrentLine);
                    sCurrentLine = br.readLine();
                }
              
                
                
                 Document ldoc = new Document();
                
                FieldType type = new FieldType();
                type.setStored(true);
                type.setStoreTermVectors(true);
                type.setTokenized(true);
                type.setStoreTermVectorPositions(true);
                type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
                ldoc.add(new Field("content", content.toString(), type));

                writer.addDocument(ldoc);
                writer.close();

            }

        }

    }
    
    
    

}
