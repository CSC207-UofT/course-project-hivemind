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

    public void initialLoad(ArrayList<ArrayList<String>> multi_array){
        /**
         * Creates PerishableFood and NonPerishableFood items from an Array of Array of Strings.
         */
        for(ArrayList<String> i:multi_array){
            String food = i.get(0);
            Double quantity = Double.parseDouble(i.get(1));
            String measurement = i.get(2);


            if (i.size() < 4){
                // Make a NonPerishable food item
                this.storeFoodList.add(new NonPerishableFood(food, quantity, measurement));
            }else{
                // Make a Perishable food item
                int day = Integer.parseInt(i.get(3));
                int month = Integer.parseInt(i.get(4));
                int year = Integer.parseInt(i.get(5));
                LocalDate local_date = LocalDate.of(day, month, year);

                this.storeFoodList.add(new PerishableFood(food,quantity, measurement, local_date));
            }

        }
    }

    public ArrayList<Food> getStoreFoodList(){
        /**
         * Create a getter method so that RecipeHandler can access the array of foods storeFoodList.
         */
        return this.storeFoodList;
    }

    public void createFood(ArrayList<String> single_array){
        /**
         * Create a Food object (Perishable or NonPerishable) based on single_array.
         */
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


}
