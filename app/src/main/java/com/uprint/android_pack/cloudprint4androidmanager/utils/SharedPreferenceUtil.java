package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.uprint.android_pack.cloudprint4androidmanager.activity.CPLoginActivity;

/**
 * Created by zhangxiaang on 15/10/16.
 * storage/get/judge/refresh the info of token and other configuration
 */
public class SharedPreferenceUtil {
    public static final String TOKEN_PREFERENCES_NAME = "cloud_print_token";
    public static final String USER_PREFERENCES_NAME = "cloud_print_user";
    public static final String USER_COMPLAINS_LAST_ID = "user_complains_id";
    public static final String USER_ORDER_FIRST_ID = "user_order_first_id";
    public static final String USER_ARRIBED_ORDER_ID = "aer_arrived_order_id";

    public static void storageUserState(Context context, int state) {
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("state", state);
        editor.apply();
    }

    //-1 默认未完善信息
    public static int getState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_APPEND);
        int state = preferences.getInt("state", -1);
        return state;
    }

    public static void storage_Token(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String get_Token(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCES_NAME, Context.MODE_APPEND);
        String token = preferences.getString("token", "");
        return token;
    }

    /**
     * 如果不存在的话说明还没登录呢
     */
    public static boolean has_Token(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCES_NAME, Context.MODE_APPEND);
        String token = preferences.getString("token", "");
        if (token.trim().length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * check the token
     */
    public static void checkToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCES_NAME, Context.MODE_APPEND);
        String token = preferences.getString("token", "");
        if (token.trim().length() == 0) {
            Intent intent = new Intent(context, CPLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * refresh the token info
     *
     * @param context
     * @param new_Token
     */
    public static void refresh_Token(Context context, String new_Token) {
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("token", new_Token);
        editor.apply();
    }

    public static void clear_Token(Context context) {
        if (!has_Token(context)) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 获取投诉信息所需要的 lastID
     */
    public static int getComplainsLastId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_COMPLAINS_LAST_ID, Context.MODE_PRIVATE);
        int lastid = preferences.getInt("lastid", -1);
        if (lastid == -1) {
            //todo 表示并没有投诉信息
        }
        return lastid;
    }

    public static void setUserComplainsLastId(Context context, int lastid) {
        SharedPreferences preferences = context.getSharedPreferences(USER_COMPLAINS_LAST_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastid", lastid);
        editor.apply();
    }

    public static void setUserOrderFirstId(Context context, int id) {
        SharedPreferences preferences = context.getSharedPreferences(USER_ORDER_FIRST_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("order_id", id);
        editor.apply();
    }

    public static int getUserOderFirstId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_ORDER_FIRST_ID, Context.MODE_PRIVATE);
        int id = preferences.getInt("order_id", -1);
        return id;
    }

    public static void clearUserOrderFirstId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_ORDER_FIRST_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void setUserArrivedOrderFirstId(Context context, int id) {
        SharedPreferences preferences = context.getSharedPreferences(USER_ARRIBED_ORDER_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("arrived_order_id", id);
        editor.apply();
    }

    public static int getUserArrivedOderFirstId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_ARRIBED_ORDER_ID, Context.MODE_PRIVATE);
        int id = preferences.getInt("arrived_order_id", -1);
        return id;
    }

    public static void clearUserArrivedOrderFirstId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_ARRIBED_ORDER_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
