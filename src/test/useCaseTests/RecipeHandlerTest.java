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
    
    @Test(timeout = 1000)
    public void test_recommendRecipe() throws IOException {
        FoodController foodController = new FoodController();
        RecipeController recipeController = new RecipeController();
        ArrayList<String> foodData = DataParser.readFile(true);
        foodController.initialLoad(foodData);
        ArrayList<String> recipeData = DataParser.readFile(false);
        recipeController.initialLoad(recipeData);
        Recipe test_recipe = recipeController.handler.findRecipe("Test Recipe");
        Recipe test_recipe2 = recipeController.handler.findRecipe("Test Recipe 2");
        Recipe test_recipe3 = recipeController.handler.findRecipe("Test Recipe 3");
        ArrayList<Recipe> recipes_array = new ArrayList<>();
        recipes_array.add(test_recipe);
        recipes_array.add(test_recipe2);
        recipes_array.add(test_recipe3);
        System.out.println(recipeController.recommendRecipe(5));
        assertEquals(recipes_array, recipeController.recommendRecipe(3));
    }



}
