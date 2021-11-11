package input;
import java.util.Arrays;
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

    /**
     * A method to delete a specified Food Object from the inventor and fooddata
     */
    private static void deleteFoodHelper() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Food Name:");
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
                printFood(foodIndexToDelete + 1, deletedFood);
            }
        }
        catch (Exception e){
            System.out.println("An error occurred, did not successfully delete food. " +
                    "Please verify that all arguments are of the proper data type and format");
        }
    }

    /**
     * prints the foods in the given ArrayList of Object Arrays. Each object array contains a food object matching the
     * name specified by parameter foodName at index 0, and the food's index in fooddata at index 1
     * @param foodList an ArrayList of Object Arrays. Each object array contains a food object at index 0,
     * and the food's index in fooddata at index 1
     */
    private static void printFoodInList(ArrayList<Object[]> foodList) {
        int index = 1;
        for (Object[] foods : foodList) {
            Food food = (Food) foods[0];
            printFood(index, food);
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

    /**
     * Prints food objects
     * @param number corresponding to given food object
     * @param food a food object
     */
    private static void printFood(int number, Food food) {
        if (food instanceof PerishableFood) {
            String isExpired = "Not Expired";
            if (((PerishableFood) food).getExpiryStatus()){
                isExpired = "Expired";
            }
            System.out.println(number + ". Food Name: " + food.getName() + ", Quantity: " +
                    food.getQuantity() + ", Unit: " + food.getUnit() + ", Expiry Date: " +
                    ((PerishableFood) food).getExpiryDate() + ", Expiry Status: " + isExpired);
        }
        else {
            System.out.println(number + ". Food Name: " + food.getName() + ", Quantity: " +
                    food.getQuantity() + ", Unit: " + food.getUnit());
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