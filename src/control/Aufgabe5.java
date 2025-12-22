package control;

import model.Graph;
import model.Windkraftanlage;
import utility.DistanceCalculator;

import java.util.List;

/**
 * Handles Teilaufgabe 5: Graph construction and Data Repair.
 */
public class Aufgabe5 {

    private static final double MAX_DISTANCE_KM = 20.0;

    /**
     * Precondition: anlagen is a non-null list.
     * Postcondition: Builds a graph and prints the number of repaired power values.
     */
    public void run(List<Windkraftanlage> anlagen) {
        System.out.println("\n=== Aufgabe 5: Graph & Data Repair ===");
        long start = System.nanoTime();

        // 1. Build the Graph (Vertices)
        Graph graph = new Graph();
        for (Windkraftanlage wka : anlagen) {
            graph.addVertex(wka);
        }

        System.out.println("Building edges (comparing distances)...");

        // 2. Build Edges (Connect if distance <= 20km)
        // Note: Comparing 8900^2 items takes a few seconds.
        for (int i = 0; i < anlagen.size(); i++) {
            Windkraftanlage a = anlagen.get(i);
            // Start j at i+1 to avoid checking same pair twice
            for (int j = i + 1; j < anlagen.size(); j++) {
                Windkraftanlage b = anlagen.get(j);

                double dist = DistanceCalculator.calculateDistance(a.getStandort(), b.getStandort());
                if (dist <= MAX_DISTANCE_KM) {
                    graph.addEdge(a, b);
                }
            }
        }

        System.out.println("Graph built with " + graph.getVertexCount() + " vertices and " + graph.getEdgeCount() + " edges.");

        // 3. Repair Missing Power Data
        int repairedCount = 0;

        System.out.printf("%-35s | %-15s | %-15s%n", "Name", "Old Power", "Estimated Power");
        System.out.println("-----------------------------------------------------------------------");

        for (Windkraftanlage wka : anlagen) {
            Double p = wka.getTechnischeDaten().getSanitizedGesamtleistung();

            // If power is missing (null) or 0, try to repair it
            if (p == null || p == 0.0) {
                double estimated = calculateAverageNeighborPower(graph, wka);
                if (estimated > 0) {
                    repairedCount++;
                    // Print the first 5 repairs as a sample
                    if (repairedCount <= 5) {
                        System.out.printf("%-35s | %-15s | %-15.2f MW%n",
                                truncate(wka.getName(), 35), "N/A", estimated);
                    }
                }
            }
        }

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Total repaired entries: " + repairedCount);
        System.out.println("Duration: " + duration + " ms.");
    }

    /**
     * Logic: Look at neighbors. If they have valid power, take the average.
     */
    private double calculateAverageNeighborPower(Graph graph, Windkraftanlage target) {
        List<Windkraftanlage> neighbors = graph.getNeighbors(target);
        if (neighbors.isEmpty()) return 0.0;

        double sum = 0;
        int count = 0;

        for (Windkraftanlage n : neighbors) {
            Double val = n.getTechnischeDaten().getSanitizedGesamtleistung();
            if (val != null && val > 0) {
                sum += val;
                count++;
            }
        }

        return count == 0 ? 0.0 : sum / count;
    }

    private String truncate(String s, int len) {
        if (s == null) return "";
        return s.length() > len ? s.substring(0, len-3) + "..." : s;
    }
}