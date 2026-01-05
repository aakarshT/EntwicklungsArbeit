package model;  // The class Standort is stored inside the package called model

/**
 * Represents the standort of a wind turbine.
 * This class is immutable.
 */
public class Standort {

    private final String ort;
    private final String landkreis;
    private final Double breitengrad;
    private final Double laengengrad;

    /**
      none; values may be null if missing in the CSV.
      creates an immutable Standort instance.
     */
    public Standort(String ort, String landkreis, Double breitengrad, Double laengengrad) {
        this.ort = ort;
        this.landkreis = landkreis;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
    }

    //Returns a new Standort instance with swapped Breitengrad and Längengrad.

    public Standort createSwappedVersion() {
        return new Standort(this.ort, this.landkreis, this.laengengrad, this.breitengrad);
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