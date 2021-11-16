package useCaseTests;

import entities.Food;
import org.junit.*;
import usecases.FoodHandler;
import static org.junit.Assert.*;
import entities.PerishableFood;
import entities.NonPerishableFood;
import controllers.FoodController;

import java.util.ArrayList;
import java.time.LocalDate;


public class FoodHandlerTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // Testing that createFood accurately creates a PerishableFood object.
    @Test(timeout = 1000)
    public void test_createPerishableFood(){
        ArrayList<String> milkArray = new ArrayList<>();
        milkArray.add("Milk");
        milkArray.add("3");
        milkArray.add("cups");
        milkArray.add("2021");
        milkArray.add("11");
        milkArray.add("22");

        // create expected value
        FoodHandler foodhandler = new FoodHandler();
        foodhandler.createFood(milkArray);
        PerishableFood expectedValue = (PerishableFood) getFoodHelper(0);

        // creating a LocalDate for the PerishableFood object for actual value
        LocalDate local_date = LocalDate.of(2021,11,22);
        // creating the actual PerishableFood value
        PerishableFood actualValue = new PerishableFood("Milk", 3.0, "cups", local_date);
        assertEquals(actualValue, expectedValue);
    }

    // Testing that createFood accurately creates a NonPerishableFood object.
    @Test(timeout = 1000)
    public void test_createNonPerishableFood(){
        ArrayList<String> saffronArray = new ArrayList<>();
        saffronArray.add("Saffron");
        saffronArray.add("1");
        saffronArray.add("teaspoon");

        // create expected value
        FoodHandler foodhandler = new FoodHandler();
        foodhandler.createFood(saffronArray);
        NonPerishableFood expectedValue = (NonPerishableFood) getFoodHelper(0);

        // creating the actual PerishableFood value
        NonPerishableFood actualValue = new NonPerishableFood("Saffron", 1.0, "teaspoon");
        assertEquals(actualValue, expectedValue);
    }

    // Test getPerishedFoods
    @Test(timeout= 1000)
    public void test_getPerishedFoods(){
        // Create expected value
        ArrayList<Food> expectedValue = new ArrayList<>();
        ArrayList<String> milkArray = new ArrayList<>();
        milkArray.add("Milk");
        milkArray.add("3");
        milkArray.add("cups");
        milkArray.add("2020");
        milkArray.add("10");
        milkArray.add("10");
        ArrayList<String> breadArray = new ArrayList<>();
        breadArray.add("Bread");
        breadArray.add("1");
        breadArray.add("loaf");
        breadArray.add("2020");
        breadArray.add("10");
        breadArray.add("11");


        FoodHandler expectedFoodhandler = new FoodHandler();
        expectedFoodhandler.createFood(milkArray);
        expectedFoodhandler.createFood(breadArray);
        expectedValue.add(getFoodHelper(0));
        expectedValue.add(getFoodHelper(1));

        // Create actual value
        ArrayList<String> driedMangoArray = new ArrayList<>();
        driedMangoArray.add("Dried Mango");
        driedMangoArray.add("1");
        driedMangoArray.add("pound");
        ArrayList<String> eggsArray = new ArrayList<>();
        eggsArray.add("Eggs");
        eggsArray.add("4");
        eggsArray.add("dozen");
        eggsArray.add("2022");
        eggsArray.add("10");
        eggsArray.add("11");

        FoodHandler actualFoodhandler = new FoodHandler();
        actualFoodhandler.createFood(milkArray);
        actualFoodhandler.createFood(breadArray);
        actualFoodhandler.createFood(driedMangoArray);
        actualFoodhandler.createFood(eggsArray);
        ArrayList<Food> actualValue = actualFoodhandler.getPerishedFoods();

        assertEquals(expectedValue, actualValue);
    }

    // Testing a case of getSpecifiedFoodList where we call on food that appears twice in foodData
    @Test(timeout = 1000)
    public void test_getSpecifiedFoodList(){
        // Creating a sample data set
        ArrayList<String> sampleFoodData = new ArrayList<>();
        sampleFoodData.add("Shrimp,,2,,cups");
        sampleFoodData.add("Bread,,6,,slices,,2020,,10,,19");
        sampleFoodData.add("Shrimp,,5,,tons");

        // load the sample data set
        FoodController controller = initialLoadFood(sampleFoodData);

        // create expected value
        ArrayList<Object[]> shrimpList = new ArrayList<>();
        Object[] shrimp1 = {getFoodHelper(0), 0};
        Object[] shrimp2 = {getFoodHelper(2), 2};
        shrimpList.add(shrimp1);
        shrimpList.add(shrimp2);
        assertEquals(shrimpList.get(0), controller.handler.getSpecifiedFoodList("Shrimp").get(0));
        assertEquals(shrimpList.get(1), controller.handler.getSpecifiedFoodList("Shrimp").get(1));
        //assertEquals(controller.handler.getSpecifiedFoodList("Shrimp"), shrimpList); //TODO: Fix this test to compare two ArrayLists
        assertEquals(shrimpList.size(), controller.handler.getSpecifiedFoodList("Shrimp").size());
    }


    @Test(timeout = 1000)
    public void test_getFoodList(){
        // create sample data
        ArrayList<Food> expectedValue = new ArrayList<>();
        ArrayList<String> milkArray = new ArrayList<>();
        milkArray.add("Milk");
        milkArray.add("3");
        milkArray.add("cups");
        milkArray.add("2020");
        milkArray.add("10");
        milkArray.add("10");

        ArrayList<String> breadArray = new ArrayList<>();
        breadArray.add("Bread");
        breadArray.add("1");
        breadArray.add("loaf");
        breadArray.add("2020");
        breadArray.add("10");
        breadArray.add("11");

        ArrayList<String> chocolateArray = new ArrayList<>();
        chocolateArray.add("Chocolate");
        chocolateArray.add("2");
        chocolateArray.add("cups");

        FoodHandler expectedFoodhandler = new FoodHandler();
        expectedFoodhandler.createFood(milkArray);
        expectedFoodhandler.createFood(breadArray);
        expectedFoodhandler.createFood(chocolateArray);
        expectedValue.add(getFoodHelper(0));
        expectedValue.add(getFoodHelper(1));
        expectedValue.add(getFoodHelper(2));

        assertEquals(expectedValue, FoodHandler.getFoodList());
    }

    private FoodController initialLoadFood(ArrayList<String> food){
        FoodController foodController = new FoodController();
        foodController.initialLoad(food);
        return foodController;
    }

    private Food getFoodHelper(Integer index) {
        ArrayList<Food> food = FoodHandler.getFoodList();
        return (food.get(index));
    }

}
