package com.example.foodapp;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import adapters.Adapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.ViewGroup.LayoutParams;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment{

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
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

//        ArrayList<String> given_recipes = CommandInput.getRecipeRecommendation();

        ArrayList<String> given_recipes = new ArrayList<>(Arrays.asList("meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat"));

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
}