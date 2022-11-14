package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
            }
        });

        //click login facebook
        imgLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login facebook
            }
        });

        //click login google
        imgLoginGoole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login google
            }
        });

        //click login zalo
        imgLoginZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login zalo
            }
        });

        //click reset password
        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code reset password
            }
        });

        //click register
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code register
            }
        });

        //click login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login
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
        btnLogin = findViewById(R.id.btn_login);

    }
}