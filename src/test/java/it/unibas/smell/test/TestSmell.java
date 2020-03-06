package it.unibas.smell.test;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.Utility;
import it.unibas.smell.modello.smellType.*;
import it.unibas.smell.persistence.DAOCsv;
import it.unibas.smell.persistence.DAOException;
import it.unibas.smell.workflow.ReportGenerator;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

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
    public void testReadAndWrite() {
        ReportGenerator.generaReportSmell(folderPath, "Report_1.csv");
    }

    @Test
    public void testCommand() throws Exception {
        String log = GitCommand.log("ANT_12", "ANT_13", "src/main/org/apache/tools/tar/TarConstants.java", "/Users/lorenzocarone/Desktop/ant/");
        List<String> strings = Utility.matchSHA1(log);
        strings.forEach(s -> System.out.println(s));
    }

    @Test
    public void testParentDir(){
        System.out.println(new File("/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.2/Validated/").getParent());
    }

    @Test
    public void testGeneraReportSmell() {
        String datasetSource = "/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.2/Validated";
        String reportName = "apache_1.2";
        ReportGenerator.generaReportSmell(datasetSource, reportName);
    }

    @Test
    public void testGeneraReportCompleto() throws Exception {
        List<String> tag = Arrays.asList("ANT_11", "ANT_12", "ANT_13", "ANT_14", "ANT_141", "ANT_151_FINAL", "ANT_152_FINAL", "ANT_153", "ANT_154", "ANT_15_FINAL", "ANT_160", "ANT_161", "ANT_162", "ANT_163", "ANT_164", "ANT_165", "ANT_170", "ANT_171", "ANT_180", "ANT_181");
        String projectDir = "/Users/lorenzocarone/Dropbox (Personal)/TESI/Progetti Tesi/ant";
        String pathReportSmell = "/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.2/Validated/Report_apache_1.2.csv";
        String pathReportCompleto = "/Users/lorenzocarone/Dropbox (Personal)/TESI/dataset/apache-ant-data/apache_1.2/Validated/Export";
        String prefix = "ReportCompleto";
        ReportGenerator.generaReportCompleto(pathReportSmell, projectDir, tag, pathReportCompleto, prefix);
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
