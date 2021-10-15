package entitiesTests;

import entities.NonPerishableFood;
import org.junit.*;
import static org.junit.Assert.*;

public class NonPerishableFoodTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    NonPerishableFood food = new NonPerishableFood("Bread", 1.5f, "Slices");

    // Testing a general case of the getName method from superclass Food
    @Test(timeout=1000)
    public void test_getName(){
        assertEquals(food.getName(), "Bread");
    }

    // Testing a general case of the getQuantity method from superclass Food
    @Test(timeout=1000)
    public void test_getQuantity() {
        assertEquals(food.getQuantity(), 1.5f, 0);
    }

    // Testing the changeQuantity method from superclass Food, for adding and subtracting quantities of food
    @Test(timeout=1000)
    public void test_addQuantity() {
        food.addQuantity(6f);
        assertEquals(food.getQuantity(), 7.5, 0);
        food.addQuantity(-1.5f);
        assertEquals(food.getQuantity(), 6.0, 0);
    }
    // Testing a general case of the getUnit method from superclass Food
    @Test(timeout=1000)
    public void test_getUnit() {
        assertEquals(food.getUnit(), "Slices");
    }

    // Testing a general case of the changeUnit method from superclass Food
    @Test(timeout=1000)
    public void test_changeUnit(){
        food.changeUnit("Cups");
        assertEquals(food.getUnit(), "Cups");
    }
}