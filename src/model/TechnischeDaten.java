package model;                                  // The class is located inside the package called model

import resources.Konstanten;                    // Import Constants
import java.time.Year;                          // Import Java Date API for validation

public class TechnischeDaten {
    // Changed Integer to int (primitive) as requested
    private int baujahr;
    private Double gesamtleistung;
    private Integer anzahl;
    private String typ;

    // Constructor now accepts int for baujahr
    public TechnischeDaten(int baujahr, Double gesamtleistung, Integer anzahl, String typ) {
        this.baujahr = baujahr;
        this.gesamtleistung = gesamtleistung;
        this.anzahl = anzahl;
        this.typ = typ;
    }

    //Standard Getters
    public int getBaujahr() { return baujahr; }
    public Double getGesamtleistung() { return gesamtleistung; }
    public Integer getAnzahl() { return anzahl; }
    public String getTyp() { return typ; }

    //Setter
    public void setGesamtleistung(Double gesamtleistung) {
        this.gesamtleistung = gesamtleistung;
    }

    // Helper Methods

    public Double getSanitizedGesamtleistung() {
        return gesamtleistung != null ? gesamtleistung : Konstanten.TECH_DEFAULT_POWER;
    }

    public int getSanitizedBaujahr() {
        // Since baujahr is int, we check if it is > 0 instead of null
        return (baujahr > 0) ? baujahr : Konstanten.TECH_DEFAULT_YEAR;
    }

    /**
     * Checks if the year is valid using Java Date API (java.time.Year).
     * @return true if year is plausible (valid and not in the future)
     */
    public boolean isPlausibleYear() {
        if (baujahr == 0) return false;

        // Use Java API to get current year dynamically
        int currentYear = Year.now().getValue();

        // Logic: Year must be > 1980 and <= Current Year
        return baujahr >= Konstanten.YEAR_MIN_VALID && baujahr <= currentYear;
    }
}