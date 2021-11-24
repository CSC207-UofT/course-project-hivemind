package entitiesTests;

import entities.Recipe;

import org.junit.*;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * Tests for methods in class Recipe
 */
public class RecipeTest{


    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    Recipe recipe1 = constructSingleIngredientRecipe("Sandwich", "Bread", "2",
            "slices", "Figure it out yourself!");
    Recipe recipe2 = constructSingleIngredientRecipe("Soup", "Broth", "10",
            "cups", "Boil and serve!");

    /**
     * Testing a general case of method getRecipeName
     */
    @Test(timeout=1000)
    public void testGetRecipeName(){
        assertEquals(recipe1.getRecipeName(), "Sandwich");
        assertEquals(recipe2.getRecipeName(), "Soup");
    }

    /**
     * Testing a general case of method changeRecipeName
     */
    @Test(timeout=1000)
    public void testChangeRecipeName(){
        recipe1.changeRecipeName("Chicken Sandwich");
        assertEquals(recipe1.getRecipeName(), "Chicken Sandwich");
    }

    /**
     * Testing a general case of method removeIngredient, where one ingredient is removed from the Recipe
     */
    @Test(timeout=1000)
    public void testRemoveIngredient(){
        assertEquals(recipe1.getIngredients().size(), 1);
        recipe1.removeIngredient("Bread");
        assertEquals(recipe1.getIngredients().size(), 0);
    }

    /**
     * Testing a general case of method getIngredient
     */
    @Test(timeout=1000)
    public void testGetIngredients(){
        HashMap<String, ArrayList<String>> snack = new HashMap<>();
        ArrayList<String> quantity= new ArrayList<>();
        quantity.add("2");
        quantity.add("slices");
        snack.put("Bread", quantity);
        assertEquals(recipe1.getIngredients().keySet(), snack.keySet());
        assertEquals(recipe1.getIngredients().get("Bread"), snack.get("Bread"));
    }

    /**
     * Testing a general case of method getInstructions
     */
    @Test(timeout=1000)
    public void testGetInstructions(){
        assertEquals(recipe1.getInstructions(), "Figure it out yourself!");
    }

    /**
     * Testing a general case of method changeInstructions
     */
    @Test(timeout=1000)
    public void testChangeInstructions() {
        recipe1.changeInstructions("It's a sandwich, why do you need instructions?!?!");
        assertEquals(recipe1.getInstructions(), "It's a sandwich, why do you need instructions?!?!");
    }

    /**
     * Helper method to construct a Recipe which contains a single ingredient
     * @param recipeName the name of the recipe
     * @param ingredientName the name of the ingredient
     * @param ingredientQuantity the numerical quantity the given ingredient
     * @param ingredientUnit the unit of measurement for hte given ingredient
     * @param instructions the recipe instructions
     * @return a recipe object with the given specifications
     */
    private Recipe constructSingleIngredientRecipe(String recipeName, String ingredientName, String ingredientQuantity,
                                                   String ingredientUnit, String instructions){
        HashMap<String, ArrayList<String>> ingredient = new HashMap<>();
        ArrayList<String> measurement = new ArrayList<>();
        measurement.add(ingredientQuantity);
        measurement.add(ingredientUnit);
        ingredient.put(ingredientName, measurement);
        return (new Recipe(recipeName, ingredient, instructions));
    }
}