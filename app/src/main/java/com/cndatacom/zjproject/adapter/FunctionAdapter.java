package com.cndatacom.zjproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.entry.FunctionEntry;
import com.cndatacom.zjproject.ui.common.WebActivity;
import com.cndatacom.zjproject.util.DownloadUtil;
import com.cndatacom.zjproject.widget.CommonDialog;

import java.util.List;

/**
 * Created by cdc4512 on 2018/1/2.
 */

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH> {


    private Context mContext;
    private List<FunctionEntry> functions;

    public FunctionAdapter(Context mContext, final List<FunctionEntry> functions) {
        this.mContext = mContext;
        this.functions = functions;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_function, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final int pos = position;
        if (position == functions.size()) {
            holder.name.setVisibility(View.GONE);
            holder.icon.setImageResource(R.mipmap.add);
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(functions.get(position).getName());
            holder.icon.setImageResource(functions.get(position).getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = functions.get(pos).getName();
                    switch (name) {
                        case "OA审批":
                            toOAApp();
                            break;
                        case "云办事":
                            WebActivity.start(mContext, "云办事", "http://bsxt.tzjjcy.zjportal.net/");
                            break;
                        case "云订餐":
                            WebActivity.start(mContext, "云订餐", "http://ydc.tzjjcy.zjportal.net/");
                            break;
                    }
                }
            });
        }

    }

    private void toOAApp() {
        if (AppUtils.isInstallApp("cn.com.do1.zjoa")) {
            AppUtils.launchApp("cn.com.do1.zjoa");
        } else {
            showIfInstallApk();
        }
    }

    /**
     * 询问安装OA对话框
     * */
    private void showIfInstallApk() {
        CommonDialog dialog = new CommonDialog(mContext);
        dialog.setContent("是否安装OA应用？");
        dialog.setPositiveButton("确定", new CommonDialog.CommonDialogInterface() {
            @Override
            public boolean onClick() {
                String apkPath = mContext.getExternalFilesDir(null).getPath() + "/Download/oa.apk";
                if (FileUtils.isFileExists(apkPath)) {
                    AppUtils.installApp(apkPath, "com.cndatacom.zjproject.fileprovider");
                } else {
                    long id = DownloadUtil.downloadApk(mContext, "http://exp.zjportal.net/ICTFS/fileDownload.aspx?FileStoragePath=\\DocumentInfo\\34e17a46ed7c401d88870900c861d89d.apk&FileName=%E6%B9%9B%E6%B1%9F%E6%AD%A3%E5%BC%8F%E7%8E%AF%E5%A2%8320170815_530_jiagu_sign.apk",
                            "oa.apk", "下载OA", "");
                    SPUtils.getInstance().put("downloadId", id);
                }
                return true;
            }
        });
        dialog.setNegativeButton("取消", new CommonDialog.CommonDialogInterface() {
            @Override
            public boolean onClick() {
                return false;
            }
        });
        dialog.show();

    }


    @Override
    public int getItemCount() {
        return functions.size() + 1;
    }

    public void addFunction(FunctionEntry function) {
        functions.add(function);
        notifyDataSetChanged();
    }

    public void addFunctions(List<FunctionEntry> datas) {
        functions.clear();
        functions.addAll(datas);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;

        public VH(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon);
            name = (TextView) v.findViewById(R.id.name);
        }
    }

}
