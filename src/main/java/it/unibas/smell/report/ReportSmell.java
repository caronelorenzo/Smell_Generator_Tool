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

//    public void addSmellRow(SmellType smellType) {
//        RowReportSmell rowReportSmell = null;
//        int indexIsAlreadyPresent = isAlreadyPresent(smellType);
//        String smellTypeString = smellType.getSmellType().trim();
//        //System.out.println(indexIsAlreadyPresent + smellTypeString);
//        if (indexIsAlreadyPresent != -1) {
//            if (smellType.getSmellType().equals(LongMethods.class.getSimpleName())) {
//                return;
//            }
//            rowReportSmell = report.get(indexIsAlreadyPresent);
//            String smellType1 = String.join(", ", Arrays.asList(rowReportSmell.getSmellType(), smellTypeString));
//            rowReportSmell.setSmellType(smellType1);
//        } else {
//            rowReportSmell = new RowReportSmell();
//            rowReportSmell.setSmellType(smellTypeString);
//            rowReportSmell.setClassString(smellType.getClassString().trim());
//            rowReportSmell.setPackageString(smellType.getPackageString().trim());
//            report.add(rowReportSmell);
//        }
//        if (smellType instanceof SmellTypeOdd) {
//            SmellTypeOdd smellTypeCast = (SmellTypeOdd) smellType;
//            int indexIsAlreadyPresent1 = isAlreadyPresent(smellTypeCast);
//            if (indexIsAlreadyPresent1 != -1) {
//                rowReportSmell = report.get(indexIsAlreadyPresent1);
//                String smellType1 = String.join(", ", Arrays.asList(rowReportSmell.getSmellType(), smellTypeString));
//                rowReportSmell.setSmellType(smellType1);
//            } else {
//                RowReportSmell rowReportSmell1 = new RowReportSmell();
//                rowReportSmell1.setSmellType(smellTypeString);
//                rowReportSmell1.setClassString(smellTypeCast.getClassString1().trim());
//                rowReportSmell1.setPackageString(smellTypeCast.getPackageString1().trim());
//                report.add(rowReportSmell1);
//            }
//        }
//    }

//    public int isAlreadyPresent(SmellType smellType) {
//        int index;
//        for (ListIterator<RowReportSmell> i = report.listIterator(); i.hasNext(); ) {
//            index = i.nextIndex();
//            RowReportSmell row = i.next();
//            if (row.getClassString().trim().equals(smellType.getClassString().trim()) && row.getPackageString().trim().equals(smellType.getPackageString().trim())) {
//                return index;
//            }
//        }
//        return -1;
//    }
//
//    public int isAlreadyPresent(SmellTypeOdd smellTypeOdd) {
//        int index;
//        for (ListIterator<RowReportSmell> i = report.listIterator(); i.hasNext(); ) {
//            index = i.nextIndex();
//            RowReportSmell row = i.next();
//            if (row.getClassString().trim().equals(smellTypeOdd.getClassString1().trim()) && row.getPackageString().trim().equals(smellTypeOdd.getPackageString1().trim())) {
//                return index;
//            }
//        }
//        return -1;
//    }

    public void addNonSmellyRow(List<RowReportSmell> rowReportSmellList) {
        for (RowReportSmell rowReportSmell : rowReportSmellList) {
            if (report.contains(rowReportSmell)) {
                continue;
            }
            report.add(rowReportSmell);
        }
    }

}
