package com.buaa.greenlife.views.custom;

/**
 * Created by QisenTang on 13-7-21.
 */
public class TimeLineMotion {

    private String photoUrl;
    private String desc;
    private int hotdegree;
    private String timeStamp;

    public TimeLineMotion(String photoUrl, String desc, int hotdegree, String timeStamp) {
        this.photoUrl = photoUrl;
        this.desc = desc;
        this.hotdegree = hotdegree;
        this.timeStamp = timeStamp;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDesc() {
        return desc;
    }

    public int getHotdegree() {
        return hotdegree;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
