package com.nunesvictor.fruitgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nunesvictor.fruitgame.util.AppSharedPreferences;

public class GameplayActivity extends AppCompatActivity {
    private Parcelable[] appFruits;
    private Parcelable[] playerFruits;
    private String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        initParceables();
        initLayoutAppSelection();
        initLayoutPlayerSelection();
        initLayoutFuitComparison();

        Button buttonRevealResult = findViewById(R.id.buttonRevealResult);
        buttonRevealResult.setOnClickListener(view -> {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("winner", winner);

            startActivity(intent);
        });
    }

    private void initParceables() {
        Intent intent = getIntent();

        appFruits = intent.getParcelableArrayExtra(getString(R.string.app_selected_fruits));
        playerFruits = intent.getParcelableArrayExtra(getString(R.string.player_selected_fruits));
    }

    private void initLayoutAppSelection() {
        LinearLayout layout = findViewById(R.id.linearLayoutAppSelection);
        layout.removeAllViewsInLayout();

        for (Parcelable parcelable : appFruits) {
            View view = getLayoutInflater().inflate(R.layout.fruit_display_item, layout);
            ImageView imageView = view.findViewById(R.id.imageViewFruit);
            TextView textView = view.findViewById(R.id.textViewFruit);
            Fruit fruit = (Fruit) parcelable;

            imageView.setId(fruit.getImage()); // previne que o mesmo elemento seja sobrescrito no loop
            imageView.setImageResource(fruit.getImage());

            textView.setId(fruit.getValue()); // previne que o mesmo elemento seja sobrescrito no loop
            textView.setText(String.valueOf(fruit.getValue()));
        }
    }

    private void initLayoutPlayerSelection() {
        LinearLayout layout = findViewById(R.id.linearLayoutPlayerSelection);
        layout.removeAllViewsInLayout();

        for (Parcelable parcelable : playerFruits) {
            View view = getLayoutInflater().inflate(R.layout.fruit_display_item, layout);
            ImageView imageView = view.findViewById(R.id.imageViewFruit);
            TextView textView = view.findViewById(R.id.textViewFruit);
            Fruit fruit = (Fruit) parcelable;

            imageView.setId(0); // previne que o mesmo elemento seja sobrescrito no loop
            imageView.setImageResource(fruit.getImage());

            textView.setId(0); // previne que o mesmo elemento seja sobrescrito no loop
            textView.setText(String.valueOf(fruit.getValue()));
        }
    }

    private void initLayoutFuitComparison() {
        LinearLayout layout = findViewById(R.id.linearLayoutFruitComparison);
        AppSharedPreferences preferences = new AppSharedPreferences(this);
        winner = "Empate";

        for (int i = 0; i < appFruits.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.fruit_display_item, layout);
            ImageView imageView = view.findViewById(R.id.imageViewFruit);

            Fruit appFruit = (Fruit) appFruits[i];
            Fruit playerFruit = (Fruit) playerFruits[i];
            int resourceId = R.drawable.ic_baseline_exposure_zero_24;

            if (!appFruit.getValue().equals(playerFruit.getValue())) {
                resourceId = appFruit.getValue() > playerFruit.getValue()
                        ? R.drawable.ic_baseline_android_24
                        : R.drawable.ic_baseline_person_24;
                winner = appFruit.getValue() > playerFruit.getValue()
                        ? "App"
                        : preferences.getPlayerName();
            }

            imageView.setId(0); // previne que o mesmo elemento seja sobrescrito no loop
            imageView.setImageResource(resourceId);
        }
    }
}
