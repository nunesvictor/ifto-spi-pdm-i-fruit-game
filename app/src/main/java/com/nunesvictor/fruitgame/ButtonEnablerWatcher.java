package com.nunesvictor.fruitgame;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

public class ButtonEnablerWatcher implements TextWatcher {
    private Button button;

    public ButtonEnablerWatcher(Button button) {
        this.button = button;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        button.setEnabled(charSequence.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
