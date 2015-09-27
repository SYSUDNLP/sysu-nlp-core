package entities;

/**
 * Created by Yoosan on 15/9/25.
 * All rights reserved @SYSUNLP GROUP
 */


public class AmazonReview {
    private String reviewerID;
    private String asin;
    private String reviewerName;
    private int[] helpful;
    private String reviewText;
    private double overall;
    private String summary;
    private long unixReviewTime;
    private String reviewTime;

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int[] getHelpful() {
        return helpful;
    }

    public void setHelpful(int[] helpful) {
        System.arraycopy(helpful, 0, this.helpful, 0, helpful.length);
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public long getUnixReviewTime() {
        return unixReviewTime;
    }

    public void setUnixReviewTime(long unixReviewTime) {
        this.unixReviewTime = unixReviewTime;
    }

    public double getOverall() {
        return overall;
    }

    public void setOverall(double overall) {
        this.overall = overall;
    }

    public String toString() {
        return reviewText;
    }

}
