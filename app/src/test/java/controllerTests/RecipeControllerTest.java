package controllerTests;

import entities.Recipe;
import org.junit.After;
import org.junit.Before;
import controllers.RecipeController;
import org.junit.Test;
import usecases.RecipeHandler;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecipeControllerTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    RecipeController c = new RecipeController();

    @Test
    public void testAddRecipeSimple(){
        assertTrue(c.addRecipe("test", "sugar 1 unit", "cook"));
    }

    @Test(timeout=1000)
    public void testAddRecipeInHandler(){
        assertTrue(c.addRecipe("test", "sugar 1 unit", "cook"));
        assertEquals("Name: test Ingredients: {sugar=[1, unit]} Instructions: cook", c.handler.findRecipe("test").toString());
        assertTrue(c.addRecipe("test2", "sugar 1 unit mint 1 unit grass 1 unit", "cook"));
        assertEquals("Name: test2 Ingredients: {mint=[1, unit], grass=[1, unit], sugar=[1, unit]} Instructions: cook", c.handler.findRecipe("test2").toString());

    }
}
