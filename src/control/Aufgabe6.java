package control;

import model.Windkraftanlage;
import utility.Konstanten; // Import constants
import java.util.*;

/**
 * Handles Teilaufgabe 6: Manufacturer-specific analysis and maintenance planning.
 */
public class Aufgabe6 {

    /**
     * Precondition: anlagen is non-null.
     * Postcondition: Groups turbines by manufacturer and prints maintenance schedules.
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println(Konstanten.A6_HEADER);

        // 1. Group turbines by Manufacturer (extracted from "Typ")
        Map<String, List<Windkraftanlage>> perManufacturer = groupByManufacturer(anlagen);

        // 2. Sort manufacturers by number of turbines (Descending)
        System.out.printf(Konstanten.A6_TABLE_FORMAT,
                Konstanten.A6_COL_MANUFACTURER,
                Konstanten.A6_COL_COUNT,
                Konstanten.A6_COL_DAYS,
                Konstanten.A6_COL_WEEKS);
        System.out.println(Konstanten.SEPARATOR_LONG);

        perManufacturer.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // Sort descending
                .limit(Konstanten.A6_TOP_LIMIT) // Show top 10
                .forEach(entry -> calculateAndPrintSchedule(entry.getKey(), entry.getValue()));
    }

    /**
     * Extracts the manufacturer name from the "Typ" field.
     */
    private Map<String, List<Windkraftanlage>> groupByManufacturer(List<Windkraftanlage> anlagen) {
        Map<String, List<Windkraftanlage>> map = new HashMap<>();

        for (Windkraftanlage wka : anlagen) {
            String typ = wka.getTechnischeDaten().getTyp();

            // Skip if type is missing
            if (typ != null && !typ.trim().isEmpty()) {
                // Heuristic: The first word is usually the manufacturer
                String[] parts = typ.trim().split("\\s+");
                if (parts.length > 0) {
                    String hersteller = parts[0].replaceAll(Konstanten.A6_REGEX_CLEAN, ""); // Clean up chars

                    if (hersteller.length() > Konstanten.A6_MIN_NAME_LEN) {
                        map.computeIfAbsent(hersteller, k -> new ArrayList<>()).add(wka);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Calculates the schedule based on work hours and capacity.
     */
    private void calculateAndPrintSchedule(String manufacturer, List<Windkraftanlage> turbines) {
        int totalTurbines = turbines.size();

        // Calculation: Ceiling division to account for partial days
        int daysNeeded = (totalTurbines + Konstanten.A6_TURBINES_PER_DAY - 1) / Konstanten.A6_TURBINES_PER_DAY;

        // Weeks assumption: 5 work days per week
        double weeks = daysNeeded / Konstanten.A6_DAYS_PER_WEEK;

        System.out.printf(Konstanten.A6_ROW_FORMAT,
                truncate(manufacturer, Konstanten.A6_TRUNCATE_LEN),
                totalTurbines,
                daysNeeded,
                weeks);
    }

    private String truncate(String s, int len) {
        if (s.length() > len) return s.substring(0, len - 3) + Konstanten.A6_ELLIPSIS;
        return s;
    }
}