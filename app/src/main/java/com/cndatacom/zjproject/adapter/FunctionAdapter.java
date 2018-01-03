package com.cndatacom.zjproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cndatacom.zjproject.R;
import com.cndatacom.zjproject.entry.FunctionEntry;

import java.util.List;

/**
 * Created by cdc4512 on 2018/1/2.
 */

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH> {


    private Context mContext;
    private List<FunctionEntry> functions;

    public FunctionAdapter(Context mContext, List<FunctionEntry> functions) {
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
            holder.icon.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(functions.get(position).getName());
            holder.icon.setImageResource(functions.get(position).getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = functions.get(pos).getName();
                    switch (name){
                        case "OA审批":
                            Toast.makeText(mContext,"OA审批",Toast.LENGTH_SHORT).show();
                            break;
                        case "云办事":
                            Toast.makeText(mContext,"云办事",Toast.LENGTH_SHORT).show();
                            break;
                        case "云订餐":
                            Toast.makeText(mContext,"云订餐",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return functions.size() + 1;
    }

    public void addFunction(FunctionEntry function){
        functions.add(function);
        notifyDataSetChanged();
    }

    public void addFunctions(List<FunctionEntry> datas){
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
