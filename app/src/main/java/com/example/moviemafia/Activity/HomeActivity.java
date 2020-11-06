package com.example.moviemafia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviemafia.Fragment.FragmentMenu;
import com.example.moviemafia.Fragment.FragmentMylist;
import com.example.moviemafia.Fragment.FragmentSearch;
import com.example.moviemafia.Fragment.HomeFragment;
import com.example.moviemafia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.page_1:
                fragment = new HomeFragment();
                break;
            case R.id.page_2:
                fragment = new FragmentSearch();
                break;
            case R.id.page_3:
                fragment = new FragmentMylist();
                break;
            case R.id.page_4:
                fragment = new FragmentMenu();
                break;

        }
        return loadFragment(fragment);
    }
}