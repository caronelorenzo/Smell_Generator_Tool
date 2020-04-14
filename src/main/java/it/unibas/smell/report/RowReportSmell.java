package it.unibas.smell.report;

import com.opencsv.bean.*;
import it.unibas.smell.smellType.SmellType;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

public class RowReportSmell {

    @CsvBindByName(column = "CLASS NAME")
    @CsvBindByPosition(position = 0)
    private String classString;

    @CsvBindByName(column = "PACKAGE")
    @CsvBindByPosition(position = 1)
    private String packageString;

    @CsvRecurse
    private SmellCategories smellCategories = new SmellCategories();

    public RowReportSmell() {
    }

    public RowReportSmell(String classString, String packageString) {
        this.classString = classString.trim();
        this.packageString = packageString.trim();
        this.smellCategories = new SmellCategories();
    }

    public void addSmellCategory(SmellType smellType) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String category = smellType.getSmellType();
        PropertyUtils.setSimpleProperty(smellCategories, category, "YES");
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

    public SmellCategories getSmellCategories() {
        return smellCategories;
    }

    public void setSmellCategories(SmellCategories smellCategories) {
        this.smellCategories = smellCategories;
    }

    @Override
    public String toString() {
        return "RowReportSmell{" +
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

