package it.unibas.smell.workflow;

import it.unibas.smell.modello.smellType.*;

import java.util.AbstractMap;
import java.util.Map;

public class Costanti {

    public static Map<String, Class<?>> fileNameClassi = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Class_Data_Should_Be_Private.csv", ClassDataShouldBePrivate.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Complex_Class.csv", ComplexClass.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Feature_Envy.csv", FeatureEnvy.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Inappropriate_Intimacy.csv", InappropriateIntimacy.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Large_Class.csv", LargeClass.class),
            //new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Lazy_Class.csv", LazyClass.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Long_Methods.csv", LongMethods.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Long_Parameter_List.csv", LongParameterList.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Message_Chains.csv", MessageChain.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Middle_Man.csv", MiddleMan.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Refused_Bequest.csv", RefusedBequest.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Spaghetti_Code.csv", SpaghettiCode.class),
            new AbstractMap.SimpleEntry<String, Class<?>>("candidate_Speculative_Generality.csv", SpeculativeGenerality.class)
    );


}
