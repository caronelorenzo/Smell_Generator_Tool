package it.unibas.smell.modello.smellType;

import com.opencsv.bean.CsvBindByPosition;

public class SpaghettiCode implements SmellType {

    private final String smellType = this.getClass().getSimpleName();

    @CsvBindByPosition(position = 0, required = true)
    private String classString;

    @CsvBindByPosition(position = 1, required = true)
    private String packageString;

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
}
