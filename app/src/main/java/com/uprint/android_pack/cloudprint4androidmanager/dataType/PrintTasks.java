package com.uprint.android_pack.cloudprint4androidmanager.dataType;

/**
 * Created by zhangxiaang on 15/10/13.
 */
public class PrintTasks {

    /**
     * fileName : 阿翔成绩单
     * pages : 5 页数
     * bothside : 0 -> 单面 1->双面
     * colorful : 0->黑白 1->彩印
     * copies : 1
     * handouts : 1 一页几面
     * uploadedAt : 34527423 -> long
     * downloadedAt : 1234
     */

    private String fileName;
    private int pages;
    private int bothside;
    private int colorful;
    private int copies;
    private int handouts;
    private String uploadedAt;
    private String downloadedAt;

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

    public void setHandouts(int handouts) {
        this.handouts = handouts;
    }

    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public void setDownloadedAt(String downloadedAt) {
        this.downloadedAt = downloadedAt;
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

    public int getHandouts() {
        return handouts;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }

    public String getDownloadedAt() {
        return downloadedAt;
    }
}
