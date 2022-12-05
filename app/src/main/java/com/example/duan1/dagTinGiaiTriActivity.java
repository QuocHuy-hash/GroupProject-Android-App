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
import com.example.duan1.model.giaiTriNews;
import com.example.duan1.model.product_type;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class dagTinGiaiTriActivity extends AppCompatActivity implements com.example.duan1.Adapter.photoAdapter.CountOfImageWhenRemove {
    private TextView tvTenDanhMuc;
    private ImageView imgBackPage;
    private Spinner spn_product_type;
    private LinearLayout layout_spnTypeProduct, addImageProduct;
    private EditText edtTitlePost, edtDescription, edtPrice, edtAddress ;
    private Button btnDangTin;

    private String strTitlePost , strDescription , strPrice , strAddress ,strLoaiSanPham , nameUser ,tenDanhMuc;
    private double dbPrice;
    private com.example.duan1.Adapter.photoAdapter photoAdapter;
    private RecyclerView rcvView_select_img_GiaiTri;
    private ArrayList<Uri> imageUri;
    private int REQUEST_PERMISSION_CODE = 35;
    private int PICK_IMAGE = 1;
    private int update_count = 0 , maxID  , idUser;
    private ProgressDialog progressDialog;
    private List<giaiTriNews> listGiaiTri;
    MainActivity mainActivity;
    private
    DatabaseReference myData;
    StorageReference imageFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dag_tin_giai_tri);

        imageFolder = FirebaseStorage.getInstance().getReference().child("Image.jpg");
        myData = FirebaseDatabase.getInstance().getReference("Tin").child("GiaiTri");
        imageUri = new ArrayList<>();

        idUser = mainActivity.id;
        nameUser = mainActivity.name;

        initUi();
        clickBackPage();
        eventClickSPN();
        clickAddImageFashion();
        getListGiaiTri();
        clickDangTin();

    }

    private void clickBackPage() {
        imgBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void eventClickSPN() {
        product_type[] product_type = dagTinGiaiTriActivity.productType.getProductType();
        ArrayAdapter<product_type> adapter = new ArrayAdapter<product_type>(this, android.R.layout.simple_spinner_item, product_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_product_type.setAdapter(adapter);

        spn_product_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(dagTinGiaiTriActivity.this, "Loại sản phẩm " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void clickAddImageFashion() {
        photoAdapter = new photoAdapter(imageUri, this, this);
        rcvView_select_img_GiaiTri.setLayoutManager(new GridLayoutManager(dagTinGiaiTriActivity.this, 6));
        rcvView_select_img_GiaiTri.setAdapter(photoAdapter);

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


    private void clickDangTin() {

        btnDangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(dagTinGiaiTriActivity.this);
                progressDialog.setMessage("Please wait for save");

                strTitlePost = edtTitlePost.getText().toString().trim();
                strDescription = edtDescription.getText().toString();
                strLoaiSanPham = spn_product_type.getSelectedItem().toString().trim();
                strAddress = edtAddress.getText().toString().trim();
                strPrice = edtPrice.getText().toString();

                try {
                    dbPrice = Double.parseDouble(strPrice);

                } catch(Exception e) {
                    System.err.println("Lỗi Parse kiểu dữ liệu");
                }



                if (strTitlePost.isEmpty() || strDescription.isEmpty() || strPrice.isEmpty()
                        || strAddress.isEmpty() ||  strLoaiSanPham.isEmpty()) {
                    MainActivity.showDiaLogWarning(dagTinGiaiTriActivity.this, "vui lòng nhập đầy đủ thông tin");

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
                    maxID = listGiaiTri.size();
                    String strId = String.valueOf(maxID);
                    myData.child(tenDanhMuc).child(strId ).setValue(new giaiTriNews(maxID ,strTitlePost , strDescription ,strAddress, dbPrice
                                      ,strLoaiSanPham ,idUser ,nameUser))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dagTinGiaiTriActivity.this, "Đăng tin thành công", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                }
            }
        });

    }


    private List<giaiTriNews> getListGiaiTri() {
        listGiaiTri = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Tin").child("GiaiTri");
        databaseReference.child(tenDanhMuc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listGiaiTri.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren() ){
                    giaiTriNews giaiTriNews = snapshot1.getValue(giaiTriNews.class);
                    listGiaiTri.add(giaiTriNews);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(dagTinGiaiTriActivity.this, "Load data Error", Toast.LENGTH_SHORT).show();
            }
        });

        return listGiaiTri;

    }

    private void StoreLick(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("List Image");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", url);
        databaseReference.push().setValue(hashMap);

    }


    private void initUi() {
        tvTenDanhMuc = findViewById(R.id.tvTenDanhMuc);
        Intent intent = getIntent();
        tvTenDanhMuc.setText("Danh Mục - " + intent.getStringExtra("tenDanhMuc"));
        imgBackPage = findViewById(R.id.icon_back);
        spn_product_type = findViewById(R.id.spn_product_type);
        layout_spnTypeProduct = findViewById(R.id.layout_spnTypeProduct);

        edtTitlePost = findViewById(R.id.title_post);
        edtDescription = findViewById(R.id.description_post);
        edtPrice = findViewById(R.id.price);
        edtAddress = findViewById(R.id.address);
        btnDangTin = findViewById(R.id.btnSubmit);
        addImageProduct = findViewById(R.id.addImageProduct);

        rcvView_select_img_GiaiTri = findViewById(R.id.rcvView_select_img_GiaiTri);

        Intent intent1 = getIntent();
       tenDanhMuc = intent1.getStringExtra("tenDanhMuc");

    }

    @Override
    public void clicked(int getSize) {

    }

    public static class productType {
        public static product_type[] getProductType() {
            product_type prd1 = new product_type("Thạch,đá quí");
            product_type prd2 = new product_type("Tranh ảnh,khung tranh");
            product_type prd3 = new product_type("Bật lửa");
            product_type prd4 = new product_type("Đồ gốm sứ");
            product_type prd5 = new product_type("Tường trang trí");
            return new product_type[]{prd1, prd2, prd3, prd4, prd5};


        }
    }
}