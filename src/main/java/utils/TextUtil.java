package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.AmazonReview;
import nlp.PreProcess;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Yoosan on 15/9/26.
 * All rights reserved @SYSUNLP GROUP
 */
public class TextUtil {

    public static boolean amazonReviewTokenizer(String input, String output) {
        String line;
        String review;
        Gson gson = new Gson();
        Type type = new TypeToken<AmazonReview>() {
        }.getType();
        LineIterator iterator = null;
        long i = 0;
        long startTime = System.currentTimeMillis();
        try {
            iterator = FileUtils.lineIterator(new File(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert iterator != null;
            File outFile = new File(output);
            FileWriter writer = new FileWriter(outFile);
            while (iterator.hasNext()) {
                line = iterator.nextLine();
                review = gson.fromJson(line, type).toString();
                review = review.trim();
                writer.write(PreProcess.tokenizer(review) + "\n");
                i++;
                if (i != 0 && i % 1000 == 0) {
                    System.out.println("[INFO] Read " + i + " lines");
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long mileSeconds = (System.currentTimeMillis() - startTime);
        if (mileSeconds < 1000) {
            System.out.println("[INFO] Cost time : " + mileSeconds + " ms");
        } else if (mileSeconds < 60000) {
            System.out.println("[INFO] Cost time : " + mileSeconds / 1000.0 + " Seconds");
        } else {
            System.out.println("[INFO] Cost time : " + mileSeconds / (60 * 1000.0) + " Seconds");
        }
        return true;
    }

    public static boolean textTokenizer(String input, String output) {
        String line;
        String review;
        LineIterator iterator = null;
        long i = 0;
        long startTime = System.currentTimeMillis();
        try {
            iterator = FileUtils.lineIterator(new File(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert iterator != null;
            File outFile = new File(output);
            FileWriter writer = new FileWriter(outFile);
            while (iterator.hasNext()) {
                line = iterator.nextLine();
                review = PreProcess.tokenizer(line.toLowerCase());
                writer.write(review + "\n");
                i++;
                if (i != 0 && i % 1000 == 0) {
                    System.out.println("[INFO] Read " + i + " lines");
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long mileSeconds = (System.currentTimeMillis() - startTime);
        if (mileSeconds < 1000) {
            System.out.println("[INFO] Cost time : " + mileSeconds + " ms");
        } else if (mileSeconds < 60000) {
            System.out.println("[INFO] Cost time : " + mileSeconds / 1000.0 + " Seconds");
        } else {
            System.out.println("[INFO] Cost time : " + mileSeconds / (60 * 1000.0) + " Seconds");
        }
        return true;
    }
}
