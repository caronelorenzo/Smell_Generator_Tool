package it.unibas.smell.modello;

import it.unibas.smell.modello.smellType.*;
import it.unibas.smell.modello.smellType.SmellType;

import java.util.ArrayList;
import java.util.List;

public class ReportSmell {

    private List<RowReportSmell> report;

    public ReportSmell() {
        this.report = new ArrayList<>();
    }

    public List<RowReportSmell> getReport() {
        return report;
    }

    public void addSmellRow(SmellType smellType) {
        RowReportSmell rowReportSmell = new RowReportSmell();
        String smellTypeString = smellType.getSmellType().trim();
        rowReportSmell.setSmellType(smellTypeString);
        rowReportSmell.setClassName(smellType.getClassString().trim());
        rowReportSmell.setPackageString(smellType.getPackageString().trim());
        report.add(rowReportSmell);
        if (smellType instanceof SmellTypeOdd) {
            SmellTypeOdd smellTypeCast = (SmellTypeOdd) smellType;
            RowReportSmell rowReportSmell1 = new RowReportSmell();
            rowReportSmell1.setSmellType(smellTypeString);
            rowReportSmell1.setClassName(smellTypeCast.getClassString1().trim());
            rowReportSmell1.setPackageString(smellTypeCast.getPackageString1().trim());
            report.add(rowReportSmell1);
        }
    }

}
