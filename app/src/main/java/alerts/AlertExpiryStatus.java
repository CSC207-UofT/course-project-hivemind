package alerts;
import java.util.*;
import controllers.FoodController;
import input.CommandInput;

public class AlertExpiryStatus implements Runnable{
    private static final FoodController foodController = CommandInput.foodController;
    protected static Calendar dateNow = Calendar.getInstance();

    /**
     * The method that checks to see if foods have expired and alerts the user to which foods have expired
     */
    public static void alertExpiredFoods() {
        List<String> expiredFoodsList = foodController.checkPerishables();
        // If there are expired foods, list them out
        if(expiredFoodsList.size() > 0){
            System.out.println();
            System.out.println("The following foods have expired: ");
            printFoodInList(expiredFoodsList);
        }
        else{
            System.out.println();
            System.out.println("Your food is fresh and safe to consume.");
        }
    }

    /**
     * The method that formats and prints a list of food for the user to read
     * @param foodList A list of string representations of foods
     */
    private static void printFoodInList(List<String> foodList) {
        int index = 1;
        for (String food : foodList) {
            System.out.println(index + ": " + food);
            index++;
        }
    }

    /**
     * The method that is run in a parallel thread to the rest of the methods runs in main. Checks every hour
     * whether the day has changed, and if the day has changed, checks to see if any foods have expired.
     */
    @Override
    public void run() {
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            // Every hour, check to see if the date has changed. If the date has changed, run alertExpiredFoods.
            public void run () {
                Calendar newDate = Calendar.getInstance();
                if(newDate.get(Calendar.DAY_OF_MONTH) != dateNow.get(Calendar.DAY_OF_MONTH)){
                    alertExpiredFoods();
                    dateNow = Calendar.getInstance();
                }
            }
        };
        timer.schedule(hourlyTask, 0L, 1000 * 60 * 60);
    }
}