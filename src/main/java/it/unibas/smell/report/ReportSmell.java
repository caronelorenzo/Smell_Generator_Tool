package it.unibas.smell.report;

import it.unibas.smell.smellType.*;
import it.unibas.smell.smellType.LongMethods;
import it.unibas.smell.smellType.SmellType;
import it.unibas.smell.smellType.SmellTypeOdd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ReportSmell {

    private List<RowReportSmell> report;

    private static Logger logger = LoggerFactory.getLogger(ReportSmell.class);

    public ReportSmell() {
        this.report = new ArrayList<>();
    }

    public List<RowReportSmell> getReport() {
        return report;
    }

    public void addSmellRow(SmellType smellType) {
        try {
            updateReport(smellType.getClassString(), smellType.getPackageString(), smellType);
            if (smellType instanceof SmellTypeOdd) {
                SmellTypeOdd smellTypeCast = (SmellTypeOdd) smellType;
                updateReport(smellTypeCast.getClassString1(), smellTypeCast.getPackageString1(), smellTypeCast);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void updateReport(String classString, String packageString, SmellType smellType) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RowReportSmell rowReportSmell = new RowReportSmell(classString, packageString);
        int index = report.indexOf(rowReportSmell);
        if (index != -1) {
            rowReportSmell = report.get(index);
            rowReportSmell.addSmellCategory(smellType);
        } else {
            rowReportSmell.addSmellCategory(smellType);
            report.add(rowReportSmell);
        }
    }

    public void addNonSmellyRow(List<RowReportSmell> rowReportSmellList) {
        for (RowReportSmell rowReportSmell : rowReportSmellList) {
            if (report.contains(rowReportSmell)) {
                continue;
            }
            report.add(rowReportSmell);
        }
    }

}
