package com.example.androidauthpostgresqlnodejs.Retrofit;

import com.example.androidauthpostgresqlnodejs.Contact_Data;
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

public interface RetroBaseApiService {

    // Tab 1 Contacts API
    @POST("register")
    Call<Object> registerUser(@Body Object user);

    @POST("login")
    Call<List<User>> loginUser(@Body User user);

    @GET("contacts/{email}")
    Call<List<Contact_Data>> loadContacts(@Path("email") String email);

    @POST("contacts")
    @FormUrlEncoded
    Call<String> addContact(@Field("phone_number") String phone_number,
                            @Field("contact_name") String contact_name);
    @POST("update")
    @FormUrlEncoded
    Call<String> updateUserContacts(@Field("email") String email,
                                    @Field("phone_number") String phone_number);
    @POST("remove")
    @FormUrlEncoded
    Call<String> deleteContact(@Field("email") String email,
                               @Field("phone_number") String phone_number);

    // Tab 2 Gallery API
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(@Part("photo") MultipartBody.Part imageFile,
                                   @Part("user") RequestBody user,
                                   @Part("photo_id") RequestBody photo_id);
}
