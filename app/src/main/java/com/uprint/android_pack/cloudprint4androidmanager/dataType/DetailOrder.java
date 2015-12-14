package com.uprint.android_pack.cloudprint4androidmanager.dataType;

import java.util.List;

/**
 * Created by tonychiang on 15/10/13.
 */
public class DetailOrder {

    /**
     * fee : 2.3
     * deliver_fee : 0.5
     * note : 加油
     * rate : 5
     * comment : 感觉还不错
     * printTasks : [{"fileName":"成绩单","pages":5,"bothside":1,"colorful":0,"copies":1,"task_id":"123"},{"fileName":"成绩单","pages":5,"bothside":0,"colorful":1,"copies":1,"task_id":"124"},{"fileName":"成绩单","pages":5,"bothside":1,"colorful":0,"copies":1,"task_id":"125"},{"fileName":"成绩单","pages":5,"bothside":0,"colorful":0,"copies":1,"task_id":"126"},{"fileName":"成绩单","pages":5,"bothside":0,"colorful":0,"copies":1,"task_id":"127"},{"fileName":"成绩单","pages":5,"bothside":1,"colorful":1,"copies":1,"task_id":"128"},{"fileName":"成绩单","pages":5,"bothside":0,"colorful":1,"copies":1,"task_id":"129"},{"fileName":"成绩单","pages":5,"bothside":0,"colorful":0,"copies":1,"task_id":"130"}]
     */

    private double fee;
    private double deliver_fee;
    private String note;
    private int rate;
    private String comment;
    private List<PrintTasks> printTasks;

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setDeliver_fee(double deliver_fee) {
        this.deliver_fee = deliver_fee;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPrintTasks(List<PrintTasks> printTasks) {
        this.printTasks = printTasks;
    }

    public double getFee() {
        return fee;
    }

    public double getDeliver_fee() {
        return deliver_fee;
    }

    public String getNote() {
        return note;
    }

    public int getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public List<PrintTasks> getPrintTasks() {
        return printTasks;
    }

    public static class PrintTasks {
        /**
         * fileName : 成绩单
         * pages : 5
         * bothside : 1
         * colorful : 0
         * copies : 1
         * task_id : 123
         */

        private String fileName;
        private int pages;
        private int bothside;
        private int colorful;
        private int copies;
        private String task_id;

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public void setBothside(int bothside) {
            this.bothside = bothside;
        }

        public void setColorful(int colorful) {
            this.colorful = colorful;
        }

        public void setCopies(int copies) {
            this.copies = copies;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getFileName() {
            return fileName;
        }

        public int getPages() {
            return pages;
        }

        public int getBothside() {
            return bothside;
        }

        public int getColorful() {
            return colorful;
        }

        public int getCopies() {
            return copies;
        }

        public String getTask_id() {
            return task_id;
        }
    }
}
