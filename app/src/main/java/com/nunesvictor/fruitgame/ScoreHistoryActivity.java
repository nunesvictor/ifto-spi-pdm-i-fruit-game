package com.nunesvictor.fruitgame;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nunesvictor.fruitgame.util.AppSharedPreferences;

import org.json.JSONException;

public class ScoreHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);

        try {
            AppSharedPreferences preferences = new AppSharedPreferences(this);
            ListView scoreHistoryListView = findViewById(R.id.score_history_list_view);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.score_list_view_item,
                    R.id.score_list_view_item,
                    preferences.getScores()
            );
            scoreHistoryListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
