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

public class CustomDrainActivity extends AppCompatActivity {


    BroadcastReceiver drainTimeProgressReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==CustomActivity.DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY){
                String[] extractedMinute = Modes.getModes().getDrainTime().split(" ");
                seekBarHeater.setProgress(Integer.parseInt(extractedMinute[0])/60000);
                currentMillis= Long.parseLong(extractedMinute[0]);
                tvCustomHeaterTimer.setText((Integer.parseInt(extractedMinute[0]) + 60000) / 60000 + " Min");

                if(currentMillis==0){
                    tvCustomHeaterTimer.setText("0 Min");
                    BluetoothOperation.sendCommand("#$DRAINOFF$#");
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
    private int mins = 10;
    private boolean isOn = false;
    private LinearLayout jet1Plus, jet1Minus;
    private long currentMillis;
    Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drain);
        getSupportActionBar().hide();
        isOn = getIntent().getBooleanExtra("isAlreadyOn", false);
//         (this, "from custom isOn : " + isOn, Toast.LENGTH_SHORT).show();

        resultIntent.putExtra("isOn", isOn);
        setResult(18, resultIntent);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(drainTimeProgressReceiver,new IntentFilter(CustomActivity.DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(drainTimeProgressReceiver);
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
                tvCustomHeaterTimer.setText(String.valueOf(i) + " Mins");
//                Modes.getModes().setDrainTime(String.valueOf(i) +" Mins");
                mins = i;
                tvCustomHeaterTimer.setText(String.valueOf(i) + "\nMin");
                currentMillis = i * 60000;

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
                Intent broadcastIntent=new Intent(CustomActivity.DRAIN_CUSTOM_MASSAGE_START_STOP);
                String time = String.format("%03d", mins);
                Log.d("TAG", "custom drain : " + String.format("%03d", mins));

                Log.d("TAG", "setTitleForModes: getHydroTime " + Modes.getModes().getHydroTime());
                if (isOn) {
                    BluetoothOperation.sendCommand("#$DRAINOFF$#");
                    custom_massage_start.setText("START");
                    isOn = false;

                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);

                    resultIntent.putExtra("isOn", isOn);
                    setResult(18, resultIntent);
                } else {
                    BluetoothOperation.sendCommand("#$DRAINON" + time + "$#");
                    custom_massage_start.setText("STOP");
                    isOn = true;

                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);

                    Modes.getModes().setDrainTime(currentMillis + " Min");
                    resultIntent.putExtra("isOn", isOn);
                    setResult(18, resultIntent);
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
        String[] extractedMinute = Modes.getModes().getDrainTime().split(" ");
//                seekBarSelectTime.setProgress(Integer.parseInt(extractedMinute[0]));
        currentMillis = Long.parseLong(extractedMinute[0]);
        String timing=getIntent().getStringExtra("previous_time");
        tvCustomHeaterTimer.setText(timing+" Min");

    }
}