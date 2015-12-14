package com.uprint.android_pack.cloudprint4androidmanager.dataType;

import java.util.List;

/**
 * Created by zhangxiaang on 15/10/13.
 */
public class ManagerInfo {


    /**
     * ID_number : 421126199403100014
     * alipay_account : 13025495405@163.com
     * building : {"ID":197,"name":"4c公寓","status":0}
     * dorm : 508
     * email : 13025495405@163.com
     * graduate_year : 2019
     * manage_buildings : [{"ID":9,"name":"南阳书院东8号公寓楼","status":0},{"ID":10,"name":"南阳书院东9号公寓楼","status":0},{"ID":103,"name":"海棠7号楼","status":0},{"ID":104,"name":"海棠8号楼","status":0},{"ID":200,"name":"5c公寓","status":0}]
     * manager_name : 向下吧
     * manager_type : 1
     * qq : 342760024
     * school : {"ID":39,"name":"西安外国语大学"}
     * sex : 0
     * state : 1
     */

    private String ID_number;
    private String alipay_account;
    private Building building;
    private String dorm;
    private String email;
    private String graduate_year;
    private String manager_name;
    private int manager_type;
    private String qq;
    private School school;
    private int sex;
    private int state;
    private List<ManageBuildings> manage_buildings;

    @Override
    public String toString() {
        return "ManagerInfo{" +
                "ID_number='" + ID_number + '\'' +
                ", alipay_account='" + alipay_account + '\'' +
                ", building=" + building +
                ", dorm='" + dorm + '\'' +
                ", email='" + email + '\'' +
                ", graduate_year='" + graduate_year + '\'' +
                ", manager_name='" + manager_name + '\'' +
                ", manager_type=" + manager_type +
                ", qq='" + qq + '\'' +
                ", school=" + school +
                ", sex=" + sex +
                ", state=" + state +
                ", manage_buildings=" + manage_buildings +
                '}';
    }

    public void setID_number(String ID_number) {
        this.ID_number = ID_number;
    }

    public void setAlipay_account(String alipay_account) {
        this.alipay_account = alipay_account;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setDorm(String dorm) {
        this.dorm = dorm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGraduate_year(String graduate_year) {
        this.graduate_year = graduate_year;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public void setManager_type(int manager_type) {
        this.manager_type = manager_type;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setManage_buildings(List<ManageBuildings> manage_buildings) {
        this.manage_buildings = manage_buildings;
    }

    public String getID_number() {
        return ID_number;
    }

    public String getAlipay_account() {
        return alipay_account;
    }

    public Building getBuilding() {
        return building;
    }

    public String getDorm() {
        return dorm;
    }

    public String getEmail() {
        return email;
    }

    public String getGraduate_year() {
        return graduate_year;
    }

    public String getManager_name() {
        return manager_name;
    }

    public int getManager_type() {
        return manager_type;
    }

    public String getQq() {
        return qq;
    }

    public School getSchool() {
        return school;
    }

    public int getSex() {
        return sex;
    }

    public int getState() {
        return state;
    }

    public List<ManageBuildings> getManage_buildings() {
        return manage_buildings;
    }

    public static class Building {
        /**
         * ID : 197
         * name : 4c公寓
         * status : 0
         */

        private int ID;
        private String name;
        private int status;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }

        public int getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "Building{" +
                    "ID=" + ID +
                    ", name='" + name + '\'' +
                    ", status=" + status +
                    '}';
        }
    }

    public static class School {
        @Override
        public String toString() {
            return "School{" +
                    "ID=" + ID +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * ID : 39
         * name : 西安外国语大学
         */

        private int ID;
        private String name;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }
    }

    public static class ManageBuildings {
        @Override
        public String toString() {
            return "ManageBuildings{" +
                    "ID=" + ID +
                    ", name='" + name + '\'' +
                    ", status=" + status +
                    '}';
        }

        /**
         * ID : 9
         * name : 南阳书院东8号公寓楼
         * status : 0
         */

        private int ID;
        private String name;
        private int status;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }

        public int getStatus() {
            return status;
        }
    }
}
