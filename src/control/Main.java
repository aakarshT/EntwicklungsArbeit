package control;

import model.Windkraftanlage;
import utility.WindkraftanlagenCsvLader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Path csvPath = Path.of("src", "resources", "Windkraftanlagen_DE.csv");
        WindkraftanlagenCsvLader lader = new WindkraftanlagenCsvLader();

        List<Windkraftanlage> anlagen;
        long ladeDauerMillis;

        long startTime = System.nanoTime();
        try {
            anlagen = lader.load(csvPath);
        } catch (IOException e) {
            System.err.println("Error while reading CSV file: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        long endTime = System.nanoTime();
        ladeDauerMillis = (endTime - startTime) / 1_000_000;

        System.out.println("Data loaded successfully. " +
                "Number of turbines: " + anlagen.size());

        // Create one controller per task
        Aufgabe1 aufgabe1Controller = new Aufgabe1(ladeDauerMillis);
        Aufgabe2 aufgabe2Controller = new Aufgabe2();
        Aufgabe3 aufgabe3Controller = new Aufgabe3();
        Aufgabe4 aufgabe4Controller = new Aufgabe4();
        Aufgabe5 aufgabe5Controller = new Aufgabe5();
        Aufgabe6 aufgabe6Controller = new Aufgabe6();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("===== Menu =====");
            System.out.println("1 - Run Aufgabe 1");
            System.out.println("2 - Run Aufgabe 2");
            System.out.println("3 - Run Aufgabe 3");
            System.out.println("4 - Run Aufgabe 4");
            System.out.println("5 - Run Aufgabe 5");
            System.out.println("6 - Run Aufgabe 6");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> aufgabe1Controller.run(anlagen);
                case "2" -> aufgabe2Controller.run(anlagen);
                case "3" -> aufgabe3Controller.run(anlagen);
                case "4" -> aufgabe4Controller.run(anlagen);
                case "5" -> aufgabe5Controller.run(anlagen);
                case "6" -> aufgabe6Controller.run(anlagen);
                case "0" -> {
                    System.out.println("Exiting program.");
                    running = false;
                }
                default -> System.out.println("Unknown choice. Please enter from 1 till 6.");
            }
        }

        scanner.close();
    }
}