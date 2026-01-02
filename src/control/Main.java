package control;

import model.Windkraftanlage;
import utility.WindkraftanlagenCsvLader;
import view.ConsoleUi;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    private List<Windkraftanlage> anlagen;
    private final ConsoleUi view;
    private long loadDuration = 0; // <--- NEW: Store the time here

    public Main() {
        this.view = new ConsoleUi();
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        System.out.println("[Debug] Working Directory: " + System.getProperty("user.dir"));
        view.printMessage(">>> Starting Windkraftanlange Analysis <<<");

        // 1. Load Data with Timer
        WindkraftanlagenCsvLader loader = new WindkraftanlagenCsvLader();
        try {
            Path path = Paths.get("src", "resources", "Windkraftanlagen_DE.csv");
            view.printMessage("Loading CSV from: " + path.toAbsolutePath());

            long start = System.currentTimeMillis(); // <--- START TIMER
            this.anlagen = loader.load(path);
            long end = System.currentTimeMillis();   // <--- END TIMER

            this.loadDuration = end - start;         // <--- CALCULATE DURATION

            view.printMessage("Loaded " + anlagen.size() + " entries successfully in " + loadDuration + " ms.");

        } catch (IOException e) {
            view.printError("Error loading CSV: " + e.getMessage());
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
                // PASS THE DURATION HERE
                case "1" -> aufgabe1.run(anlagen, loadDuration); // <--- UPDATE THIS LINE
                case "2" -> aufgabe2.run(anlagen);
                case "3" -> aufgabe3.run(anlagen);
                case "4" -> aufgabe4.run(anlagen);
                case "5" -> aufgabe5.run(anlagen);
                case "6" -> aufgabe6.run(anlagen);
                case "q", "exit" -> {
                    view.printMessage("Exiting...");
                    running = false;
                }
                default -> view.printMessage("Invalid option. Please try again.");
            }
        }
        view.close();
    }
}