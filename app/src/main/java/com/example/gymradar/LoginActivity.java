package com.example.gymradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    DBHelper3 dbHelper3;
    private EditText login_email, login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getHashKey();
        dbHelper3 = new DBHelper3(LoginActivity.this, 1);
        ((Button) findViewById(R.id.loginBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_email = findViewById( R.id.email );
                login_password = findViewById( R.id.password );
                String UserEmail = login_email.getText().toString();
                String UserPwd = login_password.getText().toString();
                if(!dbHelper3.getIds().contains(UserEmail)){
                    Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if(!dbHelper3.validationLogin(UserEmail, UserPwd)){
                    Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                    return;
                }
                else {

                    String UserName = dbHelper3.getUsername(UserEmail);

                    //Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    intent.putExtra("UserEmail", UserEmail);
                    intent.putExtra("UserPwd", UserPwd);
                    intent.putExtra("UserName", UserName);

                    startActivity(intent);
                }
            }
        });

        ((TextView) findViewById(R.id.no_loginBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        ((TextView) findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}