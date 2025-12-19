package model;

/**
 * Represents a wind turbine installation, composed of technical data and location.
 */
public class Windkraftanlage {

    private final int objektId;
    private final String name;
    private final TechnischeDaten technischeDaten;
    private final Standort standort;
    private final String betreiber;
    private final String bemerkung;

    /**
     * Precondition:
     *  - technischeDaten and standort are non-null.
     * Postcondition:
     *  - creates an immutable Windkraftanlage instance.
     */
    public Windkraftanlage(int objektId,
                           String name,
                           TechnischeDaten technischeDaten,
                           Standort standort,
                           String betreiber,
                           String bemerkung) {
        this.objektId = objektId;
        this.name = name;
        this.technischeDaten = technischeDaten;
        this.standort = standort;
        this.betreiber = betreiber;
        this.bemerkung = bemerkung;
    }

    public int getObjektId() {
        return objektId;
    }

    public String getName() {
        return name;
    }

    public TechnischeDaten getTechnischeDaten() {
        return technischeDaten;
    }

    public Standort getStandort() {
        return standort;
    }

    public String getBetreiber() {
        return betreiber;
    }

    public String getBemerkung() {
        return bemerkung;
    }
}