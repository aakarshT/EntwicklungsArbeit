package model;

/**
 * Represents the geographical location (standort) of a wind turbine.
 */
public class Standort {

    private final String ort;
    private final String landkreis;
    private final Double breitengrad;
    private final Double laengengrad;

    /**
     * Precondition: none; values may be null if missing in the CSV.
     * Postcondition: creates an immutable Standort instance.
     */
    public Standort(String ort,
                    String landkreis,
                    Double breitengrad,
                    Double laengengrad) {
        this.ort = ort;
        this.landkreis = landkreis;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
    }

    public String getOrt() {
        return ort;
    }

    public String getLandkreis() {
        return landkreis;
    }

    public Double getBreitengrad() {
        return breitengrad;
    }

    public Double getLaengengrad() {
        return laengengrad;
    }
}