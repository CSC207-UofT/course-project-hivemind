package input;

import java.io.IOException;
import java.util.Arrays;
import java.util.*;
import input.UserInput;

import alerts.AlertExpiryStatus;
import controllers.FoodController;
import controllers.RecipeController;
import parsers.DataParser;

public class CommandInput {
    public static final FoodController foodController = new FoodController();
    private static final RecipeController recipeController = new RecipeController();
    private static final UserInput userInput = new UserInput();
    public static boolean exitProgram = true;

    public static void main (String[] args) {
        String lastCommand;

        //enter preload here
        try {
            ArrayList<String> foodData = DataParser.readFile(DataParser.FOOD_FILE);
            foodController.loadFoodFromList(foodData);
            ArrayList<String> recipeData = DataParser.readFile(DataParser.RECIPE_FILE);
            System.out.println(recipeData.get(0));
            recipeController.initialLoad(recipeData);
            userInput.printUserHelper();
            AlertExpiryStatus.alertExpiredFoods();
            // create a thread to be run in parallel
            Thread thread1 = new Thread(new AlertExpiryStatus());
            thread1.start();


        }
        catch (Exception e) {
            userInput.printExceptionMessage(e);
        }

        while (exitProgram) {
            lastCommand = userInput.getInput();
            parseInput(lastCommand);
        }
        userInput.closeScanner();
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
                    switch (splitInput[1]){
                        case "exit":
                            exitProgram = false;
                            break;
                        case "help":
                            userInput.printUserHelper();
                            break;
                    }
                    break;
                case "food":
                    switch (splitInput[1]) {
                        case "add":
                            foodHelper();
                            break;
                        case "delete":
                            deleteFoodHelper();
                            break;
                        case "check":
                            AlertExpiryStatus.alertExpiredFoods();
                            break;
                        case "get":
                            for (String str : foodController.allFoodToString()) {
                                userInput.printMessage(str);
                            }
                            userInput.printMessage("-----------------------");
                            break;
                        default:
                            userInput.printMessage("Error, argument " + splitInput[1] + " not recognized");
                            break;
                    }
                    break;
                case "recipe":
                    if (splitInput[1].equals("search")) {
                        try{
                            int amount = Integer.parseInt(splitInput[2]);
                            for(int i = 0; i < recipeController.recommendRecipe(amount).size(); i++){
                                userInput.printMessage(recipeController.recommendRecipe(amount).get(i));
                                userInput.printMessage(" ");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            userInput.printMessage("error in input: no number inputted!");
                        }
                        catch (NumberFormatException e){
                            userInput.printMessage("error in type, try using a number!");
                        }


                    }

                    else if (splitInput[1].equals("add")){
                        addRecipeHelper();
                    }
                    else {
                        userInput.printMessage("Error, argument " + splitInput[1] + " not recognized");
                    }
                    break;
                default:
                    userInput.printMessage("Error: command not recognized");
            }
        }
        else {
            userInput.printMessage("Error: not enough arguments in command");
        }
    }



    /**
     * A method to delete a specified Food Object from the inventory and fooddata
     */
    private static void deleteFoodHelper() {
        Scanner scanner = new Scanner(System.in);
        printAvailableFood();
        printDeleteFoodPrompt();
        int foodAmount = foodController.makeSpecifiedFoodList(scanner.nextLine());

        try {
            if (foodAmount == 0){
                printFoodNotInSystem();
            }
            else {
                printSpecifiedDeleteFoodPrompt();
                int foodPositionToDelete = Integer.parseInt(scanner.nextLine());
                int foodIndexToDelete = foodController.getFoodIndexToDelete(foodPositionToDelete);
                String deletedFoodString = foodController.deleteFood(foodPositionToDelete);
                DataParser.deleteRowFromFoodFile(foodIndexToDelete);
                printFoodDeletedFromSystem(deletedFoodString);
            }
        }
        catch (Exception e){
            userInput.printMessage("An error occurred, did not successfully delete food. " +
                    "Please verify that all arguments are of the proper data type and format");
        }
    }

    /**
     * Prints a message prompting the user to input the number corresponding to which food they wish to delete
     */
    private static void printSpecifiedDeleteFoodPrompt() {
        userInput.printMessage("Please input the number corresponding to which food item you wish to delete:");
        printSpecifiedFoodList();
        userInput.printInLineMessage("> ");
    }

    /**
     * Prints a message prompting the user to input the name of the Food which they wish to delete
     */
    private static void printDeleteFoodPrompt() {
        userInput.printMessage("Enter Food Name To Delete:");
        userInput.printInLineMessage("> ");
    }

    /**
     * Prints a message indicating the Food specified at the given index was deleted from the system
     * @param deletedFoodString A string representation of the deleted Food
     */
    private static void printFoodDeletedFromSystem(String deletedFoodString) {
        foodController.foodDeletedFromSystem();
        userInput.printMessage("The following food item was successfully deleted from the system:");
        userInput.printMessage(deletedFoodString);
    }

    /**
     * Prints a message displaying all the Food objects present in the system
     */
    private static void printAvailableFood() {
        userInput.printMessage("Here are all your foods in your inventory:");
        userInput.printMessage("-----------------------");
        for (String str : foodController.allFoodToString()) {
            userInput.printMessage(str);
        }
        userInput.printMessage("-----------------------");
    }

    /**
     * Prints all the foods in the program which match the name of the food that the user wishes to delete
     */
    private static void printSpecifiedFoodList() {
        for (String food: foodController.printSpecifiedFoodList()) {
            userInput.printMessage(food);
        }
    }

    /**
     * Prints a message when the user searches for a food object that is not present in the system
     */
    private static void printFoodNotInSystem() {
        userInput.printMessage("This food item is not currently stored in the system. " +
                "Please verify the spelling and existence of this food item");
    }

    public static void addRecipeHelper() {
        userInput.printMessage("Enter Recipe Name:");
        userInput.printInLineMessage("> ");
        String recipeName = userInput.scanInputLine();

        userInput.printMessage("Enter List of Ingredients: (Ingredient1 Quantity1 Unit1 Ingredient2 Quantity2 Unit2....)");
        userInput.printInLineMessage("> ");
        String ingredientString = userInput.scanInputLine();

        userInput.printMessage("Enter instructions:");
        userInput.printInLineMessage("> ");
        String instructions = userInput.scanInputLine();


        try {
            String s = ingredientString.replace(" ", ",,");
            DataParser.writeToFile(recipeName + ",," + s + ",," + instructions, DataParser.RECIPE_FILE);
            userInput.printMessage("Saved to file successfully.");
        } catch (Exception e){
            userInput.printMessage("error in saving");
        }

        if (recipeController.addRecipe(recipeName, ingredientString, instructions)){
            userInput.printMessage("Successfully added recipe: " + recipeName);
        }
        else{
            userInput.printMessage("An error has occurred, did not successfully add recipe");
        }
    }

    public static String[] parseInputHelper(String input) {
        return input.split(" ");
    }

    public static void foodHelper() {
        userInput.printMessage("Enter Food Name:");
        userInput.printInLineMessage("> ");
        String foodName = userInput.scanInputLine();

        userInput.printMessage("Enter Quantity of Food (number)");
        userInput.printInLineMessage("> ");
        String foodQuant = userInput.scanInputLine();
        if (!testIsDouble(foodQuant)) {
            userInput.printMessage("Please make sure that the input is a number, and try again.");
            return;
        }

        userInput.printMessage("Enter Unit of Measurement:");
        userInput.printInLineMessage("> ");
        String foodUnit = userInput.scanInputLine();

        userInput.printMessage("Enter the Expiry Date using format" +
                " YYYY/MM/DD (leave this blank if this food is non-perishable");
        userInput.printInLineMessage("> ");
        String foodExpiry = userInput.scanInputLine();

        String[] foodInfo;

        if (!foodExpiry.equals("")) {
            try{
                String[] expiryInfo = foodExpiry.split("/");
                foodInfo = new String[]{foodName, foodQuant, foodUnit, expiryInfo[0], expiryInfo[1], expiryInfo[2]};
            }
            catch (Exception e){
                userInput.printMessage("Error in loading food, please make sure that the expiry date is either" +
                        "blank, or follows the format \"YYYY/MM/DD\"");
                return;
            }
        }
        else {
            foodInfo = new String[]{foodName, foodQuant, foodUnit};
        }

        if (handleFoodHelper(foodInfo)) {
            userInput.printMessage("Food added successfully!");
        }
    }

    public static boolean handleFoodHelper(String[] foodInfo) {
        if (foodInfo.length > 3) {
            if (testIsDouble(foodInfo[1]) && testIsDouble(foodInfo[3]) && testIsDouble(foodInfo[5])
                    && testIsDouble(foodInfo[5])) {

                try {
                    foodController.runFoodCreation(new ArrayList<>(Arrays.asList(foodInfo)));
                    DataParser.writeToFile(foodInfo[0] + ",," + foodInfo[1] + ",," + foodInfo[2] + ",," + foodInfo[3] +
                            ",," + foodInfo[4] + ",," + foodInfo[5], DataParser.FOOD_FILE);
                }
                catch (IOException e) {
                    userInput.printMessage("An error has occurred and the food could not be saved at this time." +
                            "  Please try again later");
                }
            }
            else {
                userInput.printMessage("Error: command arguments are incorrect, please verify that all" +
                        " arguments are of the proper data type");
                return false;
            }
            return true;
        }
        else {
            if (testIsDouble(foodInfo[1])) {

                try {
                    foodController.runFoodCreation(new ArrayList<>(Arrays.asList(foodInfo)));
                    DataParser.writeToFile(foodInfo[0] + ",," + foodInfo[1]+
                            ",," + foodInfo[2], DataParser.FOOD_FILE);
                }
                catch (IOException e) {
                    userInput.printMessage("An error has occurred and the food could not be saved at this time." +
                            "  Please try again later");
                }

            }
            else {
                userInput.printMessage("Error: command arguments are incorrect, please verify that all" +
                        " arguments are of the proper data type");
                return false;
            }
            return true;
        }
    }

    /**
     *
     * @param testString the string to be tested
     * @return whether the string can be parsed to a double
     */
    public static boolean testIsDouble(String testString) {
        try {
            Double.parseDouble(testString);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}