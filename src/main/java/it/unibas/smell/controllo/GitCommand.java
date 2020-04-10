package it.unibas.smell.controllo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class GitCommand {

    private static Logger logger = LoggerFactory.getLogger(GitCommand.class);

    //RITORNA LO SHAID
    public static String logShaID(String tagFrom, String tagTo, String classPath, String projectDir) throws Exception {
        //COMANDO ATTUALMENTE FUNZIONANTE MA CHE TRONCA LA FINE DEL MESSAGGIO
        String cmd = MessageFormat.format( "git log --pretty=oneline {0}...{1} --follow -- {2}", tagFrom, tagTo, classPath);
        //logger.debug("GIT COMMAND: "+ cmd);
        File destDir = new File(projectDir);
        return execCommand(cmd, destDir);
    }
    //RITORNA LO SHAID
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
                        builder.append(line+"\n");
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
                        builder.append(line+"\n");
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

    //RITORNA IL MESSAGE DATO IN INPUT LO SHA ID
    public static String logMessage(String shaId, String projectDir) throws Exception {
        //Restituisce il messaggio dal commit richiesto con lo SHA
        //git show -s --format=%B {0} | tr -d ''\n''
        //String cmd1 = MessageFormat.format("git log --pretty=format:'%s%B' -n 1 {0} | tr -d ''\\n'''", shaId);
        String cmd1 = MessageFormat.format("git log --pretty=format:'%s%B' -n 1 {0}", shaId);
        //logger.debug(cmd1);
        File destDir = new File(projectDir);
        return execCommandMessage(cmd1, destDir);
    }

    public static String execCommandMessage(String shaId, File destDir) throws Exception {
        Process process = Runtime.getRuntime().exec(shaId, null, destDir);
        StringBuilder builder = new StringBuilder();
        Thread stdOutThread = new Thread() {
            @Override
            public void run() {
                String line = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                try {
                    while ((line = reader.readLine()) != null) {
                        byte[] b = line.getBytes("UTF-8");
                        line = new String(b, "UTF-8").replace("\n", " ");
                        builder.append(line);
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
        //logger.debug("Fine");
        return builder.toString();
    }

}
