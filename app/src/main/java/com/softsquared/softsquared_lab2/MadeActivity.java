package com.softsquared.softsquared_lab2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MadeActivity extends AppCompatActivity {

    /* OnCreate 활용 */

    public static Activity Activity_made;

    private Button btn_submit_event;
    private TextView tv_selectedWord;
    private TextView tv_description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made_fireworks);
        Log.i("MadeState", "OnCreate");

        /* 이 액티비티를 static으로 선언 */
        Activity_made = MadeActivity.this;

        /* findViewByID */
        btn_submit_event = findViewById(R.id.btn_submit_event);
        tv_selectedWord = findViewById(R.id.text_selectedWord);
        tv_description = findViewById(R.id.text_description);

        /* View 초기화 */
        Intent intent = getIntent();
        tv_selectedWord.setText(intent.getStringExtra("Word"));
        tv_selectedWord.setTextColor(Color.parseColor("#" + intent.getStringExtra("Color")));
        tv_description.setTextColor(Color.parseColor("#" + intent.getStringExtra("Color")));


        /* 사용자 버튼 클릭 이벤트 */
        btn_submit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent = new Intent(MadeActivity.this, Input_Share_Activity.class);
                startActivity(newintent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MadeState", "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MadeState", "OnResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MadeState", "OnPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MadeState", "OnRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MadeState", "OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MadeState", "OnDestroy");
    }
}
