package com.example.androidauthpostgresqlnodejs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.androidauthpostgresqlnodejs.Retrofit.IMyService;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

//    String[] permission_list = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_CONTACTS
////            Manifest.permission.INTERNET
//    };

//    public void checkPermission() {
//        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
//            return;
//        List<String> PermissionList = new ArrayList<>();
//
//        for (String permission : permission_list) {
//            //권한 허용 여부를 확인한다.
//            int chk = checkCallingOrSelfPermission(permission);
//
//            if (chk == PackageManager.PERMISSION_DENIED) {
//                //권한 허용을여부를 확인하는 창을 띄운다
//                PermissionList.add(permission);
//            }
//        }
//        if (!PermissionList.isEmpty()) {
//            requestPermissions(PermissionList.toArray(new String[PermissionList.size()]), 0);
//        } else {
//            initializeView();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 0) {
//            if (grantResults.length > 0) {
//                for (int i = 0; i < grantResults.length; i++) {
//                    //허용됬다면
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();
//                        finish();
//                        return;
//                    }
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();
//                finish();
//                return;
//            }
//            initializeView();
//        }
//    }


    TextView txt_create_account;
    MaterialEditText edt_login_email, edt_login_password;
    Button btn_login;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    public void initializeView() {
        edt_login_email = (MaterialEditText) findViewById(R.id.edt_email);
        edt_login_password = (MaterialEditText) findViewById(R.id.edt_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginUser(edt_login_email.getText().toString(),
                        edt_login_password.getText().toString());
                Intent intent = new Intent(MainActivity.this, TabActivity.class);
                startActivity(intent);
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

                                registerUser(edt_register_email.getText().toString(),
                                        edt_register_name.getText().toString(),
                                        edt_register_password.getText().toString());
                            }
                        }).show();
            }
        });

    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //Initialize view
        edt_login_email = (MaterialEditText) findViewById(R.id.edt_email);
        edt_login_password = (MaterialEditText) findViewById(R.id.edt_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginUser(edt_login_email.getText().toString(),
                        edt_login_password.getText().toString());
                Intent intent = new Intent(MainActivity.this, TabActivity.class);
                startActivity(intent);
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

                                registerUser(edt_register_email.getText().toString(),
                                        edt_register_name.getText().toString(),
                                        edt_register_password.getText().toString());
                            }
                        }).show();
            }
        });

    }

    private void registerUser(String email, String name, String password) {
        compositeDisposable.add(iMyService.registerUser(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void loginUser(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email cannot be empty. Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be empty. Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(iMyService.loginUser(email, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String response) throws Exception {
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
