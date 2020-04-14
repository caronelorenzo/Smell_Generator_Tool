package it.unibas.smell.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;


public class RowReportCompleto extends RowReportSmell {
    @CsvBindByName(column = "SHA-1")
    @CsvBindByPosition(position = 15)
    private String sha1;

    @CsvBindByName(column = "COMMIT MESSAGE")
    @CsvBindByPosition(position = 16)
    private String commitMessage;

    @CsvBindByName(column = "POSITIVITY")
    @CsvBindByPosition(position = 17)
    private String positivity;

    @CsvBindByName(column = "NEGATIVITY")
    @CsvBindByPosition(position = 18)
    private String negativity;

    public RowReportCompleto(String className, String packageString, SmellCategories smellCategories, String sha1, String commitMessage, String positivity, String negativity) {
        super(className, packageString);
        super.setSmellCategories(smellCategories);
        this.sha1 = sha1;
        this.commitMessage = commitMessage;
        this.positivity = positivity;
        this.negativity = negativity;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getPositivity() {
        return positivity;
    }

    public void setPositivity(String positivity) {
        this.positivity = positivity;
    }

    public String getNegativity() {
        return negativity;
    }

    public void setNegativity(String negativity) {
        this.negativity = negativity;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }
}
