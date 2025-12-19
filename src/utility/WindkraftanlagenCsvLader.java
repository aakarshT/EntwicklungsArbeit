package utility;

import model.Standort;
import model.TechnischeDaten;
import model.Windkraftanlage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Loads Windkraftanlage objects from a CSV file using Java streams.
 * The loader is robust against:
 *  - trailing empty columns
 *  - missing values (treated as null)
 *  - commas inside quoted fields (e.g. in remarks)
 */
public class WindkraftanlagenCsvLader {

    /**
     * Precondition:
     *  - csvPath is non-null and points to an existing, readable CSV file.
     * Postcondition:
     *  - returns a non-null List of Windkraftanlage objects (may be empty).
     */
    public List<Windkraftanlage> load(Path csvPath) throws IOException {
        List<Windkraftanlage> result = new ArrayList<>();

        try (Stream<String> lines = Files.lines(csvPath)) {
            lines
                    .skip(1) // skip header line
                    .forEach(line -> {
                        Windkraftanlage anlage = parseLineToAnlage(line);
                        if (anlage != null) {
                            result.add(anlage);
                        }
                    });
        }

        return result;
    }

    /**
     * Parses a single CSV row into a Windkraftanlage.

     * Precondition:
     *  - line is non-null and represents one row of the CSV file.
     * Postcondition:
     *  - returns a non-null Windkraftanlage if at least the first 12 columns exist;
     *    returns null if the row is too short or otherwise malformed.
     */
    private Windkraftanlage parseLineToAnlage(String line) {
        List<String> columns = splitCsvLine(line);

        // We need at least the first 12 columns; extra columns will be ignored.
        if (columns.size() < 12) {
            System.err.println("Skipping malformed line (expected at least 12 columns, found "
                    + columns.size() + "): " + line);
            return null;
        }

        // Use only the first 12 columns
        String col0 = columns.get(0);  // OBJECTID
        String col1 = columns.get(1);  // Name
        String col2 = columns.get(2);  // Baujahr
        String col3 = columns.get(3);  // Gesamtleistung (MW)
        String col4 = columns.get(4);  // Anzahl
        String col5 = columns.get(5);  // Typ (WKA)
        String col6 = columns.get(6);  // Ort
        String col7 = columns.get(7);  // Landkreis
        String col8 = columns.get(8);  // Breitengrad
        String col9 = columns.get(9);  // Längengrad
        String col10 = columns.get(10); // Betreiber
        String col11 = columns.get(11); // Bemerkung

        // Convert to domain values (empty -> null, invalid numbers -> null/default)
        int objektId = parseIntOrDefault(col0, -1);
        String name = emptyToNull(col1);
        Integer baujahr = parseIntegerNullable(col2);
        Double gesamtleistungMw = parseDoubleNullable(col3);
        Integer anzahlWindraeder = parseIntegerNullable(col4);
        String typ = emptyToNull(col5);
        String ort = emptyToNull(col6);
        String landkreis = emptyToNull(col7);
        Double breitengrad = parseDoubleNullable(col8);
        Double laengengrad = parseDoubleNullable(col9);
        String betreiber = emptyToNull(col10);
        String bemerkung = emptyToNull(col11);

        TechnischeDaten technischeDaten = new TechnischeDaten(
                baujahr,
                gesamtleistungMw,
                anzahlWindraeder,
                typ
        );

        Standort standort = new Standort(
                ort,
                landkreis,
                breitengrad,
                laengengrad
        );

        return new Windkraftanlage(
                objektId,
                name,
                technischeDaten,
                standort,
                betreiber,
                bemerkung
        );
    }

    /**
     * Splits a CSV line into columns.
     * - Commas inside double quotes are ignored as separators.
     * - Double quotes are removed.

     * Precondition: line is non-null.
     * Postcondition: returns a non-null List of column strings.
     */
    private List<String> splitCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // toggle quote state (do not include quotes in value)
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                // end of field
                result.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        // add last field
        result.add(currentField.toString());
        return result;
    }

    // === helper methods for parsing and null-handling ===

    /**
     * Returns Integer value or null if empty / invalid.
     */
    private Integer parseIntegerNullable(String value) {
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(trimmed);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Returns Double value or null if empty / invalid.
     * Also replaces comma with dot, in case the CSV uses comma as decimal separator.
     */
    private Double parseDoubleNullable(String value) {
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Double.valueOf(trimmed.replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Returns parsed int or defaultValue if parsing fails or value is empty.
     */
    private int parseIntOrDefault(String value, int defaultValue) {
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Returns null if value is empty/whitespace-only, otherwise trimmed value.
     */
    private String emptyToNull(String value) {
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}