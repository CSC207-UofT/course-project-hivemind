package input;
import java.util.Arrays;
import java.util.Scanner;
import controllers.FoodController;
import controllers.RecipeController;
import entities.Recipe;
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

    /**
     * The method to parse user input and calls according methods for the given input
     *
     * @param input user's input string to be parsed
     */
    public static void parseInput(String input) {
        String[] splitInput = parseInputHelper(input);
        if (splitInput.length > 1) {
            switch (splitInput[0]) {
                case "program":
                    if (splitInput[1].equals("exit")) {
                        exitProgram = false;
                    }
                    break;
                case "food":
                    if (splitInput[1].equals("add")) {
                        handleFood(splitInput);
                    } else {
                        System.out.println("Error, argument " + splitInput[1] + " not recognized");
                    }
                    break;
                case "recipe":
                    if (splitInput[1].equals("search")) {
                        System.out.println(getRecipeRecommendation());
                    }
                    //                else if (splitInput[1].equals("info")) {
                    //                    //gets recipe info using controller
                    //                }
                    else if (splitInput[1].equals("add")){
                        addRecipeHelper();
                    }
                    else {
                        System.out.println("Error, argument " + splitInput[1] + " not recognized");
                    }
                    break;
                default:
                    System.out.println("Error: command not recognized");
            }
        }
        else {
            System.out.println("Error: not enough arguments in command");
        }
    }

    public static void addRecipeHelper() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Recipe Name:");
        System.out.print("> ");
        String recipeName = scan.nextLine();

        System.out.println("Enter List of Ingredients: (Ingredient1 Quantity1 Unit1 Ingredient2 Quantity2 Unit2....)");
        System.out.print("> ");
        String ingredientString = scan.nextLine();

        System.out.println("Enter instructions:");
        System.out.print("> ");
        String instructions = scan.nextLine();


        try {
            String s = ingredientString.replace(" ", ",,");
            DataParser.writeToFile(recipeName + ",," + s + ",," + instructions, false);
            System.out.println("Saved to file successfully.");
        } catch (Exception e){
            System.out.println("error in saving");
        }

        if (recipeController.addRecipe(recipeName, ingredientString, instructions)){
            System.out.println("Successfully added recipe: " + recipeName);
        }
        else{
            System.out.println("An error has occurred, did not successfully add recipe");
        }
    }

    public static String[] parseInputHelper(String input) {
        return input.split(" ");
    }

    public static void handleFood(String[] foodInfo) {
        if (foodInfo.length > 5) {
            try {
                Double.parseDouble(foodInfo[3]);  // checks if all inputs are proper doubles for optional inputs
                Double.parseDouble(foodInfo[5]);
                Double.parseDouble(foodInfo[6]);
                Double.parseDouble(foodInfo[7]);

                ArrayList<String> foodInfoList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(foodInfo, 2, 8)));
                foodController.runFoodCreation(foodInfoList);

                DataParser.writeToFile(foodInfo[2] + ",," + foodInfo[3]+ ",," + foodInfo[4]+ ",,"+ foodInfo[5]+
                        ",,"+ foodInfo[6]+ ",,"+ foodInfo[7], true);
            } catch (Exception e) {
                System.out.println("Error: command arguments are incorrect, please verify that all" +
                        " arguments are of the proper data type");
            }
        }
        else {
            try {
                Double.parseDouble(foodInfo[3]);  // checks if all inputs are proper doubles

                ArrayList<String> foodInfoList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(foodInfo, 2, 5)));
                foodController.runFoodCreation(foodInfoList);

                DataParser.writeToFile(foodInfo[2] + ",," + foodInfo[3]+ ",," + foodInfo[4], true);
            } catch (Exception e) {
                System.out.println("Error: command arguments are incorrect, please verify that all" +
                        " arguments are of the proper data type");
            }
        }
    }

    public static ArrayList<String> getRecipeRecommendation() {
        ArrayList<Recipe> recipeList = recipeController.recommendRecipe();
        ArrayList<String> stringRecipeList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            stringRecipeList.add(recipe.toString());
        }
        return stringRecipeList;
    }
}