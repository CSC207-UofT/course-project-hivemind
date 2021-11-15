package controllerTests;

import entities.Food;
import org.junit.After;
import org.junit.Before;
import controllers.FoodController;
import org.junit.Test;
import parsers.DataParser;
import usecases.FoodHandler;


import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoodControllerTest {
    public FoodControllerTest(){
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }



    @Test(timeout=1000)
    public void test_deleteFood() throws IOException {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        Food food = getFoodHelper("Shrimp", 0, foodController);
        int initialFoodListSize = FoodHandler.getStoreFoodList().size();
        foodController.deleteFood(food);
        assertEquals(food, foodController.deleteFood(food));
        assertEquals(initialFoodListSize - 1, FoodHandler.getFoodList().size());
        assertFalse(FoodHandler.getFoodList().contains(food));
    }

    @Test(timeout=1000)
    public void test_checkPerishables() throws IOException {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        Food food = getFoodHelper("Shrimp", 0, foodController);
        ArrayList<Food> foodList = foodController.checkPerishables();
        int perishedFoodNumber = foodList.size();
        assertEquals(perishedFoodNumber, foodController.checkPerishables().size());
        assertTrue(foodList.contains(food));
    }

    @Test(timeout=1000)
    public void test_runFoodCreation() throws IOException {
        FoodController foodController = new FoodController();
        ArrayList<String> food = new ArrayList<>();
        food.add("Potato");
        food.add("2.000");
        food.add("grams");
        foodController.runFoodCreation(food);
        assertEquals(1, foodController.handler.getStoreFoodListFoods().size());
        assertFalse(foodController.foodList.contains(food));
    }

    @Test(timeout=1000)
    public void test_initialLoad() throws IOException {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        Food food = getFoodHelper("Shrimp", 0, foodController);
        ArrayList<Food> foodList = foodController.handler.getStoreFoodListFoods();
        int perishedFoodNumber = foodList.size();
        assertEquals(2, foodController.checkPerishables().size());
        assertTrue(foodList.contains(food));
    }
    @Test(timeout=1000)
    public void test_getSpecifiedFoodList() throws IOException {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Shrimp,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        Food food = getFoodHelper("Shrimp", 0, foodController);
        ArrayList<Object[]> foodList = foodController.getSpecifiedFoodList("Shrimp");
        assertEquals(2, foodList.size());
    }
    @Test(timeout=1000)
    public void test_allFoodToString() throws IOException {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Shrimp,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        ArrayList<String> foodList = foodController.allFoodToString();
        assertEquals(2, foodList.size());
    }

    private Food getFoodHelper(String foodName, Integer index, FoodController controller) {
        ArrayList<Object[]> food = controller.getSpecifiedFoodList(foodName);
        Object[] item = food.get(index);
        return (Food) item[0];
    }
}

