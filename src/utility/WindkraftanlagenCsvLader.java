package utility;

import model.Standort;
import model.TechnischeDaten;
import model.Windkraftanlage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Objects.requireNonNull(csvPath, "CSV Path must not be null");

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
        String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if (columns.length < 12) {
            return null;
        }

        try {
            // Use nullIfEmpty to ensure missing text fields are truly null
            int objektId = Integer.parseInt(columns[0].trim());
            String name = nullIfEmpty(columns[1]);

            // Technische Daten
            Integer baujahr = parseIntegerNullable(columns[2]);
            Double gesamtleistung = parseDoubleNullable(columns[3]);
            Integer anzahl = parseIntegerNullable(columns[4]);
            String typ = nullIfEmpty(columns[5]);

            // Standort
            String ort = nullIfEmpty(columns[6]);
            String landkreis = nullIfEmpty(columns[7]);
            Double breitengrad = parseDoubleNullable(columns[8]);
            Double laengengrad = parseDoubleNullable(columns[9]);

            String betreiber = nullIfEmpty(columns[10]);
            String bemerkung = nullIfEmpty(columns[11]);

            TechnischeDaten tech = new TechnischeDaten(baujahr, gesamtleistung, anzahl, typ);
            Standort locus = new Standort(ort, landkreis, breitengrad, laengengrad);

            return new Windkraftanlage(objektId, name, tech, locus, betreiber, bemerkung);
        } catch (Exception e) {
            return null;
        }
    }

    private String nullIfEmpty(String s) {
        if (s == null) return null;
        String trimmed = s.replace("\"", "").trim();
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
            return Double.valueOf(value.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}