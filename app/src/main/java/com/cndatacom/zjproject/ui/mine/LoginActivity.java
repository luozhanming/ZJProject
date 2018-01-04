package com.cndatacom.zjproject.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.base.BaseActivity;
import com.cndatacom.zjproject.entry.LoginEntry;
import com.cndatacom.zjproject.entry.UserInfoEntry;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.cndatacom.zjproject.ui.MainActivity;
import com.cndatacom.zjproject.util.EncryptUtil;
import com.cndatacom.zjproject.widget.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录界面
 * Created by cdc4512 on 2018/1/2.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText etUserName, etPassword;
    ImageView ivDelete, ivSight;
    Button btnCommit;
    TextView tvForget;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etUserName = f(R.id.et_username);
        etPassword = f(R.id.et_password);
        ivDelete = f(R.id.iv_delete);
        ivSight = f(R.id.iv_sight);
        btnCommit = f(R.id.btn_loginCommit);
        tvForget = f(R.id.tv_forget);

        ivDelete.setOnClickListener(this);
        ivSight.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        tvForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                etPassword.setText("");
                break;
            case R.id.iv_sight:
                if (ivSight.isSelected()) {
                    ivSight.setSelected(false);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ivSight.setSelected(true);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.btn_loginCommit:
                login();
                break;
        }
    }


    private void login() {
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            showShortToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            showShortToast("请输入密码");
            return;
        }
        LoadingDialog.showLoadingDialog(this, "登录中");
        MyRetrofit.getHttpService().login(username, EncryptUtil.EncryptMD5(password))
                .enqueue(new Callback<UserInfoEntry>() {
                    @Override
                    public void onResponse(Call<UserInfoEntry> call, Response<UserInfoEntry> response) {
                        LoadingDialog.hideProgressDialog();
                        if(response.body().getStatus().equals("1")){
                            //登录成功
                            LoginEntry.instance().setLogin(true);
                            LoginEntry.instance().setUserInfo(response.body());
                            MainActivity.start(LoginActivity.this);
                            finish();
                        }else {
                            showShortToast(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoEntry> call, Throwable t) {
                        t.printStackTrace();
                        LoadingDialog.hideProgressDialog();
                    }
                });


    }
}
