package com.cndatacom.zjproject.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by cdc4512 on 2018/1/2.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setImmerse(boolean immerse) {
        if (immerse) {
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }


    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public <T> T f(@IdRes int id) {
        return (T) findViewById(id);
    }


    boolean canExit = false;

    @Override
    public void onBackPressed() {
        if (ActivityUtils.getActivityList().size() == 1) {
            if (!canExit) {
                showShortToast("再按一下退出应用");
                canExit = true;
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                canExit = false;
                            }
                        });
            } else {
                finish();
            }

        } else {
            super.onBackPressed();
        }

    }
}
