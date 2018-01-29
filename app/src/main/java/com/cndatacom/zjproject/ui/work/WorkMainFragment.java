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
import android.widget.TextView;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.adapter.FunctionAdapter;
import com.cndatacom.zjproject.common.AppConstant;
import com.cndatacom.zjproject.common.GridItemDecoration;
import com.cndatacom.zjproject.entry.FunctionEntry;
import com.cndatacom.zjproject.entry.LoginEntry;
import com.cndatacom.zjproject.entry.TaskEntry;
import com.cndatacom.zjproject.entry.TasksEntry;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.cndatacom.zjproject.ui.common.WebActivity;
import com.cndatacom.zjproject.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 描述：主页工作界面
 * Created by zhanming on 2018/1/1.
 */

public class WorkMainFragment extends Fragment implements View.OnClickListener {

    private static WorkMainFragment sInstance = null;

    //  ImageView ivSearch;
    TextView tvBacklog, tvReadLater, tvDone, tvHasRead;
    RecyclerView rvFunction;
    View item_daiban, item_daiyue, item_yiban, item_yiyue;

    List<TaskEntry> dbList;
    List<TaskEntry> dyList;
    List<TaskEntry> ybList;
    List<TaskEntry> yyList;

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
        //   ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        tvBacklog = (TextView) view.findViewById(R.id.tv_backlog);
        tvReadLater = (TextView) view.findViewById(R.id.tv_readLater);
        tvDone = (TextView) view.findViewById(R.id.tv_done);
        tvHasRead = (TextView) view.findViewById(R.id.tv_haveRead);
        rvFunction = (RecyclerView) view.findViewById(R.id.rv_functions);
        item_daiban = view.findViewById(R.id.item_daiban);
        item_daiyue = view.findViewById(R.id.item_daiyue);
        item_yiban = view.findViewById(R.id.item_yiban);
        item_yiyue = view.findViewById(R.id.item_yiyue);
        item_daiban.setOnClickListener(this);
        item_daiyue.setOnClickListener(this);
        item_yiban.setOnClickListener(this);
        item_yiyue.setOnClickListener(this);

        rvFunction.setLayoutManager(new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false));
        List<FunctionEntry> functions = new ArrayList<>();
        mFunctionAdapter = new FunctionAdapter(getActivity(), functions);
        rvFunction.addItemDecoration(new GridItemDecoration(1, Color.parseColor("#AAAAAA")));
        rvFunction.setAdapter(mFunctionAdapter);
        initData();
    }


    private void initData() {
        fetchTask();
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


    private void fetchTask() {
        String username = LoginEntry.instance().getUserInfo().getLogonId();
        String beginDate = Utils.getDate(System.currentTimeMillis() - AppConstant.DAY * 7);
        String endDate = Utils.getDate(System.currentTimeMillis());
        MyRetrofit.getHttpService().getTaskList(username, beginDate, endDate)
                .enqueue(new Callback<TasksEntry>() {
                    @Override
                    public void onResponse(Call<TasksEntry> call, Response<TasksEntry> response) {
                        TasksEntry body = response.body();
                        if (body.getStatus().equals("1")) {
                            dbList = body.getDbList();
                            dyList = body.getDyList();
                            ybList = body.getYbList();
                            yyList = body.getYyList();
                            tvBacklog.setText(dbList.size() + "");
                            tvReadLater.setText(dyList.size() + "");
                            tvDone.setText(ybList.size() + "");
                            tvHasRead.setText(yyList.size() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<TasksEntry> call, Throwable t) {

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_daiban:
                WebActivity.start(getActivity(),"云办事","http://bsxt.tzjjcy.zjportal.net/");
                break;
            case R.id.item_daiyue:
                WebActivity.start(getActivity(),"云办事","http://bsxt.tzjjcy.zjportal.net/");
                break;
            case R.id.item_yiban:
                WebActivity.start(getActivity(),"云办事","http://bsxt.tzjjcy.zjportal.net/");
                break;
            case R.id.item_yiyue:
                WebActivity.start(getActivity(),"云办事","http://bsxt.tzjjcy.zjportal.net/");
                break;
        }
    }
}
