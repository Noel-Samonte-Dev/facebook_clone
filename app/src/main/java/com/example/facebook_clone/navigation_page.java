package com.example.facebook_clone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
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

        FragmentContainerView fr = findViewById(R.id.fragmentContainerView);
        fr.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (fr.getFragment().toString().contains("home_page") || fr.getFragment().toString().contains("NavHostFragment")) {
                    nav_bottom.getMenu().getItem(0).setChecked(true);
                }

                if (fr.getFragment().toString().contains("profile_page")) {
                    nav_bottom.getMenu().getItem(1).setChecked(true);
                }
            }
        });
    }

    private final NavigationBarView.OnItemSelectedListener nav_listener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home_page:
                    setFragment(new home_page(), "home_page");
                    return true;
                case R.id.profile_page:
                    SharedPreferences sp = getSharedPreferences("Profile", MODE_PRIVATE);
                    String login_id = sp.getString("login_id", "");
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("selected_id", login_id);
                    editor.commit();
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
