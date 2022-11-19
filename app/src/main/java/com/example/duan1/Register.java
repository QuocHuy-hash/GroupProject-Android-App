
package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {

    private ImageView imgBack;
    private CircleImageView imgLoginFacebook, imgLoginGoole, imgLoginZalo;

    private TextView tvTermsAndUse, tvLogin;
    private EditText edtNumberPhone, edtPassword;
    private Button btnRegister;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


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

    private void initUi() {
        imgBack = findViewById(R.id.img_back_register);
        imgLoginFacebook = findViewById(R.id.img_register_facebook);
        imgLoginGoole = findViewById(R.id.img_register_google);
        imgLoginZalo = findViewById(R.id.img_register_zalo);
        tvLogin = findViewById(R.id.tv_login);
        tvTermsAndUse = findViewById(R.id.tv_terms_of_use);
        edtNumberPhone = findViewById(R.id.edt_numberphone_register);
        edtPassword = findViewById(R.id.edt_password_register);
        btnRegister = findViewById(R.id.btn_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    private void initListenerClick() {


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

                Toast.makeText(Register.this, "Login fb from Login", Toast.LENGTH_SHORT).show();

                Toast.makeText(Register.this, "Login fb from register", Toast.LENGTH_SHORT).show();
         }
        });

        //click login google
        imgLoginGoole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login google

                Toast.makeText(Register.this, "Login google from Login", Toast.LENGTH_SHORT).show();

                Toast.makeText(Register.this, "Login google from register", Toast.LENGTH_SHORT).show();

            }
        });

        //click login zalo
        imgLoginZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code login zalo

                Toast.makeText(Register.this, "Login zalo from Login", Toast.LENGTH_SHORT).show();
            }
        });

        //click reset password
        tvTermsAndUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code reset password
                Toast.makeText(Register.this, "Terms and use from Login", Toast.LENGTH_SHORT).show();

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


                String email = edtNumberPhone.getText().toString().trim();
                if (edtNumberPhone == null || email.isEmpty()){
                    edtNumberPhone.setError("Email không được để trống!");
                    return;
                }
                String password = edtPassword.getText().toString().trim();
                if (edtPassword == null || password.isEmpty()){
                    edtPassword.setError("Mật khẩu không được để trống!");
                }else if (password.length() < 6 ){
                    edtPassword.setError("Mật khẩu phải bằng hoặc trên 6 kí tự");
                    return;
                }
                progressDialog.setTitle("Xin chờ!");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(Register.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(Register.this, "Tạo tài khoản thất bại!.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },2000);
                            }
                        });

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



}

   

}

