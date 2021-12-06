package controllerTests;

import controllers.FoodController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usecases.FoodHandler;

import java.util.ArrayList;
import java.util.List;

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
    public void testDeleteFood() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.loadFoodFromList(foodData);
        int initialFoodListSize = FoodHandler.getCreatedFoodListNameOnly().size();
        foodController.makeSpecifiedFoodList("Bread");
        foodController.deleteFood(1);
        assertEquals(initialFoodListSize - 1, FoodHandler.getCreatedFoodList().size());
    }

    @Test(timeout=1000)
    public void testCheckPerishables() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.loadFoodFromList(foodData);
        Object food = getFoodHelper(foodController);
        List<String> foodList = foodController.checkPerishables();
        int perishedFoodNumber = foodList.size();
        assertEquals(perishedFoodNumber, foodController.checkPerishables().size());
        assertTrue(foodList.contains(food.toString()));
    }

    @Test(timeout=1000)
    public void testRunFoodCreation() {
        FoodController foodController = new FoodController();
        ArrayList<String> food = new ArrayList<>();
        food.add("Potato");
        food.add("2.000");
        food.add("grams");
        foodController.runFoodCreation(food);
        assertEquals(1, foodController.handler.getCreatedFoodListFullString().size());
        assertTrue(foodController.foodList.contains(food));
    }

    @Test(timeout=1000)
    public void testLoadFoodFromList() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.loadFoodFromList(foodData);
        assertEquals(2, foodController.checkPerishables().size());
    }
    @Test(timeout=1000)
    public void testMakeSpecifiedFoodList() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Shrimp,,6,,slices,,2020,,10,,19");
        foodController.loadFoodFromList(foodData);
        int listSize = foodController.makeSpecifiedFoodList("Shrimp");
        assertEquals(2, listSize);
    }
    @Test(timeout=1000)
    public void testAllFoodToString() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Shrimp,,6,,slices,,2020,,10,,19");
        foodController.loadFoodFromList(foodData);
        List<String> foodList = foodController.allFoodToString();
        assertEquals(2, foodList.size());
    }

    private Object getFoodHelper(FoodController controller) {
        controller.makeSpecifiedFoodList("Shrimp");
        Object[] item = controller.handler.specifiedFoodList.get(0);
        return item[0];
    }
}

