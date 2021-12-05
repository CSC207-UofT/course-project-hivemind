package com.example.foodapp;

import adapters.Adapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    View view;

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_food, container, false);

//        ArrayList<String> given_recipes = CommandInput.getRecipeRecommendation();

//        //output: [["Potato", "2 lbs"], ["Potato", "2 lbs", "Expiry Date: 3/12/2021"]]
        //Adapter adapter = new Adapter();
        //List<List<String>> foodsList = adapter.loadFoods();

        ArrayList<String> food1 = new ArrayList<>();
        food1.add("Soup");
        food1.add("2 lbs");
        ArrayList<String> food2 = new ArrayList<>();
        food2.add("Potato");
        food2.add("2 lbs");
        food2.add("Expiry Date: 3/12/2021");
        ArrayList<String> food3 = new ArrayList<>();
        food3.add("Meat");
        food3.add("3 metric tons");
        ArrayList<String> food4 = new ArrayList<>();
        food4.add("Meat");
        food4.add("16 Kilo tons");

        ArrayList<ArrayList<String>> givenFoods = new ArrayList<>();
        givenFoods.add(food1);
        givenFoods.add(food2);
        givenFoods.add(food3);
        givenFoods.add(food4);

        int index = 1;


        LinearLayout foodList = view.findViewById(R.id.food_list);
        for (List<String> food : givenFoods) {
            System.out.println(food);

            TextView textView = new TextView(getContext());
            String foodDisplay = foodStringHelper(food);
            textView.setText(foodDisplay);
            //String foodID = foodIDHelper(food, index);
            textView.setId(index);
            textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    createDeleteFoodPopUp(foodList, v);
                }
            });

            textView.setTextSize(24);
            textView.setGravity(Gravity.TOP|Gravity.START);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            foodList.addView(textView);
            index ++;
        }

//        TextView testText = new TextView(getContext());
//        testText.setText("test text lol");
//        recipeList.addView(testText);

        return view;

    }

    private String foodIDHelper(ArrayList<String> food, int index) {
        return food.get(0) + index;
    }

    private String foodStringHelper(List<String> food) {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < food.size(); i++){
            if (i == 0) {
                name.append(food.get(i).toUpperCase()).append(" ");
            }
            else if (i == food.size() -1) {
                name.append(food.get(i));
            }
            else{
                name.append(food.get(i)).append(" ");
            }
        }
        return name.toString();
    }

    public void createDeleteFoodPopUp(LinearLayout foodList, View food){
        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        Button confirmDeleteFood, cancelDeleteFood;

        dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(this.getContext()));
        final View deleteFoodPopUpView = getLayoutInflater().inflate(R.layout.deletefood_popup, null);
        confirmDeleteFood = (Button) deleteFoodPopUpView.findViewById(R.id.confirmDeleteFood);
        cancelDeleteFood = (Button) deleteFoodPopUpView.findViewById(R.id.cancelDeleteFood);
        dialogBuilder.setView(deleteFoodPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        confirmDeleteFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //define delete button
                foodList.removeView(food);
                dialog.dismiss();
            }
        });
        cancelDeleteFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //define cancel button
                dialog.dismiss();
            }
        });
    }
}