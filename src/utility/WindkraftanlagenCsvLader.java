package utility;                        // The class is located inside the package called utilities

import model.Standort;                  // Imports Standort from the model package
import model.TechnischeDaten;           // Imports TechnisheDaten from the model package
import model.Windkraftanlage;           // Imports Windkraftanlage from the model package

import java.io.IOException;             // Imports IO Exception for exception handling
import java.nio.file.Files;             // Imports
import java.nio.file.Path;              // Imports
import java.util.ArrayList;             // Imports Arraylist from the model package
import java.util.List;                  // Imports Lists from java utilities
import java.util.Objects;               // Imports Objects from java utilities
import java.util.stream.Collectors;     // Imports Collectors from stream in  java utilities
import java.util.stream.Stream;         // Imports Streams from stream in java utilities

/**
 * Loads Windkraftanlage objects from a CSV file using Java streams.
 * Returns a mutable list to allow for data corrections.
 */
public class WindkraftanlagenCsvLader {

    /**
     * Precondition: csvPath is non-null and points to a readable CSV file.
     * Postcondition: returns a mutable List of Windkraftanlage objects.
     */
    public List<Windkraftanlage> load(Path csvPath) throws IOException {
        Objects.requireNonNull(csvPath, Konstanten.ERR_CSV_PATH_NULL);

        try (Stream<String> lines = Files.lines(csvPath)) {
            return lines
                    .skip(1) // skip header line
                    .map(this::parseLineToAnlage)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    /**
     * Parses a single CSV row into a Windkraftanlage instance.
     */
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
            Integer baujahr = parseIntegerNullable(columns[Konstanten.COL_BAUJAHR]);
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

            return new Windkraftanlage(objektId, name, tech, locus, betreiber, bemerkung);
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