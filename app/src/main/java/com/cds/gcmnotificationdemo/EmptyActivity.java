package com.cds.gcmnotificationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cds.gcmnotificationdemo.Fragments.ChatFragment;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_empty,new ChatFragment()).commit();
    }
}
