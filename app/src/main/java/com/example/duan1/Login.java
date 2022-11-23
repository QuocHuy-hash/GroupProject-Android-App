package com.example.duan1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login extends AppCompatActivity {

    private ImageView imgBack;
    private CircleImageView imgLoginFacebook, imgLoginGoole, imgLoginZalo;
    private TextView tvResetPassword, tvRegister;
    private EditText edtNumberPhone, edtPassword;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
//                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
//                                FirebaseUser currentUser = mAuth.getCurrentUser();
//                                String name = currentUser.getDisplayName();
//                                String image = String.valueOf(currentUser.getPhotoUrl());
//                                intent.putExtra("name",name );
//                                intent.putExtra("image", image);
                        setResult(RESULT_OK,intent );
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        initListenerClick();


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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

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
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile"));
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

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            intent.putExtra("name", email);
                                            setResult(RESULT_OK,intent );
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Login.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },2000);
                            }
                        });

            }
        });

    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}