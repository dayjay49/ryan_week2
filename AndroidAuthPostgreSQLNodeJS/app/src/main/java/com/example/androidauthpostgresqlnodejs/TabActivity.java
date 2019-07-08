package com.example.androidauthpostgresqlnodejs;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.example.androidauthpostgresqlnodejs.Retrofit.RetroCallback;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetrofitClient;
import com.example.androidauthpostgresqlnodejs.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    public static List<Photo> serverPathList;

    String user_Email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_Email = MainActivity.login_user.getEmail();

        final RetrofitClient retrofitClient;
        retrofitClient = RetrofitClient.getInstance(this).createBaseApi();

        retrofitClient.loadGallery(user_Email, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(TabActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                serverPathList = new ArrayList<>();
                ArrayList<Photo> tempPathList = (ArrayList<Photo>) receivedData;
                for (int i = 0 ; i< tempPathList.size(); i++) {
                    String prePath = "my_uploads";
                    String postPath = tempPathList.get(i).getPath().substring(11);
                    String path = prePath+"/"+postPath;
                    Photo addPhoto = new Photo(path);
                    serverPathList.add(addPhoto);
                    initialize();
                }
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(TabActivity.this, "Code: " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initialize() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        //Log.d("TAG1", viewPager.toString());
        viewPager.setAdapter(sectionsPagerAdapter);
        //Log.d("TAG2", viewPager.toString());
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("LOG OUT")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        TabActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}