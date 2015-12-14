package com.uprint.android_pack.cloudprint4androidmanager.network;

/**
 * Created by tonychiang on 15/10/13.
 */
public interface ICallBack {
    int SUCCESS = 1;

    //以Object 数组形势是为了后期的拓展
    void displayResult(int status, Object... params);
}
