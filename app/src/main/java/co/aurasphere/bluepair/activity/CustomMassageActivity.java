package co.aurasphere.bluepair.activity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CustomMassageActivity extends AppCompatActivity {
    BroadcastReceiver airBroadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String[] extractedMinute = Modes.getModes().getAirTime().split(" ");
            if(intent.getAction().equals(CustomActivity.AIR_CUSTOM_MASSAGE_BROADCAST_KEY)) {
                String timing = "";
//                Log.d("TAG", "massage type " + Operations.valueOf(intent.getStringExtra(CustomActivity.BROADCAST_MASSAGE_TYPE)));
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
//                Log.e("TAG", "onReceive: air broadcast");
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");
                Log.e("TAG", "onReceive: air broadcast"+currentMillis );
                if(currentMillis==0){
                    tvTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$AIROFF$#");
                    tvStart.setText("START");
                    isOn = false;
                    resultIntent.putExtra("isOn",isOn);
                    resultIntent.putExtra("isTimeChanged",true);
                }
//                Log.e("TAG", "onReceive: hydro broadcast currentmillis : "+ currentMillis);
            }
        }
    };
    BroadcastReceiver waterBroadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(CustomActivity.WATER_CUSTOM_MASSAGE_BROADCAST_KEY)) {
                String[] extractedMinute = Modes.getModes().getWaterTime().split(" ");
                String timing = "";
                Log.e("TAG", "onReceive: water broadcast");
                Log.d("TAG", "massage type " + Operations.valueOf(intent.getStringExtra(CustomActivity.BROADCAST_MASSAGE_TYPE)));
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0]) / 60000);
                currentMillis = Long.parseLong(extractedMinute[0]);
                tvTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");
                if (currentMillis == 0) {
                    tvTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$CASCADEOFF$#");
                    tvStart.setText("START");
                    isOn = false;
                    resultIntent.putExtra("isOn", isOn);
                    resultIntent.putExtra("isTimeChanged", true);
                }
            }
        }
    };

    BroadcastReceiver neckBroadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(CustomActivity.NECK_CUSTOM_MASSAGE_BROADCAST_KEY)){
                String[] extractedMinute = Modes.getModes().getNeckTime().split(" ");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
                currentMillis= Long.parseLong(extractedMinute[0]);
                Log.e("TAG", "onReceive: neck broadcast");
                tvTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");
                if(currentMillis==0){
                    tvTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$NECKOFF$#");
                    tvStart.setText("START");
                    isOn = false;
                    resultIntent.putExtra("isOn",isOn);
                    resultIntent.putExtra("isTimeChanged",true);
                }
            }
        }
    };

    BroadcastReceiver ozoneBroadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(CustomActivity.OZONE_CUSTOM_MASSAGE_BROADCAST_KEY)){
                String[] extractedMinute = Modes.getModes().getOzoneTime().split(" ");
                Log.e("TAG", "onReceive: ozone broadcast");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");
                if(currentMillis==0){
                    tvTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$OZONEOFF$#");
                    tvStart.setText("START");
                    isOn = false;
                    resultIntent.putExtra("isOn",isOn);
                    resultIntent.putExtra("isTimeChanged",true);
                }
            }
        }
    };

    BroadcastReceiver hydroBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CustomActivity.HYDRO_CUSTOM_MASSAGE_BROADCAST_KEY)) {
                String timing = "";
                Log.e("TAG", "onReceive: hydro broadcast");

                String[] extractedMinute = Modes.getModes().getHydroTime().split(" ");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0]) / 60000);
                currentMillis = Long.parseLong(extractedMinute[0]);
                tvTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");
                if (currentMillis == 0) {
                    tvTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$HYDROOFF$#");
                    tvStart.setText("START");
                    isOn = false;
                    resultIntent.putExtra("isOn", isOn);
                }
