package adapters;
import controllers.FoodController;
import controllers.RecipeController;
import parsers.DataParser;
import java.io.IOException;
import java.util.*;

public class Adapter {

    public final FoodController foodController;
    public final RecipeController recipeController;

    public Adapter(){
        this.foodController = new FoodController();
        this.recipeController = new RecipeController();
    }
    // FOOD ADAPTER
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

    public List<String> createFood(String name, String amount, String unit) throws IOException {
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit));
        foodController.runFoodCreation(food);
        DataParser.writeToFile(food.get(0) + ",," + food.get(1) +
                ",," + food.get(2), DataParser.FOOD_FILE);
        return new ArrayList<>( Arrays. asList(name, amount + ' ' + unit));
    }



    public List<String> createFood(String name, String amount, String unit, String day, String month, String year) throws IOException {
        ArrayList<String> food = new ArrayList<>( Arrays. asList(name, amount, unit, day, month, year));
        foodController.runFoodCreation(food);
        DataParser.writeToFile(food.get(0) + ",," + food.get(1) + ",," + food.get(2) + ",," + food.get(5) +
                ",," + food.get(4) + ",," + food.get(3), DataParser.FOOD_FILE);
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
    // RECIPE ADAPTER
    public List<List<String>> loadRecipes(){
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
    public List<String> createRecipe(String name, String ingredients, String steps) throws IOException {
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
        String s = ingredients.replace(" ", ",,");
        DataParser.writeToFile(name + ",," + s + ",," + steps, DataParser.RECIPE_FILE);
        return new ArrayList<>(Arrays.asList(name, presentableIngredients, steps));
    }

    public List<List<String>> recommendRecipes(int amount){
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

    private void helper(List<List<String>> presentableRecipeList, List<String> recipe) {
        StringBuilder ingredients = new StringBuilder();
        int i = 1;
        while(i < recipe.size() - 1){
            ingredients.append(recipe.get(i)).append(" ");
            ingredients.append(recipe.get(i + 1)).append(" ");
            ingredients.append(recipe.get(i + 2)).append(".").append(" ");
            i += 3;
        }
        List<String> presentableRecipe = new ArrayList<>(Arrays.asList(recipe.get(0), ingredients.toString(), recipe.get(recipe.size() - 1)));
        presentableRecipeList.add(presentableRecipe);
    }

}
