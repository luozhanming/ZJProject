package com.cndatacom.zjproject.ui.common;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.base.BaseActivity;
import com.cndatacom.zjproject.entry.LoginEntry;

import okhttp3.Cookie;

/**
 * Created by cdc4512 on 2018/1/5.
 */

public class WebActivity extends BaseActivity implements View.OnClickListener {

    ProgressBar progressBar;
    WebView webView;
    ImageView back;
    private String mTitle;

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initCookie();
        initWebView();
        initData();
        //   webView.loadUrl(getIntent().getStringExtra("url"));
    }

    private void initCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        StringBuilder cookies = new StringBuilder();
        String m_ssoTokenKey = LoginEntry.instance().getUserInfo().getInfoOut().getM_SSOTokenKey();
        cookies.append(String.format("__PLATFORM_SSOToken__=%s",m_ssoTokenKey));
        cookieManager.setCookie(getIntent().getStringExtra("url"),cookies.toString());
        CookieSyncManager.getInstance().sync();

    }

    private void initData() {
        mTitle = getIntent().getStringExtra("title");
        ((TextView) findViewById(R.id.title)).setText(mTitle);
        webView.loadUrl(getIntent().getStringExtra("url"));

    }

    private void initView() {
        progressBar = f(R.id.progressBar);
        webView = f(R.id.webView);
        back = f(R.id.back);

        back.setOnClickListener(this);

    }

    private void initWebView() {
        // 设置网路兼容模式
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //自动打开js窗口，默认是false
        //webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); //控制页面布局，有三个类型（该方法不好用，会出导致页面显示不全）
        webSettings.setAllowFileAccess(true); // 是否允许访问网络文件
        webSettings.setBuiltInZoomControls(true); // 是否允许内置的放大机制（这里如果设为false，则setDisplayZoomControls(true)和setSupportZoom(true)无效）
        webSettings.setDisplayZoomControls(false); //是否显示WebView缩放按钮
        webSettings.setSupportZoom(true); // 是否允许屏幕缩放控件和手势缩放
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setDomStorageEnabled(true);//DOM Storage
        webSettings.setUseWideViewPort(true);// 加载进来的页面自适应手机屏幕
        webSettings.setLoadWithOverviewMode(true); //和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 重写WebViewClient，让网页在WebView里面显示，而不是跳转浏览器。
     */
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        public void onPageFinished(WebView view, String url) {
            /**网站加载完毕，隐藏progressBar*/
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            /**网站加载失败的操作，这里的进度条可以被标题栏水平进度条所取代*/
            progressBar.setVisibility(View.GONE);
            Toast.makeText(WebActivity.this, "加载失败=" + description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            /**接收ssl错误，原因是网站网址是https开头，WebView不支持改网站证书
             * 注意：这里一定要把super.onReceivedSslError()注释掉。这是因为WebViewClient的onReceivedSslError()函数中包含了一条handler.cancel()。
             * */
            //super.onReceivedSslError(view, handler, error);

            //handler.cancel(); 默认的处理方式，WebView变成空白页
            handler.proceed(); //接受证书
            //handleMessage(Message msg); 其他处理
            //Toast.makeText(WebViewActivity.this, "出现了sslError，只能接受证书才能正常访问", Toast.LENGTH_SHORT).show();
        }


    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }



        }






    }

    /**
     * 如果打开了网页就返回上一个页面，没有打开网页按返回键就关闭当前页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack(); //goBack()表示返回webView的上一页面
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
