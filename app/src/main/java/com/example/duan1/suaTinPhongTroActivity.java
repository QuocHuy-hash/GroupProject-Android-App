package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Adapter.photoAdapter;
import com.example.duan1.model.BDSNews;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class suaTinPhongTroActivity extends AppCompatActivity implements photoAdapter.CountOfImageWhenRemove{
    private TextView tvTenDanhMuc;
    private ImageView imgBackPage;
    private EditText title_post, description, address, dienTich, price;
    private LinearLayout addImageProduct;
    private Button btnSuaTin;
    private chonDanhMucThoiTrangAcrivity chonDanhMucThoiTrangAcrivity;


    private com.example.duan1.Adapter.photoAdapter photoAdapter;
    private RecyclerView rcvView_select_img_PhongTro;
    private ArrayList<Uri> imageUri;
    private int REQUEST_PERMISSION_CODE = 35;
    private int PICK_IMAGE = 1;
    private int update_count = 0, idUser;
    private int maxID;
    private ProgressDialog progressDialog;
    private String strTitle, strDesc, strAddress, strDienTich, strPrice, tenDanhMuc, nameUser , title_Post;
    private Double dbPrice;
    private List<BDSNews> listBDS;
    MainActivity mainActivity;
    BDSNews bdsNewsId;

    StorageReference imageFolder;
    DatabaseReference myData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tin_phong_tro);

        imageFolder = FirebaseStorage.getInstance().getReference().child("Image.jpg");
        myData = FirebaseDatabase.getInstance().getReference("Tin").child("BDS");
        imageUri = new ArrayList<>();


        nameUser = mainActivity.name;
        idUser = mainActivity.id;

        initUi();
        clickBackPage();
        setTextInput();
        clickAddImageFashion();
        editNews();

    }

    private void editNews() {
        btnSuaTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(suaTinPhongTroActivity.this);
                progressDialog.setMessage("Please wait for save");

                strTitle = title_post.getText().toString().trim();
                strDesc = description.getText().toString().trim();
                strPrice = price.getText().toString().trim();

                strDienTich = dienTich.getText().toString().trim();
                strAddress = address.getText().toString().trim();
                try {
                    dbPrice = Double.parseDouble(strPrice);
                } catch (Exception e) {
                    Toast.makeText(suaTinPhongTroActivity.this, "Lỗi chuyển đổi kiểu dữ liệu", Toast.LENGTH_SHORT).show();
                }

                if (strAddress.isEmpty() || strPrice.isEmpty() || strDesc.isEmpty() || strTitle.isEmpty() || strDienTich.isEmpty()) {
                    MainActivity.showDiaLogWarning(suaTinPhongTroActivity.this, "vui lòng nhập đầy đủ thông tin");

                } else {
                    progressDialog.show();
                    for (update_count = 0; update_count < imageUri.size(); update_count++) {
                        Uri indexImage = imageUri.get(update_count);
                        StorageReference imageName = imageFolder.child("Image " + indexImage.getLastPathSegment());
                        imageName.child(strTitle).putFile(indexImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String Url = String.valueOf(uri);
                                        StoreLick(Url);

                                    }
                                });
                            }
                        });
                    }

                    maxID = bdsNewsId.getId();
                    String strId = String.valueOf(maxID);

                    myData.child(tenDanhMuc).child(strId).setValue(
                            new BDSNews(maxID, strTitle, strDesc, dbPrice, strDienTich, strAddress,
                                    idUser, nameUser,tenDanhMuc
                            )).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(suaTinPhongTroActivity.this, "Sửa tin thành công", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    private void clickAddImageFashion() {
        photoAdapter = new photoAdapter(imageUri, this, this);
        rcvView_select_img_PhongTro.setLayoutManager(new GridLayoutManager(suaTinPhongTroActivity.this, 6));
        rcvView_select_img_PhongTro.setAdapter(photoAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
            return;
        }

        addImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                startActivityForResult(Intent.createChooser(intent, "select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();

                for (int i = 0; i < count; i++) {
                    if (imageUri.size() < 9) {
                        Uri imgUri = (data.getClipData().getItemAt(i).getUri());
                        imageUri.add(imgUri);
                    } else {
                        Toast.makeText(this, "Chỉ đăng tối đa 9 hình ảnh", Toast.LENGTH_SHORT).show();
                    }

                }
                photoAdapter.notifyDataSetChanged();
            } else {
                if (imageUri.size() < 9) {
                    Uri imgUri = data.getData();
                    imageUri.add(imgUri);
                } else {
                    Toast.makeText(this, "Chỉ đăng tối đa 9 hình ảnh", Toast.LENGTH_SHORT).show();
                }


            }
            photoAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "you haven't pick any Image", Toast.LENGTH_SHORT).show();
        }
    }


    private void StoreLick(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("List Image");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", url);
        databaseReference.push().setValue(hashMap);
    }

    private void setTextInput() {
        listBDS = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Tin").child("BDS");
        databaseReference.child(tenDanhMuc).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BDSNews bdsNews = snapshot.getValue(BDSNews.class);
                listBDS.add(bdsNews);
                if(bdsNews.getTitle().equals(title_Post)){
                    title_post.setText(bdsNews.getTitle());
                    description.setText(bdsNews.getDescription());
                    address.setText(bdsNews.getAdress());
                    dienTich.setText(bdsNews.getDienTich());
                    String strPrice = String.valueOf(bdsNews.getPrice());
                    price.setText(strPrice);
                    bdsNewsId = bdsNews;
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void clickBackPage() {
        imgBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initUi() {
        tvTenDanhMuc = findViewById(R.id.tvTenDanhMuc);
        Intent intent = getIntent();
        title_Post = intent.getStringExtra("title");
        tenDanhMuc = intent.getStringExtra("tenDanhMuc");
        tvTenDanhMuc.setText("Danh Mục - " + tenDanhMuc);
        imgBackPage = findViewById(R.id.icon_back);
        addImageProduct = findViewById(R.id.addImageProduct);
        btnSuaTin = findViewById(R.id.btnSuaTinPT);
        title_post = findViewById(R.id.title_postPT);
        description = findViewById(R.id.description_postPT);
        address = findViewById(R.id.addressPT);
        dienTich = findViewById(R.id.dienTichPT);
        price = findViewById(R.id.pricePT);
        rcvView_select_img_PhongTro = findViewById(R.id.rcvView_select_img_PhongTro);
    }

    @Override
    public void clicked(int getSize) {

    }
}