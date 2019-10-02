package com.softsquared.softsquared_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MakeActivity extends AppCompatActivity {

    /* OnCreate, OnResume, OnPause 활용 */

    public static Activity Activity_make;

    private ArrayList<TextView> tv_Words = new ArrayList<>();
    private ArrayList<TextView> tv_Colors = new ArrayList<>();
    private TextView tv_selectedWord_temp;
    private TextView tv_selectedColor_temp;

    private TextView tv_Word_Selected;
    private TextView tv_Color_Selected;
    private boolean isSelect_Word = false;
    private boolean isSelect_Color = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_fireworks);

        Log.i("MakeState", "OnCreate");

        /* 이 액티비티를 static으로 선언 */
        Activity_make = MakeActivity.this;

        /* findViewById */
        tv_Words.add((TextView) findViewById(R.id.text_adventure));
        tv_Words.add((TextView) findViewById(R.id.text_health));
        tv_Words.add((TextView) findViewById(R.id.text_hope));
        tv_Words.add((TextView) findViewById(R.id.text_marry));
        tv_Words.add((TextView) findViewById(R.id.text_love));
        tv_Words.add((TextView) findViewById(R.id.text_promise));
        tv_Words.add((TextView) findViewById(R.id.text_travel));
        tv_Words.add((TextView) findViewById(R.id.text_exit));
        tv_Words.add((TextView) findViewById(R.id.text_success));
        tv_Words.add((TextView) findViewById(R.id.text_family));
        tv_Words.add((TextView) findViewById(R.id.text_friend));
        tv_Words.add((TextView) findViewById(R.id.text_study));
        tv_Words.add((TextView) findViewById(R.id.text_challenge));
        tv_Words.add((TextView) findViewById(R.id.text_happy));
        tv_Words.add((TextView) findViewById(R.id.text_rest));

        tv_Colors.add((TextView) findViewById(R.id.text_colorSun));
        tv_Colors.add((TextView) findViewById(R.id.text_colorSea));
        tv_Colors.add((TextView) findViewById(R.id.text_colorGreen));
        tv_Colors.add((TextView) findViewById(R.id.text_colorYellow));
        tv_Colors.add((TextView) findViewById(R.id.text_colorOrange));
        tv_Colors.add((TextView) findViewById(R.id.text_colorPurple));
        tv_Colors.add((TextView) findViewById(R.id.text_colorGreen));
        tv_Colors.add((TextView) findViewById(R.id.text_colorLightgreen));
        tv_Colors.add((TextView) findViewById(R.id.text_colorSky));
        tv_Colors.add((TextView) findViewById(R.id.text_colorPink));
        tv_Colors.add((TextView) findViewById(R.id.text_colorMint));
        tv_Colors.add((TextView) findViewById(R.id.text_colorChoco));
        tv_Colors.add((TextView) findViewById(R.id.text_colorWhite));

        tv_selectedWord_temp = findViewById(R.id.text_select_temp1);
        tv_selectedColor_temp = findViewById(R.id.text_select_temp2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MakeState", "OnStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MakeState", "OnResume");

        /* 사용자 버튼 클릭 이벤트 등록 */
        for (final TextView tv : tv_Words) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelect_Word = true;
                    tv_Word_Selected = tv;

                    /* 나머지 textview 원래대로 */
                    for (final TextView tv_rest : tv_Words) {
                        tv_rest.setTextColor(getResources().getColor(R.color.white));
                    }

                    /* 현재 textview 하이라이트 */
                    tv.setTextColor(getResources().getColor(R.color.highlight));

                    /* temp Textview 노출 */
                    tv_selectedWord_temp.setText(tv.getText());

                    /* 다음 액티비티 호출 조건 */
                    if (isSelect_Color && isSelect_Word) {
                        Intent intent = new Intent(MakeActivity.this, MadeActivity.class);
                        Log.i("ColorStates", String.valueOf(tv_Color_Selected.getCurrentTextColor()));
                        intent.putExtra("Word", tv_Word_Selected.getText().toString());
                        intent.putExtra("Color", Integer.toHexString(tv_Color_Selected.getCurrentTextColor()));
                        startActivity(intent);
                    }
                }
            });
        }

        for (final TextView tv : tv_Colors) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelect_Color = true;
                    tv_Color_Selected = tv;

                    /* 나머지 textview 원래대로 */
                    for (final TextView tv_rest : tv_Colors) {
                        tv_rest.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                    }

                    /* 현재 textview 하이라이트 */
                    tv.setTypeface(tv.getTypeface(), Typeface.BOLD);

                    /* temp Textview 노출 */
                    tv_selectedColor_temp.setText(tv.getText());
                    tv_selectedColor_temp.setTextColor(tv.getCurrentTextColor());

                    /* 다음 액티비티 호출 조건 */
                    if (isSelect_Color && isSelect_Word) {
                        Intent intent = new Intent(MakeActivity.this, MadeActivity.class);
                        intent.putExtra("Word", tv_Word_Selected.getText().toString());
                        intent.putExtra("Color", Integer.toHexString(tv_Color_Selected.getCurrentTextColor()));
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MakeState", "OnPause");

        /* 액티비티를 벗어났다가 오면 선택내용 초기화 */
        isSelect_Word = false;
        isSelect_Color = false;
        if (tv_Word_Selected != null)
            tv_Word_Selected.setTextColor(getResources().getColor(R.color.white));
        if (tv_Color_Selected != null)
            tv_Color_Selected.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);

        tv_selectedColor_temp.setText("");
        tv_selectedWord_temp.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MakeState", "OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MakeState", "OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MakeState", "OnDestroy");
    }

}
