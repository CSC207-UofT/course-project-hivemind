package entities;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    private String name;
    private final HashMap<String, ArrayList<String>> ingredients;
    private String instructions;

    /**
     * Construct a Recipe with the given name, ingredients, and instructions
     * @param name of the Recipe
     * @param ingredients a hashmap with Keys to represent Strings of food items, and Values to represent an array
     *                    containing the quantity and unit of each food item. The value at index[0] of the array represents the
     *                    quantity needed. The value at index[1] represents the unit of measurement.
     * @param instructions on how to prepare the Recipe
     */
    public Recipe (String name, HashMap<String, ArrayList<String>> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    /**
     * Return the name of the Recipe
     * @return a string representation of the Recipe name
     */
    public String getRecipeName(){
        return this.name;
    }

    /**
     * Change the name of a Recipe
     * @param new_name represents the new Recipe name
     */
    public void changeRecipeName(String new_name) {
        this.name = new_name;
    }

    /**
     * Remove an ingredient from the Recipe
     * @param ingredient to be removed for the Recipe
     */
    public void removeIngredient(String ingredient){
        this.ingredients.remove(ingredient);
    }

    /**
     * Return the Recipe ingredients
     * @return the ingredients of the Recipe, represented in a HashMap. Keys of the HashMap represent String items,
     * and Values represent an array containing the quantity and unit of measurement for each food item. The array at
     * index[0] represents the quantity needed. The array at index[1] represents the unit of measurement.
     */
    public HashMap<String, ArrayList<String>> getIngredients(){
        return this.ingredients;
    }

    /**
     * Change the Recipe instructions
     * @param instructions represents the new Recipe instructions
     */
    public void changeInstructions(String instructions){
        this.instructions = instructions;
    }

    /**
     * Return the Recipe instructions
     * @return a string representation of the Recipe instructions
     */
    public String getInstructions(){
        return this.instructions;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Ingredients: " + this.ingredients + " Instructions: " + this.instructions;
    }
}
