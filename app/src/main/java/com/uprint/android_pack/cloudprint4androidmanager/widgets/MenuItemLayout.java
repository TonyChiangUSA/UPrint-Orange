package com.uprint.android_pack.cloudprint4androidmanager.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uprint.android_pack.cloudprint4androidmanager.R;


/**
 * Created by zhangxiaang on 15/10/2.
 */
public class MenuItemLayout extends LinearLayout {
    private Context mContext;
    private CPIconFontTextView iconFontTextView;
    private TextView textView;
    private boolean flag;

    public MenuItemLayout(Context context) {
        this(context, null);
    }

    public MenuItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MenuItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.cp_menuitem, this, true);
        iconFontTextView = (CPIconFontTextView) view.findViewById(R.id.iconfonttextview);
        textView = (TextView) view.findViewById(R.id.nameTextView);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuItemLayout);
        String iconfont = array.getString(R.styleable.MenuItemLayout_iconfontView);
        String name = array.getString(R.styleable.MenuItemLayout_infotextView);
        array.recycle();
        setIconFontTextView(iconfont);
        setNameText(name);

    }

    private void setIconFontTextView(String str) {
        if (str != null && str.trim().length() > 0) {
            iconFontTextView.setText(str);
            iconFontTextView.setTextSize(24);
        }
    }

    private void setNameText(String str) {
        if (str != null && str.trim().length() > 0) {
            textView.setVisibility(VISIBLE);
            textView.setTextSize(14);
            textView.setText(str);
        } else {
            textView.setVisibility(GONE);
        }
    }

    public void onPressed() {
        if (!flag) {
            iconFontTextView.setTextColor(getResources().getColor(R.color.theme_color_primary));
            textView.setTextColor(getResources().getColor(R.color.theme_color_primary));
        }
        flag = true;
    }

    public void resume() {
        if (flag) {
            iconFontTextView.setTextColor(getResources().getColor(R.color.theme_color_second_text));
            textView.setTextColor(getResources().getColor(R.color.theme_color_second_text));
        }
        flag = false;
    }
}
