package entitiesTests;

import entities.NonPerishableFood;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests for methods in class NonPerishableFood
 */
public class NonPerishableFoodTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    NonPerishableFood food = new NonPerishableFood("Bread", 1.5, "Slices");

    /**
     * Testing a general case of the getFoodName method from superclass Food
     */
    @Test(timeout=1000)
    public void testGetFoodName() {
        assertEquals(food.getFoodName(), "Bread");
    }

    /**
     * Testing a general case of the getFoodQuantity method from superclass Food
     */
    @Test(timeout=1000)
    public void testGetFoodQuantity() {
        assertEquals(food.getFoodQuantity(), 1.5, 0);
    }

    /**
     * Testing the addFoodQuantity method from superclass Food, for adding and subtracting quantities of food
     */
    @Test(timeout=1000)
    public void testAddFoodQuantity() {
        food.addFoodQuantity(6.0);
        assertEquals(food.getFoodQuantity(), 7.5, 0);
        food.addFoodQuantity(-1.5);
        assertEquals(food.getFoodQuantity(), 6.0, 0);
    }

    /**
     * Testing a general case of the getFoodUnit method from superclass Food
     */
    @Test(timeout=1000)
    public void testGetFoodUnit() {
        assertEquals(food.getFoodUnit(), "Slices");
    }

    /**
     * Testing a general case of the changeFoodUnit method from superclass Food
     */
    @Test(timeout=1000)
    public void testChangeFoodUnit(){
        food.changeFoodUnit("Cups");
        assertEquals(food.getFoodUnit(), "Cups");
    }
}