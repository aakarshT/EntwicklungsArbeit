package utility;

import model.Standort;
import utility.Konstanten; // Import Constants

/**
 * Utility class for geographical distance calculations.
 * Implements the Haversine formula.
 */
public class DistanceCalculator {

    /**
     * Calculates the distance between two locations in km.
     * Precondition: s1 and s2 are non-null with valid coordinates.
     */
    public static double calculateDistance(Standort s1, Standort s2) {
        if (s1.getBreitengrad() == null || s1.getLaengengrad() == null ||
                s2.getBreitengrad() == null || s2.getLaengengrad() == null) {
            return Double.MAX_VALUE;
        }

        double lat1 = Math.toRadians(s1.getBreitengrad());
        double lon1 = Math.toRadians(s1.getLaengengrad());
        double lat2 = Math.toRadians(s2.getBreitengrad());
        double lon2 = Math.toRadians(s2.getLaengengrad());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Use Constant for Earth Radius
        return Konstanten.GEO_EARTH_RADIUS_KM * c;
    }
}