package com.example.androidauthpostgresqlnodejs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

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
//    public static int count = 0;
    public static List<Contact_Data> contactList;
    public static User login_user;
    final int EXIT_APP = 9;

    String[] permission_list = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        List<String> PermissionRqList = new ArrayList<>();
        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                PermissionRqList.add(permission);
                //requestPermissions(permission_list,0);
            }
        }
        if(!PermissionRqList.isEmpty()){
            requestPermissions(PermissionRqList.toArray(new String[PermissionRqList.size()]),0);
        }
        else{
            initialize();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            if(grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    //허용됐다면
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            initialize();
        }
    }




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
        checkPermission();
    }

    public void initialize() {
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

                login_user = new User(edt_login_email.getText().toString(), "", edt_login_password.getText().toString());

                // login with user and check if username and password match/exist
                retrofitClient.loginUser(login_user, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        ArrayList<User> receivedList = (ArrayList<User>) receivedData;
                        if (receivedList.size() == 0) {
                            Toast.makeText(MainActivity.this, "Wrong email and/or password. Please enter valid email and password." , Toast.LENGTH_SHORT).show();
                        }
                        else {
                            retrofitClient.loadContacts(login_user.getEmail(), new RetroCallback() {
                                @Override
                                public void onError(Throwable t) {
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(int code, Object receivedData) {
                                    contactList = (List<Contact_Data>) receivedData;
                                    user_Email = login_user.getEmail();
                                    // received Contact_Data connect with Intent
                                    Intent intent = new Intent(MainActivity.this, TabActivity.class);
//                                          intent.putExtra("user_Email", edt_login_email.getText().toString());
                                    startActivity(intent);                  //Open tabbed activity
                                }

                                @Override
                                public void onFailure(int code) {
                                    Toast.makeText(MainActivity.this, "Code: " + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(int code) {
                        Toast.makeText(MainActivity.this, "Code: " + code, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        txt_create_account = findViewById(R.id.btn_create_account);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View register_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.registration_layout, null);

                new MaterialStyledDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_user)
                        .setTitle("CREATE NEW ACCOUNT")
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

                                User new_user = new User(edt_register_email.getText().toString(), edt_register_name.getText().toString(), edt_register_password.getText().toString());

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

                                retrofitClient.registerUser(new_user, new RetroCallback() {
                                    @Override
                                    public void onError(Throwable t) {
                                        System.out.println(t.getMessage());
                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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


    private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
