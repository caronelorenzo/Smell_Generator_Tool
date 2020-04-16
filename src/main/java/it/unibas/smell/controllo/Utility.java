package it.unibas.smell.controllo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

//    public static List<String> matchSHA1(String execOutput){
//        List<String> sha1 = new ArrayList<>();
//        Pattern pattern = Pattern.compile("\\b[0-9a-f]{40}\\b");
//        Matcher matcher = pattern.matcher(execOutput);
//        while(matcher.find()){
//            sha1.add(matcher.group());
//        }
//        return sha1;
//    }

    //NUOVO METODO
    public static String matchSHA1(String commit){
        Pattern pattern = Pattern.compile("\\b[0-9a-f]{40}\\b");
        Matcher matcher = pattern.matcher(commit);
        if (matcher.find()){
            return matcher.group();
        }
        System.out.println("SHA: " + matcher.group());
        return null;
    }

    public static String getMessage(String sha1, String commit){
        return commit.replace(sha1, "").trim();
    }

    //PROVO METODO RITORNA IL COMMIT MESSAGE CON IN INPUT LO SHA-ID SU UN'UNICA RIGA
    public static String getMessageFromShaId(String shaID, String projectDir) throws Exception {
        String cmdString = GitCommand.logMessage(shaID, projectDir);

        List<String> split = Arrays.asList(cmdString.trim().split("\n"));
        split.forEach(s -> System.out.println(s));

        return cmdString;
    }

    public static String packageToPath(String packageString) {
        String replace = packageString.replace(".", "/");
        return replace;
    }

    public static String pathToPackage(String pathString) {
        String replace = pathString.replace("/", ".");
        return replace;
    }

}
