package control;

import model.Windkraftanlage;
import resources.Konstanten;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;

public class Aufgabe4 {

    private final List<Windkraftanlage> anlagen;

    // --- Constructor ---
    public Aufgabe4(List<Windkraftanlage> anlagen) {
        this.anlagen = anlagen;
    }

    public void run() {
        System.out.println(Konstanten.A4_HEADER);

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println(Konstanten.A4_ERR_NO_DATA);
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

        System.out.printf(Konstanten.A4_MSG_TOTAL_POWER, totalPower);
        System.out.printf(Konstanten.A4_MSG_TOTAL_COUNT, totalTurbines);

        // 3. Find the Strongest Single Park (Max Power)
        Windkraftanlage strongest = anlagen.stream()
                .filter(w -> w.getTechnischeDaten().getGesamtleistung() != null)
                .max(Comparator.comparingDouble(w -> w.getTechnischeDaten().getGesamtleistung()))
                .orElse(null);

        if (strongest != null) {
            System.out.println(Konstanten.SEPARATOR);
            System.out.println(Konstanten.A4_MSG_STRONGEST_HEADER);
            System.out.println(Konstanten.A4_LBL_NAME + strongest.getName());
            System.out.println(Konstanten.A4_LBL_POWER + strongest.getTechnischeDaten().getGesamtleistung() + Konstanten.UNIT_MW);
            System.out.println(Konstanten.A4_LBL_TYPE + strongest.getTechnischeDaten().getTyp());
        }

        System.out.println(Konstanten.SEPARATOR);
    }
}