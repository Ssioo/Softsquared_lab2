package com.softsquared.softsquared_lab2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Input_Share_Activity extends AppCompatActivity {

    private boolean shared = false;
    private boolean notToasted = true;

    private Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Log.i("InputShareState", "OnCreate");

        /* findViewById */
        btn_submit = findViewById(R.id.btn_submit_input);

        /* 버튼 클릭 이벤트 */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* KAKAOTALK으로 공유 intent */
                /* KAKAOLINK API 사용 */
                FeedTemplate params = FeedTemplate
                        .newBuilder(ContentObject.newBuilder("DESIGN MY FIREWORKS",
                                "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                                LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").setMobileWebUrl("https://www.developers.kakao.com")
                                        .build())
                                .setDescrption("골든티켓은 물론, 내가 만든 불꽃이 여의도 밤하늘에 펼쳐지는 행운을 드립니다!")
                                .build())
                        .addButton(new ButtonObject("앱으로 보기", LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .setAndroidExecutionParams("key1=value1")
                                .setIosExecutionParams("key1=value1").build()))
                        .build();

                Map<String, String> serverCallbackArgs = new HashMap<>();
                serverCallbackArgs.put("user_id", "${current_user_id}");
                serverCallbackArgs.put("product_id", "${shared_product_id}");

                KakaoLinkService.getInstance().sendDefault(Input_Share_Activity.this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("Error", errorResult.toString());
                    }
                    @Override
                    public void onSuccess(KakaoLinkResponse result) { }
                });

                /* 두 번 누름 방지 */
                btn_submit.setEnabled(false);
                
                shared = true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("InputShareState", "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("InputShareState", "OnResume");

        /* 버튼 재활성화 */
        btn_submit.setEnabled(true);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("InputShareState", "OnPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("InputShareState", "OnRestart");
        if (shared) {
            if (notToasted) {
                Toast.makeText(getApplicationContext(), "응모가 완료되었습니다!", Toast.LENGTH_SHORT).show();
                notToasted = false;
            }
            btn_submit.setText("메인으로 돌아가기");
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(Input_Share_Activity.this, MainActivity.class);
                    startActivity(newIntent);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("InputShareState", "OnStop");
    }

    @Override
    protected void onDestroy() {

        Log.i("InputShareState", "OnDestroy");
        super.onDestroy();
    }
}
