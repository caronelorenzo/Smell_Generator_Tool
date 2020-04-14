package it.unibas.smell.workflow;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.StorePrintStream;
import it.unibas.smell.controllo.Utility;
import it.unibas.smell.report.ReportSmell;
import it.unibas.smell.report.RowReportCompleto;
import it.unibas.smell.report.RowReportSmell;
import it.unibas.smell.report.SmellCategories;
import it.unibas.smell.smellType.SmellType;
import it.unibas.smell.persistence.DAOCsv;
import it.unibas.smell.persistence.DAOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {

    private static Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    public static void generaReportCompletoPerVersione(String projectDir, List<String> tag, String folderPathProjectDataset) throws Exception {
        File[] directories = new File(folderPathProjectDataset).listFiles(File::isDirectory);
        String folderPathExport = folderPathProjectDataset.toString()+ "/Export_Report_Final";
        File file = new File(folderPathExport);
        boolean bool = file.mkdir();
        directories = Arrays.stream(directories).filter(x -> !x.getName().equals("Export_Report_Final")).toArray(File[]::new);
        if (tag.size() != directories.length) {
            throw new IllegalArgumentException("I TAG NON CORRISPONDONO CON LA LISTA DELLE CARTELLE.");
        }
        for (int i = 0; i < directories.length - 1; i++) {
            File directory = directories[i];
            String tagFrom = tag.get(i);
            String tagTo = tag.get(i+1);
            String version = directory.getName();
            String folderPathValidated = directory.getPath().toString() + "/Validated/";
            String reportName = "Report_" + version + ".csv";
            generaReportSmell(folderPathValidated, reportName, projectDir);
            generaReportCompleto(folderPathValidated + reportName, projectDir, tagFrom, tagTo, folderPathExport, version.replace(".", "-"));
            //break;
        }
    }

    public static void generaReportCompleto(String pathReportSmell, String projectDir, String tagFrom, String tagTo, String folderPathExport, String prefix) throws Exception {
        List<RowReportCompleto> reportCompleto = new ArrayList<>();
        List<RowReportSmell> reportSmell = DAOCsv.leggiCSVReportSmell(pathReportSmell);
        System.setOut(new StorePrintStream(System.out));
        for (RowReportSmell rowReportSmell : reportSmell) {
            String packageString = rowReportSmell.getPackageString();
            String className = rowReportSmell.getClassString();
            Path classPath = Paths.get(packageToPath(packageString), className);
            SmellCategories smellCategories = rowReportSmell.getSmellCategories();
            String log = GitCommand.logShaID(tagFrom, tagTo, classPath.toString(), projectDir);
            if (!log.isEmpty()) {
                List<String> commitList = Arrays.asList(log.trim().split("\n"));
                for (String commit : commitList) {
                    String sha = Utility.matchSHA1(commit).trim();
                    String message = GitCommand.logMessage(sha, projectDir);
                    //logger.debug("MESSAGE: " + message);
                    String[] sentiStrenghtOutput = ReportGenerator.getSentiStrenghtOutput(message);
                    String positivity = sentiStrenghtOutput[0];
                    String negativity = sentiStrenghtOutput[1];
                    RowReportCompleto rowReportCompleto = new RowReportCompleto(className, packageString, smellCategories, sha, message, positivity, negativity);
                    reportCompleto.add(rowReportCompleto);
                }
            } else {
                RowReportCompleto rowReportCompleto = new RowReportCompleto(className, packageString, smellCategories, "", "", "", "");
            }
        }
        String reportName = MessageFormat.format("{0}_{1}-{2}.csv", prefix, tagFrom, tagTo);
        Path pathReportCompleto = Paths.get(folderPathExport, reportName);
        DAOCsv.createCsv(pathReportCompleto.toString(), reportCompleto, RowReportCompleto.class);
        //DAOCsv.scriviCSVGenerico(pathReportCompleto.toString(), reportCompleto);
    }

    private static String[] getSentiStrenghtOutput(String message) {
        String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", message};
        SentiStrength.main(ssthInitialisationAndText);
        String s = StorePrintStream.printList.get(StorePrintStream.printList.size()-1);
        return s.trim().split(" ");
    }

    private static void makeExportFolder(String datasetSource) {
        String newFolderPath = datasetSource + "/Export";
        File file = new File(newFolderPath);
        boolean bool = file.mkdir();
        if (bool) {
            logger.debug("Directory created successfully: " + newFolderPath);
        } else {
            logger.debug("Sorry couldn’t create specified directory");
        }
    }

    private static String packageToPath(String packageString) {
        String replace = packageString.replace(".", "/");
        return replace;
    }

    private static String pathToPackage(String pathString) {
        String replace = pathString.replace("/", ".");
        return replace;
    }

    public static void generaReportSmell(String folderPathValidated, String reportName, String allProjectFolderPath) {
        try {
            List<File> allProjectFiles = getAllProjectFiles(allProjectFolderPath);
            List<RowReportSmell> rowComplete = createRowComplete(allProjectFiles);
            ReportSmell reportSmell = generaReportSmell(folderPathValidated);
            reportSmell.addNonSmellyRow(rowComplete);
            Path pathReport = Paths.get(folderPathValidated, reportName);
            //DAOCsv.scriviCSVGenerico(pathReport.toString(), reportSmell.getReport());
            DAOCsv.createCsv(pathReport.toString(), reportSmell.getReport(), RowReportSmell.class);
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
    }

    public static ReportSmell generaReportSmell(String folderPathValidated) {
        ReportSmell reportSmell = new ReportSmell();
        List<File> fileList = getFolderFilesList(folderPathValidated);
        try {
            for (File file : fileList) {
                boolean b = Costanti.fileNameClassi.containsKey(file.getName());
                if (b) {
                    Class smellClass = Costanti.fileNameClassi.get(file.getName());
                    List<SmellType> smellRow = DAOCsv.leggiCSV(smellClass, file.getPath());
                    smellRow.forEach(reportSmell::addSmellRow);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reportSmell;
    }

    public static List<File> getAllProjectFiles(String projectDir) {
        File dir = new File(projectDir);
        String[] extensions = new String[] { "java" };
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        return files;
    }

    public static List<RowReportSmell> createRowComplete(List<File> files) {
        List<RowReportSmell> list = new ArrayList<>();
        for (File file : files) {
            String className = file.getName().trim();
            String packageName = getPackageString(file).replace("/" + className, "");
            String pathNameConvert = ReportGenerator.pathToPackage(packageName);
            RowReportSmell rowReportSmell = new RowReportSmell();
            rowReportSmell.setClassString(className);
            rowReportSmell.setPackageString(pathNameConvert);
            list.add(rowReportSmell);
        }
        return list;
    }

    public static String getPackageString(File file) {
        int indexOrg = file.toString().indexOf("org");
        String packageString = file.toString().substring(indexOrg);
        return packageString;
    }

    public static List<File> getFolderFilesList(String folderPath) {
        List<File> fileList = new ArrayList<>();
        try {
            fileList = Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

}
