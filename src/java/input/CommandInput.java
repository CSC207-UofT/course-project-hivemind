package input;
import java.util.Scanner;

public class CommandInput {

    public static void main (String[] args) {
        Scanner myObj = new Scanner(System.in);
        String lastCommand;

        //enter preload here

        while (true) {
            System.out.print("> ");
            lastCommand = myObj.nextLine();
            parseInput(lastCommand);
        }

    }

    public static void parseInput(String input) {
        String[] splitInput = parseInputHelper(input);
        switch(splitInput[0]) {
            case "exit":
                System.exit(0);
            case "food":
                if (splitInput[1].equals("add")) {
                    //handle food
                }
                else {
                    System.out.println("Error, argument " + splitInput[1] + " not recognized");
                }
                break;
            case "recipe":
                if (splitInput[1].equals("search")) {
                    //searches for recipe using controller
                }
                else if (splitInput[1].equals("info")) {
                    //gets recipe info using controller
                }
                else {
                    System.out.println("Error, argument " + splitInput[1] + " not recognized");
                }
                break;
            default:
                System.out.println("Error: command not recognized");
        }
    }

    public static String[] parseInputHelper(String input) {
        return input.split(" ");
    }

    public static void handleFood(String[] foodInfo) {

    }
}