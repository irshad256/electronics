package com.spring.electronics.imports;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.List;

@Service
public class CsvUtils {

    public static List<String[]> parseCsv(String filePath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll(); // Parse all rows into List<String[]>
        }
    }
}
