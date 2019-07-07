package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_view);

        Intent i = getIntent();

        Bitmap photo = (Bitmap) i.getParcelableExtra("photo");
//        Bitmap resized_photo = resizingBitmap(photo, 5.3);

        ImageView GalleryPreviewImg = (ImageView) findViewById(R.id.full_image_view);
//        Glide.with(FullImageActivity.this)
//                .load(photo) // Uri of the picture
//                .into(GalleryPreviewImg);
        GalleryPreviewImg.setImageBitmap(photo);

        View.OnClickListener closebtn_listener = new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                finish();
            }
        };
        Button closeLogin = (Button) findViewById(R.id.close_button);
        closeLogin.setOnClickListener(closebtn_listener) ;



    }

//    public Bitmap resizingBitmap(Bitmap oBitmap){
//        if (oBitmap == null)
//            return null;
//        float width = oBitmap.getWidth();
//        float height = oBitmap.getHeight();
//        float resizing_size = 1200;
//        Bitmap rBitmap = null;
//        if (width > resizing_size){
//            float mWidth = (float) (width/100);
//            float fScale = (float) (resizing_size / mWidth);
//            width *= (fScale/100);
//            height *= (fScale/100);
//        }else if (height > resizing_size){
//            float mHeight = (float) (height/100);
//            float fScale = (float) (resizing_size / mHeight);
//            width *= (fScale/100);
//            height *= (fScale/100);
//        }
//        //Log.d("rBitmap : " + width + "," + height);
//        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int)width, (int)height, true);
//        return rBitmap;
//    }
//    public Bitmap resizingBitmap(Bitmap oBitmap, double scale) {
//        if (oBitmap == null)
//            return null;
//        float width = oBitmap.getWidth();
//        float height = oBitmap.getHeight();
//        width *= scale;
//        height *= scale;
//        Bitmap rBitmap = Bitmap.createScaledBitmap(oBitmap, (int)width, (int)height, true);
//        return rBitmap;
//    }
}
