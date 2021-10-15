package entitiesTests;

import entities.PerishableFood;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;


public class PerishableFoodTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    PerishableFood food = new PerishableFood("Bagel", 2.0, "individuals", LocalDate.of(2000, 1, 1));

    // Testing a general case of getExpiryDate from PerishableFood
    @Test(timeout=1000)
    public void test_getExpiryDate() {
        LocalDate date = food.getExpiryDate();
        assertEquals(date, LocalDate.of(2000, 1, 1));
    }

    // Testing a general case of getExpiryStatus from PerishableFood
    @Test(timeout=1000)
    public void test_getExpiryStatus() {
        assertTrue(food.getExpiryStatus());
    }
}