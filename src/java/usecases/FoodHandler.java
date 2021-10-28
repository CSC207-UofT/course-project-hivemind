package usecases;

import entities.Food;
import entities.PerishableFood;
import entities.NonPerishableFood;

import java.time.LocalDate;
import java.util.ArrayList;

public class FoodHandler {
    private static ArrayList<Food> storeFoodList;

    public FoodHandler() {
        storeFoodList = new ArrayList<>();
    }


    /**
     * Create a Food object (Perishable or NonPerishable) based on single_array.
     *
     * @param single_array An ArrayList that is in the format of [food name, quantity, measurement(, day, month, year)]
     */
    public void createFood(ArrayList<String> single_array){
        String food = single_array.get(0);
        Double quantity = Double.parseDouble(single_array.get(1));
        String measurement = single_array.get(2);

        if (single_array.size() < 4){
            // Make a NonPerishable food item
            storeFoodList.add(new NonPerishableFood(food, quantity, measurement));
        }else{
            // Make a Perishable food item
            int day = Integer.parseInt(single_array.get(3));
            int month = Integer.parseInt(single_array.get(4));
            int year = Integer.parseInt(single_array.get(5));
            LocalDate local_date = LocalDate.of(day, month, year);

            storeFoodList.add(new PerishableFood(food,quantity, measurement, local_date));
        }
    }


    /**
     * Creates PerishableFood and NonPerishableFood items from an Array of Array of Strings.
     *
     * @param multi_array an arraylist of arraylist of strings for creating food
     */
    public void initialLoad(ArrayList<ArrayList<String>> multi_array){
        for(ArrayList<String> i:multi_array){
            createFood(i);
        }
    }


    /**
     * Create a getter method so that RecipeHandler can access the array of foods storeFoodList.
     *
     * @return an ArrayList of strings of all foods that is loaded in this handler
     */
    public static ArrayList<String> getStoreFoodList(){
        ArrayList<String> names = new ArrayList<>();
        for(Food foodName : storeFoodList){
            names.add(foodName.getName());
        }
        return names;
    }
}
