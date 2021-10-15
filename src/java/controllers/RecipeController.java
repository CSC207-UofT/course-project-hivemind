package controllers;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import usecases.RecipeHandler;

public class RecipeController {
    //TODO: implement this class
    protected ArrayList<Object> recipeRawArray;
    private final RecipeHandler handler;

    public RecipeController() {
        this.recipeRawArray = new ArrayList<Object>();
        this.handler = new RecipeHandler();
    }

    public void initialLoad(ArrayList<String> csvLines){
        for (String i : csvLines){
            ArrayList<Object> out = new ArrayList<Object>();

            String[] separated = i.split(",");
            List<String> a = Arrays.asList(separated);

            String name = a.get(0);
            String instructions = a.get(a.size() - 1);


            HashMap<String, ArrayList<Object>> ingredients = new HashMap<String, ArrayList<Object>>();
            for (int j = 1; j < a.size() -2; j = j+3){
                ArrayList<Object> ingredientProperties = new ArrayList<Object>();
                Double quantity = Double.parseDouble(a.get(j+1));
                String unit = a.get(j+2);
                ingredientProperties.add(quantity);
                ingredientProperties.add(unit);
                ingredients.put(a.get(j), ingredientProperties);

            }

            out.add(name);
            out.add(ingredients);
            out.add(instructions);

            this.recipeRawArray.add(out);

        }
        handler.initializeRecipe(this.recipeRawArray);

    }

}
