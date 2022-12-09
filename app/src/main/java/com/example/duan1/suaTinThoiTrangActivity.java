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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Adapter.photoAdapter;
import com.example.duan1.model.product_type;
import com.example.duan1.model.thoiTrangNews;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
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

public class suaTinThoiTrangActivity extends AppCompatActivity implements photoAdapter.CountOfImageWhenRemove{
    private TextView tvTenDanhMuc;
    private ImageView imgBackPage, addImageProduct , iconCloser;
    private Spinner spn_product_type;
    private List<thoiTrangNews> listFashion;
    private String title_Post, tenDanhMuc;
    private EditText edtTitlePost, edtDescription, edtPrice, edtAddress;
    private Button btnSuaTin;
    private RecyclerView rcvView_select_img_fashion;
    private com.example.duan1.Adapter.photoAdapter photoAdapter;
    private ArrayList<Uri> imageUri = new ArrayList<>();
    private int REQUEST_PERMISSION_CODE = 35;
    private int PICK_IMAGE = 1;
    FirebaseApp firebaseApp;
    FirebaseAppCheck firebaseAppCheck;
    private String strTitlePost, strDescription, strPrice, strLoaiSanPham, strAddress, nameUser
            , tenDanhMucNew , title_PostNew;
    private Double dbPrice;
    private int update_count = 0, maxID , idUser;
    private ProgressDialog progressDialog;
    DatabaseReference myData;
    StorageReference imageFolder;
    thoiTrangNews newsFashion;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tin_thoi_trang);
        imageFolder = FirebaseStorage.getInstance().getReference().child("Image").child("images" + System.currentTimeMillis() +"jpg");
        myData = FirebaseDatabase.getInstance().getReference("Tin").child("ThoiTrang");

        nameUser = mainActivity.name;
        idUser = mainActivity.id;

        initUi();
        listFashion = new ArrayList<>();
        clickBackPage();
        eventClickSPN();
        setText();
        clickAddImageFashion();
        clickEditNews();
    }

    private void clickEditNews() {

        btnSuaTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseApp.initializeApp(suaTinThoiTrangActivity.this);
                firebaseAppCheck = FirebaseAppCheck.getInstance();
                firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());

                progressDialog = new ProgressDialog(suaTinThoiTrangActivity.this);
                progressDialog.setMessage("Please wait for save");
                strTitlePost = edtTitlePost.getText().toString().trim();
                strDescription = edtDescription.getText().toString();
                strPrice = edtPrice.getText().toString();
                try {
                    dbPrice = Double.parseDouble(strPrice);
                } catch (Exception e) {
                    System.err.println("Lỗi Parse kiểu dữ liệu");
                }

                strLoaiSanPham = spn_product_type.getSelectedItem().toString();
                strAddress = edtAddress.getText().toString();

                if (strTitlePost.isEmpty() || strDescription.isEmpty() || strPrice.isEmpty()
                        || strAddress.isEmpty()) {
                    MainActivity.showDiaLogWarning(suaTinThoiTrangActivity.this, "vui lòng nhập đầy đủ thông tin");

                } else {
                    progressDialog.show();
                    for (update_count = 0; update_count < imageUri.size(); update_count++) {
                        Uri indexImage = imageUri.get(update_count);
                        StorageReference imageName = imageFolder.child("Image " + indexImage.getLastPathSegment());
                        imageName.child(strTitlePost).putFile(indexImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                }
                maxID = newsFashion.getId();
                String strId = String.valueOf(maxID);
                myData.child(tenDanhMuc).child(strId ).setValue(new thoiTrangNews(maxID ,
                        strTitlePost,
                        strDescription,
                        strLoaiSanPham,
                        dbPrice,
                        strAddress ,
                        idUser,
                        nameUser,tenDanhMuc)) .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(suaTinThoiTrangActivity.this, "Sửa tin thành công", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
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
        iconCloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void StoreLick(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("List Image");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", url);
        databaseReference.push().setValue(hashMap);
    }
    private void clickAddImageFashion() {
        photoAdapter = new photoAdapter(imageUri, this, this);
        rcvView_select_img_fashion.setLayoutManager(new GridLayoutManager(suaTinThoiTrangActivity.this, 6));
        rcvView_select_img_fashion.setAdapter(photoAdapter);

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

    private void eventClickSPN() {
        product_type[] product_type = dangTinThoiTrangActivity.productType.getProductType();
        ArrayAdapter<product_type> adapter = new ArrayAdapter<product_type>(this, android.R.layout.simple_spinner_item, product_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_product_type.setAdapter(adapter);

        spn_product_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(suaTinThoiTrangActivity.this, "Loại sản phẩm " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initUi() {
        Intent intent = getIntent();
        title_Post = intent.getStringExtra("title");
        tenDanhMuc = intent.getStringExtra("tenDanhMuc");
        tvTenDanhMuc = findViewById(R.id.tvTenDanhMucTT1);
        tvTenDanhMuc.setText("Danh Mục - " + intent.getStringExtra("tenDanhMuc"));


        imgBackPage = findViewById(R.id.icon_backTT);
        spn_product_type = findViewById(R.id.spn_product_type);
        edtTitlePost = findViewById(R.id.title_postTT);
        edtDescription = findViewById(R.id.description_postTT);
        edtPrice = findViewById(R.id.priceTT);
        edtAddress = findViewById(R.id.addressTT);
        btnSuaTin = findViewById(R.id.btnSuaTinTT);
        rcvView_select_img_fashion = findViewById(R.id.rcvView_select_img_fashion);
        addImageProduct = findViewById(R.id.addImageProductTT);
        iconCloser = findViewById(R.id.iconCloser);
    }

    private void setText() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Tin").child("ThoiTrang");
        databaseReference.child(tenDanhMuc).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                thoiTrangNews thoiTrangNews = snapshot.getValue(com.example.duan1.model.thoiTrangNews.class);
                listFashion.add(thoiTrangNews);
                if(thoiTrangNews.getTitlePost().equals(title_Post)) {
                    edtTitlePost.setText(thoiTrangNews.getTitlePost());
                    edtAddress.setText(thoiTrangNews.getAddress());
                    edtDescription.setText(thoiTrangNews.getDescriptionPost());
                    String price = String.valueOf(thoiTrangNews.getPrice());
                    edtPrice.setText(price);
                    newsFashion = thoiTrangNews;

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


    @Override
    public void clicked(int getSize) {

    }
}