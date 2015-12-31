package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Yoosan on 15/9/17.
 * All rights reserved @SYSUNLP GROUP
 */
public class PreProcess {

    private static StanfordCoreNLP pipeline = null;

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

    public static StanfordCoreNLP getPipeline(String pro) {
        Properties prop = new Properties();
        prop.setProperty("annotators", pro);
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static StanfordCoreNLP getPipeline(String[] pros) {
        Properties prop = new Properties();
        for (String s : pros) {
            prop.setProperty("annotators", s);
        }
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static StanfordCoreNLP getBasicPipeline() {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos");
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static StanfordCoreNLP getAllPipeline() {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment");
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static String tokenizer(String text) {
        StringBuilder buffer = new StringBuilder();
        StanfordCoreNLP pipeline = PreProcess.getPipeline("tokenize, ssplit");
        Annotation document = pipeline.process(text);
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

    public static List<String> tokenize(String text) {
        List<String> result = new LinkedList<String>();
        StanfordCoreNLP pipeline = PreProcess.getPipeline("tokenize, ssplit");
        Annotation document = pipeline.process(text);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase().trim();
                if (word.length() == 1 && (word.charAt(0) > 'z') || word.charAt(0) < 'A') continue;
                else result.add(word);
            }
        }
        return result;
    }

    public static String delta_tokenizer(String text) {
        StringBuilder buffer = new StringBuilder();
        StanfordCoreNLP pipeline = PreProcess.getPipeline("tokenize, ssplit");
        Annotation document = pipeline.process(text);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase().trim();
                if (word.length() == 1 && (word.charAt(0) > 'z') || word.charAt(0) < 'A') continue;
                if (word.contains("-"))  {
                    String[] sp = word.split("-");
                    for (String s : sp) {
                        buffer.append(s).append(" ");
                    }
                }
                buffer.append(word).append(" ");
            }
        }
        return buffer.toString();
    }


    public static void test(String text) {
        StanfordCoreNLP pipeline = PreProcess.getAllPipeline();
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//            Tree tree1 = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
//            System.out.println(tree1);
            System.out.println(tree);
        }
    }

}
