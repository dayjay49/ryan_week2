package com.example.androidauthpostgresqlnodejs.Retrofit;

import com.example.androidauthpostgresqlnodejs.Contact;
import com.example.androidauthpostgresqlnodejs.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroBaseApiService {

    @GET("/users/{userEmail}")
    Call<User> getUserByEmail(@Path("userEmail") String email);

    @POST("register")
    Call<Object> registerUser(@Body Object user);
    /*Call<User> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);*/

    @POST("login")
    @FormUrlEncoded
    Call<Object> loginUser(@Field("email") String email,
                           @Field("password") String password);

    @GET("contacts")
    Call<List<Contact>> loadContacts(@Path("contacts") String email);

}
