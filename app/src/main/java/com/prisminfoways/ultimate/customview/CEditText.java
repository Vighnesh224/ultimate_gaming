package com.prisminfoways.ultimate.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CEditText extends EditText {

    public CEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/seravek_light.ttf");
            setTypeface(tf);
        }
    }
}
