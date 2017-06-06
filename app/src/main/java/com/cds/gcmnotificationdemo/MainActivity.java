package com.cds.gcmnotificationdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cds.gcmnotificationdemo.Adapters.RecipentAdapter;
import com.cds.gcmnotificationdemo.Adapters.RecyclerTouchListener;
import com.cds.gcmnotificationdemo.Models.RecipentData;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {

    private static final String SENDER_ID = "800720455732";
    String regId = "";
    private GoogleCloudMessaging gcm;
    private EditText username;
    private Button signin;
    private RecyclerView recipent_recyclerview;
    private Utils utils;
    private ArrayList<RecipentData> list_recipent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        utils = new Utils(MainActivity.this);
        list_recipent = new ArrayList<>();
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
        recipent_recyclerview = (RecyclerView) findViewById(R.id.recycler_view_users);
        PopulateList();
    }

    private void PopulateList() {
        if(utils.getdata("token")!=null)
        getRecipentList(utils.getdata("token"));
        if(getIntent().getStringExtra("message")!=null){
            //list_recipent.add(new RecipentData(jsonobj.getString("Name"),
              //      jsonobj.getString("LastMessage"),jsonobj.getString("DateTime")));
        }

        recipent_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recipent_recyclerview, new RecyclerTouchListener.Clicklistener() {
            @Override
            public void onClick(View view, int position) {
                RecipentData data = list_recipent.get(position);
                Intent i = new Intent(MainActivity.this,EmptyActivity.class);
                i.putExtra("RecieverName",data.getUsername());
                if(getIntent().getStringExtra("message")!=null){
                    i.putExtra("message",getIntent().getStringExtra("message"));
                    Log.d("msg_main",getIntent().getStringExtra("message"));
                }

                startActivity(i);
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void getRecipentList(final String token) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestbody = new FormBody.Builder()
                        .add("SenderToken",token)
                        .build();

                Request request = new Request.Builder()
                        .url("http://freecs13.hostei.com/fcm/getRecipent.php")
                        .post(requestbody)
                        .build();

                try{
                  Response response = client.newCall(request).execute();
                    JSONArray jsonarray = new JSONArray(response.body().string());
                    for(int i=0;i<jsonarray.length();i++){
                        JSONObject jsonobj = jsonarray.getJSONObject(i);
                        list_recipent.add(new RecipentData(jsonobj.getString("Name"),
                                jsonobj.getString("LastMessage"),jsonobj.getString("DateTime")));
                        Log.d("response_getuser",list_recipent.get(i).getUsername());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecipentAdapter adapter = new RecipentAdapter(list_recipent);
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recipent_recyclerview.setLayoutManager(layoutManager);
                            recipent_recyclerview.setItemAnimator(new DefaultItemAnimator());
                            recipent_recyclerview.setAdapter(adapter);
                        }
                    });
                    //Log.d("response_getuser",response.message());
                    //Log.d("getusers","Success");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("response_getuser",e.toString());
                }
            }
        });

        thread.start();
    }

    public void SendNotification(View view){
       final Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               OkHttpClient client = new OkHttpClient();
               RequestBody requestbody = new FormBody.Builder()
                       .add("SenderToken","")
                       .add("RecieverName","")
                       .add("Message","")
                     .build();
                Utils utils = new Utils(MainActivity.this);

               Request request = new Request.Builder()
                       .url("http://freecs13.hostei.com/fcm/gcm_send.php")
                       .post(requestbody)
                       .build();

               try{
                   client.newCall(request).execute();
                   Log.d("response_notif","Success");
               }catch (Exception e){
                   e.printStackTrace();

                   Log.d("response_notif",e.toString());
               }
           }
       });

        thread.start();
    }
}
