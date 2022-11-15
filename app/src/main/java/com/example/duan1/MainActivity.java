package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.duan1.Fragment.FragmentDaoCho;
import com.example.duan1.Fragment.FragmentQuanLiTin;
import com.example.duan1.Fragment.FragmentThem;
import com.example.duan1.Fragment.FragmentTrangChu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    FragmentTrangChu fragmentTrangChu = new FragmentTrangChu();
    FragmentQuanLiTin fragmentQuanLiTin = new FragmentQuanLiTin();
    FragmentDaoCho fragmentDaoCho = new FragmentDaoCho();
    FragmentThem fragmentThem = new FragmentThem();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DangTin.class));
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTrangChu).commit();
                        return true;

                    case R.id.believe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentQuanLiTin).commit();
                        return true;

                    case R.id.fab:
                        return true;

                    case R.id.market:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDaoCho).commit();
                        return true;

                    case R.id.more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentThem).commit();
                        return true;
                }

                return false;
            }
        });
        
    }

    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTrangChu).commit();
    }
}
