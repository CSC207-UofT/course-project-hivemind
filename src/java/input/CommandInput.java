package input;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*;


import alerts.AlertExpiryStatus;
import controllers.FoodController;
import controllers.RecipeController;
import parsers.DataParser;

public class CommandInput {
    public static final FoodController foodController = new FoodController();
    private static final RecipeController recipeController = new RecipeController();
    public static boolean exitProgram = true;

    public static void main (String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String lastCommand;

        //enter preload here
        try {
            ArrayList<String> foodData = DataParser.readFile(DataParser.FOOD_FILE);
            foodController.loadFoodFromList(foodData);
            ArrayList<String> recipeData = DataParser.readFile(DataParser.RECIPE_FILE);
            recipeController.initialLoad(recipeData);
            userHelper();
            AlertExpiryStatus.alertExpiredFoods();
            // create a thread to be run in parallel
            Thread thread1 = new Thread(new AlertExpiryStatus());
            thread1.start();


        }
        catch (Exception e) {
            System.out.println("Unfortunately, an error has occurred." +
                    "  Please verify that all data files are in their correct spots and relaunch the program");
            System.out.println(e.getMessage());
        }

        while (exitProgram) {
            System.out.print("> ");
            lastCommand = inputScanner.nextLine();
            parseInput(lastCommand);
        }
        inputScanner.close();
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
                            userHelper();
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
                                System.out.println(str);
                            }
                            System.out.println("-----------------------");
                            break;
                        default:
                            System.out.println("Error, argument " + splitInput[1] + " not recognized");
                            break;
                    }
                    break;
                case "recipe":
                    if (splitInput[1].equals("search")) {
                        try{
                            int amount = Integer.parseInt(splitInput[2]);
                            for(int i = 0; i < recipeController.recommendRecipe(amount).size(); i++){
                                System.out.println(recipeController.recommendRecipe(amount).get(i));
                                System.out.println(" ");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("error in input: no number inputted!");
                        }
                        catch (NumberFormatException e){
                            System.out.println("error in type, try using a number!");
                        }


                    }

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

    /**
     *Helper to tell user how to use the commands
     */
    private static void userHelper(){
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
     * A method to delete a specified Food Object from the inventor and fooddata
     */
    private static void deleteFoodHelper() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here are all your foods in your inventory:");
        System.out.println("-----------------------");
        for (String str : foodController.allFoodToString()) {
            System.out.println(str);
        }
        System.out.println("-----------------------");
        System.out.println("Enter Food Name To Delete:");
        System.out.print("> ");
        String foodName = scanner.nextLine();
        List<Object[]> foodList = foodController.getSpecifiedFoodList(foodName);

        try {
            if (foodList.size() == 0){
                printFoodNotInSystem();
            }
            else {
                System.out.println("Please input the number corresponding to which food item you wish to delete:");
                printFoodInList(foodList);
                System.out.print("> ");
                String foodDelete = scanner.nextLine();
                int foodIndexToDelete = Integer.parseInt(foodDelete) - 1;
                foodController.deleteFood(foodList.get(foodIndexToDelete)[0]);
                DataParser.deleteRowFromFoodFile((int)foodList.get(foodIndexToDelete)[1]);
                System.out.println("The following food item was successfully deleted from the system:");
                System.out.println(foodController.printFood(foodIndexToDelete + 1, foodList.get(foodIndexToDelete)[0]));
            }
        }
        catch (Exception e){
            System.out.println("An error occurred, did not successfully delete food. " +
                    "Please verify that all arguments are of the proper data type and format");
        }
    }

    /**
     * prints the foods in the given ArrayList of Object Arrays. Each object array contains a food object at index 0,
     * and the food's index in fooddata at index 1
     * @param foodList an ArrayList of Object Arrays. Each object array contains a food object at index 0,
     * and the food's index in fooddata at index 1
     */
    private static void printFoodInList(List<Object[]> foodList) {
        int index = 1;
        for (Object[] foods : foodList) {
            Object food = foods[0];
            System.out.println(foodController.printFood(index, food));
            index++;
        }
    }

    /**
     * prints a message when a food object is not present in the system
     */
    private static void printFoodNotInSystem() {
        System.out.println("This food item is not currently stored in the system. " +
                "Please verify the spelling and existence of this food item");
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
            DataParser.writeToFile(recipeName + ",," + s + ",," + instructions, DataParser.RECIPE_FILE);
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

    public static void foodHelper() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Food Name:");
        System.out.print("> ");
        String foodName = scan.nextLine();

        System.out.println("Enter Quantity of Food (number)");
        System.out.print("> ");
        String foodQuant = scan.nextLine();
        if (!testIsDouble(foodQuant)) {
            System.out.println("Please make sure that the input is a number, and try again.");
            return;
        }

        System.out.println("Enter Unit of Measurement:");
        System.out.print("> ");
        String foodUnit = scan.nextLine();

        System.out.println("Enter the Expiry Date using format" +
                " YYYY/MM/DD (leave this blank if this food is non-perishable");
        System.out.print("> ");
        String foodExpiry = scan.nextLine();

        String[] foodInfo;

        if (!foodExpiry.equals("")) {
            try{
                String[] expiryInfo = foodExpiry.split("/");
                foodInfo = new String[]{foodName, foodQuant, foodUnit, expiryInfo[0], expiryInfo[1], expiryInfo[2]};
            }
            catch (Exception e){
                System.out.println("Error in loading food, please make sure that the expiry date is either" +
                        "blank, or follows the format \"YYYY/MM/DD\"");
                return;
            }
        }
        else {
            foodInfo = new String[]{foodName, foodQuant, foodUnit};
        }

        if (handleFoodHelper(foodInfo)) {
            System.out.println("Food added successfully!");
        }
    }

    public static boolean handleFoodHelper(String[] foodInfo) {
        // helper function for foodHelper
        if (foodInfo.length > 3) {
            if (testIsDouble(foodInfo[1]) && testIsDouble(foodInfo[3]) && testIsDouble(foodInfo[5])
                    && testIsDouble(foodInfo[5])) {

                try {
                    foodController.runFoodCreation(new ArrayList<>(Arrays.asList(foodInfo)));
                    DataParser.writeToFile(foodInfo[0] + ",," + foodInfo[1] + ",," + foodInfo[2] + ",," + foodInfo[3] +
                            ",," + foodInfo[4] + ",," + foodInfo[5], DataParser.FOOD_FILE);
                }
                catch (IOException e) {
                    System.out.println("An error has occurred and the food could not be saved at this time." +
                            "  Please try again later");
                }
            }
            else {
                System.out.println("Error: command arguments are incorrect, please verify that all" +
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
                    System.out.println("An error has occurred and the food could not be saved at this time." +
                            "  Please try again later");
                }

            }
            else {
                System.out.println("Error: command arguments are incorrect, please verify that all" +
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