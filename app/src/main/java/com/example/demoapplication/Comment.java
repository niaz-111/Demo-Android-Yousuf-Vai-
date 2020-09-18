package com.example.demoapplication;

public class Comment {
    private int mPostId;
    private String mName;
    private String mComment;

    public Comment (String name, String comment, int pId)
    {
        mPostId = pId;
        mName = name;
        mComment = comment;
    }

    public String getmComment(){ return mComment;}
    public String getmName(){ return mName;}
    public  int getmPostId(){ return mPostId;}

    public void setmComment(String s){mComment = s;}
}
