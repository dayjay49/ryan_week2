package com.example.androidauthpostgresqlnodejs.Retrofit;

import android.content.Context;
import android.widget.Toast;

import com.example.androidauthpostgresqlnodejs.Contact;
import com.example.androidauthpostgresqlnodejs.MainActivity;
import com.example.androidauthpostgresqlnodejs.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static okhttp3.internal.Internal.instance;

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

    private RetrofitClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://143.248.38.250:3000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
    public void registerUser(String email, String name, String password, final RetroCallback callback) {
        User user = new User(email, name, password);
        Call<String> call = apiService.registerUser(user);
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
    // POST
    public void loginUser(String email, String password, final RetroCallback callback) {
        Call<User> call = apiService.loginUser(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    // GET
    public void loadContacts(String email, final RetroCallback callback) {
        apiService.loadContacts(email).enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.code());
                } else {
                    callback.onSuccess(response.code(), response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void addContact(String email, String phone_number, String contact_name, final RetroCallback callback) {
        Call<String> call = apiService.addContact(email, phone_number, contact_name);
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

    public void deleteContact(String phone_number, final RetroCallback callback) {
        apiService.deleteContact(phone_number).enqueue(new Callback<String>() {
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

}

