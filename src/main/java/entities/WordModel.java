package entities;

import java.io.Serializable;

/**
 * Created by Yoosan on 15/9/24.
 * All rights reserved @SYSUNLP GROUP
 */

/**
 * The class of WordModel
 */
public class WordModel implements Serializable {
    private String word;
    private String posTagger;
    private String ner;

    public WordModel() {
    }

    public WordModel(String word) {
        this.word = word;
    }

    public WordModel(String word, String posTagger) {
        this.word = word;
        this.posTagger = posTagger;
    }

    public WordModel(String word, String posTagger, String ner) {
        this.word = word;
        this.posTagger = posTagger;
        this.ner = ner;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPosTagger(String tagger) {
        this.posTagger = posTagger;
    }

    public void setNer(String ner) {
        this.ner = ner;
    }

    public String getWord() {
        return word;
    }

    public String getPosTagger() {
        return posTagger;
    }

    public String getNer() {
        return ner;
    }

    public String toString() {
        return "Original word is:" + word + "\t" + "POSTagger is: " +
                posTagger + "\t" + "Named Entity Recognition is:" + ner;
    }
}
