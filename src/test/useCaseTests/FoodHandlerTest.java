package useCaseTests;

import entities.Food;
import org.junit.*;
import usecases.FoodHandler;
import static org.junit.Assert.*;
import entities.PerishableFood;
import entities.NonPerishableFood;
import controllers.FoodController;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Tests for methods in the FoodHandler class.
 */

public class FoodHandlerTest {

    /**
     * Create a List of List of Strings that describe Food items for FoodHandler tests to use. Some foods include
     * Perishable but not expired food, Perishable and expired food, and Nonperishable food.
     *
     * @return a List of List of Strings that describe various Food items
     */
    private static List<List<String>> getFoodTestCases(){
        List<List<String>> foods = new ArrayList<>();

        //expired milk
        List<String> milkArray = new ArrayList<>();
        milkArray.add("Milk");
        milkArray.add("3");
        milkArray.add("cups");
        milkArray.add("2020");
        milkArray.add("10");
        milkArray.add("10");

        //expired bread
        List<String> breadArray = new ArrayList<>();
        breadArray.add("Bread");
        breadArray.add("1");
        breadArray.add("loaf");
        breadArray.add("2020");
        breadArray.add("10");
        breadArray.add("11");

        //nonperishable dried mango
        List<String> driedMangoArray = new ArrayList<>();
        driedMangoArray.add("Dried Mango");
        driedMangoArray.add("1");
        driedMangoArray.add("pound");

        //fresh eggs
        List<String> eggsArray = new ArrayList<>();
        eggsArray.add("Eggs");
        eggsArray.add("4");
        eggsArray.add("dozen");
        eggsArray.add("2300");
        eggsArray.add("10");
        eggsArray.add("11");

        //nonperishable chocolate
        List<String> chocolateArray = new ArrayList<>();
        chocolateArray.add("Chocolate");
        chocolateArray.add("2");
        chocolateArray.add("cups");

        //nonperishable chocolate2
        List<String> chocolateSecondArray = new ArrayList<>();
        chocolateSecondArray.add("Chocolate");
        chocolateSecondArray.add("500");
        chocolateSecondArray.add("pounds");

        foods.add(milkArray);
        foods.add(breadArray);
        foods.add(driedMangoArray);
        foods.add(eggsArray);
        foods.add(chocolateArray);
        foods.add(chocolateSecondArray);

        return foods;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    /**
     * Test that createFood accurately creates a PerishableFood object.
     */
    @Test(timeout = 1000)
    public void testCreatePerishableFood(){
        // create actual value
        FoodHandler foodhandler = new FoodHandler();
        foodhandler.createFood(getFoodTestCases().get(3));
        PerishableFood actualValue = (PerishableFood) getFoodHelper(0);

        // creating a LocalDate for the PerishableFood object for expected value
        LocalDate localDate = LocalDate.of(2300,10,11);
        // creating the expected PerishableFood value
        PerishableFood expectedValue = new PerishableFood("Eggs", 4.0, "dozen", localDate);
        assertEquals(expectedValue, actualValue);
    }


    /**
     * Test that createFood accurately creates a NonPerishableFood object.
     */
    @Test(timeout = 1000)
    public void testCreateNonPerishableFood(){
        // create actual value
        FoodHandler foodhandler = new FoodHandler();
        foodhandler.createFood(getFoodTestCases().get(4));
        NonPerishableFood actualValue = (NonPerishableFood) getFoodHelper(0);

        // creating the expected PerishableFood value
        NonPerishableFood expectedValue = new NonPerishableFood("Chocolate", 2.0, "cups");
        assertEquals(expectedValue, actualValue);
    }


//    /**
//     * Test createMultipleFoods accurately creates multiple food items.
//     */
//    @Test(timeout = 1000)
//    public void testCreateMultipleFoods(){
//        // create actual value
//        FoodHandler foodhandler = new FoodHandler();
//        List<List<String>> shortenedArray = new ArrayList<>();
//        shortenedArray.add(getFoodTestCases().get(3));
//        shortenedArray.add(getFoodTestCases().get(4));
//        foodhandler.createMultipleFoods(shortenedArray);
//        ArrayList<Food> expectedValue = FoodHandler.getCreatedFoodList();
//
//        // creating the expected value
//        LocalDate milkLocalDate = LocalDate.of(2300,10,11);
//        PerishableFood milk = new PerishableFood ("Milk", 3.0, "cups", milkLocalDate);
//        NonPerishableFood chocolate = new NonPerishableFood("Chocolate", 2.0, "cups");
//        ArrayList<Food> actualValue = null;
//        actualValue.add(milk);
//        actualValue.add(chocolate);
//        assertEquals(actualValue, expectedValue);
//    }


    /**
     * Test method getPerishedFoods
     */
    @Test(timeout= 1000)
    public void testGetPerishedFoods(){
        // Create actual value
        FoodHandler actualFoodhandler = new FoodHandler();
        actualFoodhandler.createFood(getFoodTestCases().get(0));
        actualFoodhandler.createFood(getFoodTestCases().get(1));
        actualFoodhandler.createFood(getFoodTestCases().get(2));
        actualFoodhandler.createFood(getFoodTestCases().get(3));
        List<String> actualValue = actualFoodhandler.getPerishedFoods();

        // Create expected value
        ArrayList<String> expectedValue = new ArrayList<>();

        FoodHandler expectedFoodHandler = new FoodHandler();
        expectedFoodHandler.createFood(getFoodTestCases().get(0));
        expectedFoodHandler.createFood(getFoodTestCases().get(1));
        expectedValue.add(getFoodHelper(0).toString());
        expectedValue.add(getFoodHelper(1).toString());

        assertEquals(expectedValue, actualValue);
    }


    /**
     * Test getSpecifiedFoodList when a certain food item appears twice.
     */
    @Test(timeout = 1000)
    public void testMakeSpecifiedFoodList(){
        // Creating a sample data set
        ArrayList<String> sampleFoodData = new ArrayList<>();
        sampleFoodData.add("Shrimp,,2,,cups");
        sampleFoodData.add("Bread,,6,,slices,,2020,,10,,19");
        sampleFoodData.add("Shrimp,,5,,tons");

        // load the sample data set
        FoodController controller = initialLoadFood(sampleFoodData);

        // create actual value
        ArrayList<Object[]> shrimpList = new ArrayList<>();
        Object[] shrimp1 = {getFoodHelper(0), 0};
        Object[] shrimp2 = {getFoodHelper(2), 2};
        shrimpList.add(shrimp1);
        shrimpList.add(shrimp2);

        // create expected
        int listSize = controller.handler.makeSpecifiedFoodList("Shrimp");

        assertEquals(shrimpList.size(), listSize);
    }


    /**
     * Test getCreatedFoodListNameOnly.
     */
    @Test(timeout = 1000)
    public void testGetCreatedFoodListNameOnly(){
        // create expected value
        List<String> expectedValue = new ArrayList<>();
        expectedValue.add("Milk");
        expectedValue.add("Bread");
        expectedValue.add("Chocolate");

        // set up for the actual value
        FoodHandler setup = new FoodHandler();
        setup.createFood(getFoodTestCases().get(0));
        setup.createFood(getFoodTestCases().get(1));
        setup.createFood(getFoodTestCases().get(4));

        assertEquals(expectedValue, FoodHandler.getCreatedFoodListNameOnly());
    }


    /**
     * Test getCreatedFoodListFullString.
     */
    @Test(timeout = 1000)
    public void testGetCreatedFoodListFullString(){
        // create expected value
        List<String> expectedValue = new ArrayList<>();
        expectedValue.add("Milk: 3.0 cups, Expired on: 2020-10-10");
        expectedValue.add("Eggs: 4.0 dozen, Expires on: 2300-10-11");
        expectedValue.add("Chocolate: 2.0 cups");

        // set up for the actual value
        FoodHandler setup = new FoodHandler();
        setup.createFood(getFoodTestCases().get(0));
        setup.createFood(getFoodTestCases().get(3));
        setup.createFood(getFoodTestCases().get(4));

        assertEquals(expectedValue, setup.getCreatedFoodListFullString());
    }


    /**
     * Test getCreatedFoodList.
     */
    @Test(timeout = 1000)
    public void testGetCreatedFoodList(){
        List<Food> expectedValue = new ArrayList<>();

        FoodHandler expectedFoodHandler = new FoodHandler();
        expectedFoodHandler.createFood(getFoodTestCases().get(0));
        expectedFoodHandler.createFood(getFoodTestCases().get(1));
        expectedFoodHandler.createFood(getFoodTestCases().get(4));
        expectedValue.add(getFoodHelper(0));
        expectedValue.add(getFoodHelper(1));
        expectedValue.add(getFoodHelper(2));

        assertEquals(expectedValue, FoodHandler.getCreatedFoodList());
    }


    /**
     * Test deleteFood.
     */
    @Test(timeout = 1000)
    public void testDeleteFood(){

        FoodHandler setupActual = new FoodHandler();
        setupActual.createFood(getFoodTestCases().get(0));
        setupActual.createFood(getFoodTestCases().get(4));
        setupActual.createFood(getFoodTestCases().get(5));
        setupActual.makeSpecifiedFoodList("Chocolate");
        setupActual.deleteFood(0);

        List<Food> expectedValue = new ArrayList<>();
        LocalDate milkLocalDate = LocalDate.of(2020,10,10);
        PerishableFood milk = new PerishableFood("Milk", 3.0, "cups", milkLocalDate);
        NonPerishableFood chocolate = new NonPerishableFood("Chocolate", 500.0, "pounds");
        expectedValue.add(milk);
        expectedValue.add(chocolate);

        assertEquals(expectedValue, FoodHandler.getCreatedFoodList());
    }

    private FoodController initialLoadFood(ArrayList<String> food){
        FoodController foodController = new FoodController();
        foodController.loadFoodFromList(food);
        return foodController;
    }

    /**
     * Get the food item at index from getCreatedFoodList
     * @param index the index at which you want the Food to be returned
     * @return Food item at index
     */
    private Food getFoodHelper(Integer index) {
        ArrayList<Food> food = FoodHandler.getCreatedFoodList();
        return (food.get(index));
    }

}
