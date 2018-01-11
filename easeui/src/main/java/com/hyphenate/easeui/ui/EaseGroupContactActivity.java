package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.GroupListAdapter;
import com.hyphenate.easeui.model.EaseGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdc4512 on 2018/1/11.
 */

public class EaseGroupContactActivity extends EaseBaseActivity {

    private ListView groupListView;
    private GroupListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ease_activity_group_contact);
        initView();
        initData();
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    List<EMGroup> groups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    final List<EaseGroup> easeGroups = new ArrayList<>();
                    for (EMGroup group : groups) {
                        EaseGroup egroup = new EaseGroup(group,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516244460&di=9ba09f7219ef396b5060d05b10f8b7eb&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fa5c27d1ed21b0ef45f4d2b59d7c451da81cb3e0b.jpg");
                        easeGroups.add(egroup);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged(easeGroups);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        groupListView = (ListView) findViewById(R.id.groupList);
        mAdapter = new GroupListAdapter(this,new ArrayList<EaseGroup>());
        groupListView.setAdapter(mAdapter);
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseGroup group = (EaseGroup) mAdapter.getItem(position);
                EaseGroupChatActivity.start(EaseGroupContactActivity.this,group.getGroupId());
            }
        });

    }


}
