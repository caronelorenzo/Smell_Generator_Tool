package it.unibas.smell.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;


public class RowReportCompleto extends RowReportSmell {
    @CsvBindByName(column = "SHA-1")
    @CsvBindByPosition(position = 15)
    private String sha1;

    @CsvBindByName(column = "COMMIT MESSAGE")
    @CsvBindByPosition(position = 16)
    private String commitMessage;

    @CsvRecurse
    private CommitScore commitScore;

    public RowReportCompleto(String className, String packageString, SmellCategories smellCategories, String sha1, String commitMessage, CommitScore commitScore) {
        super(className, packageString);
        super.setSmellCategories(smellCategories);
        this.sha1 = sha1;
        this.commitMessage = commitMessage;
        this.commitScore = commitScore;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public CommitScore getCommitScore() {
        return commitScore;
    }

    public void setCommitScore(CommitScore commitScore) {
        this.commitScore = commitScore;
    }
}
