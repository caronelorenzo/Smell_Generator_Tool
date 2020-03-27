package it.unibas.smell.modello.smellType;

import com.opencsv.bean.CsvBindByPosition;

public class ClassDataShouldBePrivate implements SmellType{

    private final String smellType = this.getClass().getSimpleName();

    @CsvBindByPosition(position = 0, required = true)
    private String classString;

    @CsvBindByPosition(position = 1, required = true)
    private String packageString;

    @Override
    public String toString() {
        return "ClassDataShouldBePrivate{" +
                "smellType='" + smellType + '\'' +
                ", classString='" + classString + '\'' +
                ", packageString='" + packageString + '\'' +
                '}';
    }

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
