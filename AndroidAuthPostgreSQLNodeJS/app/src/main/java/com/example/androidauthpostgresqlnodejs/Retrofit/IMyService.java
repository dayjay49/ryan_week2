package com.example.androidauthpostgresqlnodejs.Retrofit;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMyService {

    final String Base_URL = "http://http://143.248.38.250:3000";

//    @POST("register")
//    @FormUrlEncoded
//    Observable<String> registerUser(@Field("email") String email,
//                                    @Field("name") String name,
//                                    @Field("password") String password);
//
//    @POST("login")
//    @FormUrlEncoded
//    Observable<String> loginUser(@Field("email") String email,
//                                    @Field("password") String password);

    @GET("/posts/{userId}")
    Call<ResponseGet> getFirst(@Path("userId") String id);

    @GET("/posts")
    Call<List<ResponseGet>> getSecond(@Query("userId") String id);

    @FormUrlEncoded @POST("/posts")
    Call<ResponseGet> postFirst(@FieldMap HashMap<String, Object> parameters);

    @DELETE("/posts/1")
    Call<ResponseBody> deleteFirst();


}
