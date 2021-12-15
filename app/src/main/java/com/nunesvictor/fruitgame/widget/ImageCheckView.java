package com.nunesvictor.fruitgame.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.nunesvictor.fruitgame.R;

public class ImageCheckView extends AppCompatImageView implements Checkable {
    private boolean checked;
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    public ImageCheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) mergeDrawableStates(drawableState, CHECKED_STATE_SET);

        return drawableState;
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public void setChecked(boolean b) {
        if (checked != b) {
            checked = b;

            setBackgroundResource(checked
                    ? R.drawable.imagecheck_checked_background
                    : R.drawable.imagecheck_unchecked_background);

            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
