package input;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*;
import controllers.FoodController;
import controllers.RecipeController;
import entities.Food;
import parsers.DataParser;

public class CommandInput {
    private static final FoodController foodController = new FoodController();
    private static final RecipeController recipeController = new RecipeController();
    private static boolean exitProgram = true;
    private static Calendar dateNow = Calendar.getInstance();

    public static void main (String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String lastCommand;

        //enter preload here
        try {
            ArrayList<String> foodData = DataParser.readFile(true);
            foodController.initialLoad(foodData);
            ArrayList<String> recipeData = DataParser.readFile(false);
            recipeController.initialLoad(recipeData);
            userHelper();
            alertExpiredFoods();

        }
        catch (Exception e) {
            System.out.println("Unfortunately, an error has occurred." +
                    "  Please verify that all data files are in their correct spots and relaunch the program");
            System.out.println(e.getMessage());
        }

        while (exitProgram) {
            Calendar newDate = Calendar.getInstance();
            if(newDate.get(Calendar.YEAR) > dateNow.get(Calendar.YEAR) || newDate.get(Calendar.MONTH) > dateNow.get(Calendar.MONTH) || newDate.get(Calendar.DAY_OF_MONTH) > dateNow.get(Calendar.DAY_OF_MONTH)){
                alertExpiredFoods();
                dateNow = Calendar.getInstance();
            }
            System.out.print("> ");
            lastCommand = inputScanner.nextLine();
            parseInput(lastCommand);
        }
        inputScanner.close();
        System.exit(0);

    }

    private static void alertExpiredFoods() {
        ArrayList<Food> expiredFoodsList = foodController.checkPerishables();
        if(expiredFoodsList.size() > 0){
            System.out.println("The following foods have expired: ");
            ArrayList<Object[]> parsedExpiredFoodsList = new ArrayList<>();
            for (Food food : expiredFoodsList){
                Object[] parsedFood = new Object[1];
                parsedFood[0] = food;
                parsedExpiredFoodsList.add(parsedFood);
            }
            printFoodInList(parsedExpiredFoodsList);
        }
        else{
            System.out.println("Your food is fresh and safe to consume.");
        }


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
                    switch (splitInput[1]) {
                        case "add":
                            foodHelper();
                            break;
                        case "delete":
                            deleteFoodHelper();
                            break;
                        case "check":
                            alertExpiredFoods();
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
        ArrayList<Object[]> foodList = foodController.getSpecifiedFoodList(foodName);

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
                Food deletedFood = foodController.deleteFood((Food) foodList.get(foodIndexToDelete)[0]);
                DataParser.deleteRowFromFoodFile((int)foodList.get(foodIndexToDelete)[1]);
                System.out.println("The following food item was successfully deleted from the system:");
                System.out.println(foodController.printFood(foodIndexToDelete + 1, deletedFood));
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
    private static void printFoodInList(ArrayList<Object[]> foodList) {
        int index = 1;
        for (Object[] foods : foodList) {
            Food food = (Food) foods[0];
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

    public static void foodHelper() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Food Name:");
        System.out.print("> ");
        String foodName = scan.nextLine();

        System.out.println("Enter Quantity of Food (number)");
        System.out.print("> ");
        String foodQuant = scan.nextLine();

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

        try {
            handleFoodHelper(foodInfo);
            System.out.println("Food successfully added!");
        } catch (Exception e){
            System.out.println("Error in loading food");
        }
    }

    public static void handleFoodHelper(String[] foodInfo) {
        // helper function for foodHelper
        if (foodInfo.length > 3) {
            try {
                Double.parseDouble(foodInfo[1]);  // checks if all inputs are proper doubles for optional inputs
                Double.parseDouble(foodInfo[3]);
                Double.parseDouble(foodInfo[4]);
                Double.parseDouble(foodInfo[5]);

                foodController.runFoodCreation(new ArrayList<>(Arrays.asList(foodInfo)));

                DataParser.writeToFile(foodInfo[0] + ",," + foodInfo[1]+ ",," + foodInfo[2]+ ",,"+ foodInfo[3]+
                        ",,"+ foodInfo[4]+ ",,"+ foodInfo[5], true);
            } catch (Exception e) {
                System.out.println("Error: command arguments are incorrect, please verify that all" +
                        " arguments are of the proper data type");
            }
        }
        else {
            try {
                Double.parseDouble(foodInfo[1]);  // checks if all inputs are proper doubles

                foodController.runFoodCreation(new ArrayList<>(Arrays.asList(foodInfo)));

                DataParser.writeToFile(foodInfo[0] + ",," + foodInfo[1]+ ",," + foodInfo[2], true);
            } catch (Exception e) {
                System.out.println("Error: command arguments are incorrect, please verify that all" +
                        " arguments are of the proper data type");
            }
        }
    }
}