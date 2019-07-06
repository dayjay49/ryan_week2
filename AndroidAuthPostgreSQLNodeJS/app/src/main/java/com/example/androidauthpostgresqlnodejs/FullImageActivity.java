package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        String path = i.getStringExtra("path");

        ImageView GalleryPreviewImg = (ImageView) findViewById(R.id.full_image_view);
        Glide.with(FullImageActivity.this)
                .load(new File(path)) // Uri of the picture
                .into(GalleryPreviewImg);

        View.OnClickListener closebtn_listener = new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                finish();
            }
        };
        Button closeLogin = (Button) findViewById(R.id.close_button);
        closeLogin.setOnClickListener(closebtn_listener) ;



//
//        // 3. 연산한 결과 값을 resultIntent 에 담아서 MainActivity 로 전달하고 현재 Activity 는 종료.
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("result","연산 결과는 "+result+" 입니다.");
//        setResult(RESULT_OK,resultIntent);
//        finish();

    }
}
