package com.nunesvictor.fruitgame.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nunesvictor.fruitgame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppSharedPreferences {
    private final Context context;
    private final SharedPreferences preferences;

    public AppSharedPreferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
    }

    public String getPlayerName() {
        return preferences.getString(
                context.getString(R.string.saved_player_name_key),
                context.getString(R.string.player_editText_hint));
    }

    public void setPlayerName(String playerName) {
        SharedPreferences.Editor editor = preferences.edit();

        // faz com que o SharedPreferences seja limpo sempre que trocar de Player
        editor.clear();

        editor.putString(context.getString(R.string.saved_player_name_key), playerName);
        editor.apply();
    }

    public void addScore(String result) throws JSONException {
        SharedPreferences.Editor editor = preferences.edit();
        JSONArray jsonArray = new JSONArray(preferences.getString(context.getString(R.string.saved_scores_key), "[]"));
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("result", result);
        jsonArray.put(jsonObject);

        editor.putString(context.getString(R.string.saved_scores_key), jsonArray.toString());
        editor.apply();
    }

    public String[] getScores() throws JSONException {
        JSONArray jsonArray = new JSONArray(preferences.getString(context.getString(R.string.saved_scores_key), "[]"));
        String[] scores = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            scores[i] = jsonArray.getJSONObject(i).getString("result");
        }

        return scores;
    }
}
