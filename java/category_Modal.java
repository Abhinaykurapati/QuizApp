package com.example.quizapp;

public class category_Modal {
    private String imgUrl,title;
    public category_Modal(String imgUrl, String title)
    {
        this.imgUrl=imgUrl;
        this.title=title;

    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }


}
