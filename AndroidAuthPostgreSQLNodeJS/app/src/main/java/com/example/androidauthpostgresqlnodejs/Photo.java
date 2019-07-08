package com.example.androidauthpostgresqlnodejs;

public class Photo {
    private String path;

    public Photo(String mPath) {
        this.path = mPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
