package tools;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import nlp.PreProcess;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

/**
 * Created by yoosan on 15/11/9.
 */
public class Splits {

    public final static String[] Properties = {"tokenize", "ssplit"};

    public static void main(String[] args) {
        File file = new File("/Users/yoosan/Downloads/rt-polaritydata/rt-polaritydata/rt-polarity.pos");
        try {
            List list = FileUtils.readLines(file);
            System.out.println(list.size());
            for (Object o : list) {
                String tokenizer = PreProcess.tokenizer(o.toString());
                System.out.println(tokenizer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
