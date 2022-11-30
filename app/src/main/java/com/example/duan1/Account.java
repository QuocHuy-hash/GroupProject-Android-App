package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account extends AppCompatActivity {

    private TextView tvUsernameTitle, tvUsernameContent, tvFollowers, tvWatching, tvEditAccount
            , tvJudge, tvParticipationDate, tvLocation, tvFeedbackMessage, tvBelieve;
    private ImageView imgShare;
    private CircleImageView imgAvatar;
    private Button btnPost, btnJobApplication;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initUi();

        initClickListener();

        getProfileUser();
    }

    //Ánh xạ
    private void initUi() {
        tvUsernameTitle = findViewById(R.id.username_activity_account);
        tvUsernameContent = findViewById(R.id.username_content_activity_account);
        tvFollowers = findViewById(R.id.tv_followers_activity_account);
        tvWatching = findViewById(R.id.tv_watching_activity_account);
        tvEditAccount = findViewById(R.id.tv_edit_account_activity_account);
        tvJudge = findViewById(R.id.tv_judge_activity_account);
        tvParticipationDate = findViewById(R.id.tv_participation_date_activity_account);
        tvLocation = findViewById(R.id.tv_location_activity_account);
        tvFeedbackMessage = findViewById(R.id.tv_feedback_message_activity_account);
        tvBelieve = findViewById(R.id.tv_believe_activity_account);
        imgAvatar = findViewById(R.id.img_avatar_activity_account);
        imgShare = findViewById(R.id.img_share_acvitity_account);
        btnPost = findViewById(R.id.btn_post_news);
        btnJobApplication = findViewById(R.id.btn_job_application);
        btnBack = findViewById(R.id.btn_back_activity_account);
    }

    //sự kiện click
    private void initClickListener() {
        tvEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, EditAccount.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getProfileUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        List<Users> mListUser = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListUser.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Users user = snapshot1.getValue(Users.class);
                    mListUser.add(user);
                }
                for (int i = 0; i < mListUser.size(); i++){
                    if (mListUser.get(i).getEmail().equals(email)){
                        if (mListUser.get(i).getName().length() > 30){
                            tvUsernameTitle.setText(checkname(mListUser.get(i).getName()));
                            tvUsernameContent.setText(checkname(mListUser.get(i).getName()));
                        }else{
                            tvUsernameTitle.setText(mListUser.get(i).getName());
                            tvUsernameContent.setText(mListUser.get(i).getName());
                        }
                        String strImage = mListUser.get(i).getImage().trim();
                        if (strImage.isEmpty()){
                            return;
                        }
                        Picasso.with(Account.this)
                                .load(strImage)
                                .placeholder(R.drawable.user_icon)
                                .error(R.drawable.user_icon)
                                .into(imgAvatar);

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private String checkname(String s){

        if (s.isEmpty()){
            return null;
        }
        String name = "";
        if (s.length() > 20){
            for (String s1: s.split("")){
                name+=s1;
                if (name.length() == 30){
                    break;
                }
            };
        }

        return name+"...";
    }
}