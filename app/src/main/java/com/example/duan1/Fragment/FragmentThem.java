package com.example.duan1.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Account;
import com.example.duan1.Login;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentThem extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private ImageView imgLoginRegister;
    private TextView tvEmail;

    private MainActivity mainActivity;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private NavigationView nav;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);
        mainActivity = (MainActivity) getActivity();
        initUi(view);
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            tvEmail.setText(currentUser.getEmail());
        }

        initListenerClick();

        return view;
    }

    private void initUi(View view) {
        imgLoginRegister = view.findViewById(R.id.img_login_register);
        tvEmail = view.findViewById(R.id.tvEmail);
        nav = view.findViewById(R.id.nav_them);
        progressDialog = new ProgressDialog(mainActivity);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initListenerClick() {
        imgLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check đã đăng nhập hay chưa

                // Check if user is signed in (non-null) and update UI accordingly.
                if(currentUser != null){
                    startActivity(new Intent(mainActivity, Account.class));
                }else {
                    startActivity(new Intent(mainActivity, Login.class));
                }

            }
        });

    }

    @Override
    public void onResume() {
        if(currentUser != null){
            tvEmail.setText(currentUser.getEmail());
        }
        super.onResume();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.logout){
            progressDialog.setTitle("Đang đăng xuất...");
            progressDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(mainActivity, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                }
            }, 2000);
        }

        return true;
    }
}