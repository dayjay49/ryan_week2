package com.example.androidauthpostgresqlnodejs.Retrofit;

import com.example.androidauthpostgresqlnodejs.Contact_Data;
import com.example.androidauthpostgresqlnodejs.Photo;
import com.example.androidauthpostgresqlnodejs.User;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RetroBaseApiService {

    // Tab 1 Contacts API
    @POST("register")
    Call<Object> registerUser(@Body Object user);

    @POST("login")
    Call<List<User>> loginUser(@Body User user);

    @GET("contacts/{email}")
    Call<List<Contact_Data>> loadContactsByUser(@Path("email") String email);

    @POST("contacts")
    @FormUrlEncoded
    Call<String> addContact(@Field("phone_number") String phone_number,
                            @Field("contact_name") String contact_name);

//    @GET("contacts/{phone_number}")
//    Call<List<Contact_Data>> getContact(@Path("phone_number") String contact_name);

    @POST("contacts/update")
    @FormUrlEncoded
    Call<String> updateUserContacts(@Field("email") String email,
                                    @Field("phone_number") String phone_number);

    @POST("contacts/remove")
    @FormUrlEncoded
    Call<String> deleteContact(@Field("email") String email,
                               @Field("phone_number") String phone_number);

    // Tab 2 Gallery API
    @GET("gallery/{email}")
    Call<List<Photo>> loadGallery(@Path("email") String email);

    @Multipart
    @POST("gallery/add")
    Call<Object> uploadPhoto(@Part MultipartBody.Part imageFile);

    @POST("gallery/update")
    @FormUrlEncoded
    Call<String> updateUserGallery(@Field("email") String email,
                                   @Field("filename") String filename);

    @POST("gallery/remove")
    @FormUrlEncoded
    Call<String> deletePhoto(@Field("email") String email,
                             @Field("filename") String filename);
}
