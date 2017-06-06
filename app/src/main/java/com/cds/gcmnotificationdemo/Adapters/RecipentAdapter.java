package com.cds.gcmnotificationdemo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cds.gcmnotificationdemo.Models.RecipentData;
import com.cds.gcmnotificationdemo.R;

import java.util.List;

/**
 * Created by fazal on 2/24/2017.
 */

public class RecipentAdapter extends RecyclerView.Adapter<RecipentAdapter.RecipentViewHolder> {

    private List<RecipentData> recipentList;

    public RecipentAdapter(List<RecipentData> recipentlist) {
        this.recipentList = recipentlist;

    }

    @Override
    public RecipentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_layout_item, parent, false);

        return new RecipentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipentViewHolder holder, int position) {
        RecipentData data = recipentList.get(position);
        holder.user_name.setText(data.getUsername());
        holder.user_msg.setText(data.getMessage());
        holder.user_date.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return recipentList.size();
    }

    public class RecipentViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, user_msg, user_date;

        public RecipentViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.user_name);
            user_date = (TextView) view.findViewById(R.id.date_msg);
            user_msg = (TextView) view.findViewById(R.id.user_lastmsg);
        }
    }

}
