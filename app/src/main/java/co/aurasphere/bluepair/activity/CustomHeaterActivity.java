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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CustomHeaterActivity extends AppCompatActivity {

    BroadcastReceiver heaterTimeProgressReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==CustomActivity.HEATER_CUSTOM_MASSAGE_BROADCAST_KEY){
                String[] extractedMinute = Modes.getModes().getHeaterTime().split(" ");
                seekBarHeater.setProgress(Integer.parseInt(extractedMinute[0])/60000);
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvCustomHeaterTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");

                if(currentMillis==0){
                    Toast.makeText(context, "heater is completed activity", Toast.LENGTH_SHORT).show();
                    tvCustomHeaterTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$HEATEROFF$#");
                    custom_massage_start.setText("START");
                    isOn = false;
                    resultIntent.putExtra("isOn", isOn);
                }
            }
        }
    };
    private TextView tvCustomHeaterTimer;
    private SeekBar seekBarHeater;
    private ImageView imgBackBtn;
    private TextView custom_massage_start;
    private int mins = 05;
    private boolean isOn = false;
    private LinearLayout jet1Plus, jet1Minus;
    private long currentMillis;
    Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_heater);
        getSupportActionBar().hide();
        isOn = getIntent().getBooleanExtra("isAlreadyOn", false);

        resultIntent.putExtra("isOn", isOn);
        setResult(17, resultIntent);
        init();


    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(heaterTimeProgressReceiver,new IntentFilter(CustomActivity.HEATER_CUSTOM_MASSAGE_BROADCAST_KEY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(heaterTimeProgressReceiver);
    }

    private void init() {
        tvCustomHeaterTimer = findViewById(R.id.custom_heater_time);
        seekBarHeater = findViewById(R.id.custom_set_order_seek_bar);
        jet1Plus = findViewById(R.id.jet1plus);
        jet1Minus = findViewById(R.id.jet1minus);
        imgBackBtn = findViewById(R.id.custom_heater_back_btn);
        custom_massage_start = findViewById(R.id.custom_massage_start);
        if (isOn) {
            custom_massage_start.setText("STOP");
        } else {
            custom_massage_start.setText("START");
        }

        seekBarHeater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvCustomHeaterTimer.setText(String.valueOf(i) + " Min");
                mins = i;
                tvCustomHeaterTimer.setText(String.valueOf(i) + "\nMin");
                currentMillis = i * 60000;
//                Toast.makeText(CustomHeaterActivity.this, "seek : "+i, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        custom_massage_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent broadcastIntent=new Intent(CustomActivity.HEATER_CUSTOM_MASSAGE_START_STOP);
                String time = String.format("%03d", mins);
                Log.d("TAG", "custom Heater : " + String.format("%03d", mins));


                if (isOn) {
                    BluetoothOperation.sendCommand("#$HEATEROFF$#");
                    custom_massage_start.setText("START");
                    isOn = false;

                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
//                    Toast.makeText(CustomHeaterActivity.this, "in off : " + isOn, Toast.LENGTH_SHORT).show();
                    resultIntent.putExtra("isOn", isOn);
                    setResult(17, resultIntent);
                } else {
                    BluetoothOperation.sendCommand("#$HEATERON" + time + "$#");
                    custom_massage_start.setText("STOP");
                    isOn = true;
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
//                    Toast.makeText(CustomHeaterActivity.this, "in on : " + isOn, Toast.LENGTH_SHORT).show();
                    resultIntent.putExtra("isOn", isOn);
                    Modes.getModes().setHeaterTime(currentMillis + " Min");
                    resultIntent.putExtra("isOn", isOn);
                    setResult(17, resultIntent);
                }
//                onBackPressed();

            }
        });

        jet1Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mins < 60) {
                    seekBarHeater.setProgress(++mins);
                }
            }
        });
        jet1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mins > 0) {
                    seekBarHeater.setProgress(--mins);
                }
            }
        });
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String[] extractedMinute = Modes.getModes().getHeaterTime().split(" ");
        seekBarHeater.setProgress(Integer.parseInt(extractedMinute[0])/60000);
//        Toast.makeText(this, "heater progress"+Integer.parseInt(extractedMinute[0]), Toast.LENGTH_SHORT).show();
        String timing=getIntent().getStringExtra("previous_time");
        tvCustomHeaterTimer.setText(timing+" Min");

    }
}