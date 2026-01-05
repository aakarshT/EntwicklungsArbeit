package control;                // The class Aufgabe 1 is located in the package called control

import model.Standort;          // Imports standort from package model
import model.Windkraftanlage;   // Imports Windkraftanlage from package model
import resources.Konstanten;      // Imports Konstanten from package utility
import java.util.List;          // Imports Lists from Java Utility
import java.util.ListIterator;  // Imports List Iterator from Java Utility
import java.util.Objects;       // Imports Objects from Java Utility

public class Aufgabe2 {

    private final List<Windkraftanlage> anlagen;

    public Aufgabe2(List<Windkraftanlage> anlagen) {
        this.anlagen = anlagen;
    }
   /* Maximum and Minimum Latitude and Longitude of the German Borders have been set so that the values of laengengrad
       and breitengrad from the csv file can be corrected.
     */

    /**
     * @precondition: anlagen is a non-null List
     * @postcondition: Coordinates are corrected for decimal errors
     */
    public void run() {
        System.out.println(Konstanten.A2_HEADER);   // prints the Introduction line of the Aufgabe

        if (anlagen == null || anlagen.isEmpty()) {
            System.out.println(Konstanten.A1_ERR_NO_DATA);
            return;
        }

        long start = System.currentTimeMillis();  // saves the time at which the program started
        int correctedCount = 0;

        // Use ListIterator to replace objects from the original list
        ListIterator<Windkraftanlage> iterator = anlagen.listIterator();
        while (iterator.hasNext()) {
            Windkraftanlage current = iterator.next();
            Standort oldLoc = current.getStandort();

            // Fix missing decimal points if the value of Latitude and longitude is not less than 100
            double lat = scaleToRange(oldLoc.getBreitengrad(), Konstanten.GEO_LAT_MIN, Konstanten.GEO_LAT_MAX);
            double lon = scaleToRange(oldLoc.getLaengengrad(), Konstanten.GEO_LON_MIN, Konstanten.GEO_LON_MAX);

            //Fixes swapped coordinates (If Lat fits in Lon-range and vice versa)
            if (isSwapped(lat, lon)) {
                double temp = lat;
                lat = lon;
                lon = temp;
            }


            boolean latChanged = !Objects.equals(oldLoc.getBreitengrad(), lat);
            boolean lonChanged = !Objects.equals(oldLoc.getLaengengrad(), lon);

            // If any correction was made, create a new Standort and update the list
            if (latChanged || lonChanged) {
                Standort correctedLoc = new Standort(oldLoc.getOrt(), oldLoc.getLandkreis(), lat, lon);
                iterator.set(current.withCorrectedStandort(correctedLoc));
                correctedCount++;
            }
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println(Konstanten.A2_MSG_FINISHED);
        System.out.println(Konstanten.A2_MSG_CORRECTED + correctedCount);
        System.out.println(Konstanten.A2_MSG_DURATION + duration + Konstanten.UNIT_MS);
    }

    private double scaleToRange(Double value, double min, double max) {
        if (value == null) return 0.0; // Treat null as 0.0 temporarily
        double v = value;
        while (v > max) {
            v /= Konstanten.SCALE_FACTOR_10;
        }
        if (v >= min && v <= max) {
            return v;
        }
        // If we can't fix it, return the original value (or 0.0 if it was null)
        return (value != null) ? value : 0.0;
    }

    private boolean isSwapped(double lat, double lon) {
        boolean latIsActuallyLon = (lat >= Konstanten.GEO_LON_MIN && lat <= Konstanten.GEO_LON_MAX);
        boolean lonIsActuallyLat = (lon >= Konstanten.GEO_LAT_MIN && lon <= Konstanten.GEO_LAT_MAX);
        return latIsActuallyLon && lonIsActuallyLat;
    }
}