//                Log.e("TAG", "onReceive: hydro broadcast currentmillis : " + currentMillis);


            }
        }
    };

    private String massageTitle;
    private TextView tvMassageTitle;
    private SeekBar seekBarSelectTime;
    private Operations key;
    private TextView tvTimer;
    private TextView tvStart;
    private ImageView imgBackBtn;
    private String time;
    private int mins = 10;
    private boolean isOn = false;
    Boolean isAlreadyOn;
    private String isStillOn;
    private Intent resultIntent=new Intent();
    private LinearLayout jet1Plus, jet1Minus;
    private long currentMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_massage);
        try {
            getSupportActionBar().hide();
            init();

            setDefaultResults();
            setInitialProgress(key);
            Toast.makeText(this, "the key is " + key, Toast.LENGTH_SHORT).show();

//        isAlreadyOn=getIntent().getBooleanExtra("isOn",false);
//        if(isAlreadyOn){
//            tvStart.setText("STOP");
//            isOn=true;
//        } else {
//            tvStart.setText("START");
//            isOn=false;
//        }
//        resultIntent.putExtra("isOn",isOn);
//        setResult(3,resultIntent);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "the exception is "+e, Toast.LENGTH_LONG).show();
        }

    }

    private void setDefaultResults() {
        resultIntent.putExtra("isTimeChanged",false);
        switch (key){

            case HYDRO:
                if (isOn){
                    resultIntent.putExtra("isOn",isOn);
                    setResult(11,resultIntent);
                }else{
                    resultIntent.putExtra("isOn",isOn);
                    setResult(11,resultIntent);
                }

                break;
            case AIR:
                if (isOn){
                    resultIntent.putExtra("isOn",isOn);
                    setResult(12,resultIntent);
                }else{
                    resultIntent.putExtra("isOn",isOn);
                    setResult(12,resultIntent);
                }
                break;
            case WATER:
                if (isOn){
                    resultIntent.putExtra("isOn",isOn);
                    setResult(13,resultIntent);
                }else{
                    resultIntent.putExtra("isOn",isOn);
                    setResult(13,resultIntent);
                }
                break;
            case NECK:
                if (isOn){
                    resultIntent.putExtra("isOn",isOn);
                    setResult(3,resultIntent);
                }else{
                    resultIntent.putExtra("isOn",isOn);
                    setResult(14,resultIntent);
                }
                break;
            case CHROMA:
                break;
            case OZONE:
                if (isOn){
                    resultIntent.putExtra("isOn",isOn);
                    setResult(16,resultIntent);
                }else{
                    resultIntent.putExtra("isOn",isOn);
                    setResult(16,resultIntent);
                }
                break;
            default:
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent=new Intent();
        intent.putExtra("isOn",isOn);
        setResult(3,intent);
    }

    private void init(){
        massageTitle=getIntent().getStringExtra("title");
        key=(Operations) getIntent().getSerializableExtra("key");
        isOn=getIntent().getBooleanExtra("isAlreadyOn",false);
        Log.e("TAG", "init: isAlreadyOn : "+isOn );
        tvMassageTitle=findViewById(R.id.custom_massage_title);
        tvMassageTitle.setText(massageTitle);
        jet1Plus=findViewById(R.id.jet1plus);
        jet1Minus=findViewById(R.id.jet1minus);


            tvTimer=findViewById(R.id.custom_massage_time);
            time=getIntent().getStringExtra("time");
        Log.e("TAG", "init: time is "+time );
            tvTimer.setText(time);
            tvStart=findViewById(R.id.custom_massage_start);
            imgBackBtn=findViewById(R.id.custom_massage_back_btn);
            imgBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

//            isAlreadyOn=getIntent().getBooleanExtra("isOn",false);
        if(isOn){
            tvStart.setText("STOP");
        } else {
            tvStart.setText("START");
        }

           seekBarSelectTime=(SeekBar)findViewById(R.id.seekBar);
           seekBarSelectTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
              public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar progress"+i,Toast.LENGTH_LONG).show();
                   mins = i;
                   Log.d("TAG", "onProgressChanged: custom massage activity "+i);
                    tvTimer.setText(String.valueOf(i)+"\nMin");
                    currentMillis=i*60000;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar start",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar stop",Toast.LENGTH_LONG).show();

            }
        });


        jet1Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins<60) {
                    seekBarSelectTime.setProgress(++mins);
                }
                Log.d("TAG", "mins ++: "+mins);
            }
        });
        jet1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins>0) {
                    seekBarSelectTime.setProgress(--mins);
                }
                Log.d("TAG", "mins --: "+mins);
            }
        });

           tvStart.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   Intent broadcastIntent=new Intent(CustomActivity.CUSTOM_MASSAGE_START_STOP);
                   //Intent intent=new Intent(CustomMassageActivity.this,CustomActivity.class);
                   //startActivity(intent);
                   setTitleForModes(currentMillis+" Min");
