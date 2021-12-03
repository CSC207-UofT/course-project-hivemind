package usecases;

import entities.Food;
import entities.PerishableFood;
import entities.NonPerishableFood;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FoodHandler {
    private static ArrayList<Food> storeFoodList;
    public List<Object[]> specifiedFoodList;

    public FoodHandler() {
        storeFoodList = new ArrayList<>();
        this.specifiedFoodList = null;
    }


    /**
     * Create a Food object (Perishable or NonPerishable) based on single_array.
     * TODO: Extend on this?
     * @param single_array An ArrayList that is in the format of [food name, quantity, measurement(, day, month, year)]
     */
    public void createFood(List<String> single_array){
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
     * @param multi_array an arraylist of arraylist of strings for creating food
     */
    public void initialLoad(ArrayList<List<String>> multi_array){
        for(List<String> i:multi_array){
            createFood(i);
        }
    }

    /**
     * Compare the expiry date of the Food object to today's date to return an ArrayList of expired foods called
     * expired_foods.
     * @return expired_foods the ArrayList that lists all the expired foods.
     */
    public ArrayList<String> getPerishedFoods(){
        ArrayList<String> expired_foods = new ArrayList<>();
        for (Food food:storeFoodList){
            if (food instanceof PerishableFood){
                PerishableFood new_food = (PerishableFood)food;
                if (new_food.getExpiryStatus()){
                    expired_foods.add(new_food.toString());
                }
            }
        }
        return expired_foods;
    }


    /**
     * Create a getter method so that RecipeHandler can access the names of the array of foods, storeFoodList.
     * @return an ArrayList of strings of all foods that is loaded in this handler
     */
    public static ArrayList<String> getStoreFoodListNameOnly(){
        ArrayList<String> names = new ArrayList<>();
        for(Food foodName : storeFoodList){
            names.add(foodName.getFoodName());
        }
        return names;
    }

    /**
     * Getter method for returning list of all foods in string form
     * @return an ArrayList of foods of all foods that is loaded in this handler
     */
    public ArrayList<String> getAllFoodFullString(){
        ArrayList<String> foodStrLst = new ArrayList<>();
        for (Food foodObjects: getStoreFoodList()){
            foodStrLst.add(foodObjects.toString());
        }
        return foodStrLst;
    }


    /**
     * Getter method for returning Arraylist of all food objects stored in storeFoodList
     * @return an ArrayList containing all the Food Objects in our system
     */
    public static ArrayList<Food> getStoreFoodList(){
        return storeFoodList;
    }


    /**
     * Creates an array list of object arrays. Each object array contains a food object matching the name specified by
     * parameter foodName at index 0, and the food's index in storeFoodList at index 1
     * @param foodName the name of the food
     * @return the size of specifiedFoodList
     */
    public int makeSpecifiedFoodList(String foodName) {
        ArrayList<Object[]> foodList = new ArrayList<>();
        int index = 0;
        for (Food foods : storeFoodList) {
            String lowerFoodGetName = foods.getFoodName().toLowerCase(Locale.ROOT);
            String lowerFoodName = foodName.toLowerCase(Locale.ROOT);
            if (Objects.equals(lowerFoodGetName, lowerFoodName)) {
                Object[] food = {foods, index};
                foodList.add(food);
            }
            index++;
        }
        this.specifiedFoodList = foodList;
        return foodList.size();
    }

    /**
     * Deletes a Food object from storeFoodList given its index at specifiedFoodListIndex
     * @param specifiedFoodListIndex the index of the Food from specifiedFoodList which is to be deleted from the program
     */
    public void deleteFood(int specifiedFoodListIndex) {
        Food foodToDelete = (Food) getFoodFromArray(this.specifiedFoodList.get(specifiedFoodListIndex));
        storeFoodList.remove(foodToDelete);
    }

    /**
     * Returns the index of the given food in storeFoodList, given its corresponding index in specifiedFoodList
     * @param specifiedFoodListIndex the index of a food item in specifiedFoodList
     * @return the index of a food item in storeFoodList
     */
    public int getStoreFoodListIndex(int specifiedFoodListIndex) {
        return getFoodIndexFromArray(this.specifiedFoodList.get(specifiedFoodListIndex));
    }

    /**
     * Returns a list of strings representing foods in specifiedFoodList with their given positions in the list
     * @return a list of strings representing foods in specifiedFoodList with their given positions in the list
     */
    public List<String> getSpecifiedFoodListStrings() {
        List<String> foodList = new ArrayList<>();
        int index = 1;
        for (Object[] item: this.specifiedFoodList){
            Food food = (Food) getFoodFromArray(item);
            if (food instanceof PerishableFood) {
                String isExpired = "Not Expired";
                if (((PerishableFood) food).getExpiryStatus()){
                    isExpired = "Expired";
                }
                foodList.add(getFoodStringHelper(index, food) + ", Expiry Status: " + isExpired);
            }
            else {
                foodList.add(getFoodStringHelper(index, food));
            }
            index++;
        }
        return foodList;
    }

    /**
     * Helper method which returns a string representing a food item, listed with a specified position
     * @param position the position corresponding with the given food in specifiedFoodList
     * @param food a food object
     * @return a string representation of the given food object with the given position
     */
    private String getFoodStringHelper(int position, Food food) {
        String foodString = "";
        foodString = foodString + position + ". " + food.toString();
        return foodString;
    }

    /**
     * Returns an Object representation of a Food item from an Array
     * @param foodArray an object array containing a food object at index 0, and the food's corresponding index in food
     * data at index 1
     * @return an Object representation of a Food
     */
    private static Object getFoodFromArray(Object[] foodArray){
        return foodArray[0];
    }

    /**
     * Returns the index of a Food item in storeFoodList
     * @param foodArray an object array containing a food object at index 0, and the food's corresponding index in
     * storeFoodList at index 1
     * @return an int representing a Food's index in storeFoodList
     */
    private static int getFoodIndexFromArray(Object[] foodArray){
        return (int) foodArray[1];
    }

    /**
     * Sets specifiedFoodList to null
     */
    public void deleteSpecifiedFoodList(){
        this.specifiedFoodList = null;
    }
}
