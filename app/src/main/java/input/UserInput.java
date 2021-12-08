package input;

import java.util.Scanner;

public class UserInput {
    private final Scanner inputScanner = new Scanner(System.in);

    /**
     * This class is used to get input from the user in the console
     * @return the input string
     */
    public String getInput() {
        System.out.print("> ");
        return inputScanner.nextLine();
    }

    /**
     * This class prints out an error message from a given error
     * @param e the error
     */
    public void printExceptionMessage(Exception e) {
        System.out.println("Unfortunately, an error has occurred." +
                "  Please verify that all data files are in their correct spots and relaunch the program");
        System.out.println(e.getMessage());
    }

    /**
     * Close the scanner, only used on app shutdown
     */
    public void closeScanner() {
        inputScanner.close();
    }

    /**
     *Helper to tell user how to use the commands
     */
    public void printUserHelper(){
        System.out.println("Welcome to Sous-chef! Your Intelligent Kitchen Assistant!");
        System.out.println("----------------------------------------------------------");
        System.out.println("Here are all the current functionalities via commandline:");
        System.out.println("food add [foodName, quantity, unit (, year, month, day)] : adds a food into inventory");
        System.out.println("     delete: deletes a food from inventory");
        System.out.println("     check: check all expired foods");
        System.out.println("     get: get a list of all foods in inventory");
        System.out.println("----------------------------------------------------------");
        System.out.println("recipe search [quantity]: get a recommendation of recipes");
        System.out.println("       add: adds a recipe into the recipe book");
        System.out.println("----------------------------------------------------------");
        System.out.println("program exit: exits the program");
        System.out.println("program help: view this list of program commands");
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Print the given message
     * @param message the message to print
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Print the given message, using print instead of println
     * @param message the message to print
     */
    public void printInLineMessage(String message) {
        System.out.print(message);
    }

    /**
     * This class is used to get additional lines of input between promts from the user
     * @return the line input by the user
     */
    public String scanInputLine() {
        return inputScanner.nextLine();
    }
}
