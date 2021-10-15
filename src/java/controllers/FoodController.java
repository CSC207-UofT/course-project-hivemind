//package controllers;
//
//import entities.Food;
//import usecases.FoodHandler;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//
//public class FoodController {
//    private final ArrayList<ArrayList<Object>> foodList;
//    private FoodHandler handler;
//
//    public FoodController() {
//        this.foodList = new ArrayList<ArrayList<Object>>();
//        this.handler = new FoodHandler();
//    }
//
//    /**
//     * Run the FoodHandler use case where the user is attempting to create a food
//     * @param food the food object represented as an array list e.g ["Potato", "2.000", "grams"]
//     */
//
//    public Boolean runFoodCreation(ArrayList<Object> food) {
//        // Note: hands off the work to the use case class.
//        Boolean result = this.handler.createFood(food);
//        // createFood will return true or false depending on whether the food has been made
//        return result;
//    }
//
//    /**
//     * Run the FoodHandler use case where the user is attempting to load multiple foods
//     * @param foodStringList an ArrayList of String representations of food
//     */
//    public void initialLoad(ArrayList<String> foodStringList) {
//        try{
//            for (String s : foodStringList) {
//                String[] lst = s.split(",", 3);
//                ArrayList<Object> lst2 = new ArrayList<>();
//                int counter = 0;
//                for (String i : lst) {
//                    if (counter == 1) {
//                        lst2.add(Double.parseDouble(i));
//                    } else {
//                        lst2.add(i);
//                    }
//                    counter += 1;
//                }
//                this.foodList.add(lst2);
//            }
//             this.handler.initialLoad(this.foodList);
//        }
//        finally {
//            throw new RuntimeException("Something went wrong somewhere");
//        }
//
//
//    }
//
//
//}
