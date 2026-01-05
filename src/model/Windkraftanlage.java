package model;         // The class Windkraftanlage is inside the package called model

import utility.Konstanten; // Import Constants
import java.util.Objects; // Importing Objects from Java utilities

/*
  Represents a wind turbine installation.
  This class is immutable.
 */
public class Windkraftanlage {

    private final int objektId;
    private final String name;
    private final TechnischeDaten technischeDaten;
    private final Standort standort;
    private final String betreiber;
    private final String bemerkung;

    //technischeDaten and standort are non-null.


    public Windkraftanlage(int objektId, String name, TechnischeDaten technischeDaten,
                           Standort standort, String betreiber, String bemerkung) {
        this.objektId = objektId;
        this.name = name;

        // Use Constants for the error messages inside requireNonNull
        this.technischeDaten = Objects.requireNonNull(technischeDaten, Konstanten.ERR_NULL_TECH_DATA);
        this.standort = Objects.requireNonNull(standort, Konstanten.ERR_NULL_STANDORT);

        this.betreiber = betreiber;
        this.bemerkung = bemerkung;
    }

    /**
     * neu is non-null.
     * Returns a new Windkraftanlage instance with the updated Standort.
     */
    public Windkraftanlage withCorrectedStandort(Standort neu) {
        return new Windkraftanlage(this.objektId, this.name, this.technischeDaten,
                neu, this.betreiber, this.bemerkung);
    }

    public int getObjektId() { return objektId; }
    public String getName() { return name; }
    public TechnischeDaten getTechnischeDaten() { return technischeDaten; }
    public Standort getStandort() { return standort; }
    public String getBetreiber() { return betreiber; }
    public String getBemerkung() { return bemerkung; }
}