package com.creaarte.creaarte.Layouts.Intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.creaarte.creaarte.Layouts.MenuMain.ContainerMenuMainActivity;
import com.creaarte.creaarte.Models.ItemIntro;
import com.creaarte.creaarte.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class OnbiardingActivity extends AppCompatActivity {

    private ViewPager viewPagerScreenOnbiarding;
    private TabLayout tabLayoutOnbiarding;
    private Button buttonNextOnbiarding;
    private int position = 0;
    private Button buttonStartedOnbiarding;
    private Animation button_animation;
    private TextView buttonSkipOnbiarding;
    private static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), ContainerMenuMainActivity.class);
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_onbiarding);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //AllPrermission();

        buttonNextOnbiarding = findViewById(R.id.buttonNextOnbiarding);
        buttonStartedOnbiarding = findViewById(R.id.buttonStartedOnbiarding);
        tabLayoutOnbiarding = findViewById(R.id.tabLayoutOnbiarding);
        button_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        buttonSkipOnbiarding = findViewById(R.id.buttonSkipOnbiarding);

        final List<ItemIntro> mList = new ArrayList<>();
        mList.add(new ItemIntro(getString(R.string.text_string_04), getString(R.string.text_string_05), getString(R.string.text_string_06), getString(R.string.text_string_07), getString(R.string.text_string_08), R.mipmap.ic_logo_degrade));
        mList.add(new ItemIntro(getString(R.string.text_string_09) + getString(R.string.text_string_10) + getString(R.string.text_string_11), getString(R.string.text_string_12), getString(R.string.text_string_13), getString(R.string.text_string_14), "", R.mipmap.ic_eyes));
        mList.add(new ItemIntro(getString(R.string.text_string_15) + getString(R.string.text_string_10) + getString(R.string.text_string_16), getString(R.string.text_string_17), getString(R.string.text_string_18), getString(R.string.text_string_19), "", R.mipmap.ic_date_arts));
        mList.add(new ItemIntro(getString(R.string.text_string_20) + getString(R.string.text_string_10) + getString(R.string.text_string_21), getString(R.string.text_string_22), getString(R.string.text_string_23), getString(R.string.text_string_24), getString(R.string.text_string_25), R.mipmap.ic_heart_background));
        mList.add(new ItemIntro(getString(R.string.text_string_26) + getString(R.string.text_string_10) + getString(R.string.text_string_27), getString(R.string.text_string_28), getString(R.string.text_string_29), getString(R.string.text_string_30), getString(R.string.text_string_31) + "\n" + getString(R.string.text_string_32), R.mipmap.ic_money));
        mList.add(new ItemIntro(getString(R.string.text_string_33) + getString(R.string.text_string_10) + getString(R.string.text_string_34), getString(R.string.text_string_35), getString(R.string.text_string_36), getString(R.string.text_string_37), getString(R.string.text_string_38), R.mipmap.ic_arts_images));

        viewPagerScreenOnbiarding = findViewById(R.id.viewPagerScreenOnbiarding);
        PageOnbiardingAdapter pageOnbiardingAdapter = new PageOnbiardingAdapter(this, mList);
        viewPagerScreenOnbiarding.setAdapter(pageOnbiardingAdapter);

        tabLayoutOnbiarding.setupWithViewPager(viewPagerScreenOnbiarding);

        buttonNextOnbiarding.setOnClickListener(v -> {
            position = viewPagerScreenOnbiarding.getCurrentItem();
            if (position < mList.size()) {
                position++;
                viewPagerScreenOnbiarding.setCurrentItem(position);
            }

            if (position == mList.size() - 1) {
                loaddLastScreen();
            }
        });

        tabLayoutOnbiarding.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {
                    loaddLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonStartedOnbiarding.setOnClickListener(v -> {
            Intent mainActivity = new Intent(getApplicationContext(), ContainerMenuMainActivity.class);
            startActivity(mainActivity);
            savePrefsData();
            finish();
        });
        buttonSkipOnbiarding.setOnClickListener(v -> viewPagerScreenOnbiarding.setCurrentItem(mList.size()));
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpnend", false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.apply();
    }

    private void loaddLastScreen() {
        buttonNextOnbiarding.setVisibility(View.INVISIBLE);
        buttonStartedOnbiarding.setVisibility(View.VISIBLE);
        buttonSkipOnbiarding.setVisibility(View.INVISIBLE);
        tabLayoutOnbiarding.setVisibility(View.INVISIBLE);
        buttonStartedOnbiarding.setAnimation(button_animation);

    }

    private void AllPrermission() {
        int storage = ActivityCompat.checkSelfPermission(OnbiardingActivity.this, READ_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(OnbiardingActivity.this, CAMERA);
        //int location = ActivityCompat.checkSelfPermission(LoginActivity.this, ACCESS_FINE_LOCATION);
        //int locationCoarse = ActivityCompat.checkSelfPermission(LoginActivity.this, ACCESS_COARSE_LOCATION);
        if (/*location != activity.PackageManager.PERMISSION_GRANTED ||*/ camera != PackageManager.PERMISSION_GRANTED || storage != PackageManager.PERMISSION_GRANTED /*|| locationCoarse != PackageManager.PERMISSION_GRANTED*/) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA/*,Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION*/}, MULTIPLE_PERMISSIONS_REQUEST_CODE);
            }
        }
    }
}