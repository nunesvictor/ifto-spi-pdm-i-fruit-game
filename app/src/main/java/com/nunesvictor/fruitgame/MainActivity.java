package com.nunesvictor.fruitgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.nunesvictor.fruitgame.util.AppSharedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNome = findViewById(R.id.editTextNome);
        Button buttonPlay = findViewById(R.id.buttonPlay);

        editTextNome.addTextChangedListener(new ButtonEnablerWatcher(buttonPlay));

        buttonPlay.setOnClickListener(view -> {
            AppSharedPreferences preferences = new AppSharedPreferences(this);
            preferences.setPlayerName(editTextNome.getText().toString());

            Intent intent = new Intent(getApplicationContext(), FruitPickerActivity.class);
            startActivity(intent);
        });
    }
}
