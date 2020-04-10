package it.unibas.smell.report;

import com.opencsv.bean.CsvBindByName;

public class SmellCategories {

    @CsvBindByName(column = "CLASS DATA SHOULD BE PRIVATE")
    private String classDataShouldBePrivate = "NO";

    @CsvBindByName(column = "COMPLEX CLASS")
    private String complexClass = "NO";

    @CsvBindByName(column = "FEATURE ENVY")
    private String featureEnvy = "NO";

    @CsvBindByName(column = "INAPPROPRIATE INTIMACY")
    private String inappropriateIntimacy = "NO";

    @CsvBindByName(column = "LARGE CLASS")
    private String largeClass = "NO";

    @CsvBindByName(column = "LAZY CLASS")
    private String lazyClass = "NO";

    @CsvBindByName(column = "LONG METHODS")
    private String longMethods = "NO";

    @CsvBindByName(column = "LONG PARAMETER LIST")
    private String longParameterList = "NO";

    @CsvBindByName(column = "MESSAGE CHAIN")
    private String messageChain = "NO";

    @CsvBindByName(column = "MIDDLE MAN")
    private String middleMan = "NO";

    @CsvBindByName(column = "REFUSED BEQUEST")
    private String refusedBequest = "NO";

    @CsvBindByName(column = "SPAGHETTI CODE")
    private String spaghettiCode = "NO";

    @CsvBindByName(column = "SPECULATIVE GENERALITY")
    private String speculativeGenerality = "NO";

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
