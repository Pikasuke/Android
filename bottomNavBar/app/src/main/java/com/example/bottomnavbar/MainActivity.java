package com.example.bottomnavbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btnNav;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bottom nav
        btnNav = findViewById(R.id.navigationView);
        btnNav.setOnNavigationItemReselectedListener(navListener);

        // Setting Home Fragment as main fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new HomeFragment()).commit();
    }

    //Listener nav Bar
    private BottomNavigationView.OnNavigationItemReselectedListener navListener = new
            BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    Fragment seletedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.itam1:
                            seletedFragment = new HomeFragment();
                            break;
                        case R.id.itam2:
                            seletedFragment = new AchievementFragment();
                            break;
                        case R.id.itam3:
                            seletedFragment = new SettingsFragment();
                            break;
                    }

                    //Begin Transaction
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,seletedFragment).commit();
                    return ;
                }
            };

}
