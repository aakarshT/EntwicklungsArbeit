package view;

import java.util.Scanner;

public class ConsoleUi {

    private final Scanner scanner;

    public ConsoleUi() {
        this.scanner = new Scanner(System.in);
    }

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

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.err.println(error);
    }

    public void close() {
        scanner.close();
    }
}