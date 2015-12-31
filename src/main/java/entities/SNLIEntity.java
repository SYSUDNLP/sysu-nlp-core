package entities;

/**
 * Created by yoosan on 15/11/10.
 */
public class SNLIEntity {

    private String[] annotator_labels;
    private String captionID;
    private String gold_label;
    private String pairID;
    private String sentence1;
    private String sentence1_binary_parse;
    private String sentence1_parse;
    private String sentence2;
    private String sentence2_binary_parse;
    private String sentence2_parse;


    public void setAnnotator_labels(String[] annotator_labels) {
        this.annotator_labels = annotator_labels.clone();
    }

    public void setCaptionID(String captionID) {
        this.captionID = captionID;
    }

    public void setGold_label(String gold_label) {
        this.gold_label = gold_label;
    }

    public void setPairID(String pairID) {
        this.pairID = pairID;
    }

    public void setSentence1(String sentence1) {
        this.sentence1 = sentence1;
    }

    public void setSentence1_binary_parse(String sentence1_binary_parse) {
        this.sentence1_binary_parse = sentence1_binary_parse;
    }

    public void setSentence1_parse(String sentence1_parse) {
        this.sentence1_parse = sentence1_parse;
    }

    public void setSentence2(String sentence2) {
        this.sentence2 = sentence2;
    }

    public void setSentence2_binary_parse(String sentence2_binary_parse) {
        this.sentence2_binary_parse = sentence2_binary_parse;
    }

    public void setSentence2_parse(String sentence1_parse) {
        this.sentence2_parse = sentence2_parse;
    }

    public String[] getAnnotator_labels() {
        return annotator_labels;
    }

    public String getCaptionID() {
        return captionID;
    }

    public String getGold_label() {
        return gold_label;
    }

    public String getPairID() {
        return pairID;
    }

    public String getSentence1() {
        return sentence1;
    }

    public String getSentence1_binary_parse() {
        return sentence1_binary_parse;
    }

    public String getSentence1_parse() {
        return sentence1_parse;
    }

    public String getSentence2() {
        return sentence2;
    }

    public String getSentence2_binary_parse() {
        return sentence2_binary_parse;
    }

    public String getSentence2_parse() {
        return sentence2_parse;
    }

    public String getLabels() {
        String labels = "[";
        for (String s : annotator_labels) {
            labels += s + " ";
        }
        labels += "]";
        return labels;
    }

}
