package com.example.androidauthpostgresqlnodejs;

import android.graphics.Bitmap;

public class Data {

    //Based on 지수&동하

//    private String title;
//    private String content;
//    private int resId;
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public int getResId() {
//        return resId;
//    }
//
//    public void setResId() {
//        this.resId = resId;
//    }

    //Based on link from 현석
    private String user_phNumber, user_Name;
    private long photo_id=0, person_id=0, contact_id=0;
    private Bitmap photo;
    private int id;

    public Data() {}

    public long getPhoto_id() {
        return photo_id;
    }

    public long getPerson_id() {
        return person_id;
    }

    public void setPhoto_id(long id) {
        this.photo_id = id;
    }

    public void setPerson_id(long id) {
        this.person_id = id;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getUser_phNumber() {
        return user_phNumber;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUser_phNumber(String string) {
        this.user_phNumber = string;
    }

    public void setUser_Name(String string) {
        this.user_Name = string;
    }

    public long getContactId() {return contact_id;}

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    @Override
    public String toString() {
        return this.user_phNumber;
    }

    @Override
    public int hashCode() {
        return getPhNumberChanged().hashCode();
    }

    public String getPhNumberChanged() {
        return user_phNumber.replace("-", "");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Data)
            return getPhNumberChanged().equals(((Data) o).getPhNumberChanged());
        return false;
    }

}