//                   Toast.makeText(CustomMassageActivity.this, "Time set is : "+(currentMillis/60000), Toast.LENGTH_SHORT).show();
                   resultIntent.putExtra("isOn",isOn);
                   setResult(3,resultIntent);
                   String time = String.format("%03d", mins);
                   Log.d("TAG", "custom massage activity: "+time);
                   Log.d("TAG", "time: is "+time);
                   switch (key){

                       case HYDRO:
                           if (isOn){
                               BluetoothOperation.sendCommand("#$HYDROOFF$#");
                               tvStart.setText("START");
                               isOn = false;
                               resultIntent.putExtra("isOn",isOn);

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isTimeChanged",false);
                               setResult(11,resultIntent);
                           }else{
                               BluetoothOperation.sendCommand("#$HYDROON"+time+"$#");
                               tvStart.setText("STOP");
                               isOn = true;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isTimeChange/d",true);
                               resultIntent.putExtra("isOn",isOn);
                               setResult(11,resultIntent);
                           }

                           break;
                       case AIR:
                           if (isOn){
                               BluetoothOperation.sendCommand("#$AIROFF$#");
                               tvStart.setText("START");
                               isOn = false;
                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               resultIntent.putExtra("isTimeChanged",true);
                               setResult(12,resultIntent);
                           }else{
                               BluetoothOperation.sendCommand("#$AIRON"+time+"$#");
                               tvStart.setText("STOP");
                               isOn = true;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               resultIntent.putExtra("isTimeChanged",false);
                               setResult(12,resultIntent);
                           }
                           break;
                       case WATER:
                           if (isOn){
                               BluetoothOperation.sendCommand("#$CASCADEOFF$#");
                               tvStart.setText("START");
                               isOn = false;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               resultIntent.putExtra("isTimeChanged",true);
                               setResult(13,resultIntent);
                           }else{
                               BluetoothOperation.sendCommand("#$CASCADEON"+time+"$#");
                               tvStart.setText("STOP");
                               isOn = true;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               setResult(13,resultIntent);
                           }
                           break;
                       case NECK:
                           if (isOn){
                               BluetoothOperation.sendCommand("#$NECKOFF$#");
                               tvStart.setText("START");
                               isOn = false;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               resultIntent.putExtra("isTimeChanged",true);
                               setResult(3,resultIntent);
                           }else{
                               BluetoothOperation.sendCommand("#$NECKON"+time+"$#");
                               tvStart.setText("STOP");
                               isOn = true;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               setResult(14,resultIntent);
                           }
                           break;
                       case CHROMA:
                           break;
                       case OZONE:
                           if (isOn){
                               BluetoothOperation.sendCommand("#$OZONEOFF$#");
                               tvStart.setText("START");
                               isOn = false;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               resultIntent.putExtra("isTimeChanged",true);
                               setResult(16,resultIntent);
                           }else{
                               BluetoothOperation.sendCommand("#$OZONEON"+time+"$#");
                               tvStart.setText("STOP");
                               isOn = true;

                               broadcastIntent.putExtra("isOn",isOn);
                               broadcastIntent.putExtra("key",key.toString());
                               sendBroadcast(broadcastIntent);

                               resultIntent.putExtra("isOn",isOn);
                               setResult(16,resultIntent);
                           }
                           break;
                       default:
                           break;

                   }
