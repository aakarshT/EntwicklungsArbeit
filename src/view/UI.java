package view;               // The class is located inside the package called view

import java.util.Scanner;   // Imports Scanner from java utilities

public class UI {

    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    // Main menu which is shown when the program is first started

    public void printMenu() {
        System.out.println("\n-----------------------------------------");
        System.out.println(" Please select a task:");
        System.out.println(" 1 - Aufgabe 1");
        System.out.println(" 2 - Aufgabe 2");
        System.out.println(" 3 - Aufgabe 3");
        System.out.println(" 4 - Aufgabe 4");
        System.out.println(" 5 - Aufgabe 5");
        System.out.println(" 6 - Aufgabe 6");
        System.out.println(" q - Quit");
        System.out.print("Choice: ");
    }

    public String getUserInput() {                              // Asks the user for the input
        return scanner.nextLine();
    }

    public void printMessage(String message) {                  // prints the message
        System.out.println(message);
    }

    public void printError(String error) {                      // Prints an error message if the user has entered a value which is not from 1 till 6 or q
        System.err.println(error);
    }

    public void close() {
        scanner.close();
    }
}