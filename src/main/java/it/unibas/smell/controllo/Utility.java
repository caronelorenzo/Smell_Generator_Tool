package it.unibas.smell.controllo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static List<String> matchSHA1(String execOutput){
        List<String> sha1 = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b[0-9a-f]{40}\\b");
        Matcher matcher = pattern.matcher(execOutput);
        while(matcher.find()){
            sha1.add(matcher.group());
        }
        return sha1;
    }
}
