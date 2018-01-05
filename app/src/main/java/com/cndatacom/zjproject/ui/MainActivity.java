package com.cndatacom.zjproject.ui;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
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
    private BroadcastReceiver receiver;


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

        rgBottomNavigation.check(R.id.work);
    }

    private void initData() {
        receiver = new ApkInstallReceiver();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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


    class ApkInstallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downloadId = SPUtils.getInstance().getLong("downloadId");
                installApk(context, downloadId);
            }
        }

        private void installApk(Context ctx, long id) {
            DownloadManager dManager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
            Intent install = new Intent(Intent.ACTION_VIEW);
            Uri downloadFileUri = dManager.getUriForDownloadedFile(id);
            if (downloadFileUri != null) {
                Log.d("DownloadManager", downloadFileUri.toString());
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(install);
            } else {
                Log.e("DownloadManager", "download error");
            }
        }
    }
}
