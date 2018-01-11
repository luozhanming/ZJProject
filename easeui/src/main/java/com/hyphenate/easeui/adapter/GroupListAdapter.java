package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseGroup;
import com.hyphenate.easeui.widget.EaseImageView;

import java.util.List;

/**
 * Created by cdc4512 on 2018/1/11.
 */

public class GroupListAdapter extends BaseAdapter {

    private Context mContext;
    private List<EaseGroup> groupList;

    public GroupListAdapter(Context context, List<EaseGroup> groups) {
        mContext = context;
        groupList = groups;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EaseGroup group = groupList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ease_row_contact,parent,false);
            holder.avatar = (EaseImageView) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.sign = (TextView) convertView.findViewById(R.id.signature);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(R.drawable.ease_group_icon).into(holder.avatar);
        holder.name.setText(group.getGroupName());
        return convertView;
    }

    public void notifyDataSetChanged(List<EaseGroup> groups) {
        groupList.clear();
        groupList.addAll(groups);
        super.notifyDataSetChanged();
    }

    class ViewHolder {
        EaseImageView avatar;
        TextView name;
        TextView sign;
    }
}
