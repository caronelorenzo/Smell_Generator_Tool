package it.unibas.smell.modello;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class RowReportSmell {

    @CsvBindByName(column = "SMELL TYPES")
    private String smellType;

    @CsvBindByName(column = "CLASS NAME")
    private String classString;

    @CsvBindByName(column = "PACKAGE")
    private String packageString;

    public RowReportSmell() {
    }

    public RowReportSmell(String smellType, String classString, String packageString) {
        this.smellType = smellType;
        this.classString = classString;
        this.packageString = packageString;
    }

    public String getSmellType() {
        return smellType;
    }

    public void setSmellType(String smellType) {
        this.smellType = smellType;
    }

    public String getClassString() {
        return classString;
    }

    public void setClassString(String classString) {
        this.classString = classString;
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
                ", className='" + classString + '\'' +
                ", packageString='" + packageString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowReportSmell that = (RowReportSmell) o;
        return Objects.equals(classString, that.classString) &&
                Objects.equals(packageString, that.packageString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classString, packageString);
    }
}

