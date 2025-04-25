package com.example.memo_ku.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memo_ku.auth.LoginActivity;
import com.example.memo_ku.utils.SharedPrefHelper;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPrefHelper pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new SharedPrefHelper(this);

        boolean logged = pref.isLoggedIn();
        Toast.makeText(this, "Checking session: " + logged, Toast.LENGTH_SHORT).show();
        if (!pref.isLoggedIn()) {
            // Kalau belum login, redirect ke LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(com.example.memo_ku.R.layout.activity_main);
        // Nanti di sini akan tampil memo/catatan yang tersimpan
    }
}
