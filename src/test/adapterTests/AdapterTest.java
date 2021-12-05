package adapterTests;

import controllers.FoodController;
import entities.Recipe;
import org.junit.After;
import org.junit.Before;
import controllers.RecipeController;
import org.junit.Test;
import parsers.DataParser;
import usecases.FoodHandler;
import usecases.RecipeHandler;
import adapters.Adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AdapterTest {
    public AdapterTest(){
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    @Test(timeout=1000)
    public void testLoadFoods() throws IOException {
        Adapter a = new Adapter();
        ArrayList<String> foodData = new ArrayList<>(Arrays.asList("Butter,,1,,lbs,,2022,,3,,22"));
        a.foodController.loadFoodFromList(foodData);
        List<List<String>> actual = a.loadFoods();
        List<String> expected = new ArrayList<>(Arrays.asList("Butter", "1 lbs", "Expiry date: 22/3/2022"));
        assertEquals(expected, actual);
    }

    @Test(timeout=1000)
    public void testCreateFood() throws IOException {
        Adapter a = new Adapter();
        List<String> actual = a.createFood("Potato", "2", "lbs");
        List<String> expected = new ArrayList<>(Arrays.asList("Potato", "2 lbs"));
        assertEquals(expected, actual);
    }

    @Test(timeout=1000)
    public void testCreateLongerFood() throws IOException {
        Adapter a = new Adapter();
        List<String> actual = a.createFood("Potato", "2", "lbs", "2002", "6", "5");
        List<String> expected = new ArrayList<>(Arrays.asList("Potato", "2 lbs", "Expiry date: 5/6/2002"));
        assertEquals(expected, actual);
    }

    @Test(timeout=1000)
    public void testShowPerishables() throws IOException {
        Adapter a = new Adapter();
        List<String> actual = a.createFood("Potato", "2", "lbs", "2002", "6", "5");
        List<String> expected = new ArrayList<>(Arrays.asList("Potato", "2 lbs", "Expiry date: 5/6/2002"));
        assertEquals(expected, actual);
    }

}
