package usecases;
import entities.Recipe;

import java.util.*;

/**
 * With a list of recipe creates recipe objects using the entities.Recipe class recipe constructors
 */

public class RecipeHandler {

    private final ArrayList<Recipe> recipeHandlerRecipeList;

    //HashMap<String, ArrayList<Object>> ingredient, String instructions

    public RecipeHandler() {
        this.recipeHandlerRecipeList = new ArrayList<>();
    }

    /**
     * A method that gets the number of recipes in the recipeHandlerList
     * @return The size of the recipeHandlerList: the list of all recipes in the csv
     */
    public int getRecipeSize(){
        return recipeHandlerRecipeList.size();
    }

    /**
     * Initialize recipe objects for each ArrayList in the overall ArrayList
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
    // TODO: split this up maybe, might be good to write a new class for this
    public ArrayList<String> recommendRecipe(int rank) {

        // create a hashmap called rankTracker that keeps track of all the recipes and their score
        HashMap<Integer, ArrayList<Recipe>> RankTracker = new HashMap<>();

        // create a list that you will later return. This list will be needed to store all the recommended recipe, then
        // later sliced.
        ArrayList<Recipe> RecommendedRecipe = new ArrayList<>();

        // Iterate through each of the recipe items in recipeHandler Recipe List, which is a list of every single recipe
        // in the database.
        for (Recipe currentRecipe : recipeHandlerRecipeList) {
            // sets the current recipe score to 0 which will be used to score the availability of each food recipe
            int recipeScore = 0;
            // for each of the ingredient, we iterate and check if it's in the fridge
            for (String currentIngredient : getIngredients(currentRecipe)) {
                if (recipeDoesContain(currentIngredient)) {
                    // add 1 to the score if the ingredient is in the inventory.
                    recipeScore += 1;
                }
            }
            // weigh the score by finding the percentage of the item it has. Convert it to integer by multiplying a 100.
            int currentRecipeWeightedScore =
                    (int) (((double)recipeScore / (currentRecipe.getIngredients().keySet().size())) * 100);

            // create an int representation of the score and make it the key of a hashmap
            if (RankTracker.containsKey(currentRecipeWeightedScore)) {
                RankTracker.get(currentRecipeWeightedScore).add(currentRecipe);
            }

            // add an arraylist of the recipe as a value for the specific key
            else {
                ArrayList<Recipe> currentRecipeArrayList = new ArrayList<>();
                currentRecipeArrayList.add(currentRecipe);
                RankTracker.put(currentRecipeWeightedScore, currentRecipeArrayList);
            }
        }
        // make a stack our of the rank tracker, such that we get the best rated recipe on the top
        Stack<Recipe> rankTrackerStack = makeStack(RankTracker);
        for (int i = 0; i < rank; i++) {
            // pop rank number of times to get rank number of items.
            RecommendedRecipe.add(rankTrackerStack.pop());
        }
        ArrayList<String> out = new ArrayList<>();
        for (Recipe recipe : RecommendedRecipe) {
            out.add(recipe.toString());
        }

        return out;
    }

    /**
     * A helper method for recommend recipe that creates a stack out of the given arrayList of recipes
     * @param RankTracker a Hashmap of int ratings as its key, and an arrayList of recipe for that score as its value.
     * @return A stack of recipes with the most desired recipe at the top of the stack
     */
    private Stack<Recipe> makeStack(HashMap<Integer, ArrayList<Recipe>> RankTracker) {
        TreeSet<Integer> sortedRanks = new TreeSet<>(RankTracker.keySet());
        Stack<Recipe> recipeStack = new Stack<>();
        for (int score: sortedRanks) {
            ArrayList<Recipe> currentRecipes = RankTracker.get(score);
            for (Recipe currentRecipe:currentRecipes){
                recipeStack.push(currentRecipe);
            }
        }
        return recipeStack;
    }

    /**
     * A helper method that gets the set of ingredients from a given recipe
     * @param currentRecipe A recipe from the csv
     * @return A set of strings containing all the recipes the food needs.
     */
    private Set<String> getIngredients(Recipe currentRecipe) {
        return currentRecipe.getIngredients().keySet();
    }

    /**
     * A helper method that checks whether a recipe contains a specific ingredient
     * @param currentIngredient takes in the current ingredient checked to see if it is contained by any recipes
     * @return boolean to indicate the presence of the particular recipe.
     */
    private boolean recipeDoesContain(String currentIngredient) {
        ArrayList<String> out = new ArrayList<>();

        for (String s: FoodHandler.getStoreFoodListNameOnly()){
            out.add(s.toLowerCase(Locale.ROOT));
        }

        return out.contains(currentIngredient.toLowerCase(Locale.ROOT));
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

