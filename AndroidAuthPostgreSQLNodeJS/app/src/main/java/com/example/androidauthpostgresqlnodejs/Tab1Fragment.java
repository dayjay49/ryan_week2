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
import com.example.androidauthpostgresqlnodejs.Retrofit.RetroCallback;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class Tab1Fragment extends Fragment {
    public static final int TAKE_PICTURE = 1888;

    public View view;
    public ArrayList<Bitmap> Gallery = new ArrayList<>();
    String user_Email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user_Email = MainActivity.login_user.getEmail();

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
                        Bitmap imageFile = (Bitmap) data.getExtras().get("data");
                        if (imageFile != null) {
                            Gallery.add(imageFile);
                        }

                        File file = new File(filePath);

                        RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);
                        RequestBody current_user_email = RequestBody.create(MultipartBody.FORM, user_Email);
                        RequestBody image_id = RequestBody.create(MultipartBody.FORM, photo_id);

                        MultipartBody.Part part = MultipartBody.Part.createFormData("imageFile", file.getName(), filePart);

                        //Initialize Service
                        final RetrofitClient retrofitClient;
                        retrofitClient = RetrofitClient.getInstance(getContext()).createBaseApi();

                        retrofitClient.uploadPhoto(part, current_user_email, image_id, new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                initializeRecyclerView(Gallery);
                            }

                            @Override
                            public void onFailure(int code) {
                                Toast.makeText(getActivity(), "Code: " + code, Toast.LENGTH_SHORT).show();
                            }
                        });

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