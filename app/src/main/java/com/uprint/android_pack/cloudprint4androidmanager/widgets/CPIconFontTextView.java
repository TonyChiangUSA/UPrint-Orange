package com.uprint.android_pack.cloudprint4androidmanager.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.uprint.android_pack.cloudprint4androidmanager.utils.IconfontTypeface;

/**
 * Created by zhangxiaang on 15/10/2.
 */
public class CPIconFontTextView extends TextView {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public CPIconFontTextView(Context context) {
        super(context);
        init(context);
    }

    public CPIconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CPIconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CPIconFontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setTypeface(IconfontTypeface.getTypeface(context));
        setIncludeFontPadding(false);
        buttonClick.setDuration(300);
        buttonClick.setRepeatCount(1);
    }
}
