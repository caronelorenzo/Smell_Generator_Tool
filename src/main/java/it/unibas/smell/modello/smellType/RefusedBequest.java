package it.unibas.smell.modello.smellType;

import com.opencsv.bean.CsvBindByPosition;

public class RefusedBequest implements SmellType, SmellTypeOdd {

    private final String smellType = this.getClass().getSimpleName();

    @CsvBindByPosition(position = 0, required = true)
    private String classString;

    @CsvBindByPosition(position = 1, required = true)
    private String packageString;

    @CsvBindByPosition(position = 2, required = true)
    private String classString1;

    @CsvBindByPosition(position = 3, required = true)
    private String packageString1;

    @Override
    public String getSmellType() {
        return smellType;
    }

    @Override
    public String getClassString() {
        return classString;
    }

    public void setClassString(String classString) {
        this.classString = classString;
    }

    @Override
    public String getPackageString() {
        return packageString;
    }

    public void setPackageString(String packageString) {
        this.packageString = packageString;
    }

    @Override
    public String getClassString1() {
        return classString1;
    }

    public void setClassString1(String classString1) {
        this.classString1 = classString1;
    }

    @Override
    public String getPackageString1() {
        return packageString1;
    }

    public void setPackageString1(String packageString1) {
        this.packageString1 = packageString1;
    }
}
