package model;

import utility.Konstanten; // Import Constants
import java.util.*;

/**
 * Represents an undirected Graph using an Adjacency List.
 * (See PDF Slide 7: "Adjazenzliste als dynamische Datenstruktur")
 */
public class Graph {

    // Maps a Node (Turbine) to a List of its Neighbors
    private final Map<Windkraftanlage, List<Windkraftanlage>> adjList;

    public Graph() {
        this.adjList = new HashMap<>();
    }

    /**
     * Adds a vertex (Knoten) to the graph.
     */
    public void addVertex(Windkraftanlage wka) {
        adjList.putIfAbsent(wka, new ArrayList<>());
    }

    /**
     * Adds an edge (Kante) between two turbines.
     * Since distance is mutual, the graph is undirected (A->B and B->A).
     */
    public void addEdge(Windkraftanlage a, Windkraftanlage b) {
        adjList.get(a).add(b);
        adjList.get(b).add(a);
    }

    public List<Windkraftanlage> getNeighbors(Windkraftanlage wka) {
        return adjList.getOrDefault(wka, Collections.emptyList());
    }

    public int getVertexCount() {
        return adjList.size();
    }

    // Helper to see total connections
    public int getEdgeCount() {
        // Replaced magic number '2' with Constant
        return adjList.values().stream().mapToInt(List::size).sum() / Konstanten.GRAPH_EDGE_DIVISOR;
    }
}