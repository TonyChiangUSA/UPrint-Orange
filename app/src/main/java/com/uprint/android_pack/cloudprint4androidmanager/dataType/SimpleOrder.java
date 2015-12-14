package com.uprint.android_pack.cloudprint4androidmanager.dataType;

/**
 * Created by zhangxiaang on 15/10/13.
 */
public class SimpleOrder {

    /**
     * order_id : 1234
     * order_type : 0
     * order_state : 1
     * student_room : 502
     * student_phone : 15012977742
     * nickname : zhangxiang
     * paid_at : 2015-08-12
     * demand_time : 2015-09-12
     * shop : {"shop_id":"123","shop_name":"钟楼","shop_location":"吊塔","shop_tel":"15098762342"}
     */

    private String order_id;
    private int order_type;
    private int order_state;
    private String student_room;
    private String student_phone;
    private String nickname;
    private String paid_at;
    private String demand_time;
    /**
     * shop_id : 123
     * shop_name : 钟楼
     * shop_location : 吊塔
     * shop_tel : 15098762342
     */

    private ShopEntity shop;

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public void setStudent_room(String student_room) {
        this.student_room = student_room;
    }

    public void setStudent_phone(String student_phone) {
        this.student_phone = student_phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPaid_at(String paid_at) {
        this.paid_at = paid_at;
    }

    public void setDemand_time(String demand_time) {
        this.demand_time = demand_time;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public String getOrder_id() {
        return order_id;
    }

    public int getOrder_type() {
        return order_type;
    }

    public int getOrder_state() {
        return order_state;
    }

    public String getStudent_room() {
        return student_room;
    }

    public String getStudent_phone() {
        return student_phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPaid_at() {
        return paid_at;
    }

    public String getDemand_time() {
        return demand_time;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public static class ShopEntity {
        private String shop_id;
        private String shop_name;
        private String shop_location;
        private String shop_tel;

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setShop_location(String shop_location) {
            this.shop_location = shop_location;
        }

        public void setShop_tel(String shop_tel) {
            this.shop_tel = shop_tel;
        }

        public String getShop_id() {
            return shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getShop_location() {
            return shop_location;
        }

        public String getShop_tel() {
            return shop_tel;
        }
    }
}
