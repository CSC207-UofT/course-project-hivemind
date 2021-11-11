package input;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import controllers.FoodController;
import controllers.RecipeController;
import entities.Food;
import entities.PerishableFood;
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
                    }
                    else if (splitInput[1].equals("delete")){
                        deleteFoodHelper();
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

    private static void deleteFoodHelper() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Food Name:");
        System.out.print("> ");
        String foodName = scanner.nextLine();

        try {
            ArrayList<Object[]> foodList = foodController.getSpecifiedFoodList(foodName);
            if (foodList.size() == 0){
                System.out.println("This food item is not currently stored in the system. " +
                        "Please verify the spelling and existence of this food item");
            }
            else {
                System.out.println("Please input the number corresponding to which Food item you wish to delete:");
                int index = 1;
                for (Object[] foods : foodList) {
                    Food food = (Food) foods[0];
                    printFood(index, food);
                    index++;
                }
                System.out.print("> ");
                String foodDelete = scanner.nextLine();
                int foodIndexToDelete = Integer.parseInt(foodDelete) - 1; //Include exceptions in case the user inputs the wrong value
                Food deletedFood = foodController.deleteFood((Food) foodList.get(foodIndexToDelete)[0]);
                DataParser.deleteFoodFromFile(foodList.get(foodIndexToDelete));
                ArrayList<String> foodData = DataParser.readFile(true);
                foodController.initialLoad(foodData);
                System.out.println("The following food item was successfully deleted from the system:");
                printFood(foodIndexToDelete + 1, deletedFood);
            }
        } catch (Exception e){
            System.out.println("An error occurred, did not successfully delete food. " +
                    "Please verify that all arguments are of the proper data type");
        }
    }

    private static void printFood(int index, Food food) {
        if (food instanceof PerishableFood) {
            System.out.println(index + ". Food Name: " + food.getName() + " Quantity: " +
                    food.getQuantity() + " Unit: " + food.getUnit() + " Expiry Date: " +
                    ((PerishableFood) food).getExpiryDate() + " Expiry Status: " +
                    ((PerishableFood) food).getExpiryStatus());
        }
        else {
            System.out.println(index + ". Food Name: " + food.getName() + " Quantity: " +
                    food.getQuantity() + " Unit: " + food.getUnit());
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
}