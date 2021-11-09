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
        ArrayList<Recipe> recipes = recipeController.handler.getAllRecipes();
        String name = "Best Big Chewy Chocolate Chip Cookies";
        Recipe recipe = getRecipeFromList(recipes, name);
        ArrayList<Recipe> cookie = new ArrayList<>();
        cookie.add(recipe);
        System.out.println(recipeController.recommendRecipe(10));
        assertEquals(cookie, recipeController.recommendRecipe(1));
    }
    public Recipe getRecipeFromList(ArrayList<Recipe> recipes, String name){
        int i;
        for (i = 0; i < recipes.size(); i++){
            if (Objects.equals(recipes.get(i).getRecipeName(), name)){
                return recipes.get(i);
            }
        }
        return recipes.get(0);
    }



}
