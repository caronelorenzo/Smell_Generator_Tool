package it.unibas.smell.modello;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class RowReportSmell {

    @CsvBindByName(column = "CLASS DATA SHOULD BE PRIVATE")
    private boolean classDataShouldBePrivate;

    @CsvBindByName(column = "COMPLEX CLASS")
    private boolean complexClass;

    @CsvBindByName(column = "FEATURE ENVY")
    private boolean featureEnvy;

    @CsvBindByName(column = "INAPPROPRIATE INTIMACY")
    private boolean inappropriateIntimacy;

    @CsvBindByName(column = "LARGE CLASS")
    private boolean largeClass;

    @CsvBindByName(column = "LAZY CLASS")
    private boolean lazyClass;

    @CsvBindByName(column = "LONG METHODS")
    private boolean longMethods;

    @CsvBindByName(column = "LONG PARAMETER LIST")
    private boolean longParameterList;

    @CsvBindByName(column = "MESSAGE CHAIN")
    private boolean messageChain;

    @CsvBindByName(column = "MIDDLE MAN")
    private boolean middleMan;

    @CsvBindByName(column = "REFUSED BEQUEST")
    private boolean refusedBequest;

    @CsvBindByName(column = "SPAGHETTI CODE")
    private boolean spaghettiCode;

    @CsvBindByName(column = "SPECULATIVE GENERALITY")
    private boolean speculativeGenerality;

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

    public boolean isClassDataShouldBePrivate() {
        return classDataShouldBePrivate;
    }

    public boolean isComplexClass() {
        return complexClass;
    }

    public boolean isFeatureEnvy() {
        return featureEnvy;
    }

    public boolean isInappropriateIntimacy() {
        return inappropriateIntimacy;
    }

    public boolean isLargeClass() {
        return largeClass;
    }

    public boolean isLazyClass() {
        return lazyClass;
    }

    public boolean isLongMethods() {
        return longMethods;
    }

    public boolean isLongParameterList() {
        return longParameterList;
    }

    public boolean isMessageChain() {
        return messageChain;
    }

    public boolean isMiddleMan() {
        return middleMan;
    }

    public boolean isRefusedBequest() {
        return refusedBequest;
    }

    public boolean isSpaghettiCode() {
        return spaghettiCode;
    }

    public boolean isSpeculativeGenerality() {
        return speculativeGenerality;
    }

    public void setClassDataShouldBePrivate(boolean classDataShouldBePrivate) {
        this.classDataShouldBePrivate = classDataShouldBePrivate;
    }

    public void setComplexClass(boolean complexClass) {
        this.complexClass = complexClass;
    }

    public void setFeatureEnvy(boolean featureEnvy) {
        this.featureEnvy = featureEnvy;
    }

    public void setInappropriateIntimacy(boolean inappropriateIntimacy) {
        this.inappropriateIntimacy = inappropriateIntimacy;
    }

    public void setLargeClass(boolean largeClass) {
        this.largeClass = largeClass;
    }

    public void setLazyClass(boolean lazyClass) {
        this.lazyClass = lazyClass;
    }

    public void setLongMethods(boolean longMethods) {
        this.longMethods = longMethods;
    }

    public void setLongParameterList(boolean longParameterList) {
        this.longParameterList = longParameterList;
    }

    public void setMessageChain(boolean messageChain) {
        this.messageChain = messageChain;
    }

    public void setMiddleMan(boolean middleMan) {
        this.middleMan = middleMan;
    }

    public void setRefusedBequest(boolean refusedBequest) {
        this.refusedBequest = refusedBequest;
    }

    public void setSpaghettiCode(boolean spaghettiCode) {
        this.spaghettiCode = spaghettiCode;
    }

    public void setSpeculativeGenerality(boolean speculativeGenerality) {
        this.speculativeGenerality = speculativeGenerality;
    }

    @Override
    public String toString() {
        return "RowReportSmell{" +
                "smellType='" + smellType + '\'' +
                ", className='" + classString + '\'' +
                ", packageString='" + packageString + '\'' +
                '}';
    }


}

