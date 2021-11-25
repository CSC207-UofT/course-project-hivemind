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
        RecipeHandler h = c.handler;
        HashMap<String, ArrayList<String>> z = new HashMap<>();
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("1");
        s1.add("unit");
        z.put("sugar", s1);
        Recipe r = new Recipe("test", z, "cook");
        assertEquals(r.toString(), h.findRecipe("test").toString());
    }
}
