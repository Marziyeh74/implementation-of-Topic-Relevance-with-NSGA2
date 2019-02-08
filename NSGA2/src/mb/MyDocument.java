/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.File;
import java.io.FileReader;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class MyDocument  implements Comparable<MyDocument>{
    
    private String name;
    private String contnet;
    /*private String A;
    private String T;
    private String W;
    private String B;*/
    private float score ;
    private float max_tf;
    private int length;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
     /**
     * @return the name
     */
    public String getContent() {
        return this.contnet;
    }

    /**
     * @param name the name to set
     */
    public void setContent(String content) {
        this.contnet = content;
    }

   
   
    /**
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * @return the max_tf
     */
    public float getMax_tf() {
        return max_tf;
    }

    /**
     * @param max_tf the max_tf to set
     */
    public void setMax_tf(float max_tf) {
        this.max_tf = max_tf;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int compareTo(MyDocument o) {
        if(this.score < o.score )
            return 1;
        else
            return -1;
       }

   
}
