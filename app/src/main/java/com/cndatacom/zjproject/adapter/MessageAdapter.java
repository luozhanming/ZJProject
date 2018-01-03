package com.cndatacom.zjproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.common.CircleBitmapTransformation;
import com.cndatacom.zjproject.entry.MessageEntry;

import java.util.List;

/**
 * Created by cdc4512 on 2018/1/3.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.VH> {

    private Context mContext;
    private List<MessageEntry> messages;

    public MessageAdapter(Context context, List<MessageEntry> messages) {
        this.mContext = context;
        this.messages = messages;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_message,parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        MessageEntry message = messages.get(position);
        Glide.with(mContext)
                .load(message.getImgUrl())
                .asBitmap()
                .transform(new CircleBitmapTransformation(mContext))
                .into(holder.picture);


        holder.fromUser.setText(message.getFromUser());
        if(message.isFromDepartment()){
            holder.fromDepartment.setVisibility(View.VISIBLE);
        }else{
            holder.fromDepartment.setVisibility(View.GONE);
        }
        holder.message.setText(message.getMessage());
        holder.time.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void messageChange(List<MessageEntry> datas){
        messages.clear();
        messages.addAll(datas);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView picture;
        TextView fromUser,fromDepartment,message,time;

        public VH(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.picture);
            fromUser = (TextView) v.findViewById(R.id.fromUser);
            fromDepartment = (TextView) v.findViewById(R.id.fromDepartment);
            message = (TextView) v.findViewById(R.id.message);
            time = (TextView) v.findViewById(R.id.time);
        }
    }
}
