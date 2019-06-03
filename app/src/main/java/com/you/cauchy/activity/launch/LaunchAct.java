package com.you.cauchy.activity.launch;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.you.cauchy.R;
import com.you.cauchy.activity.home.HomeAct;
import com.you.cauchy.activity.login.LoginAct;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.user.ValidateTokenTask;
import com.you.cauchy.util.Preferences;

import java.util.Map;

public class LaunchAct extends Activity {

    protected Handler handler = new Handler(Looper.getMainLooper());
    private AbsCustomTask validateTokenTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_launch);
        validateTokenTask = new ValidateTokenTask();
        validateTokenTask.setRestCallback(restCallback);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                validateTokenTask.execute(Preferences.getToken());
            }
        }, 1000);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private RestCallback restCallback = new RestCallback(LaunchAct.this) {
        @Override
        public void onResponse(RestResult result) {
            if (result.getCode() == RestResult.SUCCESS) {
                Map<String, Object> map = (Map<String, Object>) result.getData();
                if (Boolean.valueOf(map.get("login").toString())){
                    Preferences.setToken(map.get("token").toString());
                    startHomeActivity();
                } else {
                    startLoginActivity();
                }
            }
            else
                Toast.makeText(LaunchAct.this, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
