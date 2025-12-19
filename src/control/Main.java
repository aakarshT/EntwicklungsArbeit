package control;

import model.Windkraftanlage;
import utility.WindkraftanlagenCsvLader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Path csvPath = Path.of("src", "resources", "Windkraftanlagen_DE.csv"); // adjust name if needed
        WindkraftanlagenCsvLader lader = new WindkraftanlagenCsvLader();

        long startTime = System.nanoTime();
        try {
            List<Windkraftanlage> anlagen = lader.load(csvPath);
            long endTime = System.nanoTime();
            long durationMillis = (endTime - startTime) / 1_000_000;

            System.out.println("=== Teilaufgabe 1 ===");
            System.out.println("CSV file: " + csvPath.toAbsolutePath());
            System.out.println("Number of wind turbines loaded: " + anlagen.size());
            System.out.println("Reading and parsing took: " + durationMillis + " ms.");

            long withKnownPower = anlagen.stream()
                    .filter(a -> a.getTechnischeDaten().getGesamtleistungMw() != null)
                    .count();
            System.out.println("Turbines with known total power: " + withKnownPower);
            System.out.println("Turbines with missing total power: " +
                    (anlagen.size() - withKnownPower));

        } catch (IOException e) {
            System.err.println("Error while reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}