package control;              // The class Aufgabe 1 is located in the package called control

import model.Windkraftanlage; // Importing Windkraftanlage from the package called model
import java.util.List;        // Importing List from Java utilities

public class Aufgabe1 {

    /* This method checks if the list of Wind turbines is empty or not.
       If so, it prints an error message and terminates the method.
    */
    public void run(List<Windkraftanlage> anlagen, long durationMs) {
        System.out.println("\n=== Aufgabe 1: Data Verification (Stream-Based) ===");

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println("Error: No data loaded!");
            return;
        }

        System.out.println("Status: Data Model & CSV Loader are working.");  // Introduction line of the output
        System.out.println("Total Entries Loaded: " + anlagen.size());       // Prints total no of entries

        // Calculate the total no of windparks after merging the entries with same name
        long numberOfParks = anlagen.stream()
                .map(Windkraftanlage::getName) // Get the name of every turbine
                .distinct()                    // Keep only unique names
                .count();                      // Count them

        System.out.println("Total Windparks:     " + numberOfParks);        // Prints count of windparks after merging the entries with same name
        System.out.println("Loading Duration:     " + durationMs + " ms");   // Prints total duration
        System.out.println("--------------------------------------------------");

        //Prints the first 3 entries from the Windkraftanlagen file
        System.out.println("Sample Data (First 3 entries):");

        anlagen.stream()
                .limit(3) // Only take the first 3 elements
                .forEach(this::printEntry); // Pass each one to the print method

        System.out.println("--------------------------------------------------");
    }

    // Helper method to keep the stream clean
    private void printEntry(Windkraftanlage wka) {
        System.out.printf("ID: %d | Name: %s | Typ: %s%n",
                wka.getObjektId(),
                wka.getName(),
                wka.getTechnischeDaten().getTyp());
    }
}