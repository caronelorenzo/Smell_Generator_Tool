package it.unibas.smell.persistence;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import it.unibas.smell.report.RowReportSmell;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DAOCsv {

    private static Logger logger = LoggerFactory.getLogger(DAOCsv.class);

    public static <T> List<T> leggiCSV(Class<T> t, String filePath) throws DAOException {
        try {
            Path path = Paths.get(filePath);
            return new CsvToBeanBuilder(new FileReader(path.toFile()))
                    .withSeparator(';')
                    .withType(t)
                    .build().parse();
        } catch (FileNotFoundException ex) {
            throw new DAOException("Impossibile leggere il file " + filePath);
        }
    }

    public static List<RowReportSmell> leggiCSVReportSmell(String filePath) throws DAOException {
        CustomMappingStrategy<RowReportSmell> mappingStrategy = new CustomMappingStrategy<RowReportSmell>();
        mappingStrategy.setType(RowReportSmell.class);
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(filePath));
            return new CsvToBeanBuilder<RowReportSmell>(reader)
                    .withSeparator(';')
                    .withType(RowReportSmell.class)
                    .withMappingStrategy(mappingStrategy)
                    .withSkipLines(1)
                    .build().parse();
        } catch (IOException ex) {
            throw new DAOException("Impossibile leggere il file " + filePath);
        }
    }

    public static <T> void scriviCSVGenerico(String filename, List<T> righe) throws DAOException {
        Writer writer = null;
        ICSVWriter csvWriter = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(filename));
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(';')
                    .build();
            sbc.write(righe);
        } catch (IOException ex) {
            logger.error(ex.toString());
            throw new DAOException("Impossibile scrivere il file ");
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
                if (csvWriter != null) {
                    try {
                        csvWriter.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    public static <T> void createCsv(String filename, List<T> data, Class<T> beanClazz) throws DAOException {
        CustomMappingStrategy<T> mappingStrategy = new CustomMappingStrategy<T>();
        mappingStrategy.setType(beanClazz);
        Writer writer = null;
        ICSVWriter csvWriter = null;
        String csv = "";
        try {
            writer = Files.newBufferedWriter(Paths.get(filename));
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(';')
                    .withMappingStrategy(mappingStrategy)
                    .build();
            sbc.write(data);
            csv = writer.toString();
        } catch (CsvRequiredFieldEmptyException e) {
            // TODO add some logging...
        } catch (CsvDataTypeMismatchException e) {
            // TODO add some logging...
        } catch (IOException e) {
            logger.error(e.toString());
            throw new DAOException("Impossibile scrivere il file ");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
                if (csvWriter != null) {
                    try {
                        csvWriter.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

}
