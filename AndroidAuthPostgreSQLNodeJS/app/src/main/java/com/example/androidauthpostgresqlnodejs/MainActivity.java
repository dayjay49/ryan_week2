package com.example.androidauthpostgresqlnodejs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetroBaseApiService;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetroCallback;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView txt_create_account;
    MaterialEditText edt_login_email, edt_login_password;
    Button btn_login;

    String user_Email;

//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    @Override
//    protected void onStop() {
//        compositeDisposable.clear();
//        super.onStop();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize Service
        final RetrofitClient retrofitClient;
        retrofitClient = RetrofitClient.getInstance(this).createBaseApi();

        //Initialize view
        edt_login_email = (MaterialEditText) findViewById(R.id.edt_email);
        edt_login_password = (MaterialEditText) findViewById(R.id.edt_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edt_login_email.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Email cannot be empty. Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edt_login_password.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty. Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // login with user and check if username and password match/exist
                retrofitClient.loginUser(edt_login_email.getText().toString(),
                    edt_login_password.getText().toString(), new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onSuccess(int code, Object receivedData) {

                            Toast.makeText(MainActivity.this, "Login successful." , Toast.LENGTH_SHORT).show();

                            retrofitClient.loadContacts(edt_login_email.getText().toString(), new RetroCallback() {
                                @Override
                                public void onError(Throwable t) {
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(int code, Object receivedData) {
                                    // received Data connect with Intent
                                    Intent intent = new Intent(MainActivity.this, TabActivity.class);
                                    user_Email = edt_login_email.getText().toString();
//                                    intent.putExtra("user_Email", edt_login_email.getText().toString());
                                    startActivity(intent);                  //Open tabbed activity

                                }

                                @Override
                                public void onFailure(int code) {
                                    Toast.makeText(MainActivity.this, "Code: " + code, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        @Override
                        public void onFailure(int code) {
                            Toast.makeText(MainActivity.this, "Code: " + code, Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });

        txt_create_account = findViewById(R.id.txt_create_account);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View register_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.registration_layout, null);

                new MaterialStyledDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_user)
                        .setTitle("REGISTRATION")
                        .setDescription("Please fill all fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                MaterialEditText edt_register_email = (MaterialEditText) register_layout.findViewById(R.id.edt_email);
                                MaterialEditText edt_register_name = (MaterialEditText) register_layout.findViewById(R.id.edt_name);
                                MaterialEditText edt_register_password = (MaterialEditText) register_layout.findViewById(R.id.edt_password);

                                if (TextUtils.isEmpty(edt_register_email.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Email cannot be empty. Please enter email", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (TextUtils.isEmpty(edt_register_name.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Name cannot be empty. Please enter name", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (TextUtils.isEmpty(edt_register_password.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Password cannot be empty. Please enter password", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                retrofitClient.registerUser(edt_register_email.getText().toString(),
                                        edt_register_name.getText().toString(),
                                        edt_register_password.getText().toString(), new RetroCallback() {
                                            @Override
                                            public void onError(Throwable t) {
                                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onSuccess(int code, Object receivedData) {
                                                Toast.makeText(MainActivity.this, receivedData.toString() , Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(int code) {
                                                Toast.makeText(MainActivity.this, "Code: " + code, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).show();
            }
        });
    }

    public String getUser_Email() {
        return user_Email;
    }

}
