package com.uprint.android_pack.cloudprint4androidmanager.dataType;

/**
 * Created by zhangxiaang on 15/11/13.
 */
public class ComplainsInfo {

    /**
     * complain_at : 2015-8-9
     * complain : 今天吃药了吗
     * order_id : 1653
     */

    private String complain_at;
    private String complain;
    private int order_id;

    public void setComplain_at(String complain_at) {
        this.complain_at = complain_at;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getComplain_at() {
        return complain_at;
    }

    public String getComplain() {
        return complain;
    }

    public int getOrder_id() {
        return order_id;
    }
}
