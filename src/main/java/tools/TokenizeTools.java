package tools;

import nlp.PreProcess;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by yoosan on 15/11/15.
 */
public class TokenizeTools {


    public static void main(String[] args) {
        processRTdatasets();
    }

    public static void processRTdatasets() {

        Set<String> wordList = new HashSet<String>();

        File file = new File("/Users/yoosan/Desktop/data/rt-polaritydata/rt-polaritydata/rt-polarity.pos");
        File file2 = new File("/Users/yoosan/Desktop/data/rt-polaritydata/rt-polaritydata/rt-polarity.neg");
        File out = new File("/Users/yoosan/Desktop/data/rt-polaritydata/rt-polaritydata/inputs.txt");
        try {
            List list = FileUtils.readLines(file);
            for (Object o : list) {
                List<String> tokenize = PreProcess.tokenize(o.toString());
                if (tokenize.size() < 3) continue;
                String s = SNLIParser.generateSentence(tokenize);
                wordList.add(s + "\t" + 1);
            }
            List list2 = FileUtils.readLines(file2);
            for (Object o : list2) {
                List<String> tokenize = PreProcess.tokenize(o.toString());
                if (tokenize.size() < 3) continue;
                String s = SNLIParser.generateSentence(tokenize);
                wordList.add(s + "\t" + 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.writeLines(out, wordList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
