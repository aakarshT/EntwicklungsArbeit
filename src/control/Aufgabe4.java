package control;

import model.Windkraftanlage;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;

public class Aufgabe4 {

    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("\n=== Aufgabe 4: Statistics & Analysis ===");

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println("Error: No data available for analysis.");
            return;
        }

        // 1. Calculate Total Power (Sum of 'Gesamtleistung')
        double totalPower = anlagen.stream()
                .map(wka -> wka.getTechnischeDaten().getGesamtleistung())
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        // 2. Calculate Total Turbine Count (Sum of 'Anzahl')
        int totalTurbines = anlagen.stream()
                .map(wka -> wka.getTechnischeDaten().getAnzahl())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.printf("Total Installed Power:   %.2f MW%n", totalPower);
        System.out.printf("Total Number of Turbines: %d%n", totalTurbines);

        // 3. Find the Strongest Single Park (Max Power)
        Windkraftanlage strongest = anlagen.stream()
                .filter(w -> w.getTechnischeDaten().getGesamtleistung() != null)
                .max(Comparator.comparingDouble(w -> w.getTechnischeDaten().getGesamtleistung()))
                .orElse(null);

        if (strongest != null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Strongest Windpark:");
            System.out.println(" Name:  " + strongest.getName());
            System.out.println(" Power: " + strongest.getTechnischeDaten().getGesamtleistung() + " MW");
            System.out.println(" Type:  " + strongest.getTechnischeDaten().getTyp());
        }

        System.out.println("--------------------------------------------------");
    }
}