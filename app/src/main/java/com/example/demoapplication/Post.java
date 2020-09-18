package com.example.demoapplication;

public class Post {
    private String mPost;
    private String mTitle;
    private  int mId;

    Post(String title, String post, int id)
    {
        mId = id;
        mTitle=title;
        mPost=post;
    }

    public String getmPost()
    {
        return mPost;
    }

    public String getmTitle()
    {
        return mTitle;
    }

    public  int getmId()
    {
        return  mId;
    }

    public void setmPost(String p)
    {
        mPost=p;
    }
}
