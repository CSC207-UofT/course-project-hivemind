package useCaseTests;

import controllers.FoodController;
import controllers.RecipeController;
import entities.Recipe;
import org.junit.*;
import parsers.DataParser;
import usecases.RecipeHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

public class RecipeHandlerTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    RecipeHandler handler = new RecipeHandler();

    @Test(timeout = 1000)
    public void test_addOneRecipe_simple(){
        ArrayList<String> lst = new ArrayList<>(Arrays.asList("sugar", "1", "unit", "butter", "1", "unit"));
        handler.addOneRecipe("pie", lst, "cook");

        assertEquals(handler.getRecipes(), 1);
    }
}
