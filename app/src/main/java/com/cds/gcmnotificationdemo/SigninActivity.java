package com.cds.gcmnotificationdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cds.gcmnotificationdemo.Adapters.RecipentAdapter;
import com.cds.gcmnotificationdemo.Models.RecipentData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SigninActivity extends AppCompatActivity {

    private RecyclerView ryclerview_user;
    private ArrayList<RecipentData> friend_list;
    private RecipentAdapter adapter;
    private ProgressDialog pb;
    private Utils utils;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();

    }

    private void init() {
        username = (EditText) findViewById(R.id.user_name);
        utils = new Utils(this);
        showPB("Initialization...");
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                HidePB();
            }
        }, 0, 100000);
    }

    public void SubmitClick(View view){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                RequestBody requestbody = new FormBody.Builder()
                        .add("token",utils.getdata("token"))
                        .add("Name",username.getText().toString())
                        .add("DateTime",sdf.format(new Date()))
                        .build();

                utils.savedata("sendername",username.getText().toString());
                Request request = new Request.Builder()
                        .url("http://freecs13.hostei.com/fcm/register.php")
                        .post(requestbody)
                        .build();

                try{
                    client.newCall(request).execute();
                    Intent i = new Intent(SigninActivity.this,MainActivity.class);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void showPB(String msg){
        pb = new ProgressDialog(SigninActivity.this);
        pb.setMessage(msg);
        pb.isIndeterminate();
        pb.setCancelable(false);
        pb.show();
    }

    public void HidePB(){
        if(pb!=null && pb.isShowing()){
            pb.dismiss();
        }
    }
}
