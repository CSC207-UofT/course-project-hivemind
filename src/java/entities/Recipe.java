package entities;
import java.util.HashMap;

public class Recipe {
    private String name;
    private final HashMap<Food, Object[]> ingredients;
    private String instructions;

    /**
     * Construct a Recipe with the given name, ingredients, and instructions
     * @param name of the Recipe
     * @param ingredients a hashmap with Keys to represent Food items, and Values to represent an array containing the
     *                    quantity and unit of each food item. The value at index[0] of the array represents the
     *                    quantity needed. The value at index[1] represents the unit of measurement.
     * @param instructions on how to prepare the Recipe
     */
    public Recipe (String name, HashMap<Food, Object[]> ingredients, String instructions) {
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
     * Add an ingredient, with its respective quantity and unit of measurement to the Recipe
     * @param ingredient represents a new ingredient
     * @param quantity of the ingredient needed for the Recipe
     * @param unit of measurement for the given quantity
     */
    public void addIngredient(Food ingredient, Float quantity, String unit){
        if (!(this.ingredients.containsKey(ingredient))){
        Object[] ingredients = create_quantities(quantity, unit);
        this.ingredients.put(ingredient, ingredients);
        }
    }

    /**
     * Remove an ingredient from the Recipe
     * @param ingredient to be removed for the Recipe
     */
    public void removeIngredient(Food ingredient){
        this.ingredients.remove(ingredient);
    }

    /**
     * Return the Recipe ingredients
     * @return the ingredients of the Recipe, represented in a HashMap. Keys of the HashMap represent Food items,
     * and Values represent an array containing the quantity and unit of measurement for each food item. The array at
     * index[0] represents the quantity needed. The array at index[1] represents the unit of measurement.
     */
    public HashMap<Food, Object[]> getIngredients(){
        return this.ingredients;
    }

    /**
     * Change the quantity of a specified ingredient
     * @param ingredient represent the ingredient
     * @param quantity represents the new quantity of the respective ingredient
     */
    public void changeQuantity(Food ingredient, Float quantity){
        if (this.ingredients.containsKey(ingredient)){
            this.ingredients.get(ingredient)[0] = quantity;
        }
    }

    /**
     * Change the unit of measurement for a specified ingredient
     * @param ingredient represents the ingredient
     * @param unit represents the new unit of the respective ingredient
     */
    public void changeUnit(Food ingredient, String unit){
        if (this.ingredients.containsKey(ingredient)){
            this.ingredients.get(ingredient)[1] = unit;
        }
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

    /**
     * Return an Array containing a quantity and its respective unit of measurement.
     * @param quantity of an ingredient
     * @param unit of measurement for quantity
     * @return an Array containing a quantity (at index[0]) and its respective unit of measurement (at index[1]).
     */
    private Object[] create_quantities(Float quantity, String unit){
        Object[] quantities = new Object[2];
        quantities[0] = quantity;
        quantities[1] = unit;
        return quantities;
    }

}
