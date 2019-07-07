package com.example.androidauthpostgresqlnodejs;

public class Photo {
    private String mPath;
    private int mId;
    private int count = 0;

    public Photo(String mPath) {
        this.mPath = mPath;
        this.mId = count++;
    }

    public String getmPath() {
        return mPath;
    }

    public String getmIdString(){
        return String.valueOf(mId);
    }
}
