package com.uprint.android_pack.cloudprint4androidmanager.dataType;

/**
 * Created by zhangxiaang on 15/11/13.
 */
public class ArrivedOrders {

    /**
     * order_id : 1234
     * student_room : 508
     * student_phone : 13025495405
     * nickname : zhangxiang
     * demand_time : 2015-09-12
     * rate : 2
     */

    private int order_id;
    private int student_room;
    private String student_phone;
    private String nickname;
    private String demand_time;
    private int rate;

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setStudent_room(int student_room) {
        this.student_room = student_room;
    }

    public void setStudent_phone(String student_phone) {
        this.student_phone = student_phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDemand_time(String demand_time) {
        this.demand_time = demand_time;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getStudent_room() {
        return student_room;
    }

    public String getStudent_phone() {
        return student_phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDemand_time() {
        return demand_time;
    }

    public int getRate() {
        return rate;
    }
}
