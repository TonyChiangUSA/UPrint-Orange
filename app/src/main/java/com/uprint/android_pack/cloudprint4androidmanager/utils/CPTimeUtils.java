package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.content.Context;

import com.uprint.android_pack.cloudprint4androidmanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * format time util
 */
public class CPTimeUtils {
    public static String getLeftTimeDesc(Context context, long diff, boolean chinese) {
        String leftTimeDesc = null;

        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        long leftTime = diff / 1000;

        if (leftTime >= 3600) {
            hours = leftTime / 3600;
            leftTime -= hours * 3600;
        }
        if (leftTime >= 60) {
            minutes = leftTime / 60;
            leftTime -= minutes * 60;
        }
        seconds = leftTime;

        if (chinese) {
            if (hours > 0) {
                leftTimeDesc = String.format(context.getString(R.string.tm_str_detail_jhs_buy_end_time_format_1),
                        String.valueOf(hours), String.format("%02d", minutes), String.format("%02d", seconds));
            } else {
                leftTimeDesc = String.format(context.getString(R.string.tm_str_detail_jhs_buy_end_time_format_2),
                        String.format("%02d", minutes), String.format("%02d", seconds));
            }
        } else {
            leftTimeDesc = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds);
        }
        return leftTimeDesc;
    }


    /**
     * 获取时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm(非今年) 或者 MM-dd HH:mm(今年)
     */
    public static String getDateTimeStr(long timestamp) {
        boolean debug = false;
        String dateString = "";

        Calendar current = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timestamp);

        int currentYear = current.get(Calendar.YEAR);
        int year = date.get(Calendar.YEAR);

        SimpleDateFormat formatter;
        if (debug) {
            formatter = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
            dateString = formatter.format(timestamp);
        } else {
            long duration = current.getTimeInMillis() - date.getTimeInMillis();
            if (duration < 60 * 1000) {
                dateString = "刚刚";
            } else if (duration < 60 * 60 * 1000) {
                dateString = String.format(Locale.CHINA, "%d分钟前", duration / (60 * 1000));
            } else if (duration < 24 * 60 * 60 * 1000) {
                dateString = String.format(Locale.CHINA, "%d小时前", duration / (60 * 60 * 1000));
            } else {
                if (currentYear != year) {// 非今年
                    formatter = new SimpleDateFormat("yyyy年", Locale.CHINA);
                    dateString = formatter.format(timestamp);
                } else {// 今年
                    formatter = new SimpleDateFormat("MM月dd日", Locale.CHINA);
                    dateString = formatter.format(timestamp);
                }
            }
        }
        return dateString;
    }

    public static String formateDate(String format, long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        String ret = formatter.format(date);
        return ret;
    }
}
