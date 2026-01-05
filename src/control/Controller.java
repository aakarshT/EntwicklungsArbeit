package control;                // The class is stored in the control package

import model.Windkraftanlage;    // Imports windkraftanlage from model package
import view.UI;                  // Imports UI from view package
import resources.Konstanten;      // Import constants  from utility package
import utility.WindkraftanlagenCsvLoader;   // Imports WindkraftanlageCsvloader from utility package
import java.nio.file.Path;      // Imports Path from file to locate
import java.nio.file.Paths;
import java.util.ArrayList;     // Imports Arraylists from Java Utility
import java.util.List;          // Imports Lists from Util in java

public class Controller {

    private final UI ui;
    private final WindkraftanlagenCsvLoader loader;
    private List<Windkraftanlage> anlagen;


    public Controller() {
        this.ui = new UI();
        this.loader = new WindkraftanlagenCsvLoader();
        this.anlagen = new ArrayList<>(); // Start empty
    }

    public void start() {
        ui.printMessage(Konstanten.MAIN_MSG_START);
        printCurrentDirectory(); // Debug info

        boolean running = true;

        while (running) {
            ui.printMenu();
            String choice = ui.getUserInput();

            switch (choice) {
                case Konstanten.CMD_1:
                    executeAufgabe1();
                    break;
                case Konstanten.CMD_2:
                    executeAufgabe2();
                    break;
                case Konstanten.CMD_3:
                    executeAufgabe3();
                    break;
                case Konstanten.CMD_4:
                    executeAufgabe4();
                    break;
                case Konstanten.CMD_5:
                    executeAufgabe5();
                    break;
                case Konstanten.CMD_6:
                    executeAufgabe6();
                    break;
                case Konstanten.CMD_Q:
                case Konstanten.CMD_EXIT:
                    running = false;
                    ui.printMessage(Konstanten.MAIN_MSG_EXIT);
                    break;
                default:
                    ui.printError(Konstanten.MAIN_ERR_INVALID_OPT);
            }
        }
        ui.close();
    }

    // --- Task Execution Methods ---

    private void executeAufgabe1() {
        // Only load if not already loaded (or reload if you prefer)
        if (anlagen.isEmpty()) {
            Path path = Paths.get(Konstanten.DIR_RESOURCES, Konstanten.FILE_CSV);
            ui.printMessage(Konstanten.MAIN_MSG_LOAD_PATH + path.toAbsolutePath());

            try {
                long start = System.currentTimeMillis();
                this.anlagen = loader.load(path);
                long duration = System.currentTimeMillis() - start;

                // Show success message from Constants
                System.out.printf(Konstanten.MAIN_MSG_LOAD_SUCCESS, anlagen.size(), duration);
            } catch (Exception e) {
                ui.printError(Konstanten.MAIN_ERR_LOAD + e.getMessage());
                return; // Stop if load failed
            }
        }

        // Run Aufgabe 1 Logic
        new Aufgabe1(anlagen).run();
    }

    private void executeAufgabe2() {
        if (checkDataLoaded()) {
            new Aufgabe2(anlagen).run();
        }
    }

    private void executeAufgabe3() {
        if (checkDataLoaded()) {
            new Aufgabe3(anlagen).run();
        }
    }

    private void executeAufgabe4() {
        if (checkDataLoaded()) {
            new Aufgabe4(anlagen).run();
        }
    }

    private void executeAufgabe5() {
        if (checkDataLoaded()) {
            new Aufgabe5(anlagen).run();
        }
    }

    private void executeAufgabe6() {
        if (checkDataLoaded()) {
            new Aufgabe6(anlagen).run();
        }
    }

    // --- Helper Methods ---

    private boolean checkDataLoaded() {
        if (anlagen == null || anlagen.isEmpty()) {
            ui.printError(Konstanten.A1_ERR_NO_DATA);
            return false;
        }
        return true;
    }

    private void printCurrentDirectory() {
        ui.printMessage(Konstanten.MAIN_MSG_DEBUG_DIR + System.getProperty("user.dir"));
    }
}