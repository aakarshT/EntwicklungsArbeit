package control;

import model.Windkraftanlage;
import java.util.Comparator;
import java.util.List;

/**
 * Handles Teilaufgabe 3: Sorting based on various criteria.
 * Uses sanitized data to handle outliers and missing districts.
 */
public class Aufgabe3 {

    /**
     * Precondition: anlagen is a non-null List.
     * Postcondition: Displays sorted results and durations for multiple criteria.
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("\n=== Aufgabe 3: Sorting (Sanitized) ===");

        // 1. Sort by Sanitized Construction Year (Oldest to Newest)
        measureAndSort(anlagen, Comparator.comparing(
                        a -> a.getTechnischeDaten().getSanitizedBaujahr(),
                        Comparator.nullsLast(Comparator.naturalOrder())),
                "Baujahr (Construction Year)");

        // 2. Sort by Sanitized Power (Highest first)
        measureAndSort(anlagen, Comparator.comparing(
                        a -> a.getTechnischeDaten().getSanitizedGesamtleistung(),
                        Comparator.nullsLast(Comparator.reverseOrder())),
                "Sanitized Gesamtleistung (MW)");

        // 3. Combined Sort: District then Name
        // nullsLast now works properly because the Loader converts empty strings to null
        measureAndSort(anlagen, Comparator.comparing(
                                (Windkraftanlage a) -> a.getStandort().getLandkreis(),
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Windkraftanlage::getName),
                "Landkreis then Name");
    }

    /**
     * Helper to measure time and print results.
     */
    private void measureAndSort(List<Windkraftanlage> list, Comparator<Windkraftanlage> comp, String description) {
        long start = System.nanoTime();
        list.sort(comp);
        long end = System.nanoTime();

        long durationMs = (end - start) / 1_000_000;

        System.out.println("\n--- Results for: " + description + " (Duration: " + durationMs + " ms) ---");

        // Formatted table output for clarity
        System.out.printf("%-35s | %-6s | %-12s | %-10s%n", "Name", "Year", "Power (MW)", "District");
        System.out.println("-----------------------------------------------------------------------------");

        list.stream().limit(5).forEach(a -> {
            String yearStr = a.getTechnischeDaten().getSanitizedBaujahr() == null ? "N/A" : a.getTechnischeDaten().getSanitizedBaujahr().toString();
            Double pwr = a.getTechnischeDaten().getSanitizedGesamtleistung();
            String pwrStr = pwr == null ? "N/A" : String.format("%.2f", pwr);
            String dist = a.getStandort().getLandkreis() == null ? "N/A" : a.getStandort().getLandkreis();

            System.out.printf("%-35s | %-6s | %-12s | %-10s%n",
                    truncate(a.getName(), 35),
                    yearStr,
                    pwrStr,
                    dist);
        });
    }

    private String truncate(String text, int length) {
        if (text == null) return "N/A";
        return text.length() > length ? text.substring(0, length - 3) + "..." : text;
    }
}