package com.example.vivu;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.vivu.fragment.AccountFragment;
import com.example.vivu.fragment.DiscoverFragment;
import com.example.vivu.fragment.HomeFragment;
import com.example.vivu.fragment.MessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mapping();
        mNavigationView.setOnNavigationItemSelectedListener(listener);
        mNavigationView.setSelectedItemId(R.id.itemPage);





        

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

            Fragment mFragmentSelected = null;

            switch (item.getItemId()) {
                case R.id.itemPage:
                    mFragmentSelected = new HomeFragment();
                    break;
                case R.id.itemDiscover:
                    mFragmentSelected = new DiscoverFragment();
                    break;
                case R.id.itemMessage:
                    mFragmentSelected = new MessageFragment();
                    break;
                case R.id.itemAccount:
                    mFragmentSelected = new AccountFragment();
                    break;
            }
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.frmContainer,mFragmentSelected).commit();
            return true;
        }
    };

    private void mapping() {
        mNavigationView = findViewById(R.id.nvgHome);

    }
}