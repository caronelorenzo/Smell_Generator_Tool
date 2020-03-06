package it.unibas.smell.persistence;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import it.unibas.smell.modello.RowReportSmell;
import it.unibas.smell.modello.smellType.SmellType;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
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

    public static <T> void scriviCSVGenerico(String filename, List<T> righe) throws DAOException {
        Writer writer = null;
        ICSVWriter csvWriter = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(filename));
            //TODO implementare mappingstrategy per ordinare le colonne in base all'indice
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

}
