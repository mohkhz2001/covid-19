package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.covid_19.fragments.CountryFragment;
import com.example.covid_19.fragments.HistoryFragment;
import com.example.covid_19.fragments.HomeFragment;
import com.example.covid_19.fragments.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView title;
    BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        InitViews();
        start();
        ViewsController();
    }

    private void InitViews() {
        title = findViewById(R.id.title);
        navigation = findViewById(R.id.navigation);
    }

    private void ViewsController() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (R.id.nav_home == item.getItemId()) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragment);
            fragmentTransaction.commit();
        } else if (R.id.nav_country == item.getItemId()) {
            CountryFragment countryFragment = new CountryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, countryFragment);
            fragmentTransaction.commit();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_home:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.frameLayout, homeFragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_country:
                CountryFragment countryFragment = new CountryFragment();
                fragmentTransaction.replace(R.id.frameLayout, countryFragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_history:
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransaction.replace(R.id.frameLayout, historyFragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_news:
                NewsFragment newsFragment = new NewsFragment();
                fragmentTransaction.replace(R.id.frameLayout, newsFragment);
                fragmentTransaction.commit();
                break;

        }
        return true;
    }

    private void start() {

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, homeFragment);
        fragmentTransaction.commit();
    }

}