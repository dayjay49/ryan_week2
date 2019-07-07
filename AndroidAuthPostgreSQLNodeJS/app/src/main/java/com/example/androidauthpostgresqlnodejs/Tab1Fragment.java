package com.example.androidauthpostgresqlnodejs;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Tab1Fragment extends Fragment {
    public static final int TAKE_PICTURE = 1888;

    public View view;
    public ArrayList<Bitmap> Gallery = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_tab1,container,false);

        RecyclerView recyclerViewtab2 = view.findViewById(R.id.recycler_view_tab2);
        final RecyclerViewAdapterTab2 adapterTab2 = new RecyclerViewAdapterTab2(getActivity(), Gallery);
        recyclerViewtab2.setAdapter(adapterTab2);
        recyclerViewtab2.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        FloatingActionButton button_camera = view.findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });



        adapterTab2.setOnItemClickListener(new RecyclerViewAdapterTab2.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, int request_code) {

                final Bitmap photo_to_delete = Gallery.get(position);
                Gallery.remove(position);

                //////////////////////////////////////////////////////////////
                //      Retrofit //
                //////////////////////////////////////////////////////////////
                initializeRecyclerView(Gallery);

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (resultCode == RESULT_OK && data.hasExtra("data")) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        if (bitmap != null) {
                            Gallery.add(bitmap);
                        }
                        initializeRecyclerView(Gallery);
                    } else {
                        Toast.makeText(getActivity(), "사진 찍기를 취소하였습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch(Exception e) {
            Toast.makeText(getActivity(), "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }




    public void initializeRecyclerView(ArrayList<Bitmap> Gallery) {
        RecyclerView recyclerViewtab2 = view.findViewById(R.id.recycler_view_tab2);
        RecyclerViewAdapterTab2 adapterTab2 = new RecyclerViewAdapterTab2(getActivity(), Gallery);
        recyclerViewtab2.setAdapter(adapterTab2);
        recyclerViewtab2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }



}