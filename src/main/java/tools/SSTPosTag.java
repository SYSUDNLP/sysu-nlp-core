package tools;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import nlp.PreProcess;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yoosan on 15/12/1.
 */
public class SSTPosTag {

    public static void main(String[] args) {
        String file_prefix = "/Users/yoosan/Desktop/projects/treelstm/data/sst/";
        File train_file = new File(file_prefix +  "dev/sents.toks");
        File pos_file = new File(file_prefix + "dev/sents.pos");
        StanfordCoreNLP pipeline = PreProcess.getBasicPipeline();
        List<String> result = new LinkedList<String>();
        try {
            List lines = FileUtils.readLines(train_file);
            for (Object o : lines) {
                List<String> pos_res = new LinkedList<String>();
                String sent = o.toString();
                Annotation document = pipeline.process(sent);
                List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
                for (CoreMap sentence : maps) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                        pos_res.add(pos);
                    }
                }
                String join = StringUtils.join(pos_res, " ");
                result.add(join);
                System.out.println(join);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.writeLines(pos_file, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
