package com.softsquared.softsquared_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* findViewById */
        button = findViewById(R.id.button);
        background = findViewById(R.id.background);


        /* 버튼 클릭 이벤트 */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent = new Intent(MainActivity.this, MakeActivity.class);

                /* 두번 클릭 방지 */
                button.setEnabled(false);
                startActivity(newintent);
            }
        });

        Log.i("MainState", "OnCreate");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainState", "OnStart");

        /* 배경화면 애니메이션 시작 */
        Glide.with(this).asGif().load(R.raw.background_gif).into(background);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainState", "OnResume");

        /* 두 번 클릭 방지 해제 */
        button.setEnabled(true);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainState", "OnRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainState", "OnPause");


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainState", "OnStop");

        /* 배경화면 애니메이션 해제 */
        background.setBackgroundResource(R.drawable.initial_background);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainState", "OnDestroy");
    }

}
