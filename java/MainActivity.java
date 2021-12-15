package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN= 5000;
    Animation top,bottom;
    ImageView image;
    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.top);
        bottom=AnimationUtils.loadAnimation(this,R.anim.below);
        image=findViewById(R.id.img1);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        image.setAnimation(top);
        tv1.setAnimation(bottom);
        tv2.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Quiz_Landing.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);



    }
}