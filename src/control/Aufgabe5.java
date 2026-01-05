package control;

import model.Graph;
import model.Windkraftanlage;
import utility.DistanceCalculator;
import resources.Konstanten;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Handles Aufgabe 5: Advanced Data Repair.
 */
public class Aufgabe5 {

    private final List<Windkraftanlage> anlagen;

    // Regex to find numbers in brackets, e.g., "(3 WKA)" or "(2x)"
    private static final Pattern BRACKET_COUNT_PATTERN = Pattern.compile(Konstanten.REGEX_BRACKET_COUNT);

    // --- Constructor ---
    public Aufgabe5(List<Windkraftanlage> anlagen) {
        this.anlagen = anlagen;
    }

    public void run() {
        System.out.println(Konstanten.A5_HEADER);
        long start = System.currentTimeMillis();

        // --- PHASE 1: Cluster Repair (Grouping by Name) ---
        System.out.println(Konstanten.A5_MSG_PHASE1);
        int repairedByCluster = runClusterRepair(anlagen);

        // --- PHASE 2: Graph Repair (Geographic Neighbors) ---
        System.out.println(Konstanten.A5_MSG_PHASE2);
        int repairedByGraph = runGraphRepair(anlagen);

        long duration = System.currentTimeMillis() - start;

        // --- SUMMARY ---
        System.out.println(Konstanten.SEPARATOR);
        System.out.println(Konstanten.A5_MSG_SUMMARY);
        System.out.println(Konstanten.A5_LBL_FIX_CLUSTER + repairedByCluster);
        System.out.println(Konstanten.A5_LBL_FIX_GRAPH + repairedByGraph);
        System.out.println(Konstanten.A5_LBL_TIME + duration + Konstanten.UNIT_MS);
        System.out.println(Konstanten.SEPARATOR);
    }

    // ================= PHASE 1: CLUSTER LOGIC =================

    private int runClusterRepair(List<Windkraftanlage> anlagen) {
        // 1. Group all turbines by Name
        Map<String, List<Windkraftanlage>> groups = anlagen.stream()
                .collect(Collectors.groupingBy(Windkraftanlage::getName));

        int fixCount = 0;

        // 2. Process each group
        for (List<Windkraftanlage> group : groups.values()) {
            if (group.isEmpty()) continue;

            // Try to find a "Master" row with valid Power AND a Count (e.g. "(3x)")
            Windkraftanlage master = findMasterInGroup(group);

            if (master != null) {
                // Calculate Unit Power (Total / Count)
                int count = extractCount(master.getTechnischeDaten().getTyp());
                double totalPower = master.getTechnischeDaten().getSanitizedGesamtleistung();

                // Avoid dividing by zero
                if (count > 0 && totalPower > 0) {
                    double unitPower = totalPower / count;

                    // Apply this calculated power to ALL members of the group
                    for (Windkraftanlage wka : group) {
                        Double currentP = wka.getTechnischeDaten().getSanitizedGesamtleistung();

                        // We update if it's 0 OR if it's the Master (to correct the aggregated value)
                        if (currentP == null || currentP == 0.0 || wka == master) {
                            wka.getTechnischeDaten().setGesamtleistung(unitPower);
                            fixCount++;
                        }
                    }
                }
            }
        }
        return fixCount;
    }

    private Windkraftanlage findMasterInGroup(List<Windkraftanlage> group) {
        for (Windkraftanlage wka : group) {
            Double p = wka.getTechnischeDaten().getSanitizedGesamtleistung();
            String typ = wka.getTechnischeDaten().getTyp();

            // A Master must have Power > 0 and a "(Nx)" bracket in the type
            if (p != null && p > 0 && typ != null && typ.contains(Konstanten.BRACKET_OPEN)) {
                return wka;
            }
        }
        return null;
    }

    private int extractCount(String typ) {
        if (typ == null) return 1;
        Matcher matcher = BRACKET_COUNT_PATTERN.matcher(typ);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1; // Default to 1 if no bracket found
    }

    // ================= PHASE 2: GRAPH LOGIC =================

    private int runGraphRepair(List<Windkraftanlage> anlagen) {
        // 1. Build Graph
        Graph graph = new Graph();
        for (Windkraftanlage wka : anlagen) graph.addVertex(wka);

        // Simple nested loop to build edges (fast enough for 8900 items)
        for (int i = 0; i < anlagen.size(); i++) {
            Windkraftanlage a = anlagen.get(i);
            for (int j = i + 1; j < anlagen.size(); j++) {
                Windkraftanlage b = anlagen.get(j);
                if (DistanceCalculator.calculateDistance(a.getStandort(), b.getStandort()) <= Konstanten.A5_MAX_DIST_KM) {
                    graph.addEdge(a, b);
                }
            }
        }

        int fixCount = 0;
        // 2. Find empty entries and ask neighbors
        for (Windkraftanlage wka : anlagen) {
            Double p = wka.getTechnischeDaten().getSanitizedGesamtleistung();

            // Only fix if Phase 1 didn't fix it already
            if (p == null || p == 0.0) {
                double avg = calculateNeighborAverage(graph, wka);
                if (avg > 0) {
                    wka.getTechnischeDaten().setGesamtleistung(avg);
                    fixCount++;
                }
            }
        }
        return fixCount;
    }

    private double calculateNeighborAverage(Graph graph, Windkraftanlage target) {
        List<Windkraftanlage> neighbors = graph.getNeighbors(target);
        if (neighbors.isEmpty()) return 0.0;

        double sum = 0;
        int count = 0;
        for (Windkraftanlage n : neighbors) {
            Double val = n.getTechnischeDaten().getSanitizedGesamtleistung();
            if (val != null && val > Konstanten.A5_POWER_MIN_VALID && val < Konstanten.A5_POWER_MAX_VALID) {
                sum += val;
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }
}