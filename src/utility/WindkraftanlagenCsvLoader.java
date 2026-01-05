package utility;                        // The class is located inside the package called utilities

import model.Standort;                  // Imports Standort from the model package
import model.TechnischeDaten;           // Imports TechnischeDaten from the model package
import model.Windkraftanlage;           // Imports Windkraftanlage from the model package
import resources.Konstanten;

import java.io.BufferedReader;          // Import for standard file reading
import java.io.IOException;             // Imports IO Exception for exception handling
import java.nio.file.Files;             // Imports Files utility
import java.nio.file.Path;              // Imports Path class
import java.util.ArrayList;             // Imports Arraylist
import java.util.List;                  // Imports List interface
import java.util.Objects;               // Imports Objects utility

/**
 * Loads Windkraftanlage objects from a CSV file using a BufferedReader.
 * Returns a mutable list that allows data corrections.
 */
public class WindkraftanlagenCsvLoader {

    /**
     * @precondition csvPath is non-null and points to a readable CSV file.
     * @postcondition returns a mutable List of Windkraftanlage objects.
     */
    public List<Windkraftanlage> load(Path csvPath) throws IOException
    {
        Objects.requireNonNull(csvPath, Konstanten.ERR_CSV_PATH_NULL);

        List<Windkraftanlage> resultList = new ArrayList<>();

        // explicit try-with-resources to ensure the reader is closed automatically
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {

            String line;
            //Loop through the file line by line
            while ((line = br.readLine()) != null) {
                // Parse the line
                Windkraftanlage wka = parseLineToAnlage(line);

                // Only add if parsing was successful (not null)
                if (wka != null) {
                    resultList.add(wka);
                }
            }
        }

        return resultList;
    }

    // Parses a single CSV row into a Windkraftanlage instance.
    private Windkraftanlage parseLineToAnlage(String line) {
        // Use Constant for the complex Regex
        String[] columns = line.split(Konstanten.REGEX_CSV_SPLIT, -1);

        if (columns.length < Konstanten.CSV_MIN_COLUMNS) {
            return null;
        }

        try {
            // Use nullIfEmpty to ensure missing text fields are truly null
            int objektId = Integer.parseInt(columns[Konstanten.COL_ID].trim());
            String name = nullIfEmpty(columns[Konstanten.COL_NAME]);

            // Technische Daten
            // Handle int conversion: Parse Integer first, then default to 0 if null
            Integer baujahrRaw = parseIntegerNullable(columns[Konstanten.COL_BAUJAHR]);
            int baujahr = (baujahrRaw != null) ? baujahrRaw : 0;

            Double gesamtleistung = parseDoubleNullable(columns[Konstanten.COL_LEISTUNG]);
            Integer anzahl = parseIntegerNullable(columns[Konstanten.COL_ANZAHL]);
            String typ = nullIfEmpty(columns[Konstanten.COL_TYP]);

            // Standort
            String ort = nullIfEmpty(columns[Konstanten.COL_ORT]);
            String landkreis = nullIfEmpty(columns[Konstanten.COL_LANDKREIS]);
            Double breitengrad = parseDoubleNullable(columns[Konstanten.COL_BREITE]);
            Double laengengrad = parseDoubleNullable(columns[Konstanten.COL_LAENGE]);

            String betreiber = nullIfEmpty(columns[Konstanten.COL_BETREIBER]);
            String bemerkung = nullIfEmpty(columns[Konstanten.COL_BEMERKUNG]);

            TechnischeDaten tech = new TechnischeDaten(baujahr, gesamtleistung, anzahl, typ);
            Standort locus = new Standort(ort, landkreis, breitengrad, laengengrad);

            return new Windkraftanlage(objektId,
                    name,
                    tech,
                    locus,
                    betreiber,
                    bemerkung);
        } catch (Exception e) {
            return null;
        }
    }

    private String nullIfEmpty(String s) {
        if (s == null) return null;
        String trimmed = s.replace(Konstanten.STR_QUOTE, Konstanten.STR_EMPTY).trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private Integer parseIntegerNullable(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDoubleNullable(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Double.valueOf(value.trim().replace(Konstanten.CHAR_COMMA, Konstanten.CHAR_DOT));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}