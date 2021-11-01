package controllers;
import java.util.*;

import entities.Recipe;
import usecases.RecipeHandler;

public class RecipeController {
    protected ArrayList<ArrayList<String>> recipeRawArray;
    private final RecipeHandler handler;

    public RecipeController() {
        this.recipeRawArray = new ArrayList<>();
        this.handler = new RecipeHandler();
    }

    /**
     * Adding a recipe to the handler
     *
     * @param recipeName name of the recipe given
     * @param ingrString string of all ingredients
     * @param instruction string of instruction
     * @return true if the recipe was successfully added, false otherwise
     */
    public boolean addRecipe(String recipeName, String ingrString, String instruction){
        String[] ingrSplit = ingrString.split(" ");
        List<String> a1 = Arrays.asList(ingrSplit);
        ArrayList<String> a2 = new ArrayList<>(a1);
        return handler.addOneRecipe(recipeName, a2, instruction);
    }

    /**
     * Separates every string in the given ArrayList into separated strings, then puts all separated strings into
     * another array and calls the handler
     * @param csvLines an arraylist of strings which are words separated by ",,"
     */
    public void initialLoad(ArrayList<String> csvLines){
        for (String i : csvLines){
            // Separating csv lines into an array
            String[] separated = i.split(",,");
            List<String> a1;
            a1 = Arrays.asList(separated);
            ArrayList<String> a2 = new ArrayList<>(a1);
            this.recipeRawArray.add(a2);

        }
        handler.initializeRecipe(this.recipeRawArray);
    }

    /**
     * calls the handler's recommendRecipe function and returns with an ArrayList of recipes that are recommended
     *
     * @return an ArrayList of sorted recommended recipes.
     */
    public ArrayList<Recipe> recommendRecipe () {
        return handler.recommendRecipe();
    }
}
