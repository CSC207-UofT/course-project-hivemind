package controllers;


import usecases.FoodHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodController {
    public final ArrayList<List<String>> foodList;
    public final FoodHandler handler;


    public FoodController() {
        this.foodList = new ArrayList<>();
        this.handler = new FoodHandler();
    }

    /**
     * Run the FoodHandler use case where the user is attempting to create a food
     *
     * @param food the food object represented as an array list e.g ["Potato", "2.000", "grams"]
     */
    public void runFoodCreation(List<String> food) {
        // Note: hands off the work to the use case class.
        this.handler.createFood(food);
        // createFood will return true or false depending on whether the food has been made
    }

    /**
     * Run the FoodHandler use case where the user is attempting to load multiple foods
     *
     * @param foodStringList an ArrayList of String representations of food
     */
    public void loadFoodFromList(List<String> foodStringList) {

            for (String s : foodStringList) {
                String[] lst = s.split(",,");
                List<String> lst2 = new ArrayList<>(Arrays.asList(lst));
                // add each string representation of food to the controller's food list
                this.foodList.add(lst2);
            }
            // let the food handler load the controller food list
             this.handler.createMultipleFoods(this.foodList);
    }

    /**
     * Check and let command input know if stored food objects have expired
     * @return an array list of string representations of the expired foods, or an empty array list if
     * there are no expired foods
     */
    public List<String> checkPerishables(){
        /* handler will return an array list of the perished foods */
        List<String> expired_foods = this.handler.getPerishedFoods();
        if(expired_foods.size() > 0){
            return expired_foods;
        }
        else{
            return new ArrayList<>();
        }
    }

    /**
     * Initializes specifiedFoodList in Food Handler, creating a list of object arrays. Each object array has a food
     * object at index 0, which matches foodName. Index 1 contains the respective food's index.
     * @param foodName the name of the food
     * @return the size of specifiedFoodList from Food Handler
     */
    public int makeSpecifiedFoodList(String foodName){
        return this.handler.makeSpecifiedFoodList(foodName);
    }

    /**
     * Runs Food Handler to delete the food object at the specified position foodPositionToDelete
     * @param foodPositionToDelete the position of the food item in specifiedFoodList, in which the user wants to delete
     * @return a string representation of the deleted food
     */
    public String deleteFood(int foodPositionToDelete){
        int foodIndexInListToDelete = foodPositionToDelete - 1;
        this.handler.deleteFood(foodIndexInListToDelete);
        return handler.getSpecifiedFoodListStrings().get(foodIndexInListToDelete);
    }

    /**
     * Return full string form of all foods stored in the handler by calling getAllFoodString from the FoodHandler class
     * @return an arraylist of all foods in string form
     */
    public List<String> allFoodToString(){
        return this.handler.getCreatedFoodListFullString();
    }

    /**
     * Runs Food Handler and returns a string representation of specifiedFoodList
     * @return a string representation of specifiedFoodList from Food Handler
     */
    public List<String> printSpecifiedFoodList() {
        return handler.getSpecifiedFoodListStrings();
    }

    /**
     * Returns the corresponding index of a food item in food data, specified by its foodPosition in specifiedFoodList
     * @param foodPosition the position of the food item in specifiedFoodList, in which the user wishes to delete
     * @return the corresponding index of a food item in storeFoodList, specified by foodPosition
     */
    public int getFoodIndexToDelete(int foodPosition) {
        int specifiedFoodIndex = foodPosition - 1;
        return handler.getCreatedFoodListIndex(specifiedFoodIndex);
    }

    /**
     * Runs Food Handler to delete specifiedFoodList
     */
    public void foodDeletedFromSystem() {handler.deleteSpecifiedFoodList();
    }
}

