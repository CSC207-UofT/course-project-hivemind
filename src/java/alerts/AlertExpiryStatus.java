package alerts;
import java.util.*;
import controllers.FoodController;
import controllers.RecipeController;

public class AlertExpiryStatus{
    private static final FoodController foodController = new FoodController();

    public static void alertExpiredFoods() {
        List<String> expiredFoodsList = foodController.checkPerishables();
        if(expiredFoodsList.size() > 0){
            System.out.println("The following foods have expired: ");
            ArrayList<Object[]> parsedExpiredFoodsList = new ArrayList<>();
            for (String food : expiredFoodsList){
                Object[] parsedFood = new Object[1];
                parsedFood[0] = food;
                parsedExpiredFoodsList.add(parsedFood);
            }
            printFoodInList(parsedExpiredFoodsList);
        }
        else{
            System.out.println("Your food is fresh and safe to consume.");
        }
    }
    private static void printFoodInList(ArrayList<Object[]> foodList) {
        int index = 1;
        for (Object[] foods : foodList) {
            Object food = foods[0];
            System.out.println(index + ": " + food);
            index++;
        }
    }
}
