package it.unibas.smell.workflow;

import it.unibas.smell.controllo.Utility;
import it.unibas.smell.report.*;
import it.unibas.smell.smellType.SmellType;
import it.unibas.smell.persistence.DAOCsv;
import it.unibas.smell.persistence.DAOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            String reportNameSmell = "Report_" + version + ".csv";
            String reportNameCompleto = "ReportCompleto_" + version + ".csv";
            String reportNameScore = "ReportScore_" + version + ".csv";
            generaReportSmell(folderPathValidated, reportNameSmell, projectDir);
            generaReportCompleto(folderPathValidated + reportNameSmell, projectDir, tagFrom, tagTo, folderPathExport, reportNameCompleto, reportNameScore);
//            if (i == 1) {
//                break;
//            }
        }
    }

    public static void generaReportCompleto(String pathReportSmell, String projectDir, String tagFrom, String tagTo, String folderPathExport, String nomeReportCompleto, String nomeReportScore) throws Exception {
        List<RowReportSmell> reportSmell = DAOCsv.leggiCSVReportSmell(pathReportSmell);
        ReportCompleto reportCompleto = new ReportCompleto();
        ReportScore reportScore = new ReportScore();
        reportCompleto.createReport(reportSmell, tagFrom, tagTo, projectDir);
        reportScore.createReport(reportCompleto.getReport());
        String reportNameCompleto = MessageFormat.format("{0}_{1}-{2}.csv", nomeReportCompleto, tagFrom, tagTo);
        Path pathReportCompleto = Paths.get(folderPathExport, reportNameCompleto);
        String reportNameScore = MessageFormat.format("{0}_{1}-{2}.csv", nomeReportScore, tagFrom, tagTo);
        Path pathReportScore = Paths.get(folderPathExport, reportNameScore);
        DAOCsv.createCsv(pathReportCompleto.toString(), reportCompleto.getReport(), RowReportCompleto.class);
        DAOCsv.createCsv(pathReportScore.toString(), reportScore.getReport(), RowReportScore.class);
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
            String pathNameConvert = Utility.pathToPackage(packageName);
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
