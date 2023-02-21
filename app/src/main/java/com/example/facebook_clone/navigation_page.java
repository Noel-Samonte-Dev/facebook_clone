package com.example.facebook_clone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.facebook_clone.home_page.home_page;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class navigation_page extends AppCompatActivity {
    private BottomNavigationView nav_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_page);

        nav_bottom = findViewById(R.id.nav_bottom);
        nav_bottom.setOnItemSelectedListener(nav_listener);
    }

    private final NavigationBarView.OnItemSelectedListener nav_listener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home_page:
                    setFragment(new home_page(), "home_page");
                    return true;
                case R.id.profile_page:
                    setFragment(new profile_page(), "profile_page");
                    return true;
                case R.id.settings_page:
//                    setFragment(new settings_page(), "settings_page");
//                    return true;
                    Intent intent = new Intent(navigation_page.this, login_page.class);
                    startActivity(intent);
                    finish();
                    return false;
                default:
                    setFragment(new home_page(), "home_page");
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, name).commit();
    }
}
