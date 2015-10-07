package entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yoosan on 15/9/24.
 * All rights reserved @SYSUNLP GROUP
 */

/**
 * The class of SentenceEntity
 */
public class SentenceEntity implements Serializable {
    private List<String> tokens;
    private List<String> posTaggers;
    private List<String> nerTaggers;
    private List<String> nerValue;
    private List<String> lemmaTokens;
    private List<List<DependencyEdge>> dependencyChildren;

    public SentenceEntity() {
    }

    public SentenceEntity(List<String> tokens) {
        this.tokens = tokens;
    }

    public SentenceEntity(List<String> tokens, List<String> posTaggers) {
        this.tokens = tokens;
        this.posTaggers = posTaggers;
    }

    public SentenceEntity(List<String> tokens, List<String> posTaggers, List<String> nerTaggers) {
        this.tokens = tokens;
        this.posTaggers = posTaggers;
        this.nerTaggers = nerTaggers;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public void setPosTaggers(List<String> tagger) {
        this.posTaggers = posTaggers;
    }

    public void setNerTaggers(List<String> nerTaggers) {
        this.nerTaggers = nerTaggers;
    }

    public void setNerValue(List<String> nerValue) {
        this.nerValue = nerValue;
    }

    public void setLemmaTokens(List<String> lemmaTokens) {
        this.lemmaTokens = lemmaTokens;
    }

    public void setDependencyChildren(List<List<DependencyEdge>> dependencyChildren) {
        this.dependencyChildren = dependencyChildren;
    }


    public List<String> getTokens() {
        return tokens;
    }

    public List<String> getPosTaggers() {
        return posTaggers;
    }

    public List<String> getNerTaggers() {
        return nerTaggers;
    }

    public List<String> getNerValue() {
        return nerValue;
    }

    public List<String> getLemmaTokens() {
        return lemmaTokens;
    }

    public List<List<DependencyEdge>> getDependencyChildren() {
        return dependencyChildren;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        String toStr = "%s -> %s -> %s -> %s -> %s\n";
        for(int i = 0; i < tokens.size(); i++) {
            String s = String.format(toStr, tokens.get(i), posTaggers.get(i), lemmaTokens.get(i), nerTaggers.get(i), nerValue.get(i));
            builder.append(s);
        }
        return builder.toString();
    }

    public static class DependencyEdge {
        public String label;
        public int modifier;

        public DependencyEdge(String label, int modifier) {
            this.label = label;
            this.modifier = modifier;
        }

        @Override
        public String toString() {
            return String.format("%s->%d", label, modifier);
        }
    }
}
