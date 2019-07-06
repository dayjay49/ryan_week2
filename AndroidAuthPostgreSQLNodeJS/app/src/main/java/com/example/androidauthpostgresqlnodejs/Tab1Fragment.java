package com.example.androidauthpostgresqlnodejs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Tab1Fragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PICTURE = 2;
    public int imagePosition;
    public Context thiscontext;
    private File tempFile;
    public View view;
    public ArrayList<Bitmap> Gallery = new ArrayList<>();

    private static final int PICK_FROM_CAMERA = 2;

    /** The images. */
    public static ArrayList<String> images;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_tab1,container,false);
////////////////////Original Avocado//////////////////////////////////////////////////////////////\

        return view;
    }


}