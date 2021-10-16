package usecases;
import entities.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * With a list of recipe creates recipe objects using the entities.Recipe class recipe constructors
 */

public class RecipeHandler {

    private ArrayList<ArrayList<String>> recipeList;
    private final ArrayList<Recipe> recipeHandlerRecipeList;
    private ArrayList<Recipe> RecipeHandlerRecipeList;

    //HashMap<String, ArrayList<Object>> ingredient, String instructions

    //    /**
//     * Creates a Recipe object from the given data:
//     *
//     * @param ListOfRecipes is a list of recipes given in the csv file in the form of an ArrayList
//     * @throws Exception when no recipe is available
//     * @returns ArrayList of Recipe returned is the recommended recipe to the user.
//     */
    public RecipeHandler() {
        this.recipeList = new ArrayList<>();
        this.recipeHandlerRecipeList = new ArrayList<>();
    }

    public void initializeRecipe(ArrayList<ArrayList<String>> ListOfRecipes) {
        // Looping through every Recipe on the list.
        for (ArrayList<String> currentRecipe : ListOfRecipes) {

            // Creating a new Recipe object using the constructor in Recipe entity class
            HashMap<String, ArrayList<String>> ingredientsDict = new HashMap<>();
            for (int j = 1; j < currentRecipe.size() - 2; j = j + 3) {
                ArrayList<String> lst = new ArrayList<>();
                lst.add(currentRecipe.get(j + 1));
                lst.add(currentRecipe.get(j + 2));
                ingredientsDict.put(currentRecipe.get(j), lst);
            }

            Recipe newFood = new Recipe(currentRecipe.get(0), ingredientsDict,
                    currentRecipe.get(currentRecipe.size() - 1));

            // Adding the new Recipe created into the RecipeHandlerRecipeList (The saved Recipe inside RecipeHandler)
            recipeHandlerRecipeList.add(newFood);
        }
    }

        public ArrayList<Recipe> recommendRecipe (){
            //TODO: Create a method that recommends recipe considering
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

            for (Recipe currentRecipe : RecipeHandlerRecipeList) {
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

        public ArrayList<Recipe> getAllRecipes () {
            return this.RecipeHandlerRecipeList;

        }
        public Recipe findRecipe(String foodName){
            for (Recipe currentRecipe : getAllRecipes()) {
                if (currentRecipe.getRecipeName().equals(foodName)) {
                    return currentRecipe;
                }
            }
            return null;
        }
    }
