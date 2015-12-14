package com.uprint.android_pack.cloudprint4androidmanager.dataType;

import java.util.ArrayList;

/**
 * Created by tonychiang on 15/10/28.
 */
public class SchoolInfo {
    /**
     * id : 242
     * name : 华南师范大学（大学城校区）
     * buildings : [{"id":2360,"name":"南区1","status":0},{"id":2361,"name":"南区2","status":0},{"id":2362,"name":"南区3","status":0},{"id":2363,"name":"南区4","status":0},{"id":2364,"name":"南区5","status":0},{"id":2365,"name":"南区6","status":0},{"id":2366,"name":"南区7","status":0},{"id":2367,"name":"南区8","status":0},{"id":2368,"name":"南区9","status":0},{"id":2369,"name":"北区10","status":0},{"id":2370,"name":"北区11","status":0},{"id":2371,"name":"北区12","status":0},{"id":2372,"name":"北区13","status":0},{"id":2373,"name":"北区14","status":0},{"id":2374,"name":"北区15","status":0},{"id":2375,"name":"北区16","status":0},{"id":2376,"name":"北区17","status":0},{"id":2377,"name":"北区18","status":0},{"id":2378,"name":"北区19","status":0}]
     */

    private int id;
    private String name;

    private ArrayList<DormInfo> buildings;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuildings(ArrayList<DormInfo> buildings) {
        this.buildings = buildings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<DormInfo> getBuildings() {
        return buildings;
    }

    public static class DormInfo {
        /**
         * id : 2360
         * name : 南区1
         * status : 0
         */

        private int id;
        private String name;
        private int status;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getStatus() {
            return status;
        }
    }
}