//                   onBackPressed();
               }
           });

    }

    private void setInitialProgress(Operations key){
        String timing=getIntent().getStringExtra("previous_time");
        Toast.makeText(this, "initial time "+timing, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "previous time is "+timing, Toast.LENGTH_SHORT).show();
        String[] extractedMinute;
        switch (key){

            case HYDRO:
               extractedMinute=Modes.getModes().getHydroTime().split(" ");
//               if(isOn)
               seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
//                Toast.makeText(this, "the progress of hydro is "+seekBarSelectTime.getProgress(), Toast.LENGTH_SHORT).show();
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText(Integer.parseInt(extractedMinute[0])/60000+" Min");
                tvTimer.setText(timing+" Min");

                break;
            case AIR:
                extractedMinute=Modes.getModes().getAirTime().split(" ");
                tvTimer.setText(Integer.parseInt(extractedMinute[0])/60000+" Min");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText(timing+" Min");
                break;
            case WATER:
                extractedMinute=Modes.getModes().getWaterTime().split(" ");
//                tvTimer.setText((Integer.parseInt(extractedMinute[0])/60000)+" Min");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText(timing+" Min");
                break;
            case NECK:
                extractedMinute=Modes.getModes().getNeckTime().split(" ");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
//                tvTimer.setText((Integer.parseInt(extractedMinute[0])/60000)+" Min");
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText(timing+" Min");
                break;
            case CHROMA:
                extractedMinute=Modes.getModes().getChromaTime().split(" ");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
//                tvTimer.setText((Integer.parseInt(extractedMinute[0])/60000)+" Min");
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText(timing+" Min");
                break;
            case OZONE:
                extractedMinute=Modes.getModes().getOzoneTime().split(" ");
                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0])/60000);
//                tvTimer.setText((Integer.parseInt(extractedMinute[0])/60000)+" Min");
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvTimer.setText(timing+" Min");
                break;
            default:
                break;

        }
    }
    private void setTitleForModes(String s) {
        switch (key){

            case HYDRO:
                Modes.getModes().setHydroTime(s);
                Log.d("TAG", "setTitleForModes: getHydroTime "+Modes.getModes().getHydroTime());
                break;
            case AIR:
                Modes.getModes().setAirTime(s);
                Log.d("TAG", "setTitleForModes: getAirTime  "+Modes.getModes().getAirTime());
                break;
            case WATER:
                Modes.getModes().setWaterTime(s);
                 Log.d("TAG", "setTitleForModes: getWaterTime  "+Modes.getModes().getWaterTime());
                break;
            case NECK:
                Modes.getModes().setNeckTime(s);
                 Log.d("TAG", "setTitleForModes: getNeckTime  "+Modes.getModes().getNeckTime());
                break;
            case CHROMA:
                Modes.getModes().setChromaTime(s);
                 Log.d("TAG", "setTitleForModes: getChromaTime  "+Modes.getModes().getChromaTime());
                break;
            case OZONE:
                Modes.getModes().setOzoneTime(s);
                 Log.d("TAG", "setTitleForModes: getOzoneTime  "+Modes.getModes().getOzoneTime());
                break;
            default:
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (key){
            case HYDRO:
                registerReceiver(hydroBroadCast,new IntentFilter(CustomActivity.HYDRO_CUSTOM_MASSAGE_BROADCAST_KEY));
                break;
            case AIR:
                registerReceiver(airBroadcast,new IntentFilter(CustomActivity.AIR_CUSTOM_MASSAGE_BROADCAST_KEY));
                break;
            case WATER:
                registerReceiver(waterBroadcast,new IntentFilter(CustomActivity.WATER_CUSTOM_MASSAGE_BROADCAST_KEY));
                break;
            case NECK:
                registerReceiver(neckBroadcast,new IntentFilter(CustomActivity.NECK_CUSTOM_MASSAGE_BROADCAST_KEY));
                break;
            case OZONE:
                registerReceiver(ozoneBroadcast,new IntentFilter(CustomActivity.OZONE_CUSTOM_MASSAGE_BROADCAST_KEY));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        switch (key){

            case HYDRO:
            Toast.makeText(this, "broadcasts are unregister", Toast.LENGTH_SHORT).show();
                unregisterReceiver(hydroBroadCast);
                break;
            case AIR:
                unregisterReceiver(airBroadcast);
                break;
            case WATER:
                unregisterReceiver(waterBroadcast);
                break;
            case NECK:
                unregisterReceiver(neckBroadcast);
                break;
            case CHROMA:
                break;
            case OZONE:
                unregisterReceiver(ozoneBroadcast);
                break;
            default:
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}