package com.example.android.newsapp;

public class News {
    private String mNewsUrl;
    private String mPublicationDate;
    private String mPublicationTime;
    private String mNewsTitle;
    private String mNewsAuthor;
    private String mImageUrl;


    public News(String newsUrl, String publicationDate, String publicationTime, String newsTitle, String author, String imageUrl){
        mNewsUrl = newsUrl;
        mPublicationDate = publicationDate;
        mNewsTitle = newsTitle;
        mPublicationTime = publicationTime;
        mNewsAuthor = author;
        mImageUrl = imageUrl;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public String getPublicationTime() {
        return mPublicationTime;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getNewsAuthor() {
        return mNewsAuthor;
    }
}
