package co.aurasphere.bluepair.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CustomHeaterTemp extends AppCompatActivity {

    private TextView tvCustomHeaterTimer;
    private SeekBar seekBarHeater;
    private ImageView imgBackBtn;
    private TextView custom_massage_start;
    private int mins = 27;
    private LinearLayout jet1Plus, jet1Minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_heater_temp);
        getSupportActionBar().hide();

        init();
    }

    private void init(){
        tvCustomHeaterTimer=findViewById(R.id.custom_heater_time);
        seekBarHeater=findViewById(R.id.custom_set_order_seek_bar);
        imgBackBtn=findViewById(R.id.custom_heater_back_btn);
        jet1Plus=findViewById(R.id.jet1plus);
        jet1Minus=findViewById(R.id.jet1minus);
        custom_massage_start = findViewById(R.id.custom_massage_start);

        seekBarHeater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvCustomHeaterTimer.setText(String.valueOf(i) +" C");
                mins = i;
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

                String time = String.format("%03d", mins);
                Log.d("TAG", "custom heater  : "+String.format("%03d", mins));

                Modes.getModes().setHeaterTemp(String.format("%03d", mins));
                    BluetoothOperation.sendCommand("#$HEATERTEMP"+time+"$#");


            }
        });
        jet1Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins<42) {
                    seekBarHeater.setProgress(++mins);
                }
            }
        });
        jet1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins>0) {
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

    }
}