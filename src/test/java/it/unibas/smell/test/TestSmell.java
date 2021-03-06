package it.unibas.smell.test;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.StorePrintStream;
import it.unibas.smell.controllo.Utility;
import it.unibas.smell.report.RowReportSmell;
import it.unibas.smell.workflow.ReportGenerator;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
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
        String classPath = "src/main/org/apache/tools/tar/TarConstants.java";
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant/";
        String log = GitCommand.logShaID("ANT_11", "ANT_181", classPath, projectDir);
        System.out.println(log);
        //List<String> strings = Utility.matchSHA1(log);
        //strings.forEach(s -> System.out.println(s));
        List<String> split = Arrays.asList(log.trim().split("\n"));
        split.forEach(s -> System.out.println(s));
    }

    @Test
    public void testLogMessage() throws Exception {
        String shaID = "cafa34ca0878175f8c8ab1bf7fcfaba70c2b1368";
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant/";
//      String messageCommit = Utility.getMessageFromShaId(shaID, projectDir);
//      System.out.println(messageCommit);
        String messageCommit = GitCommand.logMessage(shaID, projectDir);
        System.out.println(messageCommit);
    }

    @Test
    public void testParentDir() throws IOException {
        System.out.println(new File("/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.2/Validated/").getParent());
    }

    @Test
    public void testGeneraReportSmell() {
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant";
        String datasetSource = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/apache_1.1/Validated";
        String reportName = "Report_apache_1.1.csv";
        ReportGenerator.generaReportSmell(datasetSource, reportName,projectDir);
    }

//    @Test
//    public void testGeneraReportCompleto() throws Exception {
//        List<String> tag = Arrays.asList("ANT_11", "ANT_12", "ANT_13", "ANT_14", "ANT_141", "ANT_151_FINAL", "ANT_152_FINAL", "ANT_153", "ANT_154", "ANT_15_FINAL", "ANT_160", "ANT_161", "ANT_162", "ANT_163", "ANT_164", "ANT_165", "ANT_170", "ANT_171", "ANT_180", "ANT_181");
//        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant";
//        String pathReportSmell = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/apache_1.1/Validated/Report_apache_1.1.csv";
//        String pathReportCompleto = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/apache_1.1/Validated/Export/";
//        String prefix = "ReportCompleto";
//        ReportGenerator.generaReportCompleto(pathReportSmell, projectDir, tag, pathReportCompleto, prefix);
//    }

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
        String s = "replace isDirect with !hasArray for correctness";
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
        String log = GitCommand.logShaID("ANT_11", "ANT_181", "src/main/org/apache/tools/tar/TarConstants.java", "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant");
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
        List<String> tag = Arrays.asList("ANT_11", "ANT_12", "ANT_13", "ANT_14", "ANT_141", "ANT_15_FINAL", "ANT_151_FINAL", "ANT_152_FINAL", "ANT_153", "ANT_154", "ANT_160", "ANT_161", "ANT_162", "ANT_163", "ANT_164", "ANT_170", "ANT_171", "ANT_180_RC1", "ANT_180", "ANT_181", "ANT_182", "ANT_183");
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant/src/main/";
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ant-data/";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }

    @Test
    public void testGeneraReportCassandra() throws Exception {
        List<String> tag = Arrays.asList("cassandra-0.3.0-final", "cassandra-0.4.0-final", "cassandra-0.5.0", "cassandra-0.6.0", "cassandra-0.7.0", "cassandra-0.7.2", "cassandra-0.7.3", "cassandra-0.8.0", "cassandra-0.8.1", "cassandra-0.8.3", "cassandra-1.0.0", "cassandra-1.1.0");
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/cassandra/src/java/";
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-cassandra";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }

    @Test
    public void testGeneraReportIvy() throws Exception {
        List<String> tag = Arrays.asList("2.0.0-alpha1", "2.0.0-alpha2", "2.0.0-beta1", "2.0.0-beta2", "2.0.0-rc1", "2.0.0-rc2",  "2.0.0",  "2.1.0-rc2", "2.1.0");
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/ant-ivy/src/java";
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-ivy-data";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }

    @Test
    public void testGeneraReportNutch() throws Exception {
        List<String> tag = Arrays.asList("release-0.7", "release-0.8", "release-0.9", "release-1.1", "release-1.2", "release-1.3",  "release-1.4");
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/nutch/src/java";
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-nutch-data";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }

    @Test
    public void testGeneraReportXerces() throws Exception {
        List<String> tag = Arrays.asList("Xerces-J_1_0_1", "Xerces-J_1_0_4", "Xerces-J_1_2_0", "Xerces-J_1_2_1", "Xerces-J_1_2_2", "Xerces-J_1_2_3",  "Xerces-J_1_3_0",  "Xerces-J_1_3_1",  "Xerces-J_1_4_0",  "Xerces-J_1_4_1",  "Xerces-J_1_4_2",  "Xerces-J_1_4_4",  "Xerces-J_2_0_0",  "Xerces-J_2_1_0",  "Xerces-J_2_2_0",  "Xerces-J_2_3_0");
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/xerces2-j/src";
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-xerces-data";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }

    @Test
    public void testGeneraReportGenerale() throws Exception {
        //INSERIRE L'ELENCO DEI TAG PER CUI SI VUOLE EFFETTUARE L'ANALISI
        List<String> tag = Arrays.asList("0.10", "0.12", "0.14", "0.16", "0.18");
        //INSERIRE LA CARTELLA DEL PROGETTO DA ANALIZZARE
        String projectDir = "/Users/lorenzocarone/Google Drive/TESI/Progetti Tesi/qpid/qpid/tools/src/";
        //INSERIRE LA CARTELLA DEL DATASET DA CUI PRENDERE I DATI
        String folderPathProjectDataset = "/Users/lorenzocarone/Google Drive/TESI/dataset/apache-qpid-data";
        ReportGenerator.generaReportCompletoPerVersione(projectDir, tag, folderPathProjectDataset);
    }
}
