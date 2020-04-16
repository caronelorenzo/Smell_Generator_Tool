package it.unibas.smell.report;

import it.unibas.smell.controllo.GitCommand;
import it.unibas.smell.controllo.StorePrintStream;
import it.unibas.smell.controllo.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportCompleto {

    private List<RowReportCompleto> report;
    private static Logger logger = LoggerFactory.getLogger(ReportCompleto.class);

    public ReportCompleto() {
        this.report = new ArrayList<>();
    }

    public void createReport(List<RowReportSmell> rowReportSmellList, String tagFrom, String tagTo, String projectDir) {
        System.setOut(new StorePrintStream(System.out));
        for (RowReportSmell rowReportSmell : rowReportSmellList) {
            String packageString = rowReportSmell.getPackageString();
            String className = rowReportSmell.getClassString();
            Path classPath = Paths.get(Utility.packageToPath(packageString), className);
            SmellCategories smellCategories = rowReportSmell.getSmellCategories();
            try {
                String log = GitCommand.logShaID(tagFrom, tagTo, classPath.toString(), projectDir);
                RowReportCompleto rowReportCompleto = null;
                if (!log.isEmpty()) {
                    String[] commitList = log.trim().split("\n");
                    for (String commit : commitList) {
                        String sha = Utility.matchSHA1(commit).trim();
                        String message = GitCommand.logMessage(sha, projectDir);
                        CommitScore commitScore = getSentiStrenghtOutput(message);
                        rowReportCompleto = new RowReportCompleto(className, packageString, smellCategories, sha, message, commitScore);
                        report.add(rowReportCompleto);
                    }
                }
//                else {
//                    rowReportCompleto = new RowReportCompleto(className, packageString, smellCategories, "", "", new CommitScore());
//                    report.add(rowReportCompleto);
//                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private static CommitScore getSentiStrenghtOutput(String message) {
        String ssthInitialisationAndText[] = {"sentidata", "/Users/lorenzocarone/Google Drive/TESI/SentiStrength-SE_v1.5/ConfigFiles", "text", message};
        SentiStrength.main(ssthInitialisationAndText);
        String s = StorePrintStream.printList.get(StorePrintStream.printList.size()-1);
        String[] sentiStrenghtOutput = s.trim().split(" ");
        String positivityStr = sentiStrenghtOutput[0];
        String negativityStr = sentiStrenghtOutput[1];
        int positivity = Integer.parseInt(positivityStr);
        int negativity = Integer.parseInt(negativityStr);
        return new CommitScore(positivity, negativity);
    }

    public List<RowReportCompleto> getReport() {
        return report;
    }

}
