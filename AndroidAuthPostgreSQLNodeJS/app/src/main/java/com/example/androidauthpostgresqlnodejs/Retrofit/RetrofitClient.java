package com.example.androidauthpostgresqlnodejs.Retrofit;

import android.content.Context;
import android.net.Uri;

import com.example.androidauthpostgresqlnodejs.Contact_Data;
import com.example.androidauthpostgresqlnodejs.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private RetroBaseApiService apiService;
    private static Context mContext;
    private Retrofit retrofit;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://143.248.38.250:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RetrofitClient createBaseApi() {
        apiService = create(RetroBaseApiService.class);
        return this;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }
    // POST
    public void registerUser(Object user, final RetroCallback callback) {
        //User user = new User(email, name, password);
        Call<Object> call = apiService.registerUser(user);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    // POST
    public void loginUser(User user, final RetroCallback callback) {
        Call<List<User>> call = apiService.loginUser(user);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    // GET
    public void loadContacts(String email, final RetroCallback callback) {
        Call<List<Contact_Data>> call = apiService.loadContacts(email);
        call.enqueue(new Callback<List<Contact_Data>>() {
            @Override
            public void onResponse(Call<List<Contact_Data>> call, Response<List<Contact_Data>> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Contact_Data>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void addContact(String phone_number, String contact_name, final RetroCallback callback) {
        Call<String> call = apiService.addContact(phone_number, contact_name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void updateUserContacts(String email, String phone_number, final RetroCallback callback) {
        Call<String> call = apiService.updateUserContacts(email, phone_number);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                    callback.onError(t);
            }
        });
    }

    public void deleteContact(String email, String phone_number, final RetroCallback callback) {
        apiService.deleteContact(email, phone_number).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void uploadPhoto(MultipartBody.Part imageFile, RequestBody image_id, final RetroCallback callback) {
        apiService.uploadPhoto(imageFile, image_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}

