package com.cndatacom.zjproject.okhttp;

import android.content.Context;

import com.lkk.tyj.util.DialogUtils;

import java.io.IOException;

import okhttp3.Response;

public abstract class DialogCallback extends Callback<String>
{
    private Context mContext;

    public DialogCallback(Context context){
        mContext = context;
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        DialogUtils.hideProgressDialog();
    }
}
