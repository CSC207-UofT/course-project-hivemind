package controllerTests;

import org.junit.After;
import org.junit.Before;
import controllers.RecipeController;
import org.junit.Test;
import usecases.RecipeHandler;

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
        RecipeHandler h = c.handler;

    }
}
