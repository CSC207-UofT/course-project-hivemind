package com.example.foodapp;

import adapters.Adapter;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static Adapter adapter;
    BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.adapter = new Adapter(this);

        MainActivity.adapter.loadFoods();
        MainActivity.adapter.loadRecipes();

        setContentView(R.layout.activity_main);
        showExpiredFoodsAlert();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavView = findViewById(R.id.navigation);
        bottomNavView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.toString().equals("Food")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.placeholder, FoodFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("food") // name can be null
                    .commit();
        }
        else if (item.toString().equals("Recipe")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.placeholder, RecipeFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("recipe") // name can be null
                    .commit();
        }
        else if (item.toString().equals("Settings")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.placeholder, SettingsFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("setting") // name can be null
                    .commit();
        }
        return true;
    }
    public void showExpiredFoodsAlert() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your food status: ");
        List<List<String>> expiredFoodList = adapter.showPerishables();
        StringBuilder presentableFoodString = new StringBuilder();
        if(expiredFoodList.size() > 0){
            for(List<String> food : expiredFoodList){
                String presentableExpiredFood = food.get(0) + ": " + food.get(1) + ". " + food.get(2) + ".\n ";
                presentableFoodString = new StringBuilder(presentableFoodString + presentableExpiredFood);
            }

        }
        else {
            presentableFoodString = new StringBuilder("All your food is fresh!");
        }
        builder.setMessage(presentableFoodString);

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}