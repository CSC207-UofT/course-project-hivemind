package com.example.foodapp;
import android.os.Bundle;
import android.widget.*;
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

    public static int fontSize = 30;
    public static int recipeAmount = 7;
    View view;
    SeekBar seekbar;
    TextView textSettings;
    SeekBar recipeBar;
    TextView fontExample;
    TextView recipeText;

    /** Required Empty public constructor
     */
    public SettingsFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    /** Runs the super class's onCreate for settings fragment
     *
     * @param savedInstanceState Saved instance state as a bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /** Displays a variety of settings pieces:
     * -Settings Text
     * -Text size seekbar
     * -recipe text
     * -Recipe number seekbar
     *
     * @param inflater inflater for settings fragment
     * @param container container for settings fragment
     * @param savedInstanceState saved instance state for settings fragment
     * @return view for default
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        textSettings = view.findViewById(R.id.settingsText);
        textSettings.setTextSize(35);
        seekbar = view.findViewById(R.id.seekbar);
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