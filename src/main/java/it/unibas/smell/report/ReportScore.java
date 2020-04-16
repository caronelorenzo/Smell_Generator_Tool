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
            RowReportScore rowReportScore = null;
            if (report.containsKey(rowReportSmell)) {
                rowReportScore = report.get(rowReportSmell);
                rowReportScore.addCommitScore(rowReportCompleto.getCommitScore());
            } else {
                report.put(rowReportSmell, new RowReportScore(rowReportSmell));
            }
        }
    }

    public List<RowReportScore> getReport() {
        return new ArrayList<>(report.values());
    }

}
