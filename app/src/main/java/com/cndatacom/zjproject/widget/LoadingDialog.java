package com.cndatacom.zjproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.widget.TextView;

import com.cndatacom.zjproject.R;

/**
 * Created by cdc4512 on 2018/1/4.
 */

public class LoadingDialog extends Dialog {

    private static LoadingDialog dialog;

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.style_dialog_loading);
    }

    public static void showLoadingDialog(Context context, String message) {
        if (dialog != null && dialog.isShowing()) {
            return;
        } else {
            dialog = new LoadingDialog(context, R.style.style_dialog_loading);
        }
        dialog.setContentView(R.layout.layout_loading_dialog);//dialog布局文件
        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog

        //提示文本，默认为（加载中...）
        TextView mMessage = (TextView) dialog.findViewById(R.id.textView);
        if (TextUtils.isEmpty(message)) {
            mMessage.setText("加载中...");
        } else {
            mMessage.setText(message);
        }

        dialog.show();
    }

    /**
     * 关闭progressDialog
     */
    public static void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
