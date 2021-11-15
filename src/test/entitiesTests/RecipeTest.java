package entitiesTests;

import entities.Recipe;

import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;

public class RecipeTest{


    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    Recipe recipe = getSingleIngredientRecipe("Sandwich", "Bread", "2", "slices", "Figure it out yourself!");

    // Testing a general case of method getRecipeName
    @Test(timeout=1000)
    public void test_getRecipeName(){
        assertEquals(recipe.getRecipeName(), "Sandwich");
    }

    // Testing a general case of method changeRecipeName
    @Test(timeout=1000)
    public void test_ChangeRecipeName(){
        recipe.changeRecipeName("Chicken Sandwich");
        assertEquals(recipe.getRecipeName(), "Chicken Sandwich");
    }

    // Testing a general case of method removeIngredient, where one ingredient is removed from the Recipe
    @Test(timeout=1000)
    public void test_removeIngredients(){
        assertEquals(recipe.getIngredients().size(), 1);
        recipe.removeIngredient("Bread");
        assertEquals(recipe.getIngredients().size(), 0);
    }

    // Testing a general case of method getIngredient
    @Test(timeout=1000)
    public void test_getIngredient(){
        HashMap<String, ArrayList<String>> snack = new HashMap<>();
        ArrayList<String> quantity= new ArrayList<>();
        quantity.add("2");
        quantity.add("slices");
        snack.put("Bread", quantity);
        assertEquals(recipe.getIngredients().keySet(), snack.keySet());
        assertEquals(recipe.getIngredients().get("Bread"), snack.get("Bread"));
    }

    // Testing a general case of method getInstructions
    @Test(timeout=1000)
    public void test_getInstructions(){
        assertEquals(recipe.getInstructions(), "Figure it out yourself!");
    }

    // Testing a general case of method changeInstructions
    @Test(timeout=1000)
    public void test_changeInstructions() {
        recipe.changeInstructions("It's a sandwich, why do you need instructions?!?!");
        assertEquals(recipe.getInstructions(), "It's a sandwich, why do you need instructions?!?!");
    }
    private Recipe getSingleIngredientRecipe(String recipeName, String ingredientName, String ingredientQuantity, String ingredientUnit, String instructions){
        HashMap<String, ArrayList<String>> ingredient = new HashMap<>();
        ArrayList<String> measurement = new ArrayList<>();
        measurement.add(ingredientQuantity);
        measurement.add(ingredientUnit);
        ingredient.put(ingredientName, measurement);
        return (new Recipe(recipeName, ingredient, instructions));
    }
}