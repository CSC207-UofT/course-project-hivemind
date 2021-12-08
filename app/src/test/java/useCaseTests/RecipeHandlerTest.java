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

        assertEquals(handler.getRecipeSize(), 1);
    }
    @Test(timeout = 1000)
    public void test_recommendRecipe() {
        FoodController foodController = new FoodController();
        RecipeController recipeController = new RecipeController();
        ArrayList<String> foodData = new ArrayList<>();

        foodData.add("ingredient1,,1,,cup");
        foodData.add("ingredient2,,1,,cup");
        foodData.add("ingredient3,,1,,cup");
        foodData.add("ingredient4,,1,,cup");
        foodData.add("food1,,1,,cup");
        foodData.add("food2,,1,,cup");
        foodData.add("food3,,1,,cup");
        foodData.add("item1,,1,,cup");
        foodController.loadFoodFromList(foodData);

        ArrayList<String> recipeData = new ArrayList<>();
        recipeData.add("Test Recipe 1,,ingredient1,,1,,cup,,ingredient2,,1,,cup,,ingredient3,,1,,cup,,ingredient4,,1,,cup,,1. Cook it somehow. 2. Try to eat it");
        recipeData.add("Test Recipe 2,,food1,,1,,cup,,food2,,1,,cup,,food3,,1,,cup,,food4,,1,,cup,,1. Cook it somehow. 2. Try to eat it");
        recipeData.add("Test Recipe 3,,item1,,1,,cup,,item2,,1,,cup,,item3,,1,,cup,,item4,,1,,cup,,1. Cook it somehow. 2. Try to eat it");
        recipeController.initialLoad(recipeData);

        String test_recipe1 = recipeController.handler.findRecipe("Test Recipe 1").toString();
        String test_recipe2 = recipeController.handler.findRecipe("Test Recipe 2").toString();
        String test_recipe3 = recipeController.handler.findRecipe("Test Recipe 3").toString();
        ArrayList<String> recipes_array = new ArrayList<>();
        recipes_array.add(test_recipe1);
        recipes_array.add(test_recipe2);
        recipes_array.add(test_recipe3);
        recipeController.recommendRecipe(3);
        assertEquals(recipes_array, recipeController.recommendRecipe(3));
    }
}
