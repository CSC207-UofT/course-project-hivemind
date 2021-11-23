package controllers;


import usecases.FoodHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class FoodController {
    public final ArrayList<ArrayList<String>> foodList;
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
    public void runFoodCreation(ArrayList<String> food) {
        // Note: hands off the work to the use case class.
        this.handler.createFood(food);
        // createFood will return true or false depending on whether the food has been made
    }

    /**
     * Run the FoodHandler use case where the user is attempting to load multiple foods
     *
     * @param foodStringList an ArrayList of String representations of food
     */
    public void initialLoad(ArrayList<String> foodStringList) {

            for (String s : foodStringList) {
                String[] lst = s.split(",,");
                ArrayList<String> lst2 = new ArrayList<>(Arrays.asList(lst));
                this.foodList.add(lst2);
            }
             this.handler.initialLoad(this.foodList);
    }

    /**
     * TODO: add documentation
     * @return
     */
    public ArrayList<String> checkPerishables(){
        ArrayList<String> expired_foods = this.handler.getPerishedFoods();
        if(expired_foods.size() > 0){
            return expired_foods;
        }
        else{
            return new ArrayList<>();
        }
    }

    /**
     * Creates an array list of object arrays. Each object array contains a food object matching the name specified by
     * parameter foodName at index 0, and the food's index in fooddata at index 1
     * @param foodName the name of the food
     * @return an array list of object arrays. Each object array contains a food object matching the name specified by
     * parameter foodName at index 0, and the food's index in fooddata at index 1
     */
    public ArrayList<Object[]> getSpecifiedFoodList(String foodName){
        return this.handler.getSpecifiedFoodList(foodName);
    }

    /**
     * Deletes a food object from the system
     * @param food the food object which is to be deleted
     */
    public void deleteFood(Object food) {
        this.handler.deleteFood(food);
    }

    /**
     * Return full string form of all foods stored in the handler by calling getAllFoodString from the FoodHandler class
     * @return an arraylist of all foods in string form
     */
    public ArrayList<String> allFoodToString(){
        return this.handler.getAllFoodFullString();
    }

    /**
     * TODO: add documentation
     * @param index
     * @param food
     * @return
     */
    public String printFood(int index, Object food) {
        return handler.printFood(index, food);
    }
}

