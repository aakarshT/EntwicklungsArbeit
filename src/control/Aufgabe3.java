package control;

import model.Windkraftanlage;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;

public class Aufgabe3 {

    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("\n=== Aufgabe 3: Age Analysis (Baujahr) ===");

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println("Error: No data available.");
            return;
        }

        // 1. Collect statistics (Min, Max, Average)
        // We filter out '0' because getSanitizedBaujahr() returns 0 for missing data.
        IntSummaryStatistics stats = anlagen.stream()
                .mapToInt(w -> w.getTechnischeDaten().getSanitizedBaujahr())
                .filter(year -> year > 1900 && year <= 2025) // Filter valid years only
                .summaryStatistics();

        if (stats.getCount() == 0) {
            System.out.println("No valid year data found.");
        } else {
            System.out.println("Data Analysis of Construction Years:");
            System.out.println("------------------------------------");
            System.out.println(" Total Turbines with Data: " + stats.getCount());
            System.out.println(" Oldest Turbine Built:     " + stats.getMin());
            System.out.println(" Newest Turbine Built:     " + stats.getMax());
            System.out.printf(" Average Construction Year: %.2f%n", stats.getAverage());
        }

        System.out.println("--------------------------------------------------");
    }
}