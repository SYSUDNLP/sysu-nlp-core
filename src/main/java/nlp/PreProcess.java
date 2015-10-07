package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

/**
 * Created by Yoosan on 15/9/17.
 * All rights reserved @SYSUNLP GROUP
 */
public class PreProcess {

    private static StanfordCoreNLP pipline = null;

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);

        String text = "Easy to use a mobile. If you're taller than 4ft, be ready to tuck your legs behind you as you hang and pull.Greece held its last Summer Olympics in which year?" +
                "How many more participants were there in 1900 than in the first year?";
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                String nervalue = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
                System.out.println(word + "\t" + pos + "\t" + ne + "\t" + lemma + "\t" + nervalue);

            }
//            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//            System.out.println(tree);
//            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
//            System.out.println(dependencies);

        }
//        Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
//        System.out.println(graph);
    }

    public static StanfordCoreNLP getPipline(String pro) {
        Properties prop = new Properties();
        prop.setProperty("annotators", pro);
        if (pipline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipline = new StanfordCoreNLP(prop);
            }
        }
        return pipline;
    }

    public static StanfordCoreNLP getAllPipline() {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment");
        if (pipline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipline = new StanfordCoreNLP(prop);
            }
        }
        return pipline;
    }

    public static String tokenizer(String text) {
        StringBuilder buffer = new StringBuilder();
        StanfordCoreNLP pipline = PreProcess.getPipline("tokenize, ssplit");
        Annotation document = pipline.process(text);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase().trim();
                if (word.length() == 1 && (word.charAt(0) > 'z') || word.charAt(0) < 'A') continue;
                buffer.append(word).append(" ");
            }
        }
        return buffer.toString();
    }

    public static void test(String text) {
        StanfordCoreNLP pipline = PreProcess.getAllPipline();
        Annotation document = new Annotation(text);
        pipline.annotate(document);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//            Tree tree1 = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
//            System.out.println(tree1);
            System.out.println(tree);
        }
    }

}
