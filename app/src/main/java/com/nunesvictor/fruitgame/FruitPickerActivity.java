package com.nunesvictor.fruitgame;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nunesvictor.fruitgame.util.AppSharedPreferences;
import com.nunesvictor.fruitgame.widget.ImageCheckView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FruitPickerActivity extends AppCompatActivity {
    private final List<Fruit> dataSet = new ArrayList<>();
    private final Set<Fruit> appSelectedSet = new LinkedHashSet<>();
    private final Set<Fruit> playerSelectedFruits = new LinkedHashSet<>();
    
    private Button buttonPickAndPlay;
    private ProgressBar progressBarIndeterminate;
    private TextView textViewPickFruitsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_picker);

        initComponents();
        initTableLayout();
    }

    private void initComponents() {
        textViewPickFruitsMessage = findViewById(R.id.textViewPickFruitsMessage);
        textViewPickFruitsMessage.setText(getString(R.string.wait_message));

        progressBarIndeterminate = findViewById(R.id.progressBarIndeterminate);
        progressBarIndeterminate.setVisibility(View.VISIBLE);

        buttonPickAndPlay = findViewById(R.id.buttonPickAndPlay);
        buttonPickAndPlay.setText(getString(R.string.wait_message));
        buttonPickAndPlay.setOnClickListener(view -> {
            progressBarIndeterminate.setVisibility(View.VISIBLE);
            textViewPickFruitsMessage.setText(R.string.app_picking_message);

            view.postDelayed(() -> {
                Random random = new Random();
                appSelectedSet.clear();

                while (appSelectedSet.size() < 5)
                    appSelectedSet.add(dataSet.get(random.nextInt(10)));

                progressBarIndeterminate.setVisibility(View.GONE);

                Intent intent = new Intent(this, GameplayActivity.class);

                intent.putExtra(
                        getString(R.string.app_selected_fruits),
                        appSelectedSet.toArray(new Fruit[0]));
                intent.putExtra(
                        getString(R.string.player_selected_fruits),
                        playerSelectedFruits.toArray(new Fruit[0]));

                startActivity(intent);
            }, 1000);
        });
    }

    private void initDataSet() {
        TypedArray images = getResources().obtainTypedArray(R.array.fruit_icons);
        String[] fruits = getResources().getStringArray(R.array.fruit_names);
        Integer[] values = getFruitValues(fruits.length);

        for (int i = 0; i < fruits.length; i++)
            dataSet.add(new Fruit(fruits[i], values[i], images.getResourceId(i, 0)));

        Collections.shuffle(dataSet);
        images.recycle();
    }

    private void initTableLayout() {
        AppSharedPreferences preferences = new AppSharedPreferences(this);
        String playerName = preferences.getPlayerName();

        new Thread(() -> {
            initDataSet();

            runOnUiThread(() -> {
                TableLayout tableLayout = findViewById(R.id.tableLayout);
                int chunkSize = 2;

                for (int i = 0; i < dataSet.size(); i += chunkSize) {
                    List<Fruit> fruitSubList = dataSet.subList(
                            i, Math.min(dataSet.size(), i + chunkSize));
                    TableRow tableRow = new TableRow(this);

                    for (Fruit fruit : fruitSubList)
                        runOnUiThread(() -> inflateFruitItem(tableRow, fruit));

                    tableLayout.addView(tableRow);
                }

                textViewPickFruitsMessage.setText(getString(R.string.pick_fruits_text, playerName));
                buttonPickAndPlay.setText(getString(R.string.pick_fruits_and_play));
                progressBarIndeterminate.setVisibility(View.GONE);
            });

        }).start();
    }

    private void inflateFruitItem(TableRow tableRow, Fruit fruit) {
        View view = getLayoutInflater().inflate(
                R.layout.fruit_picker_item, tableRow, false);

        TextView textView = view.findViewById(R.id.textViewFruit);
        textView.setText(fruit.getName());

        ImageCheckView imageCheckView = view.findViewById(R.id.imageCheckViewFruit);
        imageCheckView.setImageResource(fruit.getImage());
        imageCheckView.setOnClickListener(v -> {
            ImageCheckView imageView = (ImageCheckView) v;

            if (playerSelectedFruits.size() >= 5 && imageView.isChecked())
                imageView.setChecked(false);

            if (imageView.isChecked())
                playerSelectedFruits.add(fruit);
            else playerSelectedFruits.remove(fruit);

            buttonPickAndPlay.setEnabled(playerSelectedFruits.size() == 5);
        });

        tableRow.addView(view);
    }

    private Integer[] getFruitValues(int length) {
        Set<Integer> set = new LinkedHashSet<>();
        Random random = new Random();

        while (set.size() < length)
            set.add(random.nextInt(100));

        return set.toArray(new Integer[length]);
    }
}
