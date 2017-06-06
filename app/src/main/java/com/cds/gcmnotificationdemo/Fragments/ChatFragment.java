package com.cds.gcmnotificationdemo.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cds.gcmnotificationdemo.Adapters.CustomMessageAdapter;
import com.cds.gcmnotificationdemo.MainActivity;
import com.cds.gcmnotificationdemo.Models.MessageModel;
import com.cds.gcmnotificationdemo.R;
import com.cds.gcmnotificationdemo.Utils;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fazal on 3/27/2017.
 */

public class ChatFragment extends Fragment {
    private EditText message_et;
    private Button send_btn;
    private ListView lv_chat;
    private ArrayList<MessageModel> list_msg;
    boolean sender;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.chat_layout_frag,container,false);
        init(rootview);
        return rootview;
    }

    private void init(View rootview) {
        message_et = (EditText) rootview.findViewById(R.id.message);
        send_btn = (Button) rootview.findViewById(R.id.send);
        lv_chat = (ListView) rootview.findViewById(R.id.chat_lv);
        list_msg = new ArrayList<>();

        final Utils utils = new Utils(getActivity());
        final String recievername = getActivity().getIntent().getStringExtra("RecieverName");
        Log.d("recievername", recievername);

        if(getActivity().getIntent().getStringExtra("message")!=null){
            list_msg.add(new MessageModel(getActivity().getIntent().getStringExtra("message"),recievername,true));
            CustomMessageAdapter adapter = new CustomMessageAdapter(getActivity(),R.layout.chat_layout_frag,list_msg);
            lv_chat.setAdapter(adapter);

        }

       /* mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("message");
                Log.d("msg",msg);
                list_msg.add(new MessageModel(msg,recievername,false));
                CustomMessageAdapter adapter = new CustomMessageAdapter(getActivity(),R.layout.chat_layout_frag,list_msg);
                lv_chat.setAdapter(adapter);
            }
        };

        mIntentFilter=new IntentFilter("Message_show");*/

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message_et.getText().length()>0){
                    list_msg.add(new MessageModel(message_et.getText().toString(),utils.getdata("sendername"),true));
                    Log.d("sendername",utils.getdata("sendername"));
                    SendMessage(recievername);
                    CustomMessageAdapter adapter = new CustomMessageAdapter(getActivity(),R.layout.chat_layout_frag,list_msg);
                    lv_chat.setAdapter(adapter);
                }
            }
        });
    }

    private void SendMessage(final String recievername) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Utils utils = new Utils(getActivity());
                RequestBody requestbody = new FormBody.Builder()
                        .add("SenderToken",utils.getdata("token"))
                        .add("RecieverName",recievername)
                        .add("Message",message_et.getText().toString())
                        .build();


                Request request = new Request.Builder()
                        .url("http://freecs13.hostei.com/fcm/gcm_send.php")
                        .post(requestbody)
                        .build();

                try{
                   Response res = client.newCall(request).execute();
                    Log.d("response_notif",res.message());
                }catch (Exception e){
                    e.printStackTrace();

                    Log.d("response_notif",e.toString());
                }
            }
        });

        thread.start();
    }


}
