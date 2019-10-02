package com.softsquared.softsquared_lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /* OnCreate, OnStart, OnResume, OnRestart, OnStop 활용 */

    private Button button;
    private ImageView background;

    private TypedArray backgroundSet;
    private static Handler mHandler;
    private BackgroundThread backgroundThread;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* findViewById */
        button = findViewById(R.id.button);
        background = findViewById(R.id.background);

        /* Drawable Array */
        backgroundSet = getResources().obtainTypedArray(R.array.backgroung_img_set);


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

        /* 배경화면 애니메이션 핸들러 */
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                background.setImageResource(backgroundSet.getResourceId(num, -1));
                num++;
                if (num > 111) {
                    num = 0;
                }
            }
        };
    }

    public class BackgroundThread extends Thread {
        private boolean running = false;
        public void setRunning(boolean running) { this.running = running; }
        @Override
        public void run() {
            while(running) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainState", "OnStart");

        /* 배경화면 애니메이션 시작 */
        backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();

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

        /* 배경화면 애니메이션 스레드 플래그 해제 */
        backgroundThread.setRunning(true);
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

        /* 배경화면 애니메이션 스레드 해제 */
        boolean retry = true;
        backgroundThread.setRunning(false);
        while (retry) {
            try {
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        background.setBackgroundResource(R.drawable.backgroundset000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainState", "OnDestroy");
    }
}
