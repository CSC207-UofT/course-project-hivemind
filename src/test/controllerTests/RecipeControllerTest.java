package controllerTests;

import org.junit.After;
import org.junit.Before;
import controllers.RecipeController;
import org.junit.Test;

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
    public void test_addRecipe_simple(){
        assertTrue(c.addRecipe("test", "sugar 1 unit", "cook"));
    }

}
