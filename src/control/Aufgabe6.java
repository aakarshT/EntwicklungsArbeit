package control;

import model.Windkraftanlage;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles Teilaufgabe 6: Manufacturer-specific analysis and maintenance planning.
 */
public class Aufgabe6 {

    /**
     * Precondition: anlagen is non-null.
     * Postcondition: Groups turbines by manufacturer and prints maintenance schedules.
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("\n=== Aufgabe 6: Maintenance Planning per Manufacturer ===");

        // 1. Group turbines by Manufacturer (extracted from "Typ")
        Map<String, List<Windkraftanlage>> perManufacturer = groupByManufacturer(anlagen);

        // 2. Sort manufacturers by number of turbines (Descending) to show the biggest ones first
        System.out.printf("%-20s | %-10s | %-15s | %-15s%n", "Manufacturer", "Count", "Days Needed", "Weeks (approx)");
        System.out.println("--------------------------------------------------------------------------");

        perManufacturer.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // Sort descending
                .limit(10) // Show top 10
                .forEach(entry -> calculateAndPrintSchedule(entry.getKey(), entry.getValue()));
    }

    /**
     * Extracts the manufacturer name from the "Typ" field.
     * Logic: "Enercon E-82" -> "Enercon"
     */
    private Map<String, List<Windkraftanlage>> groupByManufacturer(List<Windkraftanlage> anlagen) {
        Map<String, List<Windkraftanlage>> map = new HashMap<>();

        for (Windkraftanlage wka : anlagen) {
            String typ = wka.getTechnischeDaten().getTyp();

            // Skip if type is missing
            if (typ != null && !typ.trim().isEmpty()) {
                // Heuristic: The first word is usually the manufacturer (e.g., "Vestas", "Enercon")
                String[] parts = typ.trim().split("\\s+");
                if (parts.length > 0) {
                    String hersteller = parts[0].replaceAll("[^a-zA-Z0-9-]", ""); // Clean up chars

                    if (hersteller.length() > 2) { // Ignore short abbreviations like "E" or "V"
                        map.computeIfAbsent(hersteller, k -> new ArrayList<>()).add(wka);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Calculates the schedule based on:
     * - 2 hours per turbine
     * - 8 hours work day (08:00 - 16:00) => 4 turbines/day
     */
    private void calculateAndPrintSchedule(String manufacturer, List<Windkraftanlage> turbines) {
        int totalTurbines = turbines.size();
        int turbinesPerDay = 4;

        // Calculation: Ceiling division to account for partial days
        // Formula: (numerator + denominator - 1) / denominator
        int daysNeeded = (totalTurbines + turbinesPerDay - 1) / turbinesPerDay;

        // Weeks assumption: 5 work days per week
        double weeks = daysNeeded / 5.0;

        System.out.printf("%-20s | %-10d | %-15d | %-15.1f%n",
                truncate(manufacturer, 20),
                totalTurbines,
                daysNeeded,
                weeks);
    }

    private String truncate(String s, int len) {
        if (s.length() > len) return s.substring(0, len - 3) + "...";
        return s;
    }
}