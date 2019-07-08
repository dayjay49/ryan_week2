package com.example.androidauthpostgresqlnodejs;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class Tab1Fragment extends Fragment {
    public static final int TAKE_PICTURE = 1888;

    public View view;
    public ArrayList<Bitmap> Gallery = new ArrayList<>();
    public ArrayList<String> path_Gallery = new ArrayList<>();
//    public ArrayList<Photo> photo_Gallery = new ArrayList<>();
    public Uri mImageUri;
    public String user_Email;
    public List<Photo> serverPathList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user_Email = MainActivity.login_user.getEmail();
        serverPathList = TabActivity.serverPathList;

        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_tab1,container,false);

        RecyclerView recyclerViewtab2 = view.findViewById(R.id.recycler_view_tab2);
        final RecyclerViewAdapterTab2 adapterTab2 = new RecyclerViewAdapterTab2(getActivity(), serverPathList);
        recyclerViewtab2.setAdapter(adapterTab2);
        recyclerViewtab2.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        FloatingActionButton button_camera = view.findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mImageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues()
                );
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });

        adapterTab2.setOnItemClickListener(new RecyclerViewAdapterTab2.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, int request_code) {


//                final Bitmap photo_to_delete = Gallery.get(position);
//                Gallery.remove(position);

                //////////////////////////////////////////////////////////////
                final RetrofitClient retrofitClient;
                retrofitClient = RetrofitClient.getInstance(getContext()).createBaseApi();
                final String file_to_delete = serverPathList.get(position).getPath().replace("my_uploads\\","");

                serverPathList.remove(position);

                retrofitClient.deletePhoto(user_Email, file_to_delete, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Toast.makeText(getActivity(), "Chosen image deleted...", Toast.LENGTH_SHORT).show();
                        initializeRecyclerView();

                    }

                    @Override
                    public void onFailure(int code) {
                        Toast.makeText(getActivity(), "Code: " + code, Toast.LENGTH_SHORT).show();
                    }
                });
                //////////////////////////////////////////////////////////////


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
                    if (resultCode == RESULT_OK) {
                        String path = getRealPathFromURI(getActivity(), mImageUri);
//                        path_Gallery.add(path);
//                        serverPathList.add(new Photo(path));
//                        final Photo new_Photo = new Photo(path);
//                        photo_Gallery.add(new_Photo);
//                        Bitmap new_Photo = BitmapFactory.decodeFile(path);
//                        Gallery.add(new_Photo);
//                        initializeRecyclerView(Gallery);

                        File file = new File(path);
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
//                        Bitmap rotatedBitmap = null;

                        RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);
//                        RequestBody current_user_email = RequestBody.create(MultipartBody.FORM, user_Email);
//                        RequestBody image_id = RequestBody.create(MultipartBody.FORM, new_Photo.getmIdString());

                        MultipartBody.Part part = MultipartBody.Part.createFormData("imageFile", file.getName(), filePart);

//                        if (bitmap != null) {
//                            try {
//                                ExifInterface ei = new ExifInterface(path);
//                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                                        ExifInterface.ORIENTATION_UNDEFINED);
//
//                                rotatedBitmap = null;
//                                switch (orientation) {
//
//                                    case ExifInterface.ORIENTATION_ROTATE_90:
//                                        rotatedBitmap = rotateImage(bitmap, 90);
//                                        break;
//
//                                    case ExifInterface.ORIENTATION_ROTATE_180:
//                                        rotatedBitmap = rotateImage(bitmap, 180);
//                                        break;
//
//                                    case ExifInterface.ORIENTATION_ROTATE_270:
//                                        rotatedBitmap = rotateImage(bitmap, 270);
//                                        break;
//
//                                    case ExifInterface.ORIENTATION_NORMAL:
//                                    default:
//                                        rotatedBitmap = bitmap;
//                                        break;
//                                }
//                            } catch (Exception e) {
//
//                            }
//                            Gallery.add(rotatedBitmap);
//                        }

                        //Initialize Service
                        final RetrofitClient retrofitClient;
                        retrofitClient = RetrofitClient.getInstance(getContext()).createBaseApi();

                        retrofitClient.uploadPhoto(part, new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {

                                String filename = (String) receivedData;

                                retrofitClient.updateUserGallery(user_Email, filename, new RetroCallback() {
                                    @Override
                                    public void onError(Throwable t) {
                                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onSuccess(int code, Object receivedData) {
                                        Toast.makeText(getActivity(), "Uploaded photo taken.", Toast.LENGTH_SHORT).show();
                                        initializeRecyclerView();
                                    }

                                    @Override
                                    public void onFailure(int code) {
                                        Toast.makeText(getActivity(), "Code: " + code, Toast.LENGTH_SHORT).show();
                                    }
                                });
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
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e){
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    public void initializeRecyclerView() {
        RecyclerView recyclerViewtab2 = view.findViewById(R.id.recycler_view_tab2);
        RecyclerViewAdapterTab2 adapterTab2 = new RecyclerViewAdapterTab2(getActivity(), serverPathList);
        recyclerViewtab2.setAdapter(adapterTab2);
        recyclerViewtab2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }



}