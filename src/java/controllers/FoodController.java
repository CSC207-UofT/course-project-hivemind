package controllers;

import usecases.FoodHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class FoodController {
    private final ArrayList<ArrayList<String>> foodList;
    private final FoodHandler handler;

    public FoodController() {
        this.foodList = new ArrayList<>();
        this.handler = new FoodHandler();
    }

    /**
     * Run the FoodHandler use case where the user is attempting to create a food
     * @param food the food object represented as an array list e.g ["Potato", "2.000", "grams"]
     */

    public void runFoodCreation(ArrayList<String> food) {
        // Note: hands off the work to the use case class.
        this.handler.createFood(food);
        // createFood will return true or false depending on whether the food has been made
    }

    /**
     * Run the FoodHandler use case where the user is attempting to load multiple foods
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


}
