package com.example.foodapp;

import adapters.Adapter;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static int fontSize = 30;
    public static int recipeAmount = 7;
    public static boolean isDark = true;
    View view;
    SeekBar seekbar;
    TextView textSettings;
    SeekBar recipeBar;
    TextView fontExample;
    TextView recipeText;

//    int barProgress = 50;

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        //        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        textSettings = view.findViewById(R.id.settingsText);
        textSettings.setTextSize(35);
        seekbar = view.findViewById(R.id.seekbar);
//        spinner = view.findViewById(R.id.numbers_spinner);
        fontExample = view.findViewById(R.id.fontExample);
        recipeText = view.findViewById(R.id.recipeText);
        recipeText.setTextSize(20);

        recipeBar = view.findViewById(R.id.seekbarRecipe);

        recipeBar.setMax(20);
        recipeBar.setMin(5);

        seekbar.setMax(42);
        seekbar.setMin(24);
        recipeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                recipeAmount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fontSize = progress;
                fontExample.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        seekbar.setProgress(fontSize);
        recipeBar.setProgress(recipeAmount);
        return view;
    }
}