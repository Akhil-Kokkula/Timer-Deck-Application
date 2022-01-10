package com.example.timer;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    public static int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeScreen home = new HomeScreen();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, home, "homeScreen").addToBackStack("home").commit();
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        manager.putFragment(bundle, "home", home);
    }

}