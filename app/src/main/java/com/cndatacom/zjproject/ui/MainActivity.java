package com.cndatacom.zjproject.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.RadioGroup;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.base.BaseActivity;
import com.cndatacom.zjproject.entry.UserInfoEntry;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.cndatacom.zjproject.ui.contract.ContractMainFragment;
import com.cndatacom.zjproject.ui.message.MessageMainFragment;
import com.cndatacom.zjproject.ui.mine.MineMainFragment;
import com.cndatacom.zjproject.ui.work.WorkMainFragment;
import com.cndatacom.zjproject.util.EncryptUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgBottomNavigation;


    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        rgBottomNavigation = (RadioGroup) findViewById(R.id.rg_bottomNavigation);
        rgBottomNavigation.setOnCheckedChangeListener(this);
    }

    private void initData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.message:
                MessageMainFragment messageFragment = MessageMainFragment.instance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, messageFragment).commit();
                break;
            case R.id.work:
                WorkMainFragment workMainFragment = WorkMainFragment.instance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, workMainFragment).commit();

                break;
            case R.id.contract:
                ContractMainFragment contractMainFragment = ContractMainFragment.instance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, contractMainFragment).commit();

                break;
            case R.id.mine:
                MineMainFragment mineMainFragment = MineMainFragment.instance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, mineMainFragment).commit();
                break;
        }
    }
}
