package it.unibas.smell.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ReportScore {

    private Map<RowReportSmell, RowReportScore> report;
    private static Logger logger = LoggerFactory.getLogger(ReportScore.class);

    public ReportScore() {
        this.report = new HashMap<>();
    }

    public void createReport(List<RowReportCompleto> rowReportCompletoList) {
        for (RowReportCompleto rowReportCompleto : rowReportCompletoList) {
            RowReportSmell rowReportSmell = (RowReportSmell) rowReportCompleto;
            RowReportScore rowReportScore = report.get(rowReportSmell);
            if (rowReportScore == null) {
                rowReportScore = new RowReportScore(rowReportSmell);
                report.put(rowReportSmell, rowReportScore);
            }
            rowReportScore.addCommitScore(rowReportCompleto.getCommitScore());

//            if (report.containsKey(rowReportSmell)) {
//                rowReportScore = report.get(rowReportSmell);
//                rowReportScore.addCommitScore(rowReportCompleto.getCommitScore());
//            } else {
//                rowReportScore = new RowReportScore(rowReportSmell);
//                rowReportScore.addCommitScore(rowReportCompleto.getCommitScore());
//                report.put(rowReportSmell, rowReportScore);
//            }
        }
    }

    public List<RowReportScore> getReport() {
        return new ArrayList<>(report.values());
    }

}
