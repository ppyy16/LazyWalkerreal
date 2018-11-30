package com.example.csiott.lazywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.ActionChooserView;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.util.Log;

import java.lang.Math;
import java.math.BigDecimal;
import java.util.*;


public class NewcalcActivity extends WearableActivity implements SensorEventListener {

    private TextView mTextView;
    private TextView acctext;


    private Sensor myaccsensor;
    private Sensor countSensor;
    private SensorManager SM;

    boolean activityrunning;

    long startTime;

    private TextView stepcounter;


    private SensorEventListener mSensorListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcalc);

        mTextView = (TextView) findViewById(R.id.text);

        stepcounter = (TextView) findViewById(R.id.stepc);


        // Enables Always-on
        setAmbientEnabled();


        //Create sensor manager, not just initialize

        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelometer sensor

        myaccsensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        countSensor = SM.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);






        //setting the next button to off to turn it back on later
        Button next = (Button) findViewById(R.id.next);

        next.setVisibility(next.GONE);





    }




   @Override
   public void onResume(){
        super.onResume();
        activityrunning = true;
//        if(countSensor != null){
//            SM.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
//        } else{
//            Toast.makeText(this, "count sensor not available", Toast.LENGTH_SHORT);
//        }


   }

   @Override
   public void onPause(){
    super.onPause();
    activityrunning = false;


   }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }




    //rounding bigdecimals
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    BigDecimal result;

    //list of all the gathered Y accelerations for elevation
    List<Double> AccAver = new ArrayList<>();
    List<Float> steps = new ArrayList<>();

    private  int stepscounter = 0;
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            result = round((event.values[1]), 2);
            double d = result.doubleValue();
            AccAver.add(d);

        }
        else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
                stepscounter++;
//                Toast.makeText(this,String.valueOf(stepscounter), Toast.LENGTH_SHORT).show();

        }

    }


    //method for rounding doubles
    public static double rounddouble(double value) {
        int places = 2;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



    //to manage first and second click
    boolean first_click = true;








    //button click controls

    //need to make an array and fill with intent to send to summary page.


    //next button >>> summary
    public void onClicknext(View v) {
        Intent gonext = new Intent(this, summpage.class);
        startActivity(gonext);

    }




    //main click

    public void onClickstartwalk(View v)
    {
        Button j = (Button) v.findViewById(R.id.startnewwalk);

        //Toast.makeText(this, "Clicked on Button 2", Toast.LENGTH_SHORT).show();
        if (first_click == true) {
            j.setText("Stop New Walk");


            //Register SensorListener
            SM.registerListener(this, myaccsensor, 200000000);


            SM.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);


            // Assign TextView
            acctext = (TextView)findViewById(R.id.acctext);
            startTime = System.nanoTime();



            first_click = false;
            boolean second_click = false;
        } else {


            //timer
            long elapseTime = System.nanoTime() - startTime;

            double seconds = (double)elapseTime / 1_000_000_000.0;

            double minutes = seconds/60;

            //measuring elevation
            Double min = Collections.min(AccAver);
            Double max = Collections.max(AccAver);

            double roundeddiff = rounddouble(Math.abs(min - max));


            //setting the texts
            acctext.setText("elevation change: " + roundeddiff + " Time: " + rounddouble(minutes));



            //disable sensor listener
            SM.unregisterListener(this);


            stepcounter.setText(String.valueOf(stepscounter));


            //bring back next button
            Button next = (Button) findViewById(R.id.next);

            next.setVisibility(next.VISIBLE);

            //gray out old button
            j.setEnabled(false);



        }
    }
}
