package model;

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

    // --- Standard Getters ---
    public Integer getBaujahr() { return baujahr; }
    public Double getGesamtleistung() { return gesamtleistung; }
    public Integer getAnzahl() { return anzahl; }
    public String getTyp() { return typ; }

    // --- Setter (Needed for Aufgabe 5) ---
    public void setGesamtleistung(Double gesamtleistung) {
        this.gesamtleistung = gesamtleistung;
    }

    // --- Helper Methods (Needed for Aufgabe 3 & 4) ---

    public Double getSanitizedGesamtleistung() {
        return gesamtleistung != null ? gesamtleistung : 0.0;
    }

    // This is the method that caused your error!
    public int getSanitizedBaujahr() {
        return baujahr != null ? baujahr : 0;
    }
}