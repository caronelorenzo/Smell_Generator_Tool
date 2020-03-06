package it.unibas.smell.modello;

import com.opencsv.bean.CsvBindByName;

public class RowReportSmell {

    @CsvBindByName(column = "SMELL TYPE")
    private String smellType;

    @CsvBindByName(column = "CLASS NAME")
    private String className;

    @CsvBindByName(column = "PACKAGE")
    private String packageString;

    public RowReportSmell() {
    }

    public RowReportSmell(String smellType, String className, String packageString) {
        this.smellType = smellType;
        this.className = className;
        this.packageString = packageString;
    }

    public String getSmellType() {
        return smellType;
    }

    public void setSmellType(String smellType) {
        this.smellType = smellType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageString() {
        return packageString;
    }

    public void setPackageString(String packageString) {
        this.packageString = packageString;
    }

    @Override
    public String toString() {
        return "RowReportSmell{" +
                "smellType='" + smellType + '\'' +
                ", className='" + className + '\'' +
                ", packageString='" + packageString + '\'' +
                '}';
    }
}

