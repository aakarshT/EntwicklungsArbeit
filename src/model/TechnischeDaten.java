package model;

/**
 * Represents technical data of a wind turbine.
 * Includes heuristics to handle data outliers in construction years and power values.
 */
public class TechnischeDaten {

    private final Integer baujahr;
    private final Double gesamtleistungMw;
    private final Integer anzahlWindraeder;
    private final String typ;

    // Constants for realistic thresholds (Literals as constants rule)
    private static final double MAX_REALISTIC_POWER = 1000.0;
    private static final int EARLIEST_REALISTIC_YEAR = 1900;
    private static final int SPECIFIC_OUTLIER_YEAR = 1003;
    private static final int CORRECTED_OUTLIER_YEAR = 2003;

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

    /**
     * Heuristic for construction year.
     * Corrects the '1003' typo to '2003' and filters out impossible years.
     */
    public Integer getSanitizedBaujahr() {
        if (baujahr == null) return null;
        if (baujahr == SPECIFIC_OUTLIER_YEAR) return CORRECTED_OUTLIER_YEAR;
        if (baujahr < EARLIEST_REALISTIC_YEAR) return null;
        return baujahr;
    }

    /**
     * Heuristic to fix missing decimal points in power values.
     * Returns the power scaled down if it exceeds 1000 MW.
     */
    public Double getSanitizedGesamtleistung() {
        if (gesamtleistungMw == null) return null;
        double temp = gesamtleistungMw;
        while (temp > MAX_REALISTIC_POWER) {
            temp /= 10.0;
        }
        return temp;
    }

    public Integer getBaujahr() { return baujahr; }
    public Double getGesamtleistungMw() { return gesamtleistungMw; }
    public Integer getAnzahlWindraeder() { return anzahlWindraeder; }
    public String getTyp() { return typ; }
}