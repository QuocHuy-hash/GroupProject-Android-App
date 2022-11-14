package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login extends AppCompatActivity {

    private ImageView imgBack;
    private CircleImageView imgLoginFacebook, imgLoginGoole, imgLoginZalo;
    private TextView tvResetPassword, tvRegister;
    private EditText edtNumberPhone, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        initListenerClick();


    }

    private void initListenerClick() {
        //click back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code back
                Toast.makeText(Login.this, "Back from Login", Toast.LENGTH_SHORT).show();
            }
        });

        //click login facebook
        imgLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login facebook
                Toast.makeText(Login.this, "Login fb from Login", Toast.LENGTH_SHORT).show();
            }
        });

        //click login google
        imgLoginGoole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login google
                Toast.makeText(Login.this, "Login google from Login", Toast.LENGTH_SHORT).show();
            }
        });

        //click login zalo
        imgLoginZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login zalo
                Toast.makeText(Login.this, "Login zalo from Login", Toast.LENGTH_SHORT).show();
            }
        });

        //click reset password
        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code reset password
                Toast.makeText(Login.this, "reset Password from Login", Toast.LENGTH_SHORT).show();
            }
        });

        //click register
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code register
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        //click login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login
                Toast.makeText(Login.this, "Login from Login", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        imgLoginFacebook = findViewById(R.id.img_login_facebook);
        imgLoginGoole = findViewById(R.id.img_login_google);
        imgLoginZalo = findViewById(R.id.img_login_zalo);
        tvResetPassword = findViewById(R.id.tv_reset_password);
        tvRegister = findViewById(R.id.tv_register);
        edtNumberPhone = findViewById(R.id.edt_numberphone);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

    }
}