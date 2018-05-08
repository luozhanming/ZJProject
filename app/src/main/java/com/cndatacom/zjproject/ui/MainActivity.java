package com.cndatacom.zjproject.ui;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.SPUtils;
import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.base.BaseActivity;
import com.cndatacom.zjproject.common.UserInfoCache;
import com.cndatacom.zjproject.entry.UserInfoEntry;
import com.cndatacom.zjproject.ui.contract.ChatActivity;
import com.cndatacom.zjproject.ui.mine.MineMainFragment;
import com.cndatacom.zjproject.ui.work.WorkMainFragment;
import com.cndatacom.zjproject.widget.BadgeRadioButton;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgBottomNavigation;
    private BroadcastReceiver receiver;
    private BadgeRadioButton messageRadiobutton;
    private EaseConversationListFragment conversationFragment;
    private EaseContactListFragment contactFragment;

    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            //收到消息

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageRadiobutton.setBadgeNum(messageRadiobutton.getBadgeNum() + messages.size());
                    if (conversationFragment.isVisible()) {
                        conversationFragment.refresh();
                    }
                }
            });

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(final List<EMMessage> messages) {
            //收到已读回执
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageRadiobutton.setBadgeNum(messageRadiobutton.getBadgeNum() - messages.size());
                }
            });
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };


    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
        initCache();  //拉取所有关联用户
        initData();
//        getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//                Animator animator = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    View root = getWindow().getDecorView();
//                    int width = root.getWidth();
//                    int height = root.getHeight();
//                    animator = ViewAnimationUtils.createCircularReveal(getWindow().getDecorView(), width/2, height/2, 0, height);
//                    animator.setDuration(800);
//                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
//                    animator.start();
//                }
//            }
//        });

    }

    @Override
    public boolean isImmerse() {
        return false;
    }


    private void initCache() {
        UserInfoCache.getInstance().setCacheLoadedListener(new UserInfoCache.CacheLoadedListener() {
            @Override
            public void onCacheLoaded() {
                if (conversationFragment.isVisible()) {
                    contactFragment.refresh();
                }
                if (contactFragment.isVisible()) {
                    contactFragment.refresh();
                }
            }
        });
        UserInfoCache.getInstance().fetchInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //页面重现时重新读取未读消息数目
        messageRadiobutton.setBadgeNum(EMClient.getInstance().chatManager().getUnreadMessageCount());
    }

    private void initView() {
        rgBottomNavigation = (RadioGroup) findViewById(R.id.rg_bottomNavigation);
        messageRadiobutton = (BadgeRadioButton) findViewById(R.id.message);
        rgBottomNavigation.setOnCheckedChangeListener(this);

        rgBottomNavigation.check(R.id.work);
        conversationFragment = new EaseConversationListFragment();
        contactFragment = new EaseContactListFragment();
        contactFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                String username = user.getUsername();
                ChatActivity.start(MainActivity.this, username, EaseConstant.CHATTYPE_SINGLE);
            }
        });
        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                String from = conversation.conversationId();
                if (conversation.isGroup()) {
                    ChatActivity.start(MainActivity.this, from, EaseConstant.CHATTYPE_GROUP);
                } else {
                    ChatActivity.start(MainActivity.this, from, EaseConstant.CHATTYPE_SINGLE);
                }
            }
        });
    }

    private void initData() {
        receiver = new ApkInstallReceiver();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        //获取未读消息
        messageRadiobutton.setBadgeNum(EMClient.getInstance().chatManager().getUnreadMessageCount());

        bindEMAvatarAndName();
        Observable.just(EMClient.getInstance().getCurrentUser())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("test", "accept: " + s);
                        bindContact();
                    }
                });

    }


    /**
     * 关联好友
     */
    private void bindContact() {
        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                Map<String, EaseUser> map = new HashMap<>();
                for (String string : strings) {
                    EaseUser user = new EaseUser(string);
                    map.put(string, user);
                }
                contactFragment.setContactsMap(map);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("Test", "onError: " + s);
            }
        });
    }

    static class EaseUserProfileProviderImpl implements EaseUI.EaseUserProfileProvider{

        @Override
        public EaseUser getUser(String username) {
            EaseUser user = new EaseUser(username);
            UserInfoEntry userInfo = UserInfoCache.getInstance().getUserInfo(username);
            if (userInfo != null) {
                user.setAvatar(userInfo.getPhoto());
                user.setNickname(userInfo.getFullName());
            }
            return user;
        }
    }

    /**
     * 绑定环信头像和昵称
     */
    private void bindEMAvatarAndName() {
        EaseUI easeUI = EaseUI.getInstance();
        easeUI.setUserProfileProvider(new EaseUserProfileProviderImpl());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.message:
                if (conversationFragment == null) {
                    conversationFragment = new EaseConversationListFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content, conversationFragment).commit();
                break;
            case R.id.work:
                WorkMainFragment workMainFragment = WorkMainFragment.instance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, workMainFragment).commit();

                break;
            case R.id.contract:
                if (contactFragment == null) {
                    contactFragment = new EaseContactListFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content, contactFragment).commit();
                break;
            case R.id.mine:
                MineMainFragment mineMainFragment = MineMainFragment.instance(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, mineMainFragment).commit();
                break;
        }
    }


    static class ApkInstallReceiver extends BroadcastReceiver {

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
