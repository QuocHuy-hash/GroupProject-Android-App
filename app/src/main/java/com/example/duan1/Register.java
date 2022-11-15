
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

public class Register extends AppCompatActivity {

    private ImageView imgBack;
    private CircleImageView imgLoginFacebook, imgLoginGoole, imgLoginZalo;
    private TextView tvTermsOfUse, tvLogin;
    private EditText edtNumberPhone, edtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        initListenerClick();
    }

    private void initListenerClick() {
        //click back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code back
                finish();
            }
        });

        //click login facebook
        imgLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login facebook
                Toast.makeText(Register.this, "Login fb from register", Toast.LENGTH_SHORT).show();
            }
        });

        //click login google
        imgLoginGoole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login google
                Toast.makeText(Register.this, "Login google from register", Toast.LENGTH_SHORT).show();
            }
        });

        //click login zalo
        imgLoginZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login zalo
                Toast.makeText(Register.this, "Login zalo from register", Toast.LENGTH_SHORT).show();
            }
        });

        //click register
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code register
                finish();
            }
        });

        //click login
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login
            }
        });

        tvTermsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code terms of use
                Toast.makeText(Register.this, "Terms of use from register", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back_register);
        imgLoginFacebook = findViewById(R.id.img_register_facebook);
        imgLoginGoole = findViewById(R.id.img_register_google);
        imgLoginZalo = findViewById(R.id.img_register_zalo);
        tvLogin = findViewById(R.id.tv_login);
        edtNumberPhone = findViewById(R.id.edt_numberphone_register);
        edtPassword = findViewById(R.id.edt_password_register);
        btnRegister = findViewById(R.id.btn_register);
        tvTermsOfUse = findViewById(R.id.tv_terms_of_use);


    }

}
