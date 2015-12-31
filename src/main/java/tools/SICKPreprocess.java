package tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yoosan on 15/11/28.
 */
public class SICKPreprocess {

    final static public Map<String, Integer> LABEL_MAP = new HashMap<String, Integer>() {{
        put("ENTAILMENT", 1);
        put("NEUTRAL", 2);
        put("CONTRADICTION", 3);
    }};

    public static void main(String[] args) {

        String sicktrain_file = "/Users/yoosan/Desktop/projects/treelstm/data/sick/SICK_train.txt";
        String sicktest_file = "/Users/yoosan/Desktop/projects/treelstm/data/sick/SICK_test_annotated.txt";
        String sickdev_file = "/Users/yoosan/Desktop/projects/treelstm/data/sick/SICK_trial.txt";

        File tf = new File(sicktrain_file);
        File tsf = new File(sicktest_file);
        File df = new File(sickdev_file);

        try {
            List tfl = FileUtils.readLines(tf);
            List<Integer> tr = new LinkedList<Integer>();
            List tsfl = FileUtils.readLines(tsf);
            List dfl = FileUtils.readLines(df);
            for (Object o : tfl) {
                String ent = o.toString().split("\t")[4];
                Integer label = LABEL_MAP.get(ent);
                tr.add(label);
            }
            FileUtils.writeLines(new File("/Users/yoosan/Desktop/projects/treelstm/data/sick/train/e_label.txt"), tr);

            for (Object o : tsfl) {
                String ent = o.toString().split("\t")[4];
                Integer label = LABEL_MAP.get(ent);
                tr.add(label);
            }
            FileUtils.writeLines(new File("/Users/yoosan/Desktop/projects/treelstm/data/sick/test/e_label.txt"), tr);

            for (Object o : dfl) {
                String ent = o.toString().split("\t")[4];
                Integer label = LABEL_MAP.get(ent);
                tr.add(label);
            }
            FileUtils.writeLines(new File("/Users/yoosan/Desktop/projects/treelstm/data/sick/dev/e_label.txt"), tr);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
