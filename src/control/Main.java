package control;                                        // The class is stored in the control package

import model.Windkraftanlage;                           // Imports windkraftanlage from model package
import utility.Konstanten;                              // Import constants  from utility pacakge
import utility.WindkraftanlagenCsvLader;                // Imports windkraftanlagaencsvlader from utility package
import view.UI;                                         // Imports UI from view package

import java.io.IOException;                             // Imports IO Exception to handle exceptions while reading the csv file
import java.nio.file.Path;                              // Imports Path from file to locate
import java.nio.file.Paths;                             //
import java.util.List;                                  // Imports Lists from Util in java

public class Main {

    private List<Windkraftanlage> anlagen;
    private final UI view;
    private long loadDuration = 0; // Store the time here

    public Main() {
        this.view = new UI();
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        System.out.println(Konstanten.MAIN_MSG_DEBUG_DIR + System.getProperty("user.dir"));
        view.printMessage(Konstanten.MAIN_MSG_START);

        // 1. Load Data with Timer
        WindkraftanlagenCsvLader loader = new WindkraftanlagenCsvLader();
        try {
            // Use constants for paths
            Path path = Paths.get(Konstanten.DIR_SRC, Konstanten.DIR_RESOURCES, Konstanten.FILE_CSV);
            view.printMessage(Konstanten.MAIN_MSG_LOAD_PATH + path.toAbsolutePath());

            long start = System.currentTimeMillis(); // START TIMER
            this.anlagen = loader.load(path);
            long end = System.currentTimeMillis();   // END TIMER

            this.loadDuration = end - start;         // CALCULATE DURATION

            // Use printf style formatting from constants
            System.out.printf(Konstanten.MAIN_MSG_LOAD_SUCCESS, anlagen.size(), loadDuration);

        } catch (IOException e) {
            view.printError(Konstanten.MAIN_ERR_LOAD + e.getMessage());
            return;
        }

        // 2. Initialize Controllers
        Aufgabe1 aufgabe1 = new Aufgabe1();
        Aufgabe2 aufgabe2 = new Aufgabe2();
        Aufgabe3 aufgabe3 = new Aufgabe3();
        Aufgabe4 aufgabe4 = new Aufgabe4();
        Aufgabe5 aufgabe5 = new Aufgabe5();
        Aufgabe6 aufgabe6 = new Aufgabe6();

        // 3. Main Loop
        boolean running = true;
        while (running) {
            view.printMenu();
            String input = view.getUserInput();

            switch (input) {
                // Use Constants for cases (optional but good for consistency)
                case Konstanten.CMD_1 -> aufgabe1.run(anlagen, loadDuration);
                case Konstanten.CMD_2 -> aufgabe2.run(anlagen);
                case Konstanten.CMD_3 -> aufgabe3.run(anlagen);
                case Konstanten.CMD_4 -> aufgabe4.run(anlagen);
                case Konstanten.CMD_5 -> aufgabe5.run(anlagen);
                case Konstanten.CMD_6 -> aufgabe6.run(anlagen);
                case Konstanten.CMD_Q, Konstanten.CMD_EXIT -> {
                    view.printMessage(Konstanten.MAIN_MSG_EXIT);
                    running = false;
                }
                default -> view.printMessage(Konstanten.MAIN_ERR_INVALID_OPT);
            }
        }
        view.close();
    }
}