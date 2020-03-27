package it.unibas.smell.modello;

import it.unibas.smell.modello.smellType.*;
import it.unibas.smell.modello.smellType.SmellType;
import it.unibas.smell.workflow.Costanti;

import java.util.*;

public class ReportSmell {

    private List<RowReportSmell> report;

    public ReportSmell() {
        this.report = new ArrayList<>();
    }

    public List<RowReportSmell> getReport() {
        return report;
    }

    public void addSmellRow(SmellType smellType) {
        RowReportSmell rowReportSmell = null;
        int indexIsAlreadyPresent = isAlreadyPresent(smellType);
        String smellTypeString = smellType.getSmellType().trim();
        //System.out.println(indexIsAlreadyPresent + smellTypeString);
        if (indexIsAlreadyPresent != -1) {
            if (smellType.getSmellType().equals(LongMethods.class.getSimpleName())) {
                return;
            }
            rowReportSmell = report.get(indexIsAlreadyPresent);
            String smellType1 = String.join(", ", Arrays.asList(rowReportSmell.getSmellType(), smellTypeString));
            rowReportSmell.setSmellType(smellType1);
        } else {
            rowReportSmell = new RowReportSmell();
            rowReportSmell.setSmellType(smellTypeString);
            rowReportSmell.setClassString(smellType.getClassString().trim());
            rowReportSmell.setPackageString(smellType.getPackageString().trim());
            report.add(rowReportSmell);
        }
        if (smellType instanceof SmellTypeOdd) {
            SmellTypeOdd smellTypeCast = (SmellTypeOdd) smellType;
            int indexIsAlreadyPresent1 = isAlreadyPresent(smellTypeCast);
            if (indexIsAlreadyPresent1 != -1) {
                rowReportSmell = report.get(indexIsAlreadyPresent1);
                String smellType1 = String.join(", ", Arrays.asList(rowReportSmell.getSmellType(), smellTypeString));
                rowReportSmell.setSmellType(smellType1);
            } else {
                RowReportSmell rowReportSmell1 = new RowReportSmell();
                rowReportSmell1.setSmellType(smellTypeString);
                rowReportSmell1.setClassString(smellTypeCast.getClassString1().trim());
                rowReportSmell1.setPackageString(smellTypeCast.getPackageString1().trim());
                report.add(rowReportSmell1);
            }
        }
    }

    public int isAlreadyPresent(SmellType smellType) {
        int index;
        for (ListIterator<RowReportSmell> i = report.listIterator(); i.hasNext(); ) {
            index = i.nextIndex();
            RowReportSmell row = i.next();
            if (row.getClassString().trim().equals(smellType.getClassString().trim()) && row.getPackageString().trim().equals(smellType.getPackageString().trim())) {
                return index;
            }
        }
        return -1;
    }

    public int isAlreadyPresent(SmellTypeOdd smellTypeOdd) {
        int index;
        for (ListIterator<RowReportSmell> i = report.listIterator(); i.hasNext(); ) {
            index = i.nextIndex();
            RowReportSmell row = i.next();
            if (row.getClassString().trim().equals(smellTypeOdd.getClassString1().trim()) && row.getPackageString().trim().equals(smellTypeOdd.getPackageString1().trim())) {
                return index;
            }
        }
        return -1;
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
