package controllerTests;

import controllers.FoodController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usecases.FoodHandler;

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
    public void test_deleteFood() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        Object food = getFoodHelper(foodController);
        int initialFoodListSize = FoodHandler.getStoreFoodListNameOnly().size();
        foodController.deleteFood(food);
        assertEquals(initialFoodListSize - 1, FoodHandler.getStoreFoodList().size());
    }

    @Test(timeout=1000)
    public void test_checkPerishables() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        Object food = getFoodHelper(foodController);
        ArrayList<String> foodList = foodController.checkPerishables();
        int perishedFoodNumber = foodList.size();
        assertEquals(perishedFoodNumber, foodController.checkPerishables().size());
        assertTrue(foodList.contains(food.toString()));
    }

    @Test(timeout=1000)
    public void test_runFoodCreation() {
        FoodController foodController = new FoodController();
        ArrayList<String> food = new ArrayList<>();
        food.add("Potato");
        food.add("2.000");
        food.add("grams");
        foodController.runFoodCreation(food);
        assertEquals(1, foodController.handler.getAllFoodFullString().size());
        assertFalse(foodController.foodList.contains(food));
    }

    @Test(timeout=1000)
    public void test_initialLoad() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        assertEquals(2, foodController.checkPerishables().size());
    }
    @Test(timeout=1000)
    public void test_getSpecifiedFoodList() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Shrimp,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        ArrayList<Object[]> foodList = foodController.getSpecifiedFoodList("Shrimp");
        assertEquals(2, foodList.size());
    }
    @Test(timeout=1000)
    public void test_allFoodToString() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Shrimp,,6,,slices,,2020,,10,,19");
        foodController.initialLoad(foodData);
        ArrayList<String> foodList = foodController.allFoodToString();
        assertEquals(2, foodList.size());
    }

    private Object getFoodHelper(FoodController controller) {
        ArrayList<Object[]> food = controller.getSpecifiedFoodList("Shrimp");
        Object[] item = food.get(0);
        return item[0];
    }
}

