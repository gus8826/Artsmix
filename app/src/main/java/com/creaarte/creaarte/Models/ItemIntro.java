package com.creaarte.creaarte.Models;

public class ItemIntro {

    private String title;
    private String description_1;
    private String description_2;
    private String description_3;
    private String description_4;
    private int screenImg;

    public ItemIntro(String title, String description_1, String description_2, String description_3, String description_4, int screenImg) {
        this.title = title;
        this.description_1 = description_1;
        this.description_2 = description_2;
        this.description_3 = description_3;
        this.description_4 = description_4;
        this.screenImg = screenImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription_1() {
        return description_1;
    }

    public void setDescription_1(String description_1) {
        this.description_1 = description_1;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getDescription_3() {
        return description_3;
    }

    public void setDescription_3(String description_3) {
        this.description_3 = description_3;
    }

    public String getDescription_4() {
        return description_4;
    }

    public void setDescription_4(String description_4) {
        this.description_4 = description_4;
    }

    public int getScreenImg() {
        return screenImg;
    }

    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }

}
