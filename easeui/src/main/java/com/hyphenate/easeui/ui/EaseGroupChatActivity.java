package com.hyphenate.easeui.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;

/**
 * Created by cdc4512 on 2018/1/11.
 */

public class EaseGroupChatActivity extends EaseBaseActivity {

    EaseChatFragment chatFragment;


    public static void start(Context context,String id){
        Intent intent = new Intent(context,EaseGroupChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        chatFragment = new EaseChatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
        bundle.putString(EaseConstant.EXTRA_USER_ID,getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID));
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,chatFragment).commit();
    }
}
