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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Adapter.photoAdapter;
import com.example.duan1.model.BDSNews;
import com.example.duan1.model.direction;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class dangTinBDSActivity extends AppCompatActivity implements com.example.duan1.Adapter.photoAdapter.CountOfImageWhenRemove {
    private Spinner spn_Direction;
    private TextView tvTenDanhMuc;
    private ImageView imgBackPage, iconCloser;
    private EditText edtTitlePost, edtDescription, edtTenKhuDanCu, edtAddress, edtLoaiHinh, edtSoPhongNgu,
            edtSoPhongWc, edtPrice, edtDienTich;
    private LinearLayout addImageProduct;
    private Button btnDangTin, suaTinBDS;

    private String date, strTitlePost, strDescription, strTenKhu, strAddress, strLoaiHinhDat, strSoPhongNgu, strSoPhongWc, strPrice, strDienTich, nameUser, tenDanhMuc;
    private double dbPrice;
    private int maxID;
    private String title_Post = null;
    private com.example.duan1.Adapter.photoAdapter photoAdapter;
    private RecyclerView rcvView_select_img_BDS;
    private ArrayList<Uri> imageUri;
    private int REQUEST_PERMISSION_CODE = 35;
    private int PICK_IMAGE = 1;
    private int update_count = 0, idUser, idEdit;
    private List<BDSNews> listBDS;
    private MainActivity mainActivity;
    BDSNews bdsNewsId;

    private ProgressDialog progressDialog;
    DatabaseReference myData;
    StorageReference imageFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tin_bdsactivity);

        imageFolder = FirebaseStorage.getInstance().getReference().child("Image.jpg");
        myData = FirebaseDatabase.getInstance().getReference("Tin").child("BDS");
        imageUri = new ArrayList<>();

        nameUser = mainActivity.name;
        idUser = mainActivity.id;
        initUi();
        eventClickSpinner();
        getListBds();
        clickBackPage();
        clickAddImageFashion();
        clickDangTin();
        getCurrentDate();
        if (title_Post == null) {
            btnDangTin.setVisibility(View.VISIBLE);
            suaTinBDS.setVisibility(View.INVISIBLE);
        } else {
            suaTinBDS.setVisibility(View.VISIBLE);
            btnDangTin.setVisibility(View.INVISIBLE);
            setTextInput();
            editNews();
        }
    }
    private void getCurrentDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        date = sdf.format(c.getTime());
        System.out.println("Date : " + date);
    }
    private void editNews() {
        suaTinBDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(dangTinBDSActivity.this);
                progressDialog.setMessage("Please wait for save");
                strTitlePost = edtTitlePost.getText().toString().trim();
                strDescription = edtDescription.getText().toString();
                strTenKhu = edtTenKhuDanCu.getText().toString().trim();
                strLoaiHinhDat = edtLoaiHinh.getText().toString().trim();
                strAddress = edtAddress.getText().toString().trim();
                strPrice = edtPrice.getText().toString();
                strDienTich = edtDienTich.getText().toString().trim();
                strSoPhongNgu = edtSoPhongNgu.getText().toString().trim();
                strSoPhongWc = edtSoPhongWc.getText().toString().trim();
                try {
                    dbPrice = Double.parseDouble(strPrice);

                } catch(Exception e) {
                    System.err.println("Lỗi Parse kiểu dữ liệu");
                }



                if (strTitlePost.isEmpty() || strDescription.isEmpty() || strPrice.isEmpty()
                        || strAddress.isEmpty() ||  strTenKhu.isEmpty()|| strLoaiHinhDat.isEmpty() ||strDienTich.isEmpty()
                        ||strSoPhongWc.isEmpty() || strSoPhongNgu.isEmpty()) {
                    MainActivity.showDiaLogWarning(dangTinBDSActivity.this, "vui lòng nhập đầy đủ thông tin");

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

                    String strId = String.valueOf(idEdit);
                    myData.child(tenDanhMuc).child(strId ).setValue(
                                    new BDSNews(maxID ,strTitlePost , strDescription , dbPrice
                                            ,strDienTich ,strAddress ,strTenKhu ,strLoaiHinhDat ,
                                            strSoPhongNgu,strSoPhongWc ,idUser,nameUser ,tenDanhMuc, date))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dangTinBDSActivity.this, "Sửa tin thành công", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                }
            }
        });
    }

    private void setTextInput() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Tin").child("BDS");
        databaseReference.child(tenDanhMuc).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BDSNews bdsNews = snapshot.getValue(BDSNews.class);
                if (title_Post.equals(bdsNews.getTitle())) {
                    edtTitlePost.setText(bdsNews.getTitle());
                    edtAddress.setText(bdsNews.getAdress());
                    edtDienTich.setText(bdsNews.getDienTich());
                    edtDescription.setText(bdsNews.getDescription());
                    edtLoaiHinh.setText(bdsNews.getLoaiDat());
                    edtSoPhongNgu.setText(bdsNews.getSoPhongNgu());
                    edtSoPhongWc.setText(bdsNews.getSoPhongWc());
                    edtTenKhuDanCu.setText(bdsNews.getTenPhanKhu());
                    String price = String.valueOf(bdsNews.getPrice());
                    edtPrice.setText(price);

                    idEdit = bdsNews.getId();
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
        iconCloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void eventClickSpinner() {
        direction[] directions = EmployeeDataUtils.getEmployees();
        ArrayAdapter<direction> adapter = new ArrayAdapter<direction>(this, android.R.layout.simple_spinner_item, directions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Direction.setAdapter(adapter);

        spn_Direction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(dangTinBDSActivity.this, "Hướng " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void clickDangTin() {


        btnDangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(dangTinBDSActivity.this);
                progressDialog.setMessage("Please wait for save");

                strTitlePost = edtTitlePost.getText().toString().trim();
                strDescription = edtDescription.getText().toString();
                strTenKhu = edtTenKhuDanCu.getText().toString().trim();
                strLoaiHinhDat = edtLoaiHinh.getText().toString().trim();
                strAddress = edtAddress.getText().toString().trim();
                strPrice = edtPrice.getText().toString();
                strDienTich = edtDienTich.getText().toString().trim();
                strSoPhongNgu = edtSoPhongNgu.getText().toString().trim();
                strSoPhongWc = edtSoPhongWc.getText().toString().trim();

                try {
                    dbPrice = Double.parseDouble(strPrice);

                } catch (Exception e) {
                    System.err.println("Lỗi Parse kiểu dữ liệu");
                }


                if (strTitlePost.isEmpty() || strDescription.isEmpty() || strPrice.isEmpty()
                        || strAddress.isEmpty() || strTenKhu.isEmpty() || strLoaiHinhDat.isEmpty() || strDienTich.isEmpty()
                        || strSoPhongWc.isEmpty() || strSoPhongNgu.isEmpty()) {
                    MainActivity.showDiaLogWarning(dangTinBDSActivity.this, "vui lòng nhập đầy đủ thông tin");

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

                    if (bdsNewsId == null) {
                        maxID = 0;
                    } else {
                        maxID = bdsNewsId.getId() + 1;
                    }

                    String strId = String.valueOf(maxID);
                    myData.child(tenDanhMuc).child(strId).setValue(
                                    new BDSNews(maxID, strTitlePost, strDescription, dbPrice
                                            , strDienTich, strAddress, strTenKhu, strLoaiHinhDat,
                                            strSoPhongNgu, strSoPhongWc, idUser, nameUser, tenDanhMuc, date))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dangTinBDSActivity.this, "Đăng tin thành công", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                }
            }
        });

    }

    private void StoreLick(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("List Image");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", url);
        databaseReference.push().setValue(hashMap);

    }

    private List<BDSNews> getListBds() {
        listBDS = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Tin").child("BDS");
        databaseReference.child(tenDanhMuc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBDS.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    BDSNews bdsNews = snapshot1.getValue(BDSNews.class);
                    listBDS.add(bdsNews);
                    bdsNewsId = bdsNews;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(dangTinBDSActivity.this, "Load data Error", Toast.LENGTH_SHORT).show();
            }
        });

        return listBDS;

    }

    private void initUi() {
        spn_Direction = findViewById(R.id.spn_Direction);
        tvTenDanhMuc = findViewById(R.id.tvTenDanhMuc);
        Intent intent = getIntent();
        title_Post = intent.getStringExtra("title");

        tvTenDanhMuc.setText("Danh Mục - " + intent.getStringExtra("tenDanhMuc"));
        imgBackPage = findViewById(R.id.icon_back);
        addImageProduct = findViewById(R.id.addImageProduct);
        edtTitlePost = findViewById(R.id.title_post);
        edtDescription = findViewById(R.id.description_post);
        edtTenKhuDanCu = findViewById(R.id.tenKhuDanCu);
        edtAddress = findViewById(R.id.address);
        edtLoaiHinh = findViewById(R.id.loaiHinh);
        edtSoPhongNgu = findViewById(R.id.soPhongNgu);
        edtSoPhongWc = findViewById(R.id.soPhongWc);
        edtPrice = findViewById(R.id.price);
        edtDienTich = findViewById(R.id.dienTich);
        btnDangTin = findViewById(R.id.dangTinBDS);
        iconCloser = findViewById(R.id.iconCloser);
        suaTinBDS = findViewById(R.id.suaTinBDS);

        rcvView_select_img_BDS = findViewById(R.id.rcvView_select_img_BDS);
        Intent intent1 = getIntent();
        tenDanhMuc = intent1.getStringExtra("tenDanhMuc");

    }


    private void clickAddImageFashion() {
        photoAdapter = new photoAdapter(imageUri, this, this);
        rcvView_select_img_BDS.setLayoutManager(new GridLayoutManager(dangTinBDSActivity.this, 6));
        rcvView_select_img_BDS.setAdapter(photoAdapter);

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

    @Override
    public void clicked(int getSize) {

    }

    public static class EmployeeDataUtils {
        public static direction[] getEmployees() {
            direction drt1 = new direction("Nam");
            direction drt2 = new direction("Bắc");
            direction drt3 = new direction("Đông");
            direction drt4 = new direction("Tây");
            direction drt5 = new direction("Đông-Nam");
            direction drt6 = new direction("Tây-Băc");
            direction drt7 = new direction("Đông-Bắc");
            direction drt8 = new direction("Tây-Nam");
            return new direction[]{drt1, drt2, drt3, drt4, drt5, drt6, drt7, drt8};
        }
    }
}