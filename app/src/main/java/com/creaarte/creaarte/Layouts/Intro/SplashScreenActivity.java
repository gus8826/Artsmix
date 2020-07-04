package com.creaarte.creaarte.Layouts.Intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.creaarte.creaarte.R;

public class SplashScreenActivity extends Activity {

    Thread splashTread;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        activity = this;
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setStartAnimationsSplashScreen();
    }


    private void setStartAnimationsSplashScreen() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_splash_screen);
        anim.reset();
        RelativeLayout relativeLayoutSplash = findViewById(R.id.relativeLayoutContainerSplashScreen);
        relativeLayoutSplash.clearAnimation();
        relativeLayoutSplash.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_splash_screen);
        anim.reset();
        ImageView imageViewLogoSplash = findViewById(R.id.imageViewLogoSplashScreen);
        imageViewLogoSplash.clearAnimation();
        imageViewLogoSplash.startAnimation(anim);
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int wiated = 0;
                    while (wiated < 2000) {
                        sleep(100);
                        wiated += 100;
                    }

                    Intent intent = new Intent(activity, OnbiardingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    activity.finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SplashScreenActivity.this.finish();
                }
            }
        };
        splashTread.start();
    }
}
