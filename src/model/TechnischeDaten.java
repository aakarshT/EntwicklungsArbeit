package model;

/**
 * Represents technical data of a wind turbine.
 */
public class TechnischeDaten {

    private final Integer baujahr;
    private final Double gesamtleistungMw;
    private final Integer anzahlWindraeder;
    private final String typ;

    /**
     * Precondition: none; values may be null if missing in the CSV.
     * Postcondition: creates an immutable TechnischeDaten instance.
     */
    public TechnischeDaten(Integer baujahr,
                           Double gesamtleistungMw,
                           Integer anzahlWindraeder,
                           String typ) {
        this.baujahr = baujahr;
        this.gesamtleistungMw = gesamtleistungMw;
        this.anzahlWindraeder = anzahlWindraeder;
        this.typ = typ;
    }

    public Integer getBaujahr() {
        return baujahr;
    }

    public Double getGesamtleistungMw() {
        return gesamtleistungMw;
    }

    public Integer getAnzahlWindraeder() {
        return anzahlWindraeder;
    }

    public String getTyp() {
        return typ;
    }
}