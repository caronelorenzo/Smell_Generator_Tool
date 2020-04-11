package it.unibas.smell.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class SmellCategories {

    @CsvBindByName(column = "CLASS DATA SHOULD BE PRIVATE")
    @CsvBindByPosition(position = 2)
    private String classDataShouldBePrivate;

    @CsvBindByName(column = "COMPLEX CLASS")
    @CsvBindByPosition(position = 3)
    private String complexClass;

    @CsvBindByName(column = "FEATURE ENVY")
    @CsvBindByPosition(position = 4)
    private String featureEnvy;

    @CsvBindByName(column = "INAPPROPRIATE INTIMACY")
    @CsvBindByPosition(position = 5)
    private String inappropriateIntimacy;

    @CsvBindByName(column = "LARGE CLASS")
    @CsvBindByPosition(position = 6)
    private String largeClass;

    @CsvBindByName(column = "LAZY CLASS")
    @CsvBindByPosition(position = 7)
    private String lazyClass;

    @CsvBindByName(column = "LONG METHODS")
    @CsvBindByPosition(position = 8)
    private String longMethods;

    @CsvBindByName(column = "LONG PARAMETER LIST")
    @CsvBindByPosition(position = 9)
    private String longParameterList;

    @CsvBindByName(column = "MESSAGE CHAIN")
    @CsvBindByPosition(position = 10)
    private String messageChain;

    @CsvBindByName(column = "MIDDLE MAN")
    @CsvBindByPosition(position = 11)
    private String middleMan;

    @CsvBindByName(column = "REFUSED BEQUEST")
    @CsvBindByPosition(position = 12)
    private String refusedBequest;

    @CsvBindByName(column = "SPAGHETTI CODE")
    @CsvBindByPosition(position = 13)
    private String spaghettiCode;

    @CsvBindByName(column = "SPECULATIVE GENERALITY")
    @CsvBindByPosition(position = 14)
    private String speculativeGenerality;

    public SmellCategories() {
    }

    public String getClassDataShouldBePrivate() {
        return classDataShouldBePrivate;
    }

    public void setClassDataShouldBePrivate(String classDataShouldBePrivate) {
        this.classDataShouldBePrivate = classDataShouldBePrivate;
    }

    public String getComplexClass() {
        return complexClass;
    }

    public void setComplexClass(String complexClass) {
        this.complexClass = complexClass;
    }

    public String getFeatureEnvy() {
        return featureEnvy;
    }

    public void setFeatureEnvy(String featureEnvy) {
        this.featureEnvy = featureEnvy;
    }

    public String getInappropriateIntimacy() {
        return inappropriateIntimacy;
    }

    public void setInappropriateIntimacy(String inappropriateIntimacy) {
        this.inappropriateIntimacy = inappropriateIntimacy;
    }

    public String getLargeClass() {
        return largeClass;
    }

    public void setLargeClass(String largeClass) {
        this.largeClass = largeClass;
    }

    public String getLazyClass() {
        return lazyClass;
    }

    public void setLazyClass(String lazyClass) {
        this.lazyClass = lazyClass;
    }

    public String getLongMethods() {
        return longMethods;
    }

    public void setLongMethods(String longMethods) {
        this.longMethods = longMethods;
    }

    public String getLongParameterList() {
        return longParameterList;
    }

    public void setLongParameterList(String longParameterList) {
        this.longParameterList = longParameterList;
    }

    public String getMessageChain() {
        return messageChain;
    }

    public void setMessageChain(String messageChain) {
        this.messageChain = messageChain;
    }

    public String getMiddleMan() {
        return middleMan;
    }

    public void setMiddleMan(String middleMan) {
        this.middleMan = middleMan;
    }

    public String getRefusedBequest() {
        return refusedBequest;
    }

    public void setRefusedBequest(String refusedBequest) {
        this.refusedBequest = refusedBequest;
    }

    public String getSpaghettiCode() {
        return spaghettiCode;
    }

    public void setSpaghettiCode(String spaghettiCode) {
        this.spaghettiCode = spaghettiCode;
    }

    public String getSpeculativeGenerality() {
        return speculativeGenerality;
    }

    public void setSpeculativeGenerality(String speculativeGenerality) {
        this.speculativeGenerality = speculativeGenerality;
    }
}
