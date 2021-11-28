package input;

import java.util.Scanner;

public class UserInput {
    private final Scanner inputScanner = new Scanner(System.in);
    String lastCommand;

    public String getInput() {
        System.out.print("> ");
        lastCommand = inputScanner.nextLine();
        return lastCommand;
    }

    public void printExceptionMessage(Exception e) {
        System.out.println("Unfortunately, an error has occurred." +
                "  Please verify that all data files are in their correct spots and relaunch the program");
        System.out.println(e.getMessage());
    }

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
}
