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
import java.util.ArrayList;
import java.util.Arrays;

import android.view.ViewGroup.LayoutParams;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener {

    View view;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe, container, false);

        FloatingActionButton fab = view.findViewById(R.id.recipe_fab);

        fab.setOnClickListener(this);

//        ArrayList<String> given_recipes = CommandInput.getRecipeRecommendation();
        ArrayList<String> given_recipes = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));

        LinearLayout recipeList = view.findViewById(R.id.recipe_list);
        System.out.println(recipeList);
        for (String recipe : given_recipes) {
            System.out.println(recipe);

            TextView textView = new TextView(getContext());
            textView.setText(recipe);
            textView.setTextSize(24);
            textView.setGravity(Gravity.TOP|Gravity.START);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            recipeList.addView(textView);
        }

//        TextView testText = new TextView(getContext());
//        testText.setText("test text lol");
//        recipeList.addView(testText);

        return view;
    }

    @Override
    public void onClick(View v) {
        createRecipeDialog();
    }

    public void createRecipeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View recipePopupView = getLayoutInflater().inflate(R.layout.recipe_popup, (ViewGroup) view, false);

        EditText name = recipePopupView.findViewById(R.id.recipe_popup_name);
        EditText foods = recipePopupView.findViewById(R.id.recipe_popup_foods);
        EditText instructions = recipePopupView.findViewById(R.id.recipe_popup_instructions);

        Button cancel_button = recipePopupView.findViewById(R.id.cancel_recipe_button);
        Button save_button = recipePopupView.findViewById(R.id.save_recipe_button);

        builder.setView(recipePopupView);
        AlertDialog dialog = builder.create();
        dialog.show();

        cancel_button.setOnClickListener(v -> dialog.dismiss());

        save_button.setOnClickListener(v -> {
            //Code to parse and save recipe
        });
    }
}