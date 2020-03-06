package it.unibas.smell.modello;

import com.opencsv.bean.CsvBindByName;

public class RowReportCompleto extends RowReportSmell {

    @CsvBindByName(column = "SHA-1")
    private String sha1;

    public RowReportCompleto(String smellType, String className, String packageString, String sha1) {
        super(smellType, className, packageString);
        this.sha1 = sha1;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }
}
