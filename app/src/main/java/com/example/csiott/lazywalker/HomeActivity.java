package com.example.csiott.lazywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.Activity;

public class HomeActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();


        //


    }

    public void onClicknew(View v)
    {
        //Toast.makeText(this, "Clicked on Button", Toast.LENGTH_SHORT).show();
        Intent newintent = new Intent(this, NewcalcActivity.class);
        startActivity(newintent);
    }

    public void onClickhistory(View v)
    {
        //Toast.makeText(this, "Clicked on Button 2", Toast.LENGTH_SHORT).show();
        Intent histintent = new Intent(this, historyActivity.class);
        startActivity(histintent);
    }
}
