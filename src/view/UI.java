package view;               // The class is located inside the package called view

import utility.Konstanten;  // Imports Constants
import java.util.Scanner;   // Imports Scanner from java utilities

public class UI {

    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    // Main menu

    public void printMenu() {
        System.out.println(Konstanten.UI_MENU_SEPARATOR);
        System.out.println(Konstanten.UI_MENU_HEADER);
        System.out.println(Konstanten.UI_MENU_OPT_1);
        System.out.println(Konstanten.UI_MENU_OPT_2);
        System.out.println(Konstanten.UI_MENU_OPT_3);
        System.out.println(Konstanten.UI_MENU_OPT_4);
        System.out.println(Konstanten.UI_MENU_OPT_5);
        System.out.println(Konstanten.UI_MENU_OPT_6);
        System.out.println(Konstanten.UI_MENU_OPT_Q);
        System.out.print(Konstanten.UI_PROMPT_CHOICE);
    }

    public String getUserInput() {                              // Asks the user for the input
        return scanner.nextLine();
    }

    public void printMessage(String message) {                  // prints the message
        System.out.println(message);
    }

    public void printError(String error) {                      // Prints an error message
        System.err.println(error);
    }  // prints error

    public void close() {
        scanner.close();
    }                             // closes the scnaeer
}