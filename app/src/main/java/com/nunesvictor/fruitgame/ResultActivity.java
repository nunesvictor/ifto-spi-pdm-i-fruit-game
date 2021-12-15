package com.nunesvictor.fruitgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nunesvictor.fruitgame.util.AppSharedPreferences;

import org.json.JSONException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        String result = String.format("%s venceu!", winner);

        try {
            AppSharedPreferences preferences = new AppSharedPreferences(this);
            preferences.addScore(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView resultTextView = findViewById(R.id.result_text_view);
        resultTextView.setText(result);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.confirm_jogar_novamente)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) ->
                            startActivity(new Intent(this, FruitPickerActivity.class)))
                    .setNegativeButton(R.string.no, (dialogInterface, i) ->
                            startActivity(new Intent(this, ScoreHistoryActivity.class)))
                    .show();
        });
    }
}