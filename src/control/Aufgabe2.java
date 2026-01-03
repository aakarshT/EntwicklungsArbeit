package control;                    // The class is stored inside the package called control

import model.Windkraftanlage;       // Imports class Windkraftanlage from package called model
import model.Standort;              // Imports class Standort from package called model
import java.util.List;              // Imports List from java utilities
import java.util.ListIterator;      // Imports List Iterator from java utilities to make changes in the list

public class Aufgabe2 {

    /* Maximum and Minimum Latitude and Longitude of the German Borders have been set so that the values of laengengrad
       and breitengrad from the csv file can be corrected.
     */
    private static final double MIN_LAT = 47.0;
    private static final double MAX_LAT = 55.5;
    private static final double MIN_LON = 5.8;
    private static final double MAX_LON = 15.1;

    /**
     * Precondition: anlagen is a non-null List
     * Postcondition: Coordinates are corrected for decimal errors
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("=== Aufgabe 2: Data Correction ===");   // prints the Introduction line of the Aufgabe
        long startTime = System.nanoTime();                         // saves the time at which the program started
        int correctedCount = 0;

        // Use ListIterator to replace objects from the original list
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

            // Fix missing decimal points if the value of Latitude and longitude is not less than 100
            double fixedLat = scaleToRange(lat, MIN_LAT, MAX_LAT);
            double fixedLon = scaleToRange(lon, MIN_LON, MAX_LON);

            if (Double.compare(fixedLat, lat) != 0 || Double.compare(fixedLon, lon) != 0) {
                lat = fixedLat;
                lon = fixedLon;
                changed = true;
            }

            //Fixes swapped coordinates (If Lat fits in Lon-range and vice versa)
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

        long endTime = System.nanoTime();   // saves the time at which the program ended
        long durationMs = (endTime - startTime) / 1_000_000;  // calculates the total time required to run the program then converts it in seconds.

        System.out.println("Correction process finished.");
        System.out.println("Corrected (scaled or swapped) entries: " + correctedCount);
        System.out.println("Duration of correction: " + durationMs + " ms.");
    }


     // Divide the value with 10 till the point it is in the correct range because many value have no decimal point

    private double scaleToRange(double value, double min, double max) {
        double temp = Math.abs(value);
        if (temp == 0) return value;

        // If it's already in range then the value remains the same
        if (temp >= min && temp <= max) return value;

        // Scale down if it looks like a missing decimal
        while (temp > max) {
            temp /= 10.0;
        }

        return (temp >= min && temp <= max) ? temp : value;
    }

    private boolean isSwapped(double lat, double lon) {
        return (lat >= MIN_LON && lat <= MAX_LON) && (lon >= MIN_LAT && lon <= MAX_LAT);
    }
}