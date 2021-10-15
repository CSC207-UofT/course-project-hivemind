package entitiesTests;

import entities.Food;
import entities.NonPerishableFood;
import entities.Recipe;

import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;

public class RecipeTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    Food bread = new NonPerishableFood("Bread", 3.0f, "Loafs");
    HashMap<String, ArrayList<Object>> ingredient = new HashMap<>();
    Recipe recipe = new Recipe("Sandwich", ingredient, "Figure it out yourself!");
    Object[] quantity = {2.0f, "Slices"};

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

    // Testing a general case of method addIngredient, where one ingredient is added to the Recipe
    @Test(timeout=1000)
    public void test_AddIngredient(){
        recipe.addIngredient("Bread", 2.0f, "Slices");
        assertTrue(recipe.getIngredients().containsKey("Bread"));
        assertEquals(recipe.getIngredients().get("Bread")[0], quantity[0]);
        assertEquals(recipe.getIngredients().get("Bread")[1], quantity[1]);
    }

    // Testing a general case of method removeIngredient, where one ingredient is removed from the Recipe
    @Test(timeout=1000)
    public void test_removeIngredients(){
        recipe.addIngredient("Bread", 2.0f, "Slices");
        assertEquals(recipe.getIngredients().size(), 1);
        recipe.removeIngredient("Bread");
        assertEquals(recipe.getIngredients().size(), 0);
    }

    // Testing a general case of method getIngredient
    @Test(timeout=1000)
    public void test_getIngredient(){
        recipe.addIngredient("Bread", 2.0f, "Slices");
        HashMap<String, Object[]> snack = new HashMap<>();
        snack.put("Bread", quantity);
        assertEquals(recipe.getIngredients().keySet(), snack.keySet());
        Assert.assertArrayEquals(recipe.getIngredients().get("Bread"), snack.get("Bread"));
    }

    // Testing a general case of method changeQuantity
    @Test(timeout=1000)
    public void test_changeQuantity(){
        recipe.addIngredient("Bread", 2.0f, "Slices");
        recipe.changeQuantity("Bread", 6.0f);
        assertEquals(recipe.getIngredients().get("Bread")[0], 6.0f);
    }

    // Testing a general case of method changeUnit
    @Test(timeout=1000)
    public void test_changeUnit(){
        recipe.addIngredient("Bread", 2.0f, "Slices");
        recipe.changeUnit("Bread", "Cups");
        assertEquals(recipe.getIngredients().get("Bread")[1], "Cups");
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
}