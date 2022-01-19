package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShoppingActivity extends AppCompatActivity {

    Button cartBtn, productsBtn; // Fragment replacement butonları



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //SharedPreferences dataBringer = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        replaceFragment(new ShoppingFragment()); // Default açılan fragment

        cartBtn=(Button)findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new CartFragment()); // Click Fragment metodu
            }
        });

        productsBtn=(Button)findViewById(R.id.productsBtn);
        productsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new ShoppingFragment()); // Click Fragment metodu
            }
        });
    }

    void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); // Fragment değiştiren metod

        fragmentTransaction.replace(R.id.frameLayout, fragment); // FrameLayout kısmını değiştiren satır.
        fragmentTransaction.commit();
    }
}