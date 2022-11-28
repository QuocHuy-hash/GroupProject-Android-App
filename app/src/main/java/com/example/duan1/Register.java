
package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
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

    private TextView tvTermsAndUse, tvLogin ;
    private EditText edtNumberPhone, edtPassword;
    private Button btnRegister;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        initListenerClick();

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void initListenerClick(){

        //click back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code back
                finish();
            }
        });

        //click reset password
        tvTermsAndUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code reset password
                Toast.makeText(Register.this, "Terms and use from Login", Toast.LENGTH_SHORT).show();

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

        edtNumberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtNumberPhone.getText().toString().trim().equals("") || edtPassword.getText().toString().trim().equals("")){
                    btnRegister.setBackgroundResource(R.drawable.bg_button_login);
                    return;
                }else{
                    btnRegister.setBackgroundResource(R.drawable.bg_button_register);

                    //click login
                    btnRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //code login
                            String email = edtNumberPhone.getText().toString().trim();
                            if (isValidEmail(email)){
                                edtNumberPhone.setError("Email không được để trống!");
                                return;
                            }
                            String password = edtPassword.getText().toString().trim();
                            if (password.length() < 6 ){
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
                }
            }
        });


        tvTermsAndUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code terms of use
                Toast.makeText(Register.this, "Terms of use from register", Toast.LENGTH_SHORT).show();
            }
        });

    }
  private void initUi() {
        imgBack = findViewById(R.id.img_back_register);
        tvLogin = findViewById(R.id.tv_login);
        tvTermsAndUse = findViewById(R.id.tv_terms_of_use);
        edtNumberPhone = findViewById(R.id.edt_numberphone_register);
        edtPassword = findViewById(R.id.edt_password_register);
        btnRegister = findViewById(R.id.btn_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }
}


