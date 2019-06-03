package com.you.cauchy.activity.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.you.cauchy.R;
import com.you.cauchy.activity.home.HomeAct;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.user.LoginTask;
import com.you.cauchy.util.Preferences;

public class LoginAct extends AppCompatActivity {

    private VideoView videoview;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin, btnRegistry;
    private AbsCustomTask loginTask;

    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_login);

        initVariables();
        initView();
        initEvent();
        loadData();
        doBusiness();
    }

    private void initVariables() {
        videoPath = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro).toString();
        loginTask = new LoginTask();
        loginTask.setRestCallback(restCallback);
    }

    private void initView() {
        videoview = (VideoView) findViewById(R.id.videoview);
        etAccount = findViewById(R.id.etAccount);
        etPassword =  findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistry = findViewById(R.id.btnRegistry);
    }
    private void initEvent() {
        videoview.setVideoPath(videoPath);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTask.execute(etAccount.getText().toString(), etPassword.getText().toString());
            }
        });
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistryAct.startRegistryAct(LoginAct.this);
            }
        });
    }

    private void loadData() {

    }

    private void doBusiness() {

    }

    private RestCallback restCallback = new RestCallback(LoginAct.this) {
        @Override
        public void onResponse(RestResult result) {
            if (result.getCode() == RestResult.SUCCESS) {
                Preferences.setToken(result.getData().toString());
                HomeAct.startHomeActivity(LoginAct.this);
                finish();
            }
            else
                Toast.makeText(LoginAct.this, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
