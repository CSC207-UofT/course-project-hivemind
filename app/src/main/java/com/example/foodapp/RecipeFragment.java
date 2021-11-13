package com.example.foodapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import input.CommandInput;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommandInput.getRecipeRecommendation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }
}