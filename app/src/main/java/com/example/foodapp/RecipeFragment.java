package com.example.foodapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import adapters.Adapter;

import java.io.IOException;
import java.util.List;

import android.view.ViewGroup.LayoutParams;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class RecipeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Adapter adapter;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.adapter = MainActivity.adapter; // get the instance of the adapter from MainActivity, so that we use the same one
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe, container, false);

        FloatingActionButton fab = view.findViewById(R.id.recipe_fab); // get the button from the view

        fab.setOnClickListener(this); // set a listener

        List<List<String>> given_recipes = adapter.recommendRecipes(SettingsFragment.recipeAmount);

        LinearLayout recipeList = view.findViewById(R.id.recipe_list);
        for (List<String> recipe : given_recipes) { // For each recipe, generate a clickable text view and add it to the list

            TextView textView = new TextView(getContext());
            textView.setText(recipe.get(0));

            textView.setTextSize(SettingsFragment.fontSize);

            textView.setOnClickListener(v -> openRecipeDialog(recipe));

            textView.setPadding(10, 10, 10, 10);
            textView.setGravity(Gravity.TOP|Gravity.START);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            recipeList.addView(textView);
        }


        return view; // Return the new view with all the recommended recipes added
    }


    /**
     * Opens an Android Dialog to display a full recipe when the name is clicked
     * @param recipe the recipe that is being displayed
     */
    private void openRecipeDialog(List<String> recipe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View recipePopupView = getLayoutInflater().inflate(R.layout.recipe_view_popup,
                (ViewGroup) view, false);

        TextView recipe_text = recipePopupView.findViewById(R.id.recipe_display);

        String recipe_display_string = recipe.get(0) + ":\n\n" +
                recipe.get(1) +
                "\n\n" + recipe.get(2);
        recipe_text.setText(recipe_display_string);
        recipe_text.setTextSize(SettingsFragment.fontSize - 10);

        builder.setView(recipePopupView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        createRecipeDialog();
    }

    /**
     * Creates a new Android Dialog on screen that takes information from the user and creates a new recipe
     */
    private void createRecipeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View recipePopupView = getLayoutInflater().inflate(R.layout.recipe_popup, (ViewGroup) view, false);

        EditText name = recipePopupView.findViewById(R.id.recipe_popup_name); // Get the information entered
        EditText foods = recipePopupView.findViewById(R.id.recipe_popup_foods);
        EditText instructions = recipePopupView.findViewById(R.id.recipe_popup_instructions);

        Button cancel_button = recipePopupView.findViewById(R.id.cancel_recipe_button);
        Button save_button = recipePopupView.findViewById(R.id.save_recipe_button);

        builder.setView(recipePopupView);
        AlertDialog dialog = builder.create();
        dialog.show();

        cancel_button.setOnClickListener(v -> dialog.dismiss());

        save_button.setOnClickListener(v -> {
            try {
                adapter.createRecipe(name.getText().toString(), foods.getText().toString(), // Try to create
                        instructions.getText().toString());
                Snackbar snackbar = Snackbar.make(view, "Recipe Created Successfully",
                        BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            } catch (IOException e) {
                e.printStackTrace();
                Snackbar snackbar = Snackbar.make(view, "Error Creating Recipe",
                        BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            }
            dialog.dismiss();
        });
    }
}