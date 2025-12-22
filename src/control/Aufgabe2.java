package control;

import model.Windkraftanlage;
import model.Standort;
import java.util.List;
import java.util.ListIterator;

/**
 * Handles Teilaufgabe 2: Identification and correction of faulty coordinates[cite: 63, 64].
 */
public class Aufgabe2 {

    // Constants for German borders (Literals as constants as per KISS/Style rules) [cite: 38, 41]
    private static final double MIN_LAT = 47.0;
    private static final double MAX_LAT = 55.5;
    private static final double MIN_LON = 5.8;
    private static final double MAX_LON = 15.1;

    /**
     * Precondition: anlagen is a non-null List[cite: 35].
     * Postcondition: Coordinates are corrected for decimal errors and swaps; timing is printed[cite: 35, 65].
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("=== Aufgabe 2: Data Correction ===");
        long startTime = System.nanoTime();
        int correctedCount = 0;

        // Use ListIterator to replace objects in the original list [cite: 60]
        ListIterator<Windkraftanlage> iterator = anlagen.listIterator();

        while (iterator.hasNext()) {
            Windkraftanlage anlage = iterator.next();
            Standort s = anlage.getStandort();

            if (s.getBreitengrad() == null || s.getLaengengrad() == null) {
                continue;
            }

            double lat = s.getBreitengrad();
            double lon = s.getLaengengrad();
            boolean changed = false;

            // 1. Fix missing decimal points (Heuristic: scale down if value > 100)
            double fixedLat = scaleToRange(lat, MIN_LAT, MAX_LAT);
            double fixedLon = scaleToRange(lon, MIN_LON, MAX_LON);

            if (Double.compare(fixedLat, lat) != 0 || Double.compare(fixedLon, lon) != 0) {
                lat = fixedLat;
                lon = fixedLon;
                changed = true;
            }

            // 2. Fix swapped coordinates (If Lat fits in Lon-range and vice versa)
            if (isSwapped(lat, lon)) {
                double temp = lat;
                lat = lon;
                lon = temp;
                changed = true;
            }

            // If any correction was made, create a new Standort and update the list
            if (changed) {
                Standort correctedStandort = new Standort(s.getOrt(), s.getLandkreis(), lat, lon);
                iterator.set(anlage.withCorrectedStandort(correctedStandort));
                correctedCount++;
            }
        }

        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        System.out.println("Correction process finished.");
        System.out.println("Corrected (scaled or swapped) entries: " + correctedCount);
        System.out.println("Duration of correction: " + durationMs + " ms.");
    }

    /**
     * Heuristic to fix missing decimal points by dividing by 10 until it fits the range.
     */
    private double scaleToRange(double value, double min, double max) {
        double temp = Math.abs(value);
        if (temp == 0) return value;

        // If it's already in range, don't touch it
        if (temp >= min && temp <= max) return value;

        // Scale down if it looks like a missing decimal (e.g., 53123 instead of 53.123)
        while (temp > max) {
            temp /= 10.0;
        }

        return (temp >= min && temp <= max) ? temp : value;
    }

    private boolean isSwapped(double lat, double lon) {
        return (lat >= MIN_LON && lat <= MAX_LON) && (lon >= MIN_LAT && lon <= MAX_LAT);
    }
}