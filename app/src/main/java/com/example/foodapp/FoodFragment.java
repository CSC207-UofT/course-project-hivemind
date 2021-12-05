package com.example.foodapp;

import adapters.Adapter;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    Adapter adapter;

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.adapter = MainActivity.adapter;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_food, container, false);
        List<List<String>> given_foods = adapter.getAllFoods();

        LinearLayout foodList = view.findViewById(R.id.food_list);
        if (given_foods == null || given_foods.size() == 0) {
            String display_string = "You've got no food!";
            TextView textView = new TextView(getContext());
            textView.setText(display_string);
            textView.setTextSize(24);
            textView.setGravity(Gravity.TOP | Gravity.START);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            foodList.addView(textView);
        }
        else {
            for (List<String> food : given_foods) {

                TextView textView = new TextView(getContext());
                textView.setText(food.get(0));
                textView.setTextSize(24);
                textView.setGravity(Gravity.TOP | Gravity.START);
                textView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                foodList.addView(textView);
            }
        }
        return view;

    }
}