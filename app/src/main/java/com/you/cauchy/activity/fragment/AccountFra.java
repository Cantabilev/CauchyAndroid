package com.you.cauchy.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.login.LoginAct;
import com.you.cauchy.activity.user.ExpectAct;
import com.you.cauchy.activity.user.MoneyFlowingAct;
import com.you.cauchy.activity.user.UserExamListAct;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.BuyCourseTask;
import com.you.cauchy.net.response.UserInfoResponse;
import com.you.cauchy.net.user.UserTask;
import com.you.cauchy.ui.CommonDialog;
import com.you.cauchy.util.GlideBlurFormation;
import com.you.cauchy.util.Preferences;
import com.you.cauchy.util.ToastUtil;

/**
 * Created by you on 2019/3/20 22:52
 * e-mail: 1269859055@qq.com
 */
public class AccountFra extends BaseFragment {

    private ImageView iconAvatar, iconFuzzy;
    private TextView tvAccount;
    private Button btnLogout;
    private LinearLayout llAccount, llExpect, llExam;

    private AbsCustomTask userTask;
    private CommonDialog logoutDialog;

    @Override
    public int BindLayout() {
        return R.layout.fra_account;
    }

    @Override
    public void initVariables() {
        userTask = new UserTask();
        userTask.setRestCallback(userCallback);

        logoutDialog = new CommonDialog(getActivity(), "您确定退出到登录界面？");
    }

    @Override
    public void initView(View view) {
        iconAvatar = view.findViewById(R.id.iconAvatar);
        iconFuzzy = view.findViewById(R.id.iconFuzzy);
        tvAccount = view.findViewById(R.id.tvAccount);
        btnLogout = view.findViewById(R.id.btnLogout);

        iconFuzzy.setAlpha(0.75f);

        llAccount = view.findViewById(R.id.llAccount);
        llExpect = view.findViewById(R.id.llExpect);
        llExam = view.findViewById(R.id.llExam);
    }

    @Override
    public void initEvent() {
        btnLogout.setOnClickListener(v -> logoutDialog.show());
        logoutDialog.setListener((dialog, confirm) -> {
            if (confirm){
                logout();
            }
        });
        llAccount.setOnClickListener( v -> MoneyFlowingAct.startMoneyFlowingAct(getActivity()));
        llExpect.setOnClickListener( v -> ExpectAct.startExpectAct(getActivity()));
        llExam.setOnClickListener(v -> UserExamListAct.startUserListAct(getActivity()));
    }

    @Override
    public void loadData() {
        userTask.execute();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void create() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private void logout(){
        Preferences.clearToken();
        Intent intent = new Intent(getActivity(), LoginAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    private RestCallback userCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<UserInfoResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<UserInfoResponse>>() {}.getType());
                UserInfoResponse user = result.getData();
                Glide.with(getContext())
                        .load(user.getAvatarUrl())
                        .error(Glide.with(getContext()).load(R.drawable.ic_error))
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(iconAvatar);
                RequestOptions options = RequestOptions.bitmapTransform(new GlideBlurFormation(getContext()));
                options.bitmapTransform(new CircleCrop());
                Glide.with(getContext())
                        .load(user.getAvatarUrl())
                        // 设置高斯模糊
                        .error(Glide.with(getContext()).load(R.drawable.ic_error))
                        .apply(RequestOptions.bitmapTransform(new GlideBlurFormation(getContext())))
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(iconFuzzy);
                tvAccount.setText(user.getAccount());
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
