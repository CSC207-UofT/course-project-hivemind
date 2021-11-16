package usecases;

import entities.Food;
import entities.PerishableFood;
import entities.NonPerishableFood;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

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

//         If the length of single_array is less than 4, then there is no expiry date for the food. Then we know that
//         it is a NonPerishableFood. Otherwise, we know that the length is greater than four and that it has an expiry
//         date thus it is a PerishableFood.
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
     * Compare the expiry date of the Food object to today's date to return an ArrayList of expired foods called
     * expired_foods.
     * @return expired_foods the ArrayList that lists all the expired foods.
     */
    public ArrayList<Food> getPerishedFoods(){
        ArrayList<Food> expired_foods = new ArrayList<>();
        for (Food food:storeFoodList){
            if (food instanceof PerishableFood){
                PerishableFood new_food = (PerishableFood)food;
                if (new_food.getExpiryStatus()){
                    expired_foods.add(new_food);
                }
            }
        }
        return expired_foods;
    }


    /**
     * Create a getter method so that RecipeHandler can access the names of the array of foods, storeFoodList.
     *
     * @return an ArrayList of strings of all foods that is loaded in this handler
     */
    public static ArrayList<String> getStoreFoodListNameOnly(){
        ArrayList<String> names = new ArrayList<>();
        for(Food foodName : storeFoodList){
            names.add(foodName.getName());
        }
        return names;
    }

    /**
     * Create a getter method so that RecipeHandler can access the array of foods storeFoodList.
     *
     * @return an ArrayList of foods of all foods that is loaded in this handler
     */
    //TODO: (for Phase 2) figure out the difference between this method and getStoreFoodList and refactor appropriately
    public ArrayList<Food> getStoreFoodListFoods(){
        return new ArrayList<>(storeFoodList);
    }


    /**
     *
     * @return an ArrayList containing all the Food Objects in our system
     */
    public static ArrayList<Food> getStoreFoodList(){
        return storeFoodList;
    }


    /**
     * Creates an array list of object arrays. Each object array contains a food object matching the name specified by
     * parameter foodName at index 0, and the food's index in fooddata at index 1
     * @param foodName the name of the food
     * @return an array list of object arrays. Each object array contains a food object matching the name specified by
     *parameter foodName at index 0, and the food's index in fooddata at index 1
     */
    public ArrayList<Object[]> getSpecifiedFoodList(String foodName) {
        ArrayList<Object[]> foodList = new ArrayList<>();
        int index = 0;
        for (Food foods : storeFoodList) {
            String lowerFoodgetName = foods.getName().toLowerCase(Locale.ROOT);
            String lowerFoodName = foodName.toLowerCase(Locale.ROOT);
            if (Objects.equals(lowerFoodgetName, lowerFoodName)) {
                Object[] food = {foods, index};
                foodList.add(food);
            }
            index++;
        }
        return foodList;
    }

    /**
     * Deletes a Food object from storeFoodList
     * @param food The Food Object which is to be deleted
     * @return The Food Object deleted from the system
     */
    public void deleteFood(Object food) {
        Food item = (Food) food;
        storeFoodList.remove(item);
    }

    public String printFood(int number, Object foods) {
        Food food = (Food) foods;
        if (food instanceof PerishableFood) {
            String isExpired = "Not Expired";
            if (((PerishableFood) food).getExpiryStatus()){
                isExpired = "Expired";
            }
            String perishableFood = "";
            perishableFood = perishableFood + number + ". Food Name: " + food.getName() + ", Quantity: " +
                    food.getQuantity() + ", Unit: " + food.getUnit() + ", Expiry Date: " +
                    ((PerishableFood) food).getExpiryDate() + ", Expiry Status: " + isExpired;
            return perishableFood;
        }
        else {
            String nonPerishableFood = "";
            nonPerishableFood = nonPerishableFood + number + ". Food Name: " + food.getName() + ", Quantity: " +
                    food.getQuantity() + ", Unit: " + food.getUnit();
            return nonPerishableFood;
        }
    }
}
