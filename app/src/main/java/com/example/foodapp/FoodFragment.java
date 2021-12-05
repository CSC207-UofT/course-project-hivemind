package com.example.foodapp;

import adapters.Adapter;
import android.app.AlertDialog;
import android.app.Dialog;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment implements View.OnClickListener{

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

        FloatingActionButton fab = view.findViewById(R.id.food_fab);
        fab.setOnClickListener(this);

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
}