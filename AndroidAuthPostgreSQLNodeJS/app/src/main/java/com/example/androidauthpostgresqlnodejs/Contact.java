package com.example.androidauthpostgresqlnodejs;

import android.graphics.Bitmap;

public class Contact {

    private String phone_number;
    private String contact_name;
    //private Bitmap photo;

    public Contact(String phone_number, String contact_name) {
        this.phone_number = phone_number;
        this.contact_name = contact_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getContact_name() {
        return contact_name;
    }
}
