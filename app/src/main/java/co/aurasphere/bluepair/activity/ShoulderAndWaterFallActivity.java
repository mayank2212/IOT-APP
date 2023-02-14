package co.aurasphere.bluepair.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class ShoulderAndWaterFallActivity extends AppCompatActivity {

    BroadcastReceiver fillBroadcastTimeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String[] extractedFillMinute = Modes.getModes().getCleanFillTime().split(" ");
            String[] extractedDrainMinute = Modes.getModes().getCleanDrainTime().split(" ");
            seekBarShoulder.setProgress(Integer.parseInt(extractedFillMinute[0]));
            mins1=Integer.parseInt(extractedFillMinute[0]);
            tvShoulderTimer.setText(extractedFillMinute[0]);
            //Toast.makeText(this, "", //Toast.LENGTH_SHORT).show();
            currentCleanFillSec = Long.parseLong(extractedFillMinute[0]);
            currentCleanDrainSec = Long.parseLong(extractedDrainMinute[0]);
            if(currentCleanFillSec==0 && currentCleanDrainSec==0){
                isOn = false;
                String stopCommand = getOffCommand();
                BluetoothOperation.sendCommand(stopCommand);
                tvStart.setText("START");
                resultIntent.putExtra("isOn", isOn);
            }
//                //Toast.makeText(this, "the progress of hydro is " + seekBarShoulder.getProgress(), //Toast.LENGTH_SHORT).show();



        }
    };
    BroadcastReceiver drainBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String[] extractedFillMinute = Modes.getModes().getCleanFillTime().split(" ");
            String[] extractedDrainMinute = Modes.getModes().getCleanDrainTime().split(" ");
            seekBarWater.setProgress(Integer.parseInt(extractedDrainMinute[0]));
            mins2=Integer.parseInt(extractedDrainMinute[0]);
            tvSeekBarWater.setText(extractedDrainMinute[0]);
            currentCleanFillSec = Long.parseLong(extractedFillMinute[0]);
            currentCleanDrainSec = Long.parseLong(extractedDrainMinute[0]);
            if(currentCleanFillSec==0 && currentCleanDrainSec==0){
                isOn = false;
                String stopCommand = getOffCommand();
                BluetoothOperation.sendCommand(stopCommand);
                tvStart.setText("START");
                resultIntent.putExtra("isOn", isOn);
            }
        }
    };
    private SeekBar seekBarShoulder;
    private TextView tvShoulderTimer;
    private SeekBar seekBarWater;
    private TextView tvSeekBarWater;
    private TextView tvStart;
    //private TextView tvStop;
    private ImageView imgbackBtn;
    private boolean isOn = false;
    private boolean isAlreadyOn;
    int mins1 = 2, mins2 = 2;
    ImageView waterPlus, waterMinus, shoulderPlus, shoulderMinus;
    private Intent resultIntent = new Intent();
    private String key;
    private long currentCleanDrainSec;
    private long currentCleanFillSec;

    @Override
    protected void onStop() {
        super.onStop();

//        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoulder_and_water_fall);
        getSupportActionBar().hide();

        isAlreadyOn = getIntent().getBooleanExtra("isAlreadyOn", false);
        key = getIntent().getStringExtra("key1");
        //Toast.makeText(this, "key is  "+key, //Toast.LENGTH_SHORT).show();
        initialiseViews();
        if (isAlreadyOn) {
            tvStart.setText("STOP");
            isOn = true;
        } else {
            tvStart.setText("START");
            isOn = false;
        }
        resultIntent.putExtra("isOn", isOn);
//        setResult(0, resultIntent);
        isAlreadyOn = getIntent().getBooleanExtra("isOn", false);
        if (isAlreadyOn) {
            tvStart.setText("STOP");
            isOn = true;
        } else {
            tvStart.setText("START");
            isOn = false;
        }
        resultIntent.putExtra("isOn", isOn);
//        setResult(0, resultIntent);
        setInitialData();
        setDefaultResults();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(fillBroadcastTimeReceiver,new IntentFilter(CustomActivity.CLEANING_FALL_CUSTOM_MASSAGE_BROADCAST_KEY));
        registerReceiver(drainBroadcastReceiver,new IntentFilter(CustomActivity.CLEANING_DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(fillBroadcastTimeReceiver);
        unregisterReceiver(drainBroadcastReceiver);
    }

    private void initialiseViews() {
        ((TextView) (findViewById(R.id.title1))).setText(getIntent().getStringExtra("title1"));
        ((TextView) (findViewById(R.id.title2))).setText(getIntent().getStringExtra("title2"));
        shoulderPlus = findViewById(R.id.jet1plus);
        shoulderMinus = findViewById(R.id.jet1minus);
        waterPlus = findViewById(R.id.jet2plus);
        waterMinus = findViewById(R.id.jet2minus);
        seekBarWater = findViewById(R.id.water_fall_seek_bar);
        seekBarShoulder = (SeekBar) findViewById(R.id.shoulder_seek_bar);

        imgbackBtn = findViewById(R.id.shoulder_and_water_fall);
        tvStart = findViewById(R.id.shoulder_and_water_start);
        //tvStop=findViewById(R.id.shoulder_and_water_stop);
        tvSeekBarWater = findViewById(R.id.water_fall_timer);
//        tvSeekBarWater.setText("10 Min");
        tvShoulderTimer = findViewById(R.id.shoulder_tv);
//        tvShoulderTimer.setText("10 Min");
    }


    private void init() {

        seekBarShoulder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvShoulderTimer.setText(i + "");
                mins1 = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarWater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvSeekBarWater.setText(i + "");
                mins2 = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        waterPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mins2 < 180) {
                    seekBarWater.setProgress(++mins2);
                }
            }
        });
        shoulderPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mins1 < 180) {
                    seekBarShoulder.setProgress(++mins1);
                }
            }
        });
        waterMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mins2 > 0) {
                    seekBarWater.setProgress(--mins2);
                }
            }
        });
        shoulderMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mins1 > 0) {
                    seekBarShoulder.setProgress(--mins1);
                }
            }
        });

        imgbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent broadcastIntent=new Intent(CustomActivity.CLEANING_CUSTOM_MASSAGE_START_STOP);
                if (isOn) {
                    isOn = false;
                    String stopCommand = getOffCommand();
                    BluetoothOperation.sendCommand(stopCommand);
                    tvStart.setText("START");
                    resultIntent.putExtra("isOn", isOn);
                    setResult(3, resultIntent);
                    if(key.equals("FILL")) {
                        setResult(19, resultIntent);
                        broadcastIntent.putExtra("isOn",isOn);
                        sendBroadcast(broadcastIntent);
                    }

                } else {
                    String command = getCommand();
                    BluetoothOperation.sendCommand(command);
                    isOn = true;
                    tvStart.setText("STOP");
                    resultIntent.putExtra("isOn", isOn);
                    setResult(3, resultIntent);
                    //Toast.makeText(ShoulderAndWaterFallActivity.this, "key equal fill : " + ((key).equals("FILL")), //Toast.LENGTH_SHORT).show();
                    if(key.equals("FILL")) {
                        setResult(19, resultIntent);
                        broadcastIntent.putExtra("isOn",isOn);
                        sendBroadcast(broadcastIntent);
                    }
                }
