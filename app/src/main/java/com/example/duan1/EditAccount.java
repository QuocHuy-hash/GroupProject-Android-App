package com.example.duan1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccount extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 12;

    private TextView tvName, tvSdt, tvEmail, tvLocation, tvCCCD, tvPassword, tvSex, tvDateOfBirth
            , tvHoaDon;
    private CircleImageView imgImage;
    private RelativeLayout imgAvatar;
    private Button imgName;
    private ImageButton btnBack;
    private Bitmap bitmapselect;
    private String strImage = " ";
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog progressDialog;


    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode()== Activity.RESULT_OK){
                        //there are no request codes
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        try {
                            Calendar calendar = Calendar.getInstance();

                            bitmapselect = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();
                            StorageReference mountainsRef = storageRef.child("AvatarUser/image"+calendar.getTimeInMillis());
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmapselect.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] dataImage = baos.toByteArray();

                            UploadTask uploadTask = mountainsRef.putBytes(dataImage);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Toast.makeText(EditAccount.this, "Lỗi cập nhật hình ảnh!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            strImage = String.valueOf(uri);
                                            updateImageUrlRealtimeDatabase();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            Toast.makeText(EditAccount.this, "Lỗi tải URL hình ảnh!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        initUi();
        initClickListener();
    }

    //ánh xạ
    private void initUi() {
        tvName = findViewById(R.id.tv_name_activity_editaccount);
        tvSdt = findViewById(R.id.tv_sdt_activity_editaccount);
        tvEmail = findViewById(R.id.tv_email_activity_editaccount);
        tvLocation = findViewById(R.id.tv_location_activity_editaccount);
        tvCCCD = findViewById(R.id.tv_cccd_activity_editaccount);
        tvPassword = findViewById(R.id.tv_password_activity_editaccount);
        tvSex = findViewById(R.id.tv_sex_activity_editaccount);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth_activity_editaccount);
        tvHoaDon = findViewById(R.id.tv_xuat_hoa_don_activity_editaccount);
        imgAvatar = findViewById(R.id.img_avatar_activity_editaccount);
        imgImage = findViewById(R.id.img_image);
        imgName = findViewById(R.id.img_name);
        btnBack = findViewById(R.id.imgbtn_back);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
    }

    //sự kiện click
    private void initClickListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Đang tải ảnh...");
                progressDialog.show();
                requestPermissionsimage();
            }
        });


        imgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Xin chờ!...");
                progressDialog.show();
                updateNameRealtimeDatabase();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private void getDataUser(){
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
                            tvName.setText(checkname(mListUser.get(i).getName()));
                        }else{
                            tvName.setText(mListUser.get(i).getName());
                        }
                        String strImage = mListUser.get(i).getImage().trim();
                        if (strImage.isEmpty()){
                            return;
                        }
                        Picasso.with(EditAccount.this)
                                .load(strImage)
                                .placeholder(R.drawable.user_icon)
                                .error(R.drawable.user_icon)
                                .into(imgImage);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }

    }

    private void requestPermissionsimage() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(EditAccount.this, "Đã từ chối quyền truy cập\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void updateImageUrlRealtimeDatabase() {
        List<Users> listUser = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listUser.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Users user = snapshot1.getValue(Users.class);
                    listUser.add(user);
                }

                String email = user.getEmail();
                for (int i = 0; i< listUser.size(); i++){
                    if (listUser.get(i).getEmail().equals(email)){
                        count = i;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditAccount.this, "Lỗi cập nhật hình ảnh!", Toast.LENGTH_SHORT).show();
            }


        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateImageUrl(count, listUser);
                progressDialog.dismiss();
            }
        },2000);


    }

    private void UpdateImageUrl(int id, List<Users> users) {
        if (users.size() == 0 || id == 0){
            finish();
        }
        String email = user.getEmail();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        if (users.get(id).getEmail().equals(email)){
            myRef.child("Users").child(users.get(id).getId()+"").child("image").setValue(strImage)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditAccount.this, "Đã xong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateNameRealtimeDatabase() {
        List<Users> listUser = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listUser.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Users user = snapshot1.getValue(Users.class);

                    listUser.add(user);
                }
                String email = user.getEmail();
                for (int i = 0; i< listUser.size(); i++){
                    if (listUser.get(i).getEmail().equals(email)){
                        count = i;
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditAccount.this, "Lỗi cập nhật tên!", Toast.LENGTH_SHORT).show();
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateName(count, listUser);
                progressDialog.dismiss();
            }
        },2000);

    }

    private void UpdateName(int id, List<Users> users){
        if (users.size() == 0){
            finish();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        String email = user.getEmail();
        String name = tvName.getText().toString().trim();
        if (users.get(id).getEmail().equals(email)){
            myRef.child("Users").child(users.get(id).getId()+"").child("name").setValue(name)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditAccount.this, "Đã xong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataUser();
    }
}