package control;

import model.TechnischeDaten;
import model.Windkraftanlage;
import resources.Konstanten;
import java.util.IntSummaryStatistics;
import java.util.List;

public class Aufgabe3 {

    private final List<Windkraftanlage> anlagen;

    // --- Constructor ---
    public Aufgabe3(List<Windkraftanlage> anlagen) {
        this.anlagen = anlagen;
    }

    public void run() {
        System.out.println(Konstanten.A3_HEADER);

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println(Konstanten.A3_ERR_NO_DATA);
            return;
        }

        // 1. Collect statistics (Min, Max, Average)
        // We now use the 'isPlausibleYear()' method which uses the Java Date API
        IntSummaryStatistics stats = anlagen.stream()
                .map(Windkraftanlage::getTechnischeDaten)   // Get the TechData object first
                .filter(TechnischeDaten::isPlausibleYear)   // Filter using our new Java API logic
                .mapToInt(TechnischeDaten::getBaujahr)      // Convert to int
                .summaryStatistics();

        if (stats.getCount() == 0) {
            System.out.println(Konstanten.A3_MSG_NO_VALID_YEAR);
        } else {
            System.out.println(Konstanten.A3_MSG_ANALYSIS_HEADER);
            System.out.println(Konstanten.A3_SEPARATOR_SHORT);
            System.out.println(Konstanten.A3_MSG_TOTAL + stats.getCount());
            System.out.println(Konstanten.A3_MSG_OLDEST + stats.getMin());
            System.out.println(Konstanten.A3_MSG_NEWEST + stats.getMax());
            System.out.printf(Konstanten.A3_FORMAT_AVG, stats.getAverage());
        }

        System.out.println(Konstanten.SEPARATOR);
    }
}