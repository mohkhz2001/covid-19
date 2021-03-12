package com.mohammadKZ.oronavirus_COVID19_statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.covid_19.R;
import com.mohammadKZ.oronavirus_COVID19_statistics.StartUp.WearMaskFragment;
import com.mohammadKZ.oronavirus_COVID19_statistics.fragments.AboutFragment;
import com.mohammadKZ.oronavirus_COVID19_statistics.fragments.CountryFragment;
import com.mohammadKZ.oronavirus_COVID19_statistics.fragments.HistoryFragment;
import com.mohammadKZ.oronavirus_COVID19_statistics.fragments.HomeFragment;
import com.mohammadKZ.oronavirus_COVID19_statistics.fragments.NewsFragment;
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
//        start();
//        StartUp();
        ViewsController();
        sharedPreferences();
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
            case R.id.about:
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.frameLayout, aboutFragment);
                fragmentTransaction.commit();
                break;

        }
        return true;
    }

    private void start() {
        navigation = findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, homeFragment);
        fragmentTransaction.commit();
    }

    private void StartUp() {
        WearMaskFragment wearMaskFragment = new WearMaskFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, wearMaskFragment);
        fragmentTransaction.commit();
    }

    //read sharedPreferences (start-up)
    private void sharedPreferences() {
        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("start-up", MODE_PRIVATE);

        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show
        String start_up = sh.getString("start-up", "false");

        // We can then use the data
        if (start_up.equals("false")) {
            StartUp();
        } else if (start_up.equals("true")) {
            start();
        }

    }

}