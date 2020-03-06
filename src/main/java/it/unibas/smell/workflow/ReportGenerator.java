package it.unibas.smell.workflow;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.Utility;
import it.unibas.smell.modello.ReportSmell;
import it.unibas.smell.modello.RowReportCompleto;
import it.unibas.smell.modello.RowReportSmell;
import it.unibas.smell.modello.smellType.SmellType;
import it.unibas.smell.persistence.DAOCsv;
import it.unibas.smell.persistence.DAOException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {

    public static void generaReportCompleto(String pathReportSmell, String projectDir, List<String> tag, String folderPathReportCompleto, String prefix) throws Exception {
        List<RowReportCompleto> reportCompleto = null;
        List<RowReportSmell> reportSmell = DAOCsv.leggiCSV(RowReportSmell.class, pathReportSmell);
        for (int i = 0; i < tag.size() - 1; i++) {
            reportCompleto = new ArrayList<>();
            String tagFrom = tag.get(i);
            String tagTo = tag.get(i + 1);
            for (RowReportSmell rowReportSmell : reportSmell) {
                String smellType = rowReportSmell.getSmellType();
                String packageString = rowReportSmell.getPackageString();
                String className = rowReportSmell.getClassName();
                Path classPath = Paths.get("src/main/" + packageToPath(packageString), className);

                String log = GitCommand.log(tagFrom, tagTo, classPath.toString(), projectDir);
                List<String> sha1List = Utility.matchSHA1(log);
                String sha1 = String.join(", ", sha1List);
                RowReportCompleto rowReportCompleto = new RowReportCompleto(smellType, packageString, className, sha1);
                reportCompleto.add(rowReportCompleto);
            }
            String reportName = MessageFormat.format("{0}_{1}-{2}.csv", prefix, tagFrom, tagTo);
            Path pathReportCompleto = Paths.get(folderPathReportCompleto, reportName);
            DAOCsv.scriviCSVGenerico(pathReportCompleto.toString(), reportCompleto);
        }

    }

    private static String packageToPath(String packageString) {
        String replace = packageString.replace(".", "/");
        return replace;
    }

    public static void generaReportSmell(String folderPath, String reportName) {
        ReportSmell reportSmell = new ReportSmell();
        List<File> fileList = getFolderFilesList(folderPath);
        try {
            for (File file : fileList) {
                boolean b = Costanti.fileNameClassi.containsKey(file.getName());
                if (b) {
                    Class smellClass = Costanti.fileNameClassi.get(file.getName());
                    List<SmellType> smellRow = DAOCsv.leggiCSV(smellClass, file.getPath());
                    smellRow.forEach(reportSmell::addSmellRow);
                }
            }
            Path pathReport = Paths.get(folderPath, reportName);
            DAOCsv.scriviCSVGenerico(pathReport.toString(), reportSmell.getReport());
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
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
