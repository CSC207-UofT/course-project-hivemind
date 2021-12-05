package com.example.foodapp;

import adapters.Adapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment implements View.OnClickListener{

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newfoodpopup_foodname, newfoodpopup_quantity, newfoodpopup_unit, newfoodpopup_year, newfoodpopup_month, newfoodpopup_day;
    private Button newfoodpopup_save, newfoodpopup_cancel;

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

        View view = inflater.inflate(R.layout.fragment_food, container, false);

        FloatingActionButton fab = view.findViewById(R.id.food_fab);
        fab.setOnClickListener(this);
//        ArrayList<String> given_recipes = CommandInput.getRecipeRecommendation();

        ArrayList<String> given_foods = new ArrayList<>(Arrays.asList("meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat", "meat"));

        LinearLayout foodList = view.findViewById(R.id.food_list);
        for (String food : given_foods) {
            System.out.println(food);

            TextView textView = new TextView(getContext());
            textView.setText(food);
            textView.setTextSize(24);
            textView.setGravity(Gravity.TOP|Gravity.START);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            foodList.addView(textView);
        }
//        TextView testText = new TextView(getContext());
//        testText.setText("test text lol");
//        recipeList.addView(testText);

        return view;

    }

//    @Override
//    public void onOptionsItemSelected(@NonNull MenuItem item){
//
//    }

    public void createNewFoodDialog(){
        dialogBuilder = new AlertDialog.Builder(this.getContext());
        final View foodPopupView = getLayoutInflater().inflate(R.layout.popup,null);
        newfoodpopup_foodname = foodPopupView.findViewById(R.id.foodname);
        newfoodpopup_quantity = foodPopupView.findViewById(R.id.quantity);
        newfoodpopup_unit = foodPopupView.findViewById(R.id.unit);
        newfoodpopup_year = foodPopupView.findViewById(R.id.year);
        newfoodpopup_month = foodPopupView.findViewById(R.id.month);
        newfoodpopup_day = foodPopupView.findViewById(R.id.day);

        newfoodpopup_save = foodPopupView.findViewById(R.id.saveButton);
        newfoodpopup_cancel = foodPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(foodPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newfoodpopup_save.setOnClickListener(v -> {
            //define save button
        });

        newfoodpopup_cancel.setOnClickListener(v -> {
            //define cancel button
            dialog.dismiss();
        });
    }

    @Override
    public void onClick(View v) {
        createNewFoodDialog();
    }
}