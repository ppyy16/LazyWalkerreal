package com.example.csiott.lazywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class summpage extends WearableActivity {

    private TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summpage);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }



    //yeets to history if name is not left empty
    public void onClickhist2(View v) {

        EditText routename = (EditText) findViewById(R.id.edit_name);
        String sUsername = routename.getText().toString();
        if(!sUsername.matches("")) {
            Intent gonext = new Intent(this, historyActivity.class);
            startActivity(gonext);

        } else{

            Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_SHORT).show();

        }


    }


}
