package com.cndatacom.zjproject.ui.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.base.BaseActivity;
import com.cndatacom.zjproject.entry.LoginEntry;
import com.cndatacom.zjproject.entry.Result;
import com.cndatacom.zjproject.entry.UserInfoEntry;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.cndatacom.zjproject.ui.MainActivity;
import com.cndatacom.zjproject.util.EncryptUtil;
import com.cndatacom.zjproject.widget.LoadingDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.luozm.captcha.Captcha;

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


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmerse(true);
        setContentView(R.layout.activity_login);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO", "android.permission.CAMERA"}, 1);
        } else {
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        }
    }

    private void initData() {
        String username = SPUtils.getInstance().getString("username", "");
        String password = SPUtils.getInstance().getString("password", "");
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            login(username, password);
        }
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


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivDelete.setVisibility(View.VISIBLE);
                } else {
                    ivDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                    ivSight.setImageResource(R.mipmap.icon_openeye);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().toString().length());
                } else {
                    ivSight.setSelected(true);
                    ivSight.setImageResource(R.mipmap.icon_closeeye);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().toString().length());
                }
                break;
            case R.id.btn_loginCommit:
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
                showCaptchaDialog();
//                login(username, EncryptUtil.EncryptMD5(password));
                break;
        }
    }

    AlertDialog captchaDialog;

    private void showCaptchaDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_captcha, null);
        Captcha captcha = (Captcha) view.findViewById(R.id.captcha);
        captchaDialog = new AlertDialog.Builder(this, R.style.style_dialog_loading)
                .setView(view)
                .create();
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public void onAccess(long time) {
                String username = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                login(username, EncryptUtil.EncryptMD5(password));
            }

            @Override
            public void onFailed() {
            }
        });
        captchaDialog.setCanceledOnTouchOutside(false);
        captchaDialog.show();
    }


    private void login(String username, String password) {
        LoadingDialog.showLoadingDialog(this, "登录中");
        MyRetrofit.getHttpService().login(username, password)
                .enqueue(new Callback<Result<UserInfoEntry>>() {
                    @Override
                    public void onResponse(Call<Result<UserInfoEntry>> call, Response<Result<UserInfoEntry>> response) {

                        if (response.body().getStatus().equals("1")) {
                            //登录成功
                            final UserInfoEntry user = response.body().getBody();
                            LoginEntry.instance().setUserInfo(response.body().getBody());
                            EMClient.getInstance().login(user.getLogonId(), user.getPassword()
                                    , new EMCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            LoadingDialog.hideProgressDialog();
                                            if (captchaDialog != null && captchaDialog.isShowing()) {
                                                captchaDialog.dismiss();
                                            }
                                            Log.e("test", user.getLogonId() + "登录成功");
                                            MainActivity.start(LoginActivity.this);
                                            finish();
                                        }

                                        @Override
                                        public void onError(int i, String s) {
                                            LoadingDialog.hideProgressDialog();
                                            if (captchaDialog != null && captchaDialog.isShowing()) {
                                                captchaDialog.dismiss();
                                            }
                                            Log.e("test", user.getLogonId() + "登录失败" + s);
                                        }

                                        @Override
                                        public void onProgress(int i, String s) {

                                        }
                                    });
                        } else {
                            if (captchaDialog != null && captchaDialog.isShowing()) {
                                captchaDialog.dismiss();
                            }
                            LoadingDialog.hideProgressDialog();
                            showShortToast(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<UserInfoEntry>> call, Throwable t) {
                        t.printStackTrace();
                        LoadingDialog.hideProgressDialog();
                    }
                });


    }
}
