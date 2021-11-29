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
    private static ArrayList<Food> createdFoodList;
    public List<Object[]> specifiedFoodList;

    public FoodHandler() {
        createdFoodList = new ArrayList<>();
        this.specifiedFoodList = null;
    }


    /**
     * Takes in a List of strings, single_array, that describes and contains all the information needed to
     * create a Food object (Perishable or NonPerishable), and creates the Food object.
     *
     * @param single_array A List of strings that is in the format of 
     *                     [food name, quantity, measurement(, day, month, year)]
     */
    public void createFood(List<String> single_array){
        String foodName = single_array.get(0);
        Double quantity = Double.parseDouble(single_array.get(1));
        String measurement = single_array.get(2);

//         If the length of single_array is less than 4, then there is no expiry date for the food. Then, we know that
//         it is a NonPerishableFood. Otherwise, we know that the length is greater than four and that it has an expiry
//         date thus it is a PerishableFood.
        if (single_array.size() < 4){
            // Make a NonPerishable food item
            createdFoodList.add(new NonPerishableFood(foodName, quantity, measurement));
        }else{
            // Make a Perishable food item.
            int day = Integer.parseInt(single_array.get(3));
            int month = Integer.parseInt(single_array.get(4));
            int year = Integer.parseInt(single_array.get(5));
            LocalDate expiryDate = LocalDate.of(day, month, year);

            createdFoodList.add(new PerishableFood(foodName,quantity, measurement, expiryDate));
        }
    }


    /**
     * Creates PerishableFood and NonPerishableFood items from an ArrayList of List of strings, where each
     * List of strings describes a Food item
     *
     * @param multi_array an Arraylist of List of strings for creating food
     */
    public void createMultipleFoods(ArrayList<List<String>> multi_array){
        for(List<String> i:multi_array){
            createFood(i);
        }
    }


    /**
     * For each food object in createdFoodList, if it is a PeirshableFood, then compare the expiry date of the
     * Food object to today's date, and return an ArrayList of expired foods called expired_foods.
     *
     * @return expired_foods the ArrayList that lists all the expired foods.
     */
    public List<String> getPerishedFoods(){
        List<String> expired_foods = new ArrayList<>();
        for (Food food: createdFoodList){
            if (food instanceof PerishableFood new_food){
                if (new_food.getExpiryStatus()){
                    expired_foods.add(new_food.toString());
                }
            }
        }
        return expired_foods;
    }


    /**
     * Getter method returning the names of all foods in createdFoodList.
     * @return an ArrayList of strings of names of all foods in createdFoodList.
     */
    public static ArrayList<String> getCreatedFoodListNameOnly(){
        ArrayList<String> names = new ArrayList<>();
        for(Food foodName : createdFoodList){
            names.add(foodName.getFoodName());
        }
        return names;
    }


    /**
     * Getter method returning list of all foods in string form
     * @return an ArrayList of strings of all foods in createdFoodList.
     */
    public ArrayList<String> getCreatedFoodListFullString(){
        ArrayList<String> foodStringList = new ArrayList<>();
        for (Food foodObjects: getCreatedFoodList()){
            foodStringList.add(foodObjects.toString());
        }

        return foodStringList;
    }


    /**
     * Getter method returning all food objects stored in createdFoodList
     * @return an ArrayList containing all the Food Objects in createdFoodList.
     */
    public static ArrayList<Food> getCreatedFoodList(){
        return createdFoodList;
    }


    /**
     * Creates an ArrayList of object arrays, each of which contains a food object matching the name specified by
     * parameter foodName at index 0, and the food's index in createdFoodList at index 1. This ArrayList will be stored
     * as specifiedFoodList, and the size of the ArrayList will be returned.
     *
     * @param foodName the name of a Food object
     * @return the size of specifiedFoodList
     */
    public int makeSpecifiedFoodList(String foodName) {
        ArrayList<Object[]> foodList = new ArrayList<>();
        int index = 0;
        for (Food foods : createdFoodList) {
            String foodsLowerCase = foods.getFoodName().toLowerCase(Locale.ROOT);
            String foodNameLowerCase = foodName.toLowerCase(Locale.ROOT);
            // if the name from foodName and foods is equal, then add the foods Food object and its corresponding index
            // in createdFoodList, as an Object, to the final ArrayList that will be returned.
            if (Objects.equals(foodsLowerCase, foodNameLowerCase)) {
                Object[] food = {foods, index};
                foodList.add(food);
            }
            index++;
        }
        this.specifiedFoodList = foodList;
        return foodList.size();
    }


    /**
     * Deletes a Food object from createdFoodList at index specifiedFoodListIndex.
     * @param specifiedFoodListIndex the index of the Food from specifiedFoodList which is to be deleted from the program
     */
    public void deleteFood(int specifiedFoodListIndex) {
        Food foodToDelete = (Food) getFoodFromArrayObject(this.specifiedFoodList.get(specifiedFoodListIndex));
        createdFoodList.remove(foodToDelete);
    }


    /**
     * Returns the index of the given food in createdFoodList, given its corresponding index in specifiedFoodList
     * @param specifiedFoodListIndex the index of a food item in specifiedFoodList
     * @return the index of a food item in storeFoodList
     */
    public int getCreatedFoodListIndex(int specifiedFoodListIndex) {
         return getFoodIndexFromArrayObject(this.specifiedFoodList.get(specifiedFoodListIndex));
    }


    /**
     * Returns a list of strings representing foods in specifiedFoodList with their given positions in the list. If the
     * food is perishable, also print out the expiry status of the food.
     * @return a list of strings representing foods in specifiedFoodList with their given positions in the list
     */
    public List<String> getSpecifiedFoodListStrings() {
        List<String> foodList = new ArrayList<>();
        int index = 1;
        for (Object[] item: this.specifiedFoodList){
            Food food = (Food) getFoodFromArrayObject(item);
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
     * Helper method which returns a string representing a Food item, listed with a specified position
     * @param position the int position corresponding with the given food in specifiedFoodList
     * @param food a Food object
     * @return a string representation of the given food object with the given position
     */
    private String getFoodStringHelper(int position, Food food) {
        String foodString;
        foodString = position + ". " + food.toString();
        return foodString;
    }


    /**
     * Returns an Object representation of a Food item from an Array
     * @param foodArray an object array containing a food object at index 0, and the food's corresponding index in food
     * data at index 1
     * @return an Object representation of a Food
     */
    private static Object getFoodFromArrayObject(Object[] foodArray){
        return foodArray[0];
    }


    /**
     * Returns the index of a Food item that is stored in an object array in the form:
     * {Food, index in createdFoodList}
     *
     * @param foodArray an object array containing a food object at index 0, and the food's corresponding index in
     * storeFoodList at index 1
     * @return an int representing a Food's index in storeFoodList
     */
    private static int getFoodIndexFromArrayObject(Object[] foodArray){
        return (int) foodArray[1];
    }

    /**
     * Sets specifiedFoodList to null
     */
    public void deleteSpecifiedFoodList(){
        this.specifiedFoodList = null;
    }
}
