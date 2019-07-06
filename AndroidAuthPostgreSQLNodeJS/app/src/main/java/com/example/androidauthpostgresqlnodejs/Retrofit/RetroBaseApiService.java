package com.example.androidauthpostgresqlnodejs.Retrofit;

import com.example.androidauthpostgresqlnodejs.Contact;
import com.example.androidauthpostgresqlnodejs.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroBaseApiService {

   // request User, response String
    @POST("register")
    Call<String> registerUser(@Body User user);

    // request Strings, response User
    @POST("login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("email") String email,
                           @Field("password") String password);

    @GET("contacts/{email}")
    Call<List<Contact>> loadContacts(@Path("contacts") String email);

    @POST("contacts")
    @FormUrlEncoded
    Call<String> addContact(@Field("email") String email,
                            @Field("phone_number") String phone_number,
                            @Field("contact_name") String contact_name);

    @DELETE("contacts/{phone_number}")
    Call<String> deleteContact(@Path("phone_number") String phone_number);

}
