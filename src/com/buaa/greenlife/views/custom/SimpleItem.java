package com.buaa.greenlife.views.custom;

/**
 * Created by QisenTang on 13-7-20.
 */
public class SimpleItem {

    private int drawableId;

    private String title;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SimpleItem(int drawableId, String title) {
        this.drawableId = drawableId;
        this.title = title;
    }
}
