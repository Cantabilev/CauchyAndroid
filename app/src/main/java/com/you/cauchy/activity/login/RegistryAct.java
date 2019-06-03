package com.you.cauchy.activity.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.activity.home.HomeAct;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.user.AuthCodeTask;
import com.you.cauchy.net.user.RegistryTask;
import com.you.cauchy.util.Preferences;
import com.you.cauchy.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistryAct extends BaseActivity {

    private EditText etAccount, etPassword, etPhone, etCode;
    private Button btnSendCode, btnRegistry;

    private AbsCustomTask authCodeTask, registryTask;

    private static Integer COUNT_DOWN = 60;

    @Override
    public void initVariables() {
        authCodeTask = new AuthCodeTask();
        authCodeTask.setRestCallback(authCodeCallback);

        registryTask = new RegistryTask();
        registryTask.setRestCallback(registryCallback);
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_registry);

        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etCode = findViewById(R.id.etCode);

        btnSendCode = findViewById(R.id.btnSendCode);
        btnRegistry = findViewById(R.id.btnRegistry);
    }

    @Override
    public void initEvent() {
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTelPhoneNumber(etPhone.getText().toString().trim())){
                    authCodeTask.execute(etPhone.getText().toString().trim());
                } else {
                    etPhone.setError("The phone is required true");
                }
            }
        });
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidate()){
                    registryTask.execute(
                            etAccount.getText().toString(),
                            etPassword.getText().toString(),
                            etPhone.getText().toString(),
                            etCode.getText().toString());
                }
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private boolean checkValidate(){
        boolean result = true;
        if (etAccount.getText().toString().trim().isEmpty()){
            etAccount.setError("account is required");
            result = false;
        }
        if (etPassword.getText().toString().trim().isEmpty()){
            etPassword.setError("password is required");
            result = false;
        }
        if (etPhone.getText().toString().trim().isEmpty()){
            etPhone.setError("phone is required");
            result = false;
        }
        if (etCode.getText().toString().trim().isEmpty()){
            etCode.setError("auth code is required");
            result = false;
        }
        return result;
    }

    private void beginCountdown(){
        if (COUNT_DOWN != 60){
            return;
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    btnSendCode.setText(COUNT_DOWN-- + "");
                    if (COUNT_DOWN == 0){
                        btnSendCode.setEnabled(true);
                        COUNT_DOWN = 60;
                        timer.cancel();
                    }
                });
            }
        };
        btnSendCode.setEnabled(false);
        timer.schedule(task, 0L, 1000L);
    }

    public static void startRegistryAct(Context context){
        Intent intent = new Intent(context, RegistryAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    private RestCallback authCodeCallback = new RestCallback(null) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                beginCountdown();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback registryCallback = new RestCallback(null) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Preferences.setToken(response.getData().toString());
                HomeAct.startHomeActivity(RegistryAct.this);
                finish();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
