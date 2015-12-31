package tools;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.stanford.nlp.util.StringUtils;
import entities.SNLIEntity;
import nlp.PreProcess;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by yoosan on 15/11/10.
 */
public class SNLIParser {

    final static public Map<String, Integer> LABEL_MAP = new HashMap<String, Integer>() {{
        put("entailment", 1);
        put("neutral", 2);
        put("contradiction", 3);
    }};

    public Map<String, Integer> upvote_table = new HashMap<String, Integer>() {
    };

    public static void main(String[] args) throws Exception {
        generateDict();
    }

    public static String toString(String[] strList) {
        String res = "[";
        for (String s : strList) {
            res += s + " ";
        }
        res += "]";
        return res;
    }

    public static void generateVocab() {
        Set<String> word_list = new HashSet<String>();

        File devFile = new File("/Users/yoosan/Desktop/projects/snli/preprocess/snli_1.0/snli_1.0_dev.jsonl");
        File trainFile = new File("/Users/yoosan/Desktop/projects/snli/preprocess/snli_1.0/snli_1.0_train.jsonl");
        File testFile = new File("/Users/yoosan/Desktop/projects/snli/preprocess/snli_1.0/snli_1.0_test.jsonl");

        Gson gson = new Gson();
        Type type = new TypeToken<SNLIEntity>() {
        }.getType();

        File[] files = {devFile, trainFile, testFile};

        try {
            for (File f : files) {
                List list = FileUtils.readLines(f);
                for (Object e : list) {
                    SNLIEntity entity = (SNLIEntity) gson.fromJson(e.toString(), type);
                    String premise = entity.getSentence1();
                    String hypothesis = entity.getSentence2();
                    String[] splits = PreProcess.tokenizer(premise).split(" ");
                    for (String s : splits) {
                        String word = s.trim();
                        StringBuffer buffer = new StringBuffer();
                        if (word.length() > 2 && word.contains("-")) {
                            for (String w : word.split("-")) {
                                buffer.append(w);
                            }
                            word_list.add(buffer.toString());

                        }
                        if (word != null && word.length() > 0 && (word.charAt(0) > 97) && (word.charAt(0) < 123))
                            word_list.add(s);
                    }
                    String[] hysplits = PreProcess.tokenizer(hypothesis).split(" ");
                    for (String s : hysplits) {
                        String word = s.trim();
                        StringBuffer buffer = new StringBuffer();
                        if (word.length() > 2 && word.contains("-")) {
                            for (String w : word.split("-")) {
                                buffer.append(w);
                            }
                            word_list.add(buffer.toString());
                        }
                        if (word != null && word.length() > 0 && (word.charAt(0) > 97) && (word.charAt(0) < 123))
                            word_list.add(s);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File writeFile = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/vocab.txt");

        try {
            FileUtils.writeLines(writeFile, word_list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateDict() throws Exception {
        File train = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/preprocess/train_inputs.txt");
        File dev = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/preprocess/dev_inputs.txt");
        File test = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/preprocess/test_inputs.txt");

        Set<String> vocab = new HashSet<String>();

        int sequence_max_len = 0;

        File[] files = {train, dev, test};
        for (File f : files) {
            List list = FileUtils.readLines(f);
            for (Object line : list) {
                String sen = line.toString();
                String[] list1 = sen.split("\t")[1].split(" ");
                if (list1.length > sequence_max_len) sequence_max_len = list1.length;
                String[] list2 = sen.split("\t")[2].split(" ");
                if (list2.length > sequence_max_len) sequence_max_len = list2.length;
                for (String s : list1) {
                    vocab.add(s);
                }
                for (String s : list2) {
                    vocab.add(s);
                }
            }
        }
//        File writeFile = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/dict.txt");
//
//        try {
//            FileUtils.writeLines(writeFile, vocab);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(sequence_max_len);

    }

    public static void generateInput() {

        File devFile = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/snli_1.0_dev.jsonl");
        File trainFile = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/snli_1.0_train.jsonl");
        File testFile = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/snli_1.0_test.jsonl");

        Gson gson = new Gson();
        Type type = new TypeToken<SNLIEntity>() {
        }.getType();

        File[] files = {testFile};

        File writeFile = new File("/Users/yoosan/Desktop/projects/snli/snli_1.0/test_inputs.txt");

        List<String> input_list = new LinkedList<String>();


        try {
            for (File f : files) {
                List list = FileUtils.readLines(f);
                for (Object e : list) {
                    SNLIEntity entity = (SNLIEntity) gson.fromJson(e.toString(), type);
                    String premise = entity.getSentence1();
                    String hypothesis = entity.getSentence2();
                    List<String> premise_split = PreProcess.tokenize(premise);
                    List<String> hypothesis_split = PreProcess.tokenize(hypothesis);
                    int label = generateLabel(entity);
                    if (premise_split.size() < 3 || hypothesis_split.size() < 3 || label < 1) continue;
                    String premise_final = generateSentence(premise_split);
                    String hypothesis_final = generateSentence(hypothesis_split);
                    input_list.add(premise_final + "\t" + hypothesis_final + "\t" + label);
                }
//                input_list.add(0, String.valueOf(input_list.size()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        try {
            FileUtils.writeLines(writeFile, input_list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateSentence(String[] list) {
        List<String> res_list = new LinkedList<String>();
        for (int i = 0; i < list.length; i++) {
            String word = list[i].trim();
            if (word != null && word.length() > 0 && (word.charAt(0) > 97) && (word.charAt(0) < 123))
                res_list.add(word);
        }
        return StringUtils.join(res_list);
    }

    public static String generateSentence(List<String> list) {
        List<String> res_list = new LinkedList<String>();
        for (String word : list) {
            String w = word.trim();
            if (w != null && w.length() > 0 && (w.charAt(0) > 97) && (w.charAt(0) < 123))
                res_list.add(w);
        }
        return StringUtils.join(res_list);
    }

    public static int generateLabel(SNLIEntity entity) {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        String gold_label = entity.getGold_label();
        if (gold_label.length() == 0 || gold_label.equals("-")) {
            return 0;
//            String[] labels = entity.getAnnotator_labels();
//            for (String label : labels) {
//                if (label.equals("entailment")) count1 ++;
//                else if (label.equals("neutral")) count2++;
//                else count3++;
//            }
//
//            int pre = count1 > count2 ? count1 : count2;
//            int cmp = pre > count3 ? pre : count3;
//            if (cmp == count1) return 1;
//            else if (cmp == count2) return 2;
//            else return 3;
        } else {
            return LABEL_MAP.get(gold_label);
        }
    }

    public static String getLabel(SNLIEntity entity) {
        if (entity.getGold_label().length() == 0 || entity.getGold_label().equals("-"))
            return "-";
        else return entity.getGold_label();
    }

}
