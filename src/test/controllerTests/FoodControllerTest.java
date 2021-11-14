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
        ArrayList<String> foodData = DataParser.readFile(true);
        foodController.initialLoad(foodData);
        Food food = getFoodHelper("Salt", 0, foodController);
        int initialFoodListSize = FoodHandler.getStoreFoodList().size();
        foodController.deleteFood(food);
        assertEquals(food, foodController.deleteFood(food));
        assertEquals(initialFoodListSize - 1, FoodHandler.getFoodList().size());
        assertFalse(FoodHandler.getFoodList().contains(food));
    }

    private Food getFoodHelper(String foodName, Integer index, FoodController controller) {
        ArrayList<Object[]> food = controller.getSpecifiedFoodList(foodName);
        Object[] item = food.get(index);
        return (Food) item[0];
    }
}

