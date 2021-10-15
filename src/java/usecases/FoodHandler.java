package usecases;

import entities.Food;
import entities.PerishableFood;
import entities.NonPerishableFood;

import java.time.LocalDate;
import java.util.ArrayList;

public class FoodHandler {
    private ArrayList<Food> storeFoodList;

    public FoodHandler() {
        this.storeFoodList = new ArrayList<>();
    }


    /**
     * Create a Food object (Perishable or NonPerishable) based on single_array.
     */
    public void createFood(ArrayList<String> single_array){
        String food = single_array.get(0);
        Double quantity = Double.parseDouble(single_array.get(1));
        String measurement = single_array.get(2);

        if (single_array.size() < 4){
            // Make a NonPerishable food item
            this.storeFoodList.add(new NonPerishableFood(food, quantity, measurement));
        }else{
            // Make a Perishable food item
            int day = Integer.parseInt(single_array.get(3));
            int month = Integer.parseInt(single_array.get(4));
            int year = Integer.parseInt(single_array.get(5));
            LocalDate local_date = LocalDate.of(day, month, year);

            this.storeFoodList.add(new PerishableFood(food,quantity, measurement, local_date));
        }
    }


    /**
     * Creates PerishableFood and NonPerishableFood items from an Array of Array of Strings.
     */
    public void initialLoad(ArrayList<ArrayList<String>> multi_array){
        for(ArrayList<String> i:multi_array){
            createFood(i);
        }
    }


    /**
     * Create a getter method so that RecipeHandler can access the array of foods storeFoodList.
     */
    public ArrayList<Food> getStoreFoodList(){
        return this.storeFoodList;
    }
}
