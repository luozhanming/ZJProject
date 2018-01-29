package com.cndatacom.zjproject.ui.mine;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.common.CircleBitmapTransformation;
import com.cndatacom.zjproject.entry.LoginEntry;
import com.cndatacom.zjproject.entry.Result;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.cndatacom.zjproject.widget.LoadingDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 描述：我的界面
 * Created by cdc4512 on 2018/1/2.
 */

public class MineMainFragment extends Fragment implements View.OnClickListener {

    private static MineMainFragment sInstance;

    ImageView ivHead;
    TextView tvName;
    TextView tvDepartment;

    private LoginEntry loginEntry = LoginEntry.instance();


    private View viewLogout;


    public MineMainFragment() {

    }

    public static MineMainFragment instance(Bundle bundle) {
        if (sInstance == null) {
            synchronized (MineMainFragment.class) {
                if (sInstance == null) {
                    sInstance = new MineMainFragment();
                    sInstance.setArguments(bundle);
                }
            }
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewLogout = view.findViewById(R.id.view_logout);
        ivHead = (ImageView) view.findViewById(R.id.iv_head);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvDepartment = (TextView) view.findViewById(R.id.tv_department);

        viewLogout.setOnClickListener(this);

        tvName.setText(loginEntry.getUserInfo().getFullName());
        tvDepartment.setText(loginEntry.getUserInfo().getParentNames());
        Glide.with(getActivity()).load(loginEntry.getUserInfo().getPhoto())
                .asBitmap()
                .placeholder(R.mipmap.ic_head_default)
                .transform(new CircleBitmapTransformation(getActivity()))
                .into(ivHead);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_logout:
                logout();
                break;
        }
    }

    private void logout() {
        LoadingDialog.showLoadingDialog(getActivity(), "退出中..");
        MyRetrofit.getHttpService().logout(LoginEntry.instance().getUserInfo().getLogonId())
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {

                        if (response.body().getStatus().equals("1")) {

                            EMClient.getInstance().logout(true, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    Log.e("test","退出登录");
                                    LoadingDialog.hideProgressDialog();
                                    LoginEntry.instance().logout();
                                    LoginActivity.start(getActivity());
                                    getActivity().finish();
                                }

                                @Override
                                public void onError(int i, String s) {

                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        //    ActivityUtils.finishActivity(MainActivity.class);

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        t.printStackTrace();
                        LoadingDialog.hideProgressDialog();
                    }
                });
    }
}
