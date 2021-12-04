package adapters;
import controllers.FoodController;
import parsers.DataParser;
import java.io.IOException;
import java.util.*;

public class FoodAdapter {

    public final FoodController foodController;

    public FoodAdapter(){
        this.foodController = new FoodController();
    }

    // TODO: Return an ArrayList of Strings instead
    public List<List<String>> loadFoods(){
        List<List<String>> presentableFoodList = new ArrayList<>();
        try {
            ArrayList<String> foodData = DataParser.readFile(DataParser.FOOD_FILE);
            foodController.loadFoodFromList(foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(List<String> food: foodController.foodList){
            List<String> presentableFood;
            if(food.size() < 4){
                presentableFood = new ArrayList<>(Arrays.asList(food.get(0), food.get(1) + " " + food.get(2)));
            }
            else{
                presentableFood = new ArrayList<>(Arrays.asList(food.get(0),
                        food.get(1) + " " + food.get(2),
                        "Expiry date: " + food.get(5) + "/" + food.get(4) + "/" + food.get(3)));
            }
            presentableFoodList.add(presentableFood);

        }
        return presentableFoodList;
    }

    public List<String> createFood(String name, String amount, String unit){
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit));
        foodController.runFoodCreation(food);
        return new ArrayList<>( Arrays. asList(name, amount + ' ' + unit));
    }

    public List<String> createFood(String name, String amount, String unit, String day, String month, String year){
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit, day, month, year));
        foodController.runFoodCreation(food);
        return new ArrayList<>( Arrays. asList(name, amount + ' ' + unit,
                "Expiry date: " + day + "/" + month + "/" + year));
    }
    public ArrayList<List<String>> showPerishables(){
        try {
            ArrayList<String> foodData = DataParser.readFile(DataParser.FOOD_FILE);
            foodController.loadFoodFromList(foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodController.foodList;
    }
    public void showDeletedFood(Integer foodPositionToDelete) {
        int foodIndexToDelete = foodController.getFoodIndexToDelete(foodPositionToDelete);
        foodController.deleteFood(foodPositionToDelete);
        try{
            DataParser.deleteRowFromFoodFile(foodIndexToDelete);
        }
        catch (Exception e){
            System.out.println("An error occurred, did not successfully delete food. " +
                    "Please verify that all arguments are of the proper data type and format");
        }
    }
}
