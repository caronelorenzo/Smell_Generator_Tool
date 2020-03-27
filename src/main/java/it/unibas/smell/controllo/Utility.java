package it.unibas.smell.controllo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String matchSHA1(String commit){
        Pattern pattern = Pattern.compile("\\b[0-9a-f]{40}\\b");
        Matcher matcher = pattern.matcher(commit);
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }

    public static String getMessage(String sha1, String commit){
        return commit.replace(sha1, "").trim();
    }

}
