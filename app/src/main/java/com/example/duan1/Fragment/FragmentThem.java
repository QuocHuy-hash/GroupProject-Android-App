package com.example.duan1.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentThem extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private ImageView imgLoginRegister;
    private TextView tvEmail;

    private MainActivity mainActivity;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private NavigationView nav;
    private ProgressDialog progressDialog;
    private AccessToken accessToken = AccessToken.getCurrentAccessToken();
    boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Restart();
//                    if (result.getResultCode() == RESULT_OK){
//                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//                        if (isLoggedIn){
//
//                        }else{
//                            Intent intent = result.getData();
//                            String strName = "";
//                            String strImage = "";
//                            if (intent != null){
//                                strName = intent.getStringExtra("name");
//                                strImage = intent.getStringExtra("image");
//                                Picasso.with(mainActivity)
//                                        .load(strImage)
//                                        .resize(60,60)
//                                        .placeholder(R.mipmap.ic_launcher)
//                                        .error(R.mipmap.ic_launcher)
//                                        .into(imgLoginRegister);
//                            }
//                            tvEmail.setText(strName);
//                        }

//                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);
        mainActivity = (MainActivity) getActivity();
        initUi(view);

        nav.setItemIconTintList(null);
        nav.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this );

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
                currentUser = mAuth.getCurrentUser();
                // Check if user is signed in (non-null) and update UI accordingly.
                if(currentUser != null || isLoggedIn){
                    startActivity(new Intent(mainActivity, Account.class));
                }else {
                    mActivityResultLauncher.launch(new Intent(mainActivity, Login.class));
                }

            }
        });

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
                    LoginManager.getInstance().logOut();
                    Toast.makeText(mainActivity, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Restart();

                }
            }, 2000);
        }

        return true;
    }

    private void Restart(){
        FragmentManager fragmentManager;
        fragmentManager = mainActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.container, new FragmentThem());
        fragmentTransaction1.detach(FragmentThem.this);
        fragmentTransaction1.commit();

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        if (isLoggedIn){
            getProfileUser();
        }else{
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }

    }

    private void updateUI(FirebaseUser currentUser) {
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            tvEmail.setText(currentUser.getDisplayName());
            String strImage = String.valueOf(currentUser.getPhotoUrl());
            Picasso.with(mainActivity)
                    .load(strImage)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgLoginRegister);
        }
    }

    private void getProfileUser() {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String fullName = object.getString("name");
                                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                    tvEmail.setText(fullName);
                                    Picasso.with(mainActivity)
                                            .load(image)
                                            .placeholder(R.mipmap.ic_launcher)
                                            .error(R.mipmap.ic_launcher)
                                            .into(imgLoginRegister);

                                } catch (JSONException e) {
                                    Toast.makeText(mainActivity, "Lỗi login fb", Toast.LENGTH_SHORT).show();
                                }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

}