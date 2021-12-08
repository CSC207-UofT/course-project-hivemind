package controllerTests;

import controllers.FoodController;
import entities.Food;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usecases.FoodHandler;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test(timeout=1000)
    public void testFoodDeleteFromSystem() {
        FoodController foodController = new FoodController();
        ArrayList<String> foodData = new ArrayList<>();
        foodData.add("Shrimp,,2,,cups,,2020,,10,,19");
        foodData.add("Bread,,6,,slices,,2020,,10,,19");
        foodController.loadFoodFromList(foodData);
        foodController.foodDeletedFromSystem();
        assertNull(foodController.handler.specifiedFoodList);
    }

    @Test(timeout=1000)
    public void testGetFoodIndexToDelete() {
        ArrayList<String> sampleFoodData = new ArrayList<>();
        sampleFoodData.add("Shrimp,,2,,cups");
        sampleFoodData.add("Bread,,6,,slices,,2020,,10,,19");
        sampleFoodData.add("Shrimp,,5,,tons");

        FoodController controller = initialLoadFood(sampleFoodData);


        ArrayList<Object[]> shrimpList = new ArrayList<>();
        Object[] shrimp1 = {getFoodHelper(0), 0};
        Object[] shrimp2 = {getFoodHelper(2), 2};
        shrimpList.add(shrimp1);
        shrimpList.add(shrimp2);

        int listSize = controller.handler.makeSpecifiedFoodList("Shrimp");

        assertEquals(shrimpList.size(), listSize);

        int a = controller.getFoodIndexToDelete(1);

        assertEquals(0, a);


    }

    @Test(timeout=1000)
    public void testPrintSpecifiedFoodList() {
        ArrayList<String> sampleFoodData = new ArrayList<>();
        sampleFoodData.add("Shrimp,,2,,cups");
        sampleFoodData.add("Bread,,6,,slices,,2020,,10,,19");
        sampleFoodData.add("Shrimp,,5,,tons");

        FoodController controller = initialLoadFood(sampleFoodData);


        ArrayList<Object[]> shrimpList = new ArrayList<>();
        Object[] shrimp1 = {getFoodHelper(0), 0};
        Object[] shrimp2 = {getFoodHelper(2), 2};
        shrimpList.add(shrimp1);
        shrimpList.add(shrimp2);

        int listSize = controller.handler.makeSpecifiedFoodList("Shrimp");

        assertEquals(shrimpList.size(), listSize);

        List<String> actual = Arrays.asList("1. Shrimp: 2.0 cups", "2. Shrimp: 5.0 tons");

        assertEquals(actual , controller.printSpecifiedFoodList());

    }





    private Food getFoodHelper(Integer index) {
        ArrayList<Food> food = FoodHandler.getCreatedFoodList();
        return (food.get(index));
    }

    private FoodController initialLoadFood(ArrayList<String> food){
        FoodController foodController = new FoodController();
        foodController.loadFoodFromList(food);
        return foodController;
    }

}

