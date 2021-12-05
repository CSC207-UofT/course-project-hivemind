package com.example.foodapp;

import adapters.Adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment implements View.OnClickListener{

    Adapter adapter;

    View view;
    Adapter adapter = new Adapter();

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

        view = inflater.inflate(R.layout.fragment_food, container, false);

        FloatingActionButton fab = view.findViewById(R.id.food_fab);
        fab.setOnClickListener(this);

        int index = 0;

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
            index ++;
        }
        else {
            for (List<String> food : given_foods) {
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

    public void createNewFoodDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
        final View foodPopupView = getLayoutInflater().inflate(R.layout.popup,null);
        EditText newfoodpopup_foodname = foodPopupView.findViewById(R.id.foodname);
        EditText newfoodpopup_quantity = foodPopupView.findViewById(R.id.quantity);
        EditText newfoodpopup_unit = foodPopupView.findViewById(R.id.unit);
        EditText newfoodpopup_year = foodPopupView.findViewById(R.id.year);
        EditText newfoodpopup_month = foodPopupView.findViewById(R.id.month);
        EditText newfoodpopup_day = foodPopupView.findViewById(R.id.day);

        Button newfoodpopup_save = foodPopupView.findViewById(R.id.saveButton);
        Button newfoodpopup_cancel = foodPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(foodPopupView);
        Dialog dialog = dialogBuilder.create();
        dialog.show();

        assert newfoodpopup_day.getText().toString().equals("");

        newfoodpopup_save.setOnClickListener(v -> {
            try {
                if (newfoodpopup_day.getText().toString().equals("") &&
                        newfoodpopup_month.getText().toString().equals("") &&
                        newfoodpopup_year.getText().toString().equals("")) {
                    adapter.createFood(newfoodpopup_foodname.getText().toString(),
                            newfoodpopup_quantity.getText().toString(),
                            newfoodpopup_unit.getText().toString());
                }
                else {
                    adapter.createFood(newfoodpopup_foodname.getText().toString(),
                            newfoodpopup_quantity.getText().toString(),
                            newfoodpopup_unit.getText().toString(),
                            newfoodpopup_day.getText().toString(),
                            newfoodpopup_month.getText().toString(),
                            newfoodpopup_year.getText().toString());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });

        newfoodpopup_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    @Override
    public void onClick(View v) {
        createNewFoodDialog();
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
                int index = food.getId();
                Adapter.showDeletedFood(index);
                dialog.dismiss();
                Snackbar snackbar = Snackbar.make(view, "Food Deleted Successfully",
                        BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
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