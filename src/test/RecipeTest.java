import entities.Food;
import entities.NonPerishableFood;
import entities.Recipe;

import org.junit.*;
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
    HashMap<Food, Object[]> ingredient = new HashMap<>();
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
        recipe.addIngredient(bread, 2.0f, "Slices");
        assertTrue(recipe.getIngredients().containsKey(bread));
        assertEquals(recipe.getIngredients().get(bread)[0], quantity[0]);
        assertEquals(recipe.getIngredients().get(bread)[1], quantity[1]);
    }

    // Testing a general case of method removeIngredient, where one ingredient is removed from the Recipe
    @Test(timeout=1000)
    public void test_removeIngredients(){
        recipe.addIngredient(bread, 2.0f, "Slices");
        assertEquals(recipe.getIngredients().size(), 1);
        recipe.removeIngredient(bread);
        assertEquals(recipe.getIngredients().size(), 0);
    }

    // Testing a general case of method getIngredient
    @Test(timeout=1000)
    public void test_getIngredient(){
        recipe.addIngredient(bread, 2.0f, "Slices");
        HashMap<Food, Object[]> snack = new HashMap<>();
        snack.put(bread, quantity);
        assertEquals(recipe.getIngredients().keySet(), snack.keySet());
        Assert.assertArrayEquals(recipe.getIngredients().get(bread), snack.get(bread));
    }

    // Testing a general case of method changeQuantity
    @Test(timeout=1000)
    public void test_changeQuantity(){
        recipe.addIngredient(bread, 2.0f, "Slices");
        recipe.changeQuantity(bread, 6.0f);
        assertEquals(recipe.getIngredients().get(bread)[0], 6.0f);
    }

    // Testing a general case of method changeUnit
    @Test(timeout=1000)
    public void test_changeUnit(){
        recipe.addIngredient(bread, 2.0f, "Slices");
        recipe.changeUnit(bread, "Cups");
        assertEquals(recipe.getIngredients().get(bread)[1], "Cups");
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
