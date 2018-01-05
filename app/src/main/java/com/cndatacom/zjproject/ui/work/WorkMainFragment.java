package com.cndatacom.zjproject.ui.work;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.adapter.FunctionAdapter;
import com.cndatacom.zjproject.common.GridItemDecoration;
import com.cndatacom.zjproject.entry.FunctionEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：主页工作界面
 * Created by zhanming on 2018/1/1.
 */

public class WorkMainFragment extends Fragment {

    private static WorkMainFragment sInstance = null;

    ImageView ivSearch;
    TextView tvBacklog, tvReadLater, tvDone, tvHasRead;
    RecyclerView rvFunction;

    private FunctionAdapter mFunctionAdapter;

    public static WorkMainFragment instance(Bundle bundle) {
        if (sInstance == null) {
            synchronized (WorkMainFragment.class) {
                if (sInstance == null) {
                    sInstance = new WorkMainFragment();
                    sInstance.setArguments(bundle);
                }
            }
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        tvBacklog = (TextView) view.findViewById(R.id.tv_backlog);
        tvReadLater = (TextView) view.findViewById(R.id.tv_readLater);
        tvDone = (TextView) view.findViewById(R.id.tv_done);
        tvHasRead = (TextView) view.findViewById(R.id.tv_haveRead);
        rvFunction = (RecyclerView) view.findViewById(R.id.rv_functions);

        rvFunction.setLayoutManager(new GridLayoutManager(getActivity(),4, LinearLayoutManager.VERTICAL,false));
        List<FunctionEntry> functions = new ArrayList<>();
        mFunctionAdapter = new FunctionAdapter(getActivity(), functions);
        rvFunction.addItemDecoration(new GridItemDecoration(1, Color.parseColor("#AAAAAA")));
        rvFunction.setAdapter(mFunctionAdapter);

        initData();
    }

    private void initData() {
        List<FunctionEntry> functions = new ArrayList<>();
        FunctionEntry entry1 = new FunctionEntry();
        entry1.setIcon(R.mipmap.ic_shenpi);
        entry1.setName("OA审批");
        FunctionEntry entry2 = new FunctionEntry();
        entry2.setIcon(R.mipmap.ic_banshi);
        entry2.setName("云办事");
        FunctionEntry entry3 = new FunctionEntry();
        entry3.setIcon(R.mipmap.ic_dingcan);
        entry3.setName("云订餐");
//        FunctionEntry entry4 = new FunctionEntry();
//        entry4.setIcon(R.mipmap.ic_gonggao);
//        entry4.setName("通知公告");
//        FunctionEntry entry5 = new FunctionEntry();
//        entry5.setIcon(R.mipmap.ic_duban);
//        entry5.setName("督办系统");
//        FunctionEntry entry6 = new FunctionEntry();
//        entry6.setIcon(R.mipmap.ic_videophone);
//        entry6.setName("视频电话");
//        FunctionEntry entry7 = new FunctionEntry();
//        entry7.setIcon(R.mipmap.ic_mail);
//        entry7.setName("内部邮件");
        functions.add(entry1);
        functions.add(entry2);
        functions.add(entry3);
//        functions.add(entry4);
//        functions.add(entry5);
//        functions.add(entry6);
//        functions.add(entry7);
        mFunctionAdapter.addFunctions(functions);
    }



}
