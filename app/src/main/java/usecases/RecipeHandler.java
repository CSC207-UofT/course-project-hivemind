package usecases;
import entities.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * With a list of recipe creates recipe objects using the entities.Recipe class recipe constructors
 */

public class RecipeHandler {

    private final ArrayList<Recipe> recipeHandlerRecipeList;

    //HashMap<String, ArrayList<Object>> ingredient, String instructions

    public RecipeHandler() {
        this.recipeHandlerRecipeList = new ArrayList<>();
    }


    public int getRecipes(){
        return recipeHandlerRecipeList.size();
    }

    /**
     * Initialize recipe objects for each ArrayList in the overall ArrayList
     *
     * @param ListOfRecipes An arraylist of all arraylist of strings that can be used to create recipe objects
     */
    public void initializeRecipe(ArrayList<ArrayList<String>> ListOfRecipes) {
        // Looping through every Recipe on the list.
        for (ArrayList<String> currentRecipe : ListOfRecipes) {

            // Creating a new Recipe object using the constructor in Recipe entity class
            HashMap<String, ArrayList<String>> ingredientsDict = arrayListToDictHelper(currentRecipe);

            Recipe newFood = new Recipe(currentRecipe.get(0), ingredientsDict,
                    currentRecipe.get(currentRecipe.size() - 1));

            // Adding the new Recipe created into the RecipeHandlerRecipeList (The saved Recipe inside RecipeHandler)
            recipeHandlerRecipeList.add(newFood);
        }
    }

    /**
     * Helper to turn an Arraylist of ingredients into an appropriate hashmap of ingredient to [quantity, measurement]
     *
     * @param listIngr Arraylist of strings used to construct the hashmap
     * @return a hashmap of appropriate structure for recipe object creation
     */
    public HashMap<String, ArrayList<String>> arrayListToDictHelper(ArrayList<String> listIngr) {
        HashMap<String, ArrayList<String>> ingredientsDict = new HashMap<>();
        for (int j = 1; j < listIngr.size() - 2; j = j + 3) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add(listIngr.get(j + 1));
            lst.add(listIngr.get(j + 2));
            ingredientsDict.put(listIngr.get(j), lst);
        }
        return ingredientsDict;
    }

    /**
     * Find appropriate recipes according to the inventory using an algorithm considering:
     * 1. Availability of ingredients (This considers Expiry dates and how many ingredients we use from the fridge)
     * 2. Give a score to each of these Recipes
     * 3. Return the number of recipes that the user wants, returning from best score to worst score recipe.
     * Percentage of food
     * 4. Food scores are tracked by:
     *  1. Find all recipes using the specific ingredient.
     *  2. Find the score of each recipe by taking the total number of ingredients that overlap/total number of ingredient
     *  3. Return the Recipe with the highest score.
     * @return An arraylist of sorted recipes
     */
    public ArrayList<Recipe> recommendRecipe() {
        //: Create a method that recommends recipe considering
        // 1. Availability of ingredients (This considers Expiry dates and how many ingredients we use from the fridge)
        // 2. Give a score to each of these Recipes
        // 3. Return the number of recipes that the user wants, returning from best score to worst score recipe.
        // Percentage of food
        // 4. Food scores are tracked by:
        //      1. Find all recipes using the specific ingredient.
        //      2. Find the score of each recipe by taking the total number of ingredients that overlap/total number of ingredient
        //      3. Return the Recipe with the highest score.
        int currentRecipeScore = 0;
        HashMap<Integer, ArrayList<String>> RankTracker = new HashMap<>();

        ArrayList<Recipe> RecommendedRecipe = new ArrayList<>();

        for (Recipe currentRecipe : recipeHandlerRecipeList) {
            for (String currentIngredient : currentRecipe.getIngredients().keySet()) {
                if (FoodHandler.getStoreFoodList().contains(currentIngredient)) {
                    currentRecipeScore += 1;
                }


                int currentRecipeWeightedScore = currentRecipeScore / (currentRecipe.getIngredients().keySet().size());
                if (RankTracker.containsKey(currentRecipeWeightedScore)) {
                    RankTracker.get(currentRecipeWeightedScore).add(currentRecipe.getRecipeName());
                }
                ArrayList<String> value = new ArrayList<>();
                value.add(currentRecipe.getRecipeName());
                RankTracker.put(currentRecipeWeightedScore, value);
            }
            TreeSet<Integer> sortedRanks = new TreeSet<>(RankTracker.keySet());
            ArrayList<String> BestRecipeNames = RankTracker.get(sortedRanks.first());
            Recipe BestRecipe = findRecipe(BestRecipeNames.get(0));
            RecommendedRecipe.add(BestRecipe);
        }
        return RecommendedRecipe;
    }



    /**
     * A helper to get all recipes stored in this handler.
     *
     * @return An arraylist of all recipes to be returned
     */
    public ArrayList<Recipe> getAllRecipes() {
        return this.recipeHandlerRecipeList;

    }

    /**
     * Find a particular recipe stored in this handler
     *
     * @param foodName the name of the food that is needed
     * @return the recipe needed, if not found, returns null
     */
    public Recipe findRecipe(String foodName) {

        for (Recipe currentRecipe : getAllRecipes()) {
            if (currentRecipe.getRecipeName().equals(foodName)) {
                return currentRecipe;
            }

        }
        return null;
    }

    /**
     * Adding one recipe to this handler
     *
     * @param recipeName String of the name of the recipe
     * @param ingredientList List of the ingredients in format: ing1 quantity1 measurement1 ing2 quantity2 measurement2...
     * @param instructions string of the instruction
     * @return true if recipe was successfully added, false if it was not successfully added.
     */
    public boolean addOneRecipe(String recipeName, ArrayList<String> ingredientList, String instructions) {
        try {
            HashMap<String, ArrayList<String>> map = arrayListToDictHelper(ingredientList);
            Recipe newFood = new Recipe(recipeName, map, instructions);
            // Adding the new Recipe created into the RecipeHandlerRecipeList (The saved Recipe inside RecipeHandler)
            recipeHandlerRecipeList.add(newFood);
            return true;
        } catch(Exception IndexOutOfBoundsException) {
            return false;
        }
    }

}