//                onBackPressed();



            }
        });


    }

    private String getCommand() {
        String command = "#$";
        switch (getIntent().getStringExtra("key1")) {
//            case "HYDRO":
//                command = command + "TOGHYDRO"+seekBarShoulder.getProgress()+"AIR"+seekBarWater.getProgress()+"ON" ;
//                break;
//            case "SHOULDER":
//                command = command + "TOGSHD"+seekBarShoulder.getProgress()+"WAT"+seekBarWater.getProgress()+"ON" ;
//                break;
//            case "FILL":
//                command = command + "CLEANINGONFILL"+seekBarShoulder.getProgress()+"DRAIN"+seekBarWater.getProgress() ;
//                break;
            case "HYDRO":
                command = command + "TOGHYDRO" + String.format("%03d", seekBarShoulder.getProgress()) + "AIR" + String.format("%03d", seekBarWater.getProgress()) + "ON";
                break;
            case "SHOULDER":
                command = command + "TOGSHD" + String.format("%03d", seekBarShoulder.getProgress()) + "WAT" + String.format("%03d", seekBarWater.getProgress()) + "ON";
                break;
            case "FILL":
                Modes.getModes().setCleanFillTime(String.valueOf(mins1));
                Modes.getModes().setCleanDrainTime(String.valueOf(mins2));
                command = command + "CLEANINGONFILL" + String.format("%03d", seekBarShoulder.getProgress()) + "DRAIN" + String.format("%03d", seekBarWater.getProgress());
                break;
        }
        command = command + "$#";
        Log.d("TAG", "Shoulder and WaterFall Activity: " + command);
        return command;
    }

    private String getOffCommand() {
        String command = "#$";
        switch (getIntent().getStringExtra("key1")) {
            case "HYDRO":
                command = command + "TOGHYDROAIROFF";
                break;
            case "SHOULDER":
                command = command + "TOGSHDWATOFF";
                break;
            case "FILL":
                command = command + "CLEANINGOFF";
                break;
        }
        command = command + "$#";

        return command;
    }

    private void setInitialData() {
        isAlreadyOn = getIntent().getBooleanExtra("isAlreadyOn", false);
        //Toast.makeText(this, "is already on "+isAlreadyOn, //Toast.LENGTH_SHORT).show();
        if (isAlreadyOn) {
            tvStart.setText("STOP");
            isOn = true;
        } else {
            tvStart.setText("START");
            isOn = false;
        }
        String[] extractedMinute;
        String fillTiming = getIntent().getStringExtra("previous_fill_time");
        String drainTiming = getIntent().getStringExtra("previous_drain_time");
        switch (key) {
            case "FILL":
                extractedMinute = Modes.getModes().getCleanFillTime().split(" ");
                seekBarShoulder.setProgress(Integer.parseInt(extractedMinute[0]));
                mins1=Integer.parseInt(extractedMinute[0]);
                tvShoulderTimer.setText(extractedMinute[0]);
                //Toast.makeText(this, "", //Toast.LENGTH_SHORT).show();
                currentCleanFillSec = Long.parseLong(extractedMinute[0]);
//                //Toast.makeText(this, "the progress of hydro is " + seekBarShoulder.getProgress(), //Toast.LENGTH_SHORT).show();

                extractedMinute = Modes.getModes().getCleanDrainTime().split(" ");
                seekBarWater.setProgress(Integer.parseInt(extractedMinute[0]));
                mins2=Integer.parseInt(extractedMinute[0]);
                tvSeekBarWater.setText(extractedMinute[0]);
                currentCleanDrainSec = Long.parseLong(extractedMinute[0]);

                break;
        }
    }
    private void setDefaultResults() {
        if(key.equals("FILL")) {
            if (isOn) {
                resultIntent.putExtra("isOn", isOn);
                setResult(19, resultIntent);
            } else {
                resultIntent.putExtra("isOn", isOn);
                setResult(19, resultIntent);
            }
        }
    }
}