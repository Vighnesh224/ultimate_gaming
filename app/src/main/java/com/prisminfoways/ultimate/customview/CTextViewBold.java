package com.prisminfoways.ultimate.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CTextViewBold extends TextView {

    public CTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CTextViewBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/avenirblack.otf");
            setTypeface(tf);
        }
    }
}
