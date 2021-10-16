package input;
import java.util.Scanner;
import controllers.FoodController;
import controllers.RecipeController;
import parsers.DataParser;
import java.util.ArrayList;

public class CommandInput {
    private static final FoodController foodController = new FoodController();
    private static final RecipeController recipeController = new RecipeController();
    private static boolean exitProgram = true;

    public static void main (String[] args) {
        Scanner myObj = new Scanner(System.in);
        String lastCommand;

        //enter preload here
        try {
            ArrayList<String> foodData = DataParser.readFile(true);
            foodController.initialLoad(foodData);
            ArrayList<String> recipeData = DataParser.readFile(false);
            recipeController.initialLoad(recipeData);
        }
        catch (Exception e) {
            System.out.println("Unfortunately, an error has occurred." +
                    "  Please verify that all data files are in their correct spots and relaunch the program");
            System.out.println(e.getMessage());
        }

        while (exitProgram) {
            System.out.print("> ");
            lastCommand = myObj.nextLine();
            parseInput(lastCommand);
        }
        System.exit(0);

    }

    public static void parseInput(String input) {
        String[] splitInput = parseInputHelper(input);
        switch(splitInput[0]) {
            case "exit":
                exitProgram = false;
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
                    System.out.println(recipeController.recommendRecipe());
                }
//                else if (splitInput[1].equals("info")) {
//                    //gets recipe info using controller
//                }
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