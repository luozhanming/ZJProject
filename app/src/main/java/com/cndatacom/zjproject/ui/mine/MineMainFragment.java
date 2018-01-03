package com.cndatacom.zjproject.ui.mine;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.ui.contract.ContractMainFragment;

/**
 * 描述：我的界面
 * Created by cdc4512 on 2018/1/2.
 */

public class MineMainFragment extends Fragment {

    private static MineMainFragment sInstance;


    public MineMainFragment(){

    }

    public static MineMainFragment instance(Bundle bundle) {
        if (sInstance == null) {
            synchronized (ContractMainFragment.class) {
                if (sInstance == null) {
                    sInstance = new MineMainFragment();
                    sInstance.setArguments(bundle);
                }
            }
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine,null);
    }
}
