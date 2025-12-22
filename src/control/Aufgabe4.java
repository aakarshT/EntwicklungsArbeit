package control;

import model.Windkraftanlage;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Handles Teilaufgabe 4: Specific searches and summations with data cleaning.
 */
public class Aufgabe4 {

    // Heuristic: A very large wind park might be 500MW, but 1000+ is usually a decimal error in this dataset.
    private static final double MAX_REALISTIC_POWER = 1000.0;

    /**
     * Precondition: anlagen is a non-null List.
     * Postcondition: Calculates statistics after sanitizing power values.
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("\n=== Aufgabe 4: Search & Statistics (with Power Correction) ===");
        long startTime = System.nanoTime();

        // 1. Southernmost turbine [cite: 70]
        Optional<Windkraftanlage> southernmost = anlagen.stream()
                .filter(a -> a.getStandort().getBreitengrad() != null)
                .min(Comparator.comparing(a -> a.getStandort().getBreitengrad()));

        // 2. Highest Total Power (Sanitized) [cite: 71]
        Optional<Windkraftanlage> maxPower = anlagen.stream()
                .filter(a -> a.getTechnischeDaten().getGesamtleistungMw() != null)
                .max(Comparator.comparing(a -> sanitizePower(a.getTechnischeDaten().getGesamtleistungMw())));

        // 3. Most Wind Turbines [cite: 72]
        Optional<Windkraftanlage> mostTurbines = anlagen.stream()
                .filter(a -> a.getTechnischeDaten().getAnzahlWindraeder() != null)
                .max(Comparator.comparing(a -> a.getTechnischeDaten().getAnzahlWindraeder()));

        // 4. Sum of all power (Sanitized) [cite: 73]
        double totalPowerSum = anlagen.stream()
                .filter(a -> a.getTechnischeDaten().getGesamtleistungMw() != null)
                .mapToDouble(a -> sanitizePower(a.getTechnischeDaten().getGesamtleistungMw()))
                .sum();

        long durationMs = (System.nanoTime() - startTime) / 1_000_000;

        // Output results [cite: 74]
        System.out.println("Results:");
        southernmost.ifPresent(a -> System.out.println(" - Southernmost: " + a.getName() + " (Lat: " + a.getStandort().getBreitengrad() + ")"));

        maxPower.ifPresent(a -> {
            double power = sanitizePower(a.getTechnischeDaten().getGesamtleistungMw());
            System.out.println(" - Highest Power: " + a.getName() + " (" + power + " MW)");
        });

        mostTurbines.ifPresent(a -> System.out.println(" - Most Wind Wheels: " + a.getName() + " (" + a.getTechnischeDaten().getAnzahlWindraeder() + ")"));

        System.out.printf(" - Corrected Total Power Sum: %.2f MW%n", totalPowerSum);
        System.out.println("Processing Duration: " + durationMs + " ms. [cite: 74]");
    }

    /**
     * Heuristic to fix missing decimal points in power values.
     * If the value is unrealistically high, divide by 10 until it is plausible.
     */
    private double sanitizePower(Double power) {
        if (power == null) return 0.0;
        double temp = power;
        while (temp > MAX_REALISTIC_POWER) {
            temp /= 10.0;
        }
        return temp;
    }
}