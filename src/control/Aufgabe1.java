package control;

import model.Windkraftanlage;

import java.util.List;

/**
 * Handles the logic for Teilaufgabe 1:
 * - loading has already been done in Main
 * - here we just analyse and print basic information.
 */
public class Aufgabe1 {

    private final long ladeDauerMillis;

    /**
     * Precondition: ladeDauerMillis is the measured load duration in milliseconds.
     * Postcondition: controller instance is ready to run Aufgabe 1.
     */
    public Aufgabe1(long ladeDauerMillis) {
        this.ladeDauerMillis = ladeDauerMillis;
    }

    /**
     * Precondition: anlagen is non-null (may be empty).
     * Postcondition: Prints results for Aufgabe 1 to the console.
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("=== Aufgabe 1 ===");
        System.out.println("Number of wind turbines loaded: " + anlagen.size());
        System.out.println("Reading and parsing took: " + ladeDauerMillis + " ms.");

        long withKnownPower = anlagen.stream()
                .filter(a -> a.getTechnischeDaten().getGesamtleistungMw() != null)
                .count();
        System.out.println("Turbines with known total power: " + withKnownPower);
        System.out.println("Turbines with missing total power: " +
                (anlagen.size() - withKnownPower));

        long withValidCoordinates = anlagen.stream()
                .filter(a -> a.getStandort().getBreitengrad() != null
                        && a.getStandort().getLaengengrad() != null)
                .count();
        System.out.println("Turbines with valid coordinates: " + withValidCoordinates);
    }
}