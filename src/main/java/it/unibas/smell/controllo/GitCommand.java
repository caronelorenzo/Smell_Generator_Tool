package it.unibas.smell.controllo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

public class GitCommand {

    public static String log(String tagFrom, String tagTo, String classPath, String projectDir) throws Exception {
        String cmd = MessageFormat.format( "git log --pretty=oneline {0}...{1} --follow -- {2}", tagFrom, tagTo, classPath);
        File destDir = new File(projectDir);
        return execCommand(cmd, destDir);
    }

    public static String execCommand(String cmd, File destDir) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd, null, destDir);
        StringBuilder builder = new StringBuilder();
        Thread stdOutThread = new Thread() {
            @Override
            public void run() {
                String line = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                try {
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        //System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        stdOutThread.start();

        Thread stdErrThread = new Thread() {
            @Override
            public void run() {
                String line = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                try {
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        //System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        stdErrThread.start();

        int exitValue = process.waitFor();
        if (exitValue != 0) {
            throw new Exception("An error has occurred when interacting with GitHub.");
        }

        return builder.toString();
    }

}
