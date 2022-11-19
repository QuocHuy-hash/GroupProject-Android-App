package com.example.duan1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.duan1.Login;
import com.example.duan1.MainActivity;
import com.example.duan1.R;


public class FragmentThem extends Fragment {

    private ImageView imgLoginRegister;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);
        mainActivity = (MainActivity) getActivity();
        initUi(view);
        initListenerClick();

        return view;
    }

    private void initUi(View view) {
        imgLoginRegister = view.findViewById(R.id.img_login_register);
    }

    private void initListenerClick() {
        imgLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainActivity, Login.class));
            }
        });
    }


}