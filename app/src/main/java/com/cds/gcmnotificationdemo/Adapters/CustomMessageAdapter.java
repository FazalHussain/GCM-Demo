package com.cds.gcmnotificationdemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cds.gcmnotificationdemo.Models.MessageModel;
import com.cds.gcmnotificationdemo.R;

import java.util.List;

/**
 * Created by fazal on 3/28/2017.
 */

public class CustomMessageAdapter extends ArrayAdapter<MessageModel> {

    Context context;
    public CustomMessageAdapter(Context context, int resource, List<MessageModel> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        MessageModel msgmodel = getItem(position);
        if(convertView==null){
            if(msgmodel.issender()){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.sender_item,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.reciever_item,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.username.setText(msgmodel.getUsername());
        viewHolder.sendermessage.setText(msgmodel.getMsg());

        return convertView;
    }

    public class ViewHolder{
        TextView username;
        TextView sendermessage;

        public ViewHolder(View view) {
            this.username = (TextView) view.findViewById(R.id.name);
            this.sendermessage = (TextView) view.findViewById(R.id.msg);
        }
    }
}
