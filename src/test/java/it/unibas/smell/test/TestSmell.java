package it.unibas.smell.test;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.StorePrintStream;
import it.unibas.smell.controllo.Utility;
import it.unibas.smell.modello.RowReportSmell;
import it.unibas.smell.modello.smellType.*;
import it.unibas.smell.persistence.DAOCsv;
import it.unibas.smell.persistence.DAOException;
import it.unibas.smell.workflow.ReportGenerator;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import uk.ac.wlv.sentistrength.*;

public class TestSmell extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestSmell.class);

    private String folderPath = "/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.1/Validated";
    private String fileName = "candidate_Spaghetti_Code.csv";

    @Test
    public void testFolderFiles() throws IOException {
        List<File> folderFilesList = ReportGenerator.getFolderFilesList(folderPath);
        for (File file : folderFilesList) {
            logger.debug(file.getName());
        }
    }

    @Test
    public void testCommand() throws Exception {
        String log = GitCommand.log("ANT_11", "ANT_181", "src/main/org/apache/tools/tar/TarConstants.java", "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant");
        System.out.println(log);
        //List<String> strings = Utility.matchSHA1(log);
        //strings.forEach(s -> System.out.println(s));
        List<String> split = Arrays.asList(log.trim().split("\n"));
        split.forEach(s -> System.out.println(s));
    }

    @Test
    public void testParentDir(){
        System.out.println(new File("/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.2/Validated/").getParent());
    }

    @Test
    public void testGeneraReportSmell() {
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant";
        String datasetSource = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/apache_1.1/Validated";
        String reportName = "Report_apache_1.1.csv";
        ReportGenerator.generaReportSmell(datasetSource, reportName,projectDir);
    }

    @Test
    public void testGeneraReportCompleto() throws Exception {
        List<String> tag = Arrays.asList("ANT_11", "ANT_12", "ANT_13", "ANT_14", "ANT_141", "ANT_151_FINAL", "ANT_152_FINAL", "ANT_153", "ANT_154", "ANT_15_FINAL", "ANT_160", "ANT_161", "ANT_162", "ANT_163", "ANT_164", "ANT_165", "ANT_170", "ANT_171", "ANT_180", "ANT_181");
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant";
        String pathReportSmell = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/apache_1.1/Validated/Report_apache_1.1.csv";
        String pathReportCompleto = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/apache_1.1/Validated/Export/";
        String prefix = "ReportCompleto";
        ReportGenerator.generaReportCompleto(pathReportSmell, projectDir, tag, pathReportCompleto, prefix);
    }

    @Test
    public void testRecuperoCartelle() throws IOException {
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant";
        File dir = new File(projectDir);
        String[] extensions = new String[] { "java" };
        System.out.println("Getting all .java files in " + dir.getCanonicalPath()
                + " including those in subdirectories");
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        List<RowReportSmell> listaCompletaClassi = ReportGenerator.createRowComplete(files);
        for (RowReportSmell rowReportSmell : listaCompletaClassi) {
            System.out.println(rowReportSmell.toString());
        }
    }

    @Test
    public void testSentiStrenght() throws Exception {
        //Method 1: one-off classification (inefficient for multiple classifications)
        //Create an array of command line parameters, including text or file to process
        //String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", "I+hate+frogs+but+love+dogs.", "explain"};
        //String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", "Remove unnecessary use of Project.resolveFile by converting arguments to Files from Strings", "explain"};
        //SentiStrength.main(ssthInitialisationAndText);
        //Method 2: One initialisation and repeated classifications
        //SentiStrength sentiStrength = new SentiStrength();
        //Create an array of command line parameters to send (not text or file to process)
        //String ssthInitialisation[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "explain"};
        //sentiStrength.initialise(ssthInitialisation); //Initialise
        //can now calculate sentiment scores quickly without having to initialise again
        //System.out.println(sentiStrength.computeSentimentScores("I hate frogs."));
        //System.out.println(sentiStrength.computeSentimentScores("I love dogs."));
    }

    @Test
    public void testSentiStrenght2() throws Exception {
        //String ssthInitialisationAndText5[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", line5, "explain"};
        //SentiStrength.main(ssthInitialisationAndText5);
        System.setOut(new StorePrintStream(System.out));
        //List<String> listMessages = Arrays.asList("Remove unnecessary use of Project.resolveFile by converting arguments to Files from Strings.", "Only delete the target file when it is in fact a file - don't delete directories.", "delete existing target files before copying, hate this avoids overwriting symlinks and is consistent with Unix's cp(1).", "Some initial work on rmic to make it handle -iiop better (doesn't detect generated files properly) - doesn't quite work ATM.", "Allow data types to appear inside of targets.");
        //List<String> listMessages = Arrays.asList("Remove unnecessary use of Project.resolveFile by converting arguments to Files from Strings.");
        String s = "Copyright Owner is The Apache Software Foundation";
        //for (String message : listMessages) {
            //String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", message};
            //SentiStrength.main(ssthInitialisationAndText);
        //}
        String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", s};
        SentiStrength.main(ssthInitialisationAndText);
        //System.out.println(StorePrintStream.printList);
        for (String message : StorePrintStream.printList) {
            System.out.println(message);
        }
    }

    @Test
    public void test2() throws Exception {
        String log = GitCommand.log("ANT_11", "ANT_181", "src/main/org/apache/tools/tar/TarConstants.java", "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant");
        List<String> commitList = Arrays.asList(log.trim().split("\n"));
        //int index = 0;
        System.setOut(new StorePrintStream(System.out));
        for (String commit : commitList) {
            String sha = Utility.matchSHA1(commit).trim();
            String message = Utility.getMessage(sha, commit).trim();
            logger.debug("Message: "+message);
            //String message = "trailing love spaces";
            String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", message};
            SentiStrength.main(ssthInitialisationAndText);
            String s = StorePrintStream.printList.get(StorePrintStream.printList.size()-1);
            String[] finaleStringa = s.trim().split(" ");
            logger.debug("Risultato: " + finaleStringa[0]+" "+finaleStringa[1]);
            logger.debug("\n");
            //index++;
        }
    }

    @Test
    public void testGeneraReportAnt() throws Exception {
        List<String> tag = Arrays.asList("ANT_11", "ANT_12", "ANT_13", "ANT_14", "ANT_141", "ANT_151_FINAL", "ANT_152_FINAL", "ANT_153", "ANT_154", "ANT_15_FINAL", "ANT_160", "ANT_161", "ANT_162", "ANT_163", "ANT_164", "ANT_165", "ANT_170", "ANT_171", "ANT_180", "ANT_181");
        //String projectDir = "/Users/lorenzocarone/Dropbox (Personal)/TESI/Progetti Tesi/ant";
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant";
        //String folderPathProjectDataset = "/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/";
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }

}
