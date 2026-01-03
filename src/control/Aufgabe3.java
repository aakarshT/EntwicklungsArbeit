package control;

import model.Windkraftanlage;
import utility.Konstanten; // Import constants
import java.util.IntSummaryStatistics;
import java.util.List;

public class Aufgabe3 {

    public void run(List<Windkraftanlage> anlagen) {
        System.out.println(Konstanten.A3_HEADER);

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println(Konstanten.A3_ERR_NO_DATA);
            return;
        }

        // 1. Collect statistics (Min, Max, Average)
        // We filter out '0' because getSanitizedBaujahr() returns 0 for missing data.
        IntSummaryStatistics stats = anlagen.stream()
                .mapToInt(w -> w.getTechnischeDaten().getSanitizedBaujahr())
                .filter(year -> year > Konstanten.YEAR_MIN_VALID && year <= Konstanten.YEAR_MAX_VALID) // Filter valid years only
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