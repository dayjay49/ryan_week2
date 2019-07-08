package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class FullImageActivity extends AppCompatActivity {

    private String baseURL = "http://143.248.38.250:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_view);

        Intent i = getIntent();
        ImageView GalleryPreviewImg = (ImageView) findViewById(R.id.full_image_view);

        String path = (String) i.getStringExtra("path");
        Glide.with(FullImageActivity.this)
                .load(baseURL+path)// Uri of the picture
                .into(GalleryPreviewImg);

//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        Bitmap rotatedBitmap = null;
//        if (bitmap != null) {
//            try {
//                ExifInterface ei = new ExifInterface(path);
//                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                        ExifInterface.ORIENTATION_UNDEFINED);
//
//                rotatedBitmap = null;
//                switch (orientation) {
//
//                    case ExifInterface.ORIENTATION_ROTATE_90:
//                        rotatedBitmap = rotateImage(bitmap, 90);
//                        break;
//
//                    case ExifInterface.ORIENTATION_ROTATE_180:
//                        rotatedBitmap = rotateImage(bitmap, 180);
//                        break;
//
//                    case ExifInterface.ORIENTATION_ROTATE_270:
//                        rotatedBitmap = rotateImage(bitmap, 270);
//                        break;
//
//                    case ExifInterface.ORIENTATION_NORMAL:
//                    default:
//                        rotatedBitmap = bitmap;
//                        break;
//                }
//            } catch (Exception e) {
//
//            }
//
//        }


//        Glide.with(FullImageActivity.this)
//                .load(photo) // Uri of the picture
//                .into(GalleryPreviewImg);
//        GalleryPreviewImg.setImageBitmap(rotatedBitmap);

//        View.OnClickListener closebtn_listener = new View.OnClickListener(){
//            @Override
//            public  void onClick(View v){
//                finish();
//            }
//        };
//        Button closeLogin = (Button) findViewById(R.id.close_button);
//        closeLogin.setOnClickListener(closebtn_listener) ;

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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
