package it.unibas.smell.smellType;

import com.opencsv.bean.CsvBindByPosition;

public class FeatureEnvy  implements SmellType{

    private final String smellType = this.getClass().getSimpleName();

    @CsvBindByPosition(position = 1, required = true)
    private String classString;

    @CsvBindByPosition(position = 2, required = true)
    private String packageString;

    @Override
    public String getSmellType() {
        return smellType.substring(0,1).toLowerCase() + smellType.substring(1);
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
