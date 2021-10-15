package usecases;

import entities.Recipe;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * With a list of recipe creates recipe objects using the entities.Recipe class recipe constructors
 */

public class RecipeHandler {

    private ArrayList<ArrayList<String>> recipeList;
    private final ArrayList<Recipe> recipeHandlerRecipeList;

    //HashMap<String, ArrayList<Object>> ingredient, String instructions

//    /**
//     * Creates a Recipe object from the given data:
//     *
//     * @param ListOfRecipes is a list of recipes given in the csv file in the form of an ArrayList
//     * @throws Exception when no recipe is available
//     * @returns ArrayList of Recipe returned is the recommended recipe to the user.
//     */
    public RecipeHandler(){
        this.recipeList = new ArrayList<>();
        this.recipeHandlerRecipeList = new ArrayList<>();
    }

    public void initializeRecipe(ArrayList<ArrayList<String>> ListOfRecipes) {
        // Looping through every Recipe on the list.
        for (ArrayList<String> currentRecipe : ListOfRecipes) {

            // Creating a new Recipe object using the constructor in Recipe entity class
            HashMap<String, ArrayList<String>> ingredientsDict = new HashMap<>();
            for (int j = 1; j < currentRecipe.size() - 2; j = j + 3){
                ArrayList<String> lst = new ArrayList<>();
                lst.add(currentRecipe.get(j+1));
                lst.add(currentRecipe.get(j+2));
                ingredientsDict.put(currentRecipe.get(j), lst);
            }

            Recipe newFood = new Recipe(currentRecipe.get(0), ingredientsDict,
                    currentRecipe.get(currentRecipe.size() - 1));

            // Adding the new Recipe created into the RecipeHandlerRecipeList (The saved Recipe inside RecipeHandler)
            recipeHandlerRecipeList.add(newFood);
        }

    }

//    public ArrayList<Recipe> recommendRecipe(int rank) {
//        //TODO: Create a method that recommends recipe considering
//        // 1. Availability of ingredients (This considers Expiry dates and how many ingredients we use from the fridge)
//        // 2. Give a score to each of these Recipes
//        // 3. Return the number of recipes that the user wants, returning from best score to worst score recipe.
//        // Percentage of food
//        // 4. Food
//
//
//
//        return
//    }
}