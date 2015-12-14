package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.os.CountDownTimer;

import com.dd.processbutton.FlatButton;

/**
 * Created by zhangxiaang on 15/10/14.
 */
public class CountDownButtonHelper {
    private CountDownTimer countDownTimer;
    // 计时结束的回调接口
    private OnFinishListener listener;
    private FlatButton button;

    /**
     * @param button        需要显示倒计时的Button
     * @param defaultString 默认显示的字符串
     * @param max           需要进行倒计时的最大值,单位是秒
     * @param interval      倒计时的间隔，单位是秒
     */
    public CountDownButtonHelper(final FlatButton button,
                                 final String defaultString, int max, int interval) {

        this.button = button;
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {

            @Override
            public void onTick(long time) {
                button.setText(((time + 15) / 1000)
                        + "秒后可重发");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText(defaultString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
    }

    /**
     * 关闭倒计时
     */
    public void stop() {
        countDownTimer.cancel();
    }

    /**
     * 设置倒计时结束的监听器
     *
     * @param listener
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 计时结束的回调接口
     *
     * @author zhaokaiqiang
     */
    public interface OnFinishListener {
        public void finish();
    }
}
