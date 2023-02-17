package co.aurasphere.bluepair.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CustomHydroMassage extends AppCompatActivity {
    private ImageView imgBackBtn;
    private SeekBar seekBarHydroMassage,seekBarAirMassage;
    private TextView tvHydroMassage,tvAirMassage;
    private TextView jet1TimerText,jet2TimerText,jet3TimerText,jet4TimerText;
    private Button start;
    private boolean isOn = false;
    private boolean isAlreadyOn;
    private Intent resultIntent=new Intent();
    int mins1=5,mins2=5;
    ImageView airPlus,airMinus,hydroPlus,hydroMinus;

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent=new Intent();
        intent.putExtra("isOn",isOn);
        setResult(1,intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_hydro_massage);
        getSupportActionBar().hide();
        isAlreadyOn=getIntent().getBooleanExtra("isOn",false);
        init();
        if(isAlreadyOn){
            start.setText("STOP");
            isOn=true;
        } else {
            start.setText("START");
            isOn=false;
        }
        resultIntent.putExtra("isOn",isOn);
        setResult(0,resultIntent);
    }

    public void  init(){
        imgBackBtn=findViewById(R.id.custom_hydro_message_back_btn);
        start = findViewById(R.id.chkState);
        hydroPlus=findViewById(R.id.jet1plus);
        hydroMinus=findViewById(R.id.jet1minus);
        airPlus=findViewById(R.id.jet2plus);
        airMinus=findViewById(R.id.jet2minus);
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        seekBarAirMassage=findViewById(R.id.air_massage_Seek_bar);
        tvAirMassage=findViewById(R.id.custom_air_massage_timer);
        seekBarAirMassage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAirMassage.setText(i +"");
                mins2 = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        seekBarHydroMassage=findViewById(R.id.hydro_massage_Seek_bar);
        tvHydroMassage=findViewById(R.id.custom_hydro_massage_timer);

        seekBarHydroMassage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvHydroMassage.setText(i +"");
                mins1 = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        airPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins2<180) {
                    seekBarAirMassage.setProgress(++mins2);
                }

            }
        });
        hydroPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins1<180) {
                    seekBarHydroMassage.setProgress(++mins1);
                }
            }
        });
        airMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins2>0) {
                    seekBarAirMassage.setProgress(--mins2);
                }
            }
        });
        hydroMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins1>0) {
                    seekBarHydroMassage.setProgress(--mins1);
                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOn){
                    BluetoothOperation.sendCommand("#$TOGHYDROAIROFF$#");
                    isOn = false;
                    start.setText("START");
                    resultIntent.putExtra("isOn",isOn);
                    setResult(1,resultIntent);

                }
                else {
                    BluetoothOperation.sendCommand("#$TOGHYDRO"+String.format("%03d", mins1)+"AIR"+String.format("%03d", mins2)+"ON$#");
                    isOn = true;
                    start.setText("STOP");
                    resultIntent.putExtra("isOn",isOn);
                    setResult(1,resultIntent);
                }
                Log.d("TAG", ":custom hydro massage : hydro "+String.format("%03d", mins1));
                Log.d("TAG", ":custom hydro massage  :Air "+String.format("%03d", mins2));
            }
        });


    }


}