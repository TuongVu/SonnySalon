package com.tvswitch.sonnylibrary.utils;

/**
 * Created by Tuong on 6/17/15.
 */
public class ScheduleItem {
    private int color;
    private String name;

    public ScheduleItem(int color, String name){
        this.color = color;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
