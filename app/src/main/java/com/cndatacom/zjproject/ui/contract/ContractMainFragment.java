package com.cndatacom.zjproject.ui.contract;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.BarUtils;
import com.cndatacom.zjproject.R;

/**
 * 描述：联系人界面
 * Created by cdc4512 on 2018/1/2.
 */

public class ContractMainFragment extends Fragment {

    private static ContractMainFragment sInstance;


    public ContractMainFragment(){

    }

    public static ContractMainFragment instance(Bundle bundle) {
        if (sInstance == null) {
            synchronized (ContractMainFragment.class) {
                if (sInstance == null) {
                    sInstance = new ContractMainFragment();
                    sInstance.setArguments(bundle);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_contract,null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
