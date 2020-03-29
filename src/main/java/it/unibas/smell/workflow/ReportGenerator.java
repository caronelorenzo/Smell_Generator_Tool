package it.unibas.smell.workflow;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.StorePrintStream;
import it.unibas.smell.controllo.Utility;
import it.unibas.smell.modello.ReportSmell;
import it.unibas.smell.modello.RowReportCompleto;
import it.unibas.smell.modello.RowReportSmell;
import it.unibas.smell.modello.smellType.SmellType;
import it.unibas.smell.persistence.DAOCsv;
import it.unibas.smell.persistence.DAOException;
import org.apache.commons.io.FileUtils;
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

    public static void generaReportCompletoPerVersione(String projectDir, List<String> tag, String folderPathProjectDataset) throws Exception {
        File[] directories = new File(folderPathProjectDataset).listFiles(File::isDirectory);
        for (File directory : directories) {
            String version = directory.getName();
            String folderPathValidated = directory.getPath().toString() + "/Validated/";
            String reportName = "Report_" + version + ".csv";
            generaReportSmell(folderPathValidated, reportName, projectDir);
            makeExportFolder(directory.toString());
            String folderPathExport = directory.getPath() + "/Export";
            generaReportCompleto(folderPathValidated + reportName, projectDir, tag, folderPathExport, version.replace(".", "-"));
            //break;
        }
    }

//    public static void generaReportCompleto(String pathReportSmell, String projectDir, List<String> tag, String folderPathExport, String prefix) throws Exception {
//        List<RowReportCompleto> reportCompleto = null;
//        List<RowReportSmell> reportSmell = DAOCsv.leggiCSV(RowReportSmell.class, pathReportSmell);
//        //makeExportFolder(pathReportSmell);
//        for (int i = 0; i < tag.size() - 1; i++) {
//            reportCompleto = new ArrayList<>();
//            String tagFrom = tag.get(i);
//            String tagTo = tag.get(i + 1);
//            for (RowReportSmell rowReportSmell : reportSmell) {
//                String smellType = rowReportSmell.getSmellType();
//                String packageString = rowReportSmell.getPackageString();
//                String className = rowReportSmell.getClassString();
//                Path classPath = Paths.get("src/main/" + packageToPath(packageString), className);
//                String log = GitCommand.log(tagFrom, tagTo, classPath.toString(), projectDir);
//                List<String> sha1List = Utility.matchSHA1(log);
//                String sha1 = String.join(", ", sha1List);
//                RowReportCompleto rowReportCompleto = new RowReportCompleto(smellType, packageString, className, sha1, "", "", "");
//                reportCompleto.add(rowReportCompleto);
//            }
//            String reportName = MessageFormat.format("{0}_{1}-{2}.csv", prefix, tagFrom, tagTo);
//            Path pathReportCompleto = Paths.get(folderPathExport, reportName);
//            DAOCsv.scriviCSVGenerico(pathReportCompleto.toString(), reportCompleto);
//        }
//    }


//  METODO CHE GENERA LO STACKOVERFLOW
    public static void generaReportCompleto(String pathReportSmell, String projectDir, List<String> tag, String folderPathExport, String prefix) throws Exception {
        List<RowReportCompleto> reportCompleto = null;
        List<RowReportSmell> reportSmell = DAOCsv.leggiCSV(RowReportSmell.class, pathReportSmell);
        //makeExportFolder(pathReportSmell);
        System.setOut(new StorePrintStream(System.out));
        for (int i = 0; i < tag.size() - 1; i++) {
            reportCompleto = new ArrayList<>();
            String tagFrom = tag.get(i);
            String tagTo = tag.get(i + 1);
            //System.out.println(tagFrom+" "+tagTo);
            for (RowReportSmell rowReportSmell : reportSmell) {
                String smellType = rowReportSmell.getSmellType();
                String packageString = rowReportSmell.getPackageString();
                String className = rowReportSmell.getClassString();
                //System.out.println(className);
                Path classPath = Paths.get(packageToPath(packageString), className);
                //Path classPath = Paths.get("src/main/" + packageToPath(packageString), className);
                String log = GitCommand.log(tagFrom, tagTo, classPath.toString(), projectDir);
                if (!log.isEmpty()) {
                    //System.out.println("Log: "+log.isEmpty());
                    //List<String> sha1List = Utility.matchSHA1(log);
                    List<String> commitList = Arrays.asList(log.trim().split("\n"));
                    //
                    for (String commit : commitList) {
                        //System.out.println("Commit: "+commit);
                        String sha = Utility.matchSHA1(commit).trim();
                        //System.out.println(sha);
                        String message = Utility.getMessage(sha, commit).trim();
                        //System.out.println(message);
                        String[] sentiStrenghtOutput = ReportGenerator.getSentiStrenghtOutput(message);
                        String positivity = sentiStrenghtOutput[0];
                        String negativity = sentiStrenghtOutput[1];
                        RowReportCompleto rowReportCompleto = new RowReportCompleto(smellType, packageString, className, sha, message, positivity, negativity);
                        //RowReportCompleto rowReportCompleto = new RowReportCompleto(smellType, packageString, className, "", "", "", "");
                        reportCompleto.add(rowReportCompleto);
                        //index++;
                    }
                } else {
                    RowReportCompleto rowReportCompleto = new RowReportCompleto(smellType, packageString, className, "", "", "", "");
                }
            }
            String reportName = MessageFormat.format("{0}_{1}-{2}.csv", prefix, tagFrom, tagTo);
            Path pathReportCompleto = Paths.get(folderPathExport, reportName);
            DAOCsv.scriviCSVGenerico(pathReportCompleto.toString(), reportCompleto);
        }

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
            System.out.println("Directory created successfully: " + newFolderPath);
        } else {
            System.out.println("Sorry couldn’t create specified directory");
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
            DAOCsv.scriviCSVGenerico(pathReport.toString(), reportSmell.getReport());
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
        } catch (DAOException ex) {
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
            String className = file.getName();
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
