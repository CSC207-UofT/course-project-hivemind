package adapters;
import android.content.Context;
import controllers.FoodController;
import controllers.RecipeController;
import parsers.AndroidDataParser;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Adapter {

    private final FoodController foodController = new FoodController();
    private final RecipeController recipeController = new RecipeController();
    private final AndroidDataParser adp;

    public Adapter(Context context) {
        adp = new AndroidDataParser(context);
    }

    // FOOD ADAPTER
    /**
     * Loads all the food from the android APK and creates a presentable food data
     *  output: [["Potato", "2 lbs"], ["Potato", "2 lbs", "Expiry Date: 3/12/2021"]]
     */
    public void loadFoods(){
        // Retaining foodData from the database using Android Data Parser
        ArrayList<String> foodData = adp.readFoodFile();
        foodController.loadFoodFromList(foodData);
    }


    /**
     * Returns the foodList which is a list of foods.
     * @return List of foods, where each food is of 3 strings, name, amount and units ,and if the food is perishable
     * add expiry date.
     */
    public List<List<String>> getAllFoods(){
        List<List<String>> presentableFoodList = new ArrayList<>();
        for(List<String> food: foodController.foodList){
            List<String> presentableFood;
            // below means that the food must be non-perishable as it does not contain expiry date. food.size() < 4
            if(food.size() < 4){
                presentableFood = new ArrayList<>(Arrays.asList(food.get(0), food.get(1) + " " + food.get(2)));
            }
            else{
                presentableFood = new ArrayList<>(Arrays.asList(food.get(0),
                        food.get(1) + " " + food.get(2),
                        "Expiry date: " + food.get(5) + "/" + food.get(4) + "/" + food.get(3)));
            }
            presentableFoodList.add(presentableFood);

        }
        return presentableFoodList;
    }

    /**
     * Creates non-perishable food and return the created food in strings, adapted to android gui.
     * @param name a string of the name of the food
     * @param amount a string of the number of food
     * @param unit a string of the unit for the amount of food
     * @return createdFood list of strings of the food added to the data, returns in the format of [sugar, 1, jar]
     * @throws IOException Exception thrown when food could not be added to the file.
     */
    public List<String> createFood(String name, String amount, String unit) throws IOException{
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit));
        foodController.runFoodCreation(food);
        adp.writeFile(food.get(0) + ",," + food.get(1) +
                ",," + food.get(2), AndroidDataParser.FOOD_FILE);
        return new ArrayList<>(Arrays.asList(name, amount + ' ' + unit));
    }


    /**
     * Creates non-perishable food and return the created food in strings, adapted to android gui.
     * @param name the string of the name of the perishable food being added
     * @param amount the string of the amount of the perishable food you are adding
     * @param unit the string of the unit for the amount of food
     * @param day the string of the day of expiry date
     * @param month the string of the month of the expiry date
     * @param year the string of the yea of the expiry date
     * @throws IOException throws an exception when the food cannot be added
     */
    public void createFood(String name, String amount, String unit, String year,
                                   String month, String day) throws IOException{
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit, year, month, day));
        foodController.runFoodCreation(food);
        adp.writeFile(food.get(0) + ",," + food.get(1) + ",," + food.get(2) + ",," + food.get(3) +
                ",," + food.get(4) + ",," + food.get(5), AndroidDataParser.FOOD_FILE);

    }

    /**
     * Returns the perishable foods from the foodList
     * @return foodList the list of perishable foods.
     */
    public List<List<String>> showPerishables(){
        List<String> expiredFoodList = foodController.checkPerishables();
        List<List<String>> presentableExpiredFoods = new ArrayList<>();
        for(String food : expiredFoodList){
            String[] foodSplit = food.split(" ");
            String[] foodNameSplit = foodSplit[0].split(":");
            String foodName = foodNameSplit[0];
            String foodAmount = foodSplit[1];
            String[] foodUnitSplit = foodSplit[2].split(",");
            String foodUnit = foodUnitSplit[0];
            String[] foodExpirySplit = foodSplit[5].split(",");
            String[] foodExpirySplitByNum = foodExpirySplit[0].split("-");
            String foodAmountAndUnit = foodAmount + " " + foodUnit;
            String foodExpiryDate = "Expiry date: " + foodExpirySplitByNum[2] + "/" + foodExpirySplitByNum[1] + "/" + foodExpirySplitByNum[0];
            List<String> presentableExpiredFood = new ArrayList<>(Arrays.asList(foodName, foodAmountAndUnit, foodExpiryDate));
            System.out.println(foodAmountAndUnit);
            System.out.println(foodName);
            System.out.println(foodExpiryDate);
            presentableExpiredFoods.add(presentableExpiredFood);
        }
        return presentableExpiredFoods;

    }

    /**
     * A method that deletes the food from the data.
     * @param foodIndexToDelete An Integer value for index of the food to be deleted
     */
    public void showDeletedFood(Integer foodIndexToDelete) {
        try{
            foodController.deleteFoodAndroid(foodIndexToDelete);
            adp.deleteRowFromFoodFile(foodIndexToDelete);
        }
        // Exception when the food is not deleted for reasons potentially related to input.
        catch (Exception e){
            System.out.println("An error occurred, did not successfully delete food. " +
                    "Please verify that all arguments are of the proper data type and format");
        }
    }

    // RECIPE ADAPTER

    /**
     * The method to load all the recipes into the recipeRawArray
     */
    public void loadRecipes(){
        ArrayList<String> recipeData = adp.readRecipeFile();
        recipeController.initialLoad(recipeData);
    }

    /**
     * Method to help create a recipe, from string inputs
     * ingredients input: potato 1bs 1 potato lbs 2
     * @param name string of the name of the recipe
     * @param ingredients string of the required ingredient amount and unit for the recipe
     * @param steps string for the instructions for cooking that recipe.
     * @throws IOException throws the exception when recipe fails to be created
     */
    public void createRecipe(String name, String ingredients, String steps) throws IOException{
        String[] ingrSplit = ingredients.split(" ");
        List<String> a1 = Arrays.asList(ingrSplit);
        ArrayList<String> a2 = new ArrayList<>(a1);

        int j = 1;
        while (j < a2.size() - 1) {
            try {
                double res = Double.parseDouble(a2.get(j));
            } catch (Exception e) {
                throw new IOException();
            }
            j+=3;
        }

        recipeController.addRecipe(name, ingredients, steps);
        String presentableIngredients = "";
        int i = 0;
        StringBuilder ingredientsBuilder = new StringBuilder(presentableIngredients);
        while(i < a2.size() - 1){
            ingredientsBuilder.append(a2.get(i)).append(" ");
            ingredientsBuilder.append(a2.get(i + 1)).append(" ");
            ingredientsBuilder.append(a2.get(i + 2)).append(". ");
            i += 3;
        }
        String s = ingredients.replace(" ", ",,");
        //Adding the new Recipe to the database, in the format of name,,ingredients,,steps, where ingredients are in the
        // format of name,,amount,,unit,,(expiry date = [day,,month,,year] if applicable)
        adp.writeFile(name + ",," + s + ",," + steps, AndroidDataParser.RECIPE_FILE);
    }

    /**
     * A method to return a number of recommended recipes depending on the inputted amount
     * @param amount the int of the number of recipes the user wants recommended to them
     * @return returns a list of recipes(in List<String> representation) in the format of:
     * [[mashed potato: potato 1 unit, butter 1 unit, 1. boil the potatoes, 2: mash the potatoes and add butter.]]
     */
    public List<List<String>> recommendRecipes(int amount){
        ArrayList<String> recipes = recipeController.recommendRecipe(amount);
        List<List<String>> presentableRecipeList = new ArrayList<>();
        for(String unpolishedRecipe : recipes){
            String[] separated = unpolishedRecipe.split(" Ingredients:");
            String[] nameArray = separated[0].split(": ");
            String name = nameArray[1];
            for(List<String> recipe : recipeController.recipeRawArray){
                if(Objects.equals(recipe.get(0), name)){
                    helper(presentableRecipeList, recipe);
                }
            }

        }
        return presentableRecipeList;

    }

    /**
     * The helper method to parse through the recipe and create a recipe list
     * @param presentableRecipeList the arraylist that the recipe will go into
     * @param recipe the recipe that is to be added to the presntable recipe list.
     */
    private void helper(List<List<String>> presentableRecipeList, List<String> recipe) {
        StringBuilder ingredients = new StringBuilder();
        int i = 1;
        // for each of the ingredient in the recipe, adding a " " at the end of it.
        while(i < recipe.size() - 1){
            ingredients.append(recipe.get(i)).append(" ");
            i++;
        }
        List<String> presentableRecipe = new ArrayList<>(Arrays.asList(recipe.get(0),
                ingredients.toString(), recipe.get(recipe.size() - 1)));
        presentableRecipeList.add(presentableRecipe);
    }

    // check variable names
    public boolean expiredFoodAdded(String year, String month, String day){
        int day1 = Integer.parseInt(day);
        int month1 = Integer.parseInt(month);
        int year1 = Integer.parseInt(year);
        LocalDate expiryDate = LocalDate.of(year1, month1, day1);

        LocalDate today = LocalDate.now();
        return today.isAfter(expiryDate);
    }
}
