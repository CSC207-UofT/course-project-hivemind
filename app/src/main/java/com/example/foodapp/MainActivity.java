package com.example.foodapp;

import adapters.Adapter;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavView;
    Adapter adapter = new Adapter();
    FoodFragment foodFragment = new FoodFragment();
    RecipeFragment recipeFragment = new RecipeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    .addToBackStack("name") // name can be null
                    .commit();
        }
        return true;
    }
}