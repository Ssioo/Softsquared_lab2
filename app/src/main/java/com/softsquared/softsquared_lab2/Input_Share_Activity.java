package com.softsquared.softsquared_lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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

public class Input_Share_Activity extends AppCompatActivity {

    private boolean shared = false;
    private boolean notToasted = true;

    private Button btn_submit;
    private RadioGroup agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Log.i("InputShareState", "OnCreate");

        /* findViewById */
        btn_submit = findViewById(R.id.btn_submit_input);
        agreement = findViewById(R.id.radio_agreement);


        /* 제출 버튼 클릭 이벤트 */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* 두 번 누름 방지를 위한 일시적 비활성화 */
                btn_submit.setEnabled(false);

                /* KAKAOTALK으로 공유 intent */
                callKakaoLinkApi();

                shared = true;
            }
        });

        /* 라디오버튼 동의 시 제출 버튼 활성화 */
        agreement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_agree:
                        btn_submit.setEnabled(true);
                        break;
                    case R.id.radio_disagree:
                        btn_submit.setEnabled(false);
                        break;
                }
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

        /* 두 번 누름 방지를 위한 일시적 비활성화 해제 */
        btn_submit.setEnabled(true);

        /* 라디오버튼에 따라 Button 뷰 초기화 */
        switch (agreement.getCheckedRadioButtonId()) {
            case R.id.radio_disagree:
                btn_submit.setEnabled(false);
                break;
            case R.id.radio_agree:
                btn_submit.setEnabled(true);
        }

        Log.i("InputShareState", "OnResume");
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

        /* 공유가 완료되었으면, 토스트와 함께 제출 버튼 글자와 클릭이벤트 변경 */
        if (shared) {
            if (notToasted) {
                Toast.makeText(getApplicationContext(), "응모가 완료되었습니다!", Toast.LENGTH_SHORT).show();
                notToasted = false;
            }
            btn_submit.setText("메인으로 돌아가기");
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // onDestroy 호출.
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

        /* 공유가 완료 되었으면, 앞의 두 액티비티 또한 같이 삭제 */
        if (shared && !notToasted) {
            MakeActivity makeActivity = (MakeActivity) MakeActivity.Activity_make;
            MadeActivity madeActivity = (MadeActivity) MadeActivity.Activity_made;
            madeActivity.finish();
            makeActivity.finish();
        }

        Log.i("InputShareState", "OnDestroy");
        super.onDestroy();
    }

    void callKakaoLinkApi() {
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
    }
}
