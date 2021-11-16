package useCaseTests;

import controllers.FoodController;
import entities.Food;
import org.junit.*;
import usecases.FoodHandler;
import static org.junit.Assert.*;
import entities.PerishableFood;
import entities.NonPerishableFood;

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


    //    // Testing that createFood accurately creates a PerishableFood object from
//    @Test(timeout = 1000)
//    public void test_initialLoad(){
//        ArrayList<ArrayList<String>> initialLoad_parameter = new ArrayList<>();
//        ArrayList<String> milkArray = new ArrayList<>();
//        milkArray.add("Milk");
//        milkArray.add("3");
//        milkArray.add("cups");
//        ArrayList<String> jamArray = new ArrayList<>();
//        jamArray.add("Jam");
//        jamArray.add("1");
//        jamArray.add("tablespoon");
//        initialLoad_parameter.add(milkArray);
//        initialLoad_parameter.add(jamArray);
//
//    }
//
//    // Testing a case of getSpecifiedFoodList where we call on food that appears twice in foodData
//    @Test(timeout = 1000)
//    public void test_getSpecifiedFoodList(){
//        ArrayList<String> foodData = new ArrayList<>();
//        foodData.add("Shrimp,,2,,cups");
//        foodData.add("Bread,,6,,slices,,2020,,10,,19");
//        foodData.add("Shrimp,,5,,tons");
//        FoodController controller = initialLoadFood(foodData);
//        ArrayList<Object[]> shrimpList = new ArrayList<>();
//        Object[] shrimp1 = {getFoodHelper(0), 0};
//        Object[] shrimp2 = {getFoodHelper(2), 2};
//        shrimpList.add(shrimp1);
//        shrimpList.add(shrimp2);
//        assertEquals(controller.handler.getSpecifiedFoodList("Shrimp").get(0), shrimpList.get(0));
//        assertEquals(controller.handler.getSpecifiedFoodList("Shrimp").get(1), shrimpList.get(1));
//        //assertEquals(controller.handler.getSpecifiedFoodList("Shrimp"), shrimpList); //TODO: Fix this test to compare two ArrayLists
//        assertEquals(controller.handler.getSpecifiedFoodList("Shrimp").size(), shrimpList.size());
//    }
//    @Test(timeout = 1000)
//    public void test_getFoodList(){
//        ArrayList<String> foodData = new ArrayList<>();
//        foodData.add("Chocolate,,2,,cups");
//        foodData.add("Bread,,6,,slices,,2020,,10,,19");
//        foodData.add("Ham,,5,,tons");
//        FoodController controller = initialLoadFood(foodData);
//        ArrayList<Food> foodList = new ArrayList<>();
//        foodList.add(getFoodHelper(0));
//        foodList.add(getFoodHelper(1));
//        foodList.add(getFoodHelper((2)));
//        assertEquals(FoodHandler.getFoodList(), foodList);
////    }
//
//    private FoodController initialLoadFood(ArrayList<String> food){
//        FoodController foodController = new FoodController();
//        foodController.initialLoad(food);
//        return foodController;
//    }

    private Food getFoodHelper(Integer index) {
        ArrayList<Food> food = FoodHandler.getFoodList();
        return (food.get(index));
    }
}
