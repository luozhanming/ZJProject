package com.cndatacom.zjproject.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.adapter.MessageAdapter;
import com.cndatacom.zjproject.entry.MessageEntry;
import com.cndatacom.zjproject.ui.contract.ContractMainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：消息界面
 * Created by cdc4512 on 2018/1/2.
 */

public class MessageMainFragment extends Fragment {

    ImageView ivSearch;
    RecyclerView rvMessages;


    private static MessageMainFragment sInstance;

    private MessageAdapter mMessageAdapter;

    public MessageMainFragment() {

    }

    public static MessageMainFragment instance(Bundle bundle) {
        if (sInstance == null) {
            synchronized (ContractMainFragment.class) {
                if (sInstance == null) {
                    sInstance = new MessageMainFragment();
                    sInstance.setArguments(bundle);
                }
            }
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        rvMessages = (RecyclerView) view.findViewById(R.id.rv_messages);

        rvMessages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mMessageAdapter = new MessageAdapter(getActivity(), new ArrayList<MessageEntry>());
        rvMessages.setAdapter(mMessageAdapter);
        initData();
    }

    private void initData() {
        List<MessageEntry> datas = new ArrayList<>();
        MessageEntry entry = new MessageEntry();
        entry.setFromDepartment(true);
        entry.setFromUser("财务部");
        entry.setImgUrl("http://img.boqiicdn.com/Data/BK/P/img99621405665139.jpg");
        entry.setTime("20:11");
        entry.setMessage("王科长:今日工作安排完成了没有..");
        datas.add(entry);
        mMessageAdapter.messageChange(datas);
    }
}
