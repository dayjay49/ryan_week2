package com.example.androidauthpostgresqlnodejs;

import android.graphics.Bitmap;

public class Contact_Data {

    //Based on link from 현석
    private String phone_number, contact_name;
    //private long photo_id=0, person_id=0, contact_id=0;
    private Bitmap photo;
    //private int id;

    public Contact_Data() {}

//    public long getPhoto_id() {
//        return photo_id;
//    }
//
//    public long getPerson_id() {
//        return person_id;
//    }
//
//    public void setPhoto_id(long id) {
//        this.photo_id = id;
//    }
//
//    public void setPerson_id(long id) {
//        this.person_id = id;
//    }
//
    public Bitmap getPhoto() {
        return photo;
    }
//
//    public void setPhoto(Bitmap photo) {
//        this.photo = photo;
//    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getContact_name() {
        return contact_name;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return id;
//    }

    public void setPhone_number(String string) {
        this.phone_number = string;
    }

    public void setContact_name(String string) {
        this.contact_name = string;
    }

//    public long getContactId() {return contact_id;}
//
//    public void setContact_id(long contact_id) {
//        this.contact_id = contact_id;
//    }

    @Override
    public String toString() {
        return this.phone_number;
    }

    @Override
    public int hashCode() {
        return getPhNumberChanged().hashCode();
    }

    public String getPhNumberChanged() {
        return phone_number.replace("-", "");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Contact_Data)
            return getPhNumberChanged().equals(((Contact_Data) o).getPhNumberChanged());
        return false;
    }

}
