package it.unibas.smell.modello;

import com.opencsv.bean.CsvBindByName;


public class RowReportCompleto extends RowReportSmell {
    @CsvBindByName(column = "SHA-1")
    private String sha1;

    @CsvBindByName(column = "COMMIT MESSAGE")
    private String commitMessage;

    @CsvBindByName(column = "POSITIVITY")
    private String positivity;

    @CsvBindByName(column = "NEGATIVITY")
    private String negativity;

    public RowReportCompleto(String smellType, String className, String packageString, String sha1, String commitMessage, String positivity, String negativity) {
        //Ho sostituito packageString con className invertendone l'ordine
        super(smellType, packageString, className);
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
