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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment implements View.OnClickListener{

    private List<TextView> listFoodList = new ArrayList<>();
    private View view;
    private Adapter adapter;
    private int index = 0;

    public FoodFragment() {
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
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_food, container, false);
        LinearLayout foodList = view.findViewById(R.id.food_list);

        FloatingActionButton fab = view.findViewById(R.id.food_fab);
        fab.setOnClickListener(this); // Set a listener on the add food button

        List<List<String>> given_foods = adapter.getAllFoods();

        if (!(given_foods == null || given_foods.size() == 0)) { // If we do have food, then we can add them
            for (List<String> food : given_foods) {
                addToFoodList(foodList, food, index);
                index ++;
            }
        }

        return view;

    }

    /**
     * Creates a TextView and sets the text in the text using a List of String, food.
     * It also takes in an index, which the delete food function uses.
     * @param foodList A list of the foods
     * @param food the List of string of food that is being added
     * @param i the integer that represents the index of the food item
     */
    private void addToFoodList(LinearLayout foodList, List<String> food, int i){
        TextView no_food_text = view.findViewById(R.id.nofood); // Creates a new TextView, sets its properties
        no_food_text.setVisibility(View.GONE);
        TextView textView = new TextView(getContext());
        String foodDisplay = foodStringHelper(food);
        System.out.println(foodDisplay);
        textView.setText(foodDisplay);
        textView.setId(i);
        textView.setOnClickListener(v -> createDeleteFoodPopUp(foodList, v));
        textView.setTextSize(SettingsFragment.fontSize);
        textView.setGravity(Gravity.TOP | Gravity.START);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        foodList.addView(textView); // Add the created textview to the view of the list
        listFoodList.add(textView);

    }

    /**
     * Takes the user input using an Android Dialog to make and display a Food object.
     * [["Potato", "2 lbs"], ["Potato", "2 lbs", "Expiry Date: 3/12/2021"]]
     *
     */
    private void createNewFoodDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
        final View foodPopupView = getLayoutInflater().inflate(R.layout.popup,null);
        EditText newfoodpopup_foodname = foodPopupView.findViewById(R.id.foodname); // Get all inputs of the food
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

        // assert newfoodpopup_day.getText().toString().equals("");

        LinearLayout foodList = view.findViewById(R.id.food_list);

        newfoodpopup_save.setOnClickListener(v -> {
            List<String> labelList = new ArrayList<>();
            try {
                if (newfoodpopup_day.getText().toString().equals("") && // If there is no date specified, add nonperishable
                        newfoodpopup_month.getText().toString().equals("") &&
                        newfoodpopup_year.getText().toString().equals("")) {
                    adapter.createFood(newfoodpopup_foodname.getText().toString(),
                            newfoodpopup_quantity.getText().toString(),
                            newfoodpopup_unit.getText().toString());
                    // create an array of strings
                    labelList.add(newfoodpopup_foodname.getText().toString());
                    labelList.add(newfoodpopup_quantity.getText().toString() + " " +
                            newfoodpopup_unit.getText().toString());
                    addToFoodList(foodList,labelList,index);
                    index ++;
                }
                else { // Otherwise, add a perishablefood
                    adapter.createFood(newfoodpopup_foodname.getText().toString(),
                            newfoodpopup_quantity.getText().toString(),
                            newfoodpopup_unit.getText().toString(),
                            newfoodpopup_year.getText().toString(),
                            newfoodpopup_month.getText().toString(),
                            newfoodpopup_day.getText().toString());

                    //create an array of strings
                    labelList.add(newfoodpopup_foodname.getText().toString());
                    labelList.add(newfoodpopup_quantity.getText().toString() + " " +
                            newfoodpopup_unit.getText().toString());
                    labelList.add("Expiry Date: " + newfoodpopup_year.getText().toString() + "/" +
                            newfoodpopup_month.getText().toString()
                    + "/" + newfoodpopup_day.getText().toString());
                    addToFoodList(foodList,labelList,index);
                    index ++;

                    //popup for adding an expired foods
                    if (adapter.expiredFoodAdded(newfoodpopup_year.getText().toString(),
                            newfoodpopup_month.getText().toString(), newfoodpopup_day.getText().toString())){
                        showExpiredFoodAlertDialogButtonClicked();
                    }
                }
                Snackbar snackbar = Snackbar.make(view, "Food Added Successfully",
                        BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            }
            catch (Exception e) {
                e.printStackTrace();
                Snackbar snackbar = Snackbar.make(view, "Food Add Failed. Please try again.",
                        BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            }
            dialog.dismiss();
        });
        newfoodpopup_cancel.setOnClickListener(v -> dialog.dismiss());
    }


    public void showExpiredFoodAlertDialogButtonClicked() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Warning: Expired Food Added");
        builder.setMessage("The food you just added is expired. Click on the food to delete it from your list.");
        // add a button
        builder.setPositiveButton("OK", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        createNewFoodDialog();
    }

    /**
     * A helper method used to change the list of strings representing the food into a string
     * @param food the list representing the food
     * @return A string of the elements of the list representing the food put together to be displayed
     */
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

    /**
     * Create an Android Dialog to delete food from the list
     * @param foodList the view of the list containing the food
     * @param food the food that we are deleting
     */
    private void createDeleteFoodPopUp(LinearLayout foodList, View food){
        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        Button confirmDeleteFood, cancelDeleteFood;

        dialogBuilder = new AlertDialog.Builder(this.requireContext());
        final View deleteFoodPopUpView = getLayoutInflater().inflate(R.layout.deletefood_popup, null);
        confirmDeleteFood = deleteFoodPopUpView.findViewById(R.id.confirmDeleteFood);
        cancelDeleteFood = deleteFoodPopUpView.findViewById(R.id.cancelDeleteFood);
        dialogBuilder.setView(deleteFoodPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        confirmDeleteFood.setOnClickListener(v -> {
            //define delete button
            foodList.removeView(food);
            int foodIndex = food.getId();
            adapter.showDeletedFood(foodIndex);
            updateFoodIndexes(foodIndex);
            index = index - 1;
            dialog.dismiss();
            Snackbar snackbar = Snackbar.make(view,"Food Deleted Successfully",
                    BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.show();
        });
        cancelDeleteFood.setOnClickListener(v -> {
            //define cancel button
            dialog.dismiss();
        });
    }

    private void updateFoodIndexes(int foodIndex) {
        listFoodList.remove(foodIndex);
        for (int i = foodIndex; i <= listFoodList.size(); i++){
            TextView foodItem = listFoodList.get(i);
            int currId = foodItem.getId();
            int newId = currId - 1;
            foodItem.setId(newId);
        }
    }
}