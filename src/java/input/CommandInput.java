package input;
import java.util.Scanner;

public class CommandInput {

    public static void main (String[] args) {
        Scanner myObj = new Scanner(System.in);
        String lastCommand;

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
                System.out.println("food lol");
                break;
            case "recipe":
                System.out.println("recipe lol");
                break;
            default:
                System.out.println("Error: command not recognized");
        }
    }

    public static String[] parseInputHelper(String input) {
        return input.split(" ");
    }

//    public static void handleFood(String ) {
//
//    }
}