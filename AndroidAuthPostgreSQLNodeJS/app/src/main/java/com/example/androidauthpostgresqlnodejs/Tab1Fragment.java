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
////////////////////Original Avocado//////////////////////////////////////////////////////////////
        final GridView gallery = (GridView) view.findViewById(R.id.galleryGridView);

        gallery.setAdapter(new ImageAdapter(requireContext()));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1,
                                    int position, long arg3) {
                imagePosition = position;

                if (null != images && !images.isEmpty()) {

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Choose Action",
                            Toast.LENGTH_SHORT).show();

                    Button btnLogin = (Button) getActivity().findViewById(R.id.edit_button);

                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            arg1.setVisibility(View.GONE);
                        }
                    });

                    Button btn2Login = (Button) getActivity().findViewById(R.id.full_button);
                    btn2Login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), FullImageActivity.class);

                            i.putExtra("path", images.get(imagePosition));
                            startActivity(i);
                        }
                    });

                }


            }

        });
////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////     Initailize recyclerview for gallery       /////////////////////////
//        RecyclerView recyclerViewtab2 = view.findViewById(R.id.recycler_view_tab2);
//        RecyclerViewAdapterTab2 adapterTab2 = new RecyclerViewAdapterTab2();
//        recyclerViewtab2.setAdapter(adapterTab2);
//        recyclerViewtab2.setLayoutManager(new GridLayoutManager(getContext(), 3));
//
        FloatingActionButton btn_gallery = view.findViewById(R.id.button_gallery);
        FloatingActionButton camera = view.findViewById(R.id.button_camera);
//
        btn_gallery.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
//
        camera.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });


        return view;
    }

//    private void initGalleryInfo(ArrayList<Bitmap> Gallery) {
//        initTab2RecyclerView(Gallery);
//    }
//
//    private void initTab2RecyclerView(ArrayList<Bitmap> Gallery) {
////        Log.d(TAG, "initTab2RecyclerView: init recyclerView for tab2.");
//        RecyclerView recyclerViewtab2 = view.findViewById(R.id.recycler_view_tab2);
//        RecyclerViewAdapterTab2 adapterTab2 = new RecyclerViewAdapterTab2();
//        recyclerViewtab2.setAdapter(adapterTab2);
//        recyclerViewtab2.setLayoutManager(new GridLayoutManager(getContext(), 3));
//    }
//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            switch(requestCode) {
                case PICK_IMAGE_REQUEST:
                    if (resultCode == RESULT_OK && data != null) {
                        if(data.getClipData() != null){
                            int count = data.getClipData().getItemCount();
                            for (int i=0; i<count; i++){
                                Uri uri = data.getClipData().getItemAt(i).getUri();
                                String photo_Uri = uri.toString();

                            }
                        }
                        else if(data.getData() != null){
                            InputStream in = getContext().getContentResolver().openInputStream(data.getData());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();
//                            Gallery.add(img);
                        }
//                        initGalleryInfo(Gallery);
                    }else{
                        Toast.makeText(getActivity(), "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



//    /**
//     * The Class ImageAdapter.
//     */
    public static class ImageAdapter extends BaseAdapter {

        /** The context. */
        public static Context context;

        /**
         * Instantiates a new image adapter.
         *
         * @param localContext
         *            the local context
         */
        public ImageAdapter(Context localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
        }

        public int getCount() {
            return images.size();
        }

        public String getImage (int position) {
            return images.get(position);
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(700, 700));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position))
                    //.placeholder(R.drawable.ic_launcher).centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity
         *            the activity
         * @return ArrayList with images Path
         */
        private ArrayList<String> getAllShownImagesPath(Context activity) {
            Uri uri;
            Cursor cursor;
            int data,album;

            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);


            ///////<< 앨범으로 만들기
//            cursor = activity.getContentResolver().query(uri, projection, "GROUP BY (BUCKET_DISPLAY_NAME",
//                    null, null);
//            while(cursor.moveToNext()){
//
//            }
//                data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//                album = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME);
//
//                viewholder{
//                    image view = set(data);
//                    image view onclik{
//
//                    }
//                    text view = set(album);
//
//                }
//
//
//
//             cursor=   activity.getContentResolver().query(uri, projection, "BUCKET_DISTPLAY_NAME ==" +"'" +album+"'" ,
//                     null, null);
            //<<---- 2번째 view
            //////<< 앨범으로 만들기


            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }
    }
}