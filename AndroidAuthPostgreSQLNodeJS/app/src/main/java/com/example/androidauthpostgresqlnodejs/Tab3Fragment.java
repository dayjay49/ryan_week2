package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;


public class Tab3Fragment extends Fragment {


    public Tab3Fragment() {
    }

    boolean prepared = false;
    boolean started = false;
    MediaPlayer mediaPlayer;
    int count = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.blank_tab, container, false);

        final String stream = "http://15.164.165.240:3000/GEM.mp3";

        final ImageView error = view.findViewById(R.id.imageView2);
        final TextView error_text = view.findViewById(R.id.textView2);

        Glide.with(this).load(R.drawable.loading).into(error);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);



        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (started) {
                    started = false;
                    mediaPlayer.pause();
                } else {
                    started = true;
                    if (count == 0) {
                        new PlayerTask().execute(stream);
                        count++;
                    } else {
                        mediaPlayer.start();
                    }
                }


//        mediaPlayer.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(R.drawable.fatalerror).into(error);
                        error_text.setVisibility(View.VISIBLE);

                    }
                },7200);

            }
        });






//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setLooping(true);
//
//        new PlayerTask().execute(stream);
//
////        mediaPlayer.start();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Glide.with(getContext()).load(R.drawable.fatalerror).into(error);
//                error_text.setVisibility(View.VISIBLE);
//
//            }
//        },7200);
        return view;
    }

    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return prepared;
        }

        @Override
        protected void onPostExecute (Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mediaPlayer.start();
        }
    }
}

