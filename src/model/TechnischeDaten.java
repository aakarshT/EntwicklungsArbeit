package model;                                  // The class is located inside the package called model

import utility.Konstanten;                      // Import Constants

public class TechnischeDaten {
    private Integer baujahr;
    private Double gesamtleistung;
    private Integer anzahl;
    private String typ;

    public TechnischeDaten(Integer baujahr, Double gesamtleistung, Integer anzahl, String typ) {
        this.baujahr = baujahr;
        this.gesamtleistung = gesamtleistung;
        this.anzahl = anzahl;
        this.typ = typ;
    }

    //Standard Getters
    public Integer getBaujahr() { return baujahr; }
    public Double getGesamtleistung() { return gesamtleistung; }
    public Integer getAnzahl() { return anzahl; }
    public String getTyp() { return typ; }

    //Setter
    public void setGesamtleistung(Double gesamtleistung) {
        this.gesamtleistung = gesamtleistung;
    }

    // Helper Methods

    public Double getSanitizedGesamtleistung() {
        // Replaced 0.0 with Constant
        return gesamtleistung != null ? gesamtleistung : Konstanten.TECH_DEFAULT_POWER;
    }

    public int getSanitizedBaujahr() {
        // Replaced 0 with Constant
        return baujahr != null ? baujahr : Konstanten.TECH_DEFAULT_YEAR;
    }
}