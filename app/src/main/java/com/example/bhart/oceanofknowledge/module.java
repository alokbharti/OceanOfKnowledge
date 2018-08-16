package com.example.bhart.oceanofknowledge;

public class module {
    private String title;
    private String lang;
    private String image;
    private String moddir;
    private String size;


    public String getSize() {
        return size;
    }

    public module(String title, String lang, String image, String moddir, String size) {

        this.title = title;
        this.lang = lang;
        this.image = image;
        this.moddir=moddir;
        this.size=size;

    }

    public String getTitle() {
        return title;
    }

    public String getLang() {
        return lang;
    }

    public String getImage() {
        return image;
    }
    public String getModdir(){
        return moddir;
    }
}
