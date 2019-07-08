package com.example.androidauthpostgresqlnodejs;

import android.graphics.Bitmap;

public class Contact_Data {

    private String phone_number, contact_name;
    private Bitmap photo;

    public Contact_Data() {}

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setPhone_number(String string) {
        this.phone_number = string;
    }

    public void setContact_name(String string) {
        this.contact_name = string;
    }

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
        return false;e
    }

}
