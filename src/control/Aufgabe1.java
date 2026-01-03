package control;              // The class Aufgabe 1 is located in the package called control

import model.Windkraftanlage; // Importing Windkraftanlage from the package called model
import utility.Konstanten;    // Importing our new Constants class
import java.util.List;        // Importing List from Java utilities

public class Aufgabe1 {

    /* This method checks if the list of Wind turbines is empty or not.
       If so, it prints an error message and terminates the method.
    */
    public void run(List<Windkraftanlage> anlagen, long durationMs) {
        System.out.println(Konstanten.A1_HEADER);

        if (anlagen == null || anlagen.isEmpty()) {
            System.err.println(Konstanten.A1_ERR_NO_DATA);
            return;
        }

        System.out.println(Konstanten.A1_STATUS_OK);  // Introduction line of the output
        System.out.println(Konstanten.A1_MSG_ENTRIES + anlagen.size());       // Prints total no of entries

        // Calculate the total no of windparks after merging the entries with same name
        long numberOfParks = anlagen.stream()
                .map(Windkraftanlage::getName) // Get the name of every turbine
                .distinct()                    // Keep only unique names
                .count();                      // Count them

        System.out.println(Konstanten.A1_MSG_PARKS + numberOfParks);        // Prints count of windparks after merging the entries with same name
        System.out.println(Konstanten.A1_MSG_DURATION + durationMs + Konstanten.UNIT_MS);   // Prints total duration
        System.out.println(Konstanten.SEPARATOR);

        //Prints the first 3 entries from the Windkraftanlagen file
        System.out.println(Konstanten.A1_MSG_SAMPLE);

        anlagen.stream()
                .limit(Konstanten.A1_SAMPLE_LIMIT) // Only take the first 3 elements
                .forEach(this::printEntry); // Pass each one to the print method

        System.out.println(Konstanten.SEPARATOR);
    }

    // Helper method to keep the stream clean
    private void printEntry(Windkraftanlage wka) {
        System.out.printf(Konstanten.A1_FORMAT_ENTRY,
                wka.getObjektId(),
                wka.getName(),
                wka.getTechnischeDaten().getTyp());
    }
}