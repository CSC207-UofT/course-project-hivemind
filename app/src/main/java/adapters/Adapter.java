package adapters;
import controllers.FoodController;
import controllers.RecipeController;
import parsers.DataParser;
import java.io.IOException;
import java.util.*;

public class Adapter {

    public static final FoodController foodController = new FoodController();
    public static final RecipeController recipeController = new RecipeController();

    // FOOD ADAPTER
    // TODO: Return an ArrayList of Strings instead

    /**
     *  output: [["Potato", "2 lbs"], ["Potato", "2 lbs", "Expiry Date: 3/12/2021"]]
     *
     */
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

    public static List<String> createFood(String name, String amount, String unit){
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit));
        foodController.runFoodCreation(food);
        return new ArrayList<>( Arrays. asList(name, amount + ' ' + unit));
    }

    public static List<String> createFood(String name, String amount, String unit, String day, String month, String year){
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit, day, month, year));
        foodController.runFoodCreation(food);
        return new ArrayList<>( Arrays. asList(name, amount + ' ' + unit,
                "Expiry date: " + day + "/" + month + "/" + year));
    }
    public static ArrayList<List<String>> showPerishables(){
        try {
            ArrayList<String> foodData = DataParser.readFile(DataParser.FOOD_FILE);
            foodController.loadFoodFromList(foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodController.foodList;
    }
    public static void showDeletedFood(Integer foodPositionToDelete) {
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
    // RECIPE ADAPTER
    public static List<List<String>> loadRecipes(){
        List<List<String>> presentableRecipeList = new ArrayList<>();
        try {
            ArrayList<String> recipeData = DataParser.readFile(DataParser.RECIPE_FILE);
            recipeController.initialLoad(recipeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(List<String> recipe : recipeController.recipeRawArray){
            helper(presentableRecipeList, recipe);
        }
        return presentableRecipeList;
    }
    /**
     * ingredients input: potato 1bs 1 potato lbs 2
     *
     */
    public static List<String> createRecipe(String name, String ingredients, String steps){
        recipeController.addRecipe(name, ingredients, steps);
        String[] ingrSplit = ingredients.split(" ");
        List<String> a1 = Arrays.asList(ingrSplit);
        ArrayList<String> a2 = new ArrayList<>(a1);
        String presentableIngredients = "";
        int i = 1;
        StringBuilder ingredientsBuilder = new StringBuilder(ingredients);
        while(i < a2.size() - 1){
            ingredientsBuilder.append(a2.get(i)).append(" ");
            ingredientsBuilder.append(a2.get(i + 1)).append(" ");
            ingredientsBuilder.append(a2.get(i + 2)).append(". ");
            i += 3;
        }
        return new ArrayList<>(Arrays.asList(name, presentableIngredients, steps));
    }
    public static List<List<String>> recommendRecipes(int amount){
        ArrayList<String> recipes = recipeController.recommendRecipe(amount);
        List<List<String>> presentableRecipeList = new ArrayList<>();
        for(String unpolishedRecipe : recipes){
            String[] separated = unpolishedRecipe.split(" Ingredients:");
            String[] nameArray = separated[0].split(": ");
            String name = nameArray[1];
            for(List<String> recipe : recipeController.recipeRawArray){
                if(Objects.equals(recipe.get(0), name)){
                    helper(presentableRecipeList, recipe);
                }
            }

        }
        return presentableRecipeList;

    }

    private static void helper(List<List<String>> presentableRecipeList, List<String> recipe) {
        StringBuilder ingredients = new StringBuilder();
        int i = 1;
        while(i < recipe.size() - 1){
            ingredients.append(recipe.get(i)).append(" ");
            ingredients.append(recipe.get(i + 1)).append(" ");
            ingredients.append(recipe.get(i + 2)).append(".");
            ingredients.append(recipe.get(i + 2)).append(" ");
            i += 3;
        }
        List<String> presentableRecipe = new ArrayList<>(Arrays.asList(recipe.get(0), ingredients.toString(), recipe.get(recipe.size() - 1)));
        presentableRecipeList.add(presentableRecipe);
    }

}
