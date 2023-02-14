package co.aurasphere.bluepair.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CascadeSetOrderActivity extends AppCompatActivity {

    ImageView backBtn;
    private SeekBar massage1,massage2;
    int mins1=5,mins2=5;
    TextView start;
    private boolean isOn = false;
    ImageView jet1Plus,jet2Plus;
    ImageView jet1Minus, jet2Minus;
    private Intent resultIntent=new Intent();
    TextView massage1txt,massage2txt;
    private Boolean isAlreadyOn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cascade_set_order);
        initialiseViews();
        isAlreadyOn=getIntent().getBooleanExtra("isOn",false);
        if(isAlreadyOn){
            start.setText("STOP");
            isOn=true;
        } else {
            start.setText("START");
            isOn=false;
        }
        resultIntent.putExtra("isOn",isOn);
        setResult(0,resultIntent);


        massage1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar progress"+i,Toast.LENGTH_LONG).show();
                mins1 = i;
                massage1txt.setText(i+"");
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
        massage2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar progress"+i,Toast.LENGTH_LONG).show();
                mins2 = i;
                massage2txt.setText(i+"");
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
                massage1.setProgress(++mins1);
            }
        });
        jet2Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                massage2.setProgress(++mins2);
            }
        });
        jet1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                massage1.setProgress(--mins1);
            }
        });jet2Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                massage2.setProgress(--mins2);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOn){
                    isOn = false;
                    start.setText("START");
                    BluetoothOperation.sendCommand("#$CASCADESEQOFF$#");
//                    Toast.makeText(CascadeSetOrderActivity.this, "off command : "+"#$CASCADESEQOFF$#", Toast.LENGTH_SHORT).show();
                    resultIntent.putExtra("isOn",isOn);
                    setResult(2,resultIntent);

                }
                else {
                    isOn = true;
                    start.setText("STOP");
                    BluetoothOperation.sendCommand("#$CASCADESEQH1"+String.format("%03d", mins1)+"H2"+String.format("%03d", mins2)+"$#");
//                    Toast.makeText(CascadeSetOrderActivity.this, "on command : "+"#$CASCA DESEQH1"+String.format("%03d", mins1)+"H2"+String.format("%03d", mins2)+"$#", Toast.LENGTH_SHORT).show();
                    resultIntent.putExtra("isOn",isOn);
                    setResult(2,resultIntent);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    private void initialiseViews() {
        massage1 = findViewById(R.id.animate_progress_bar);
        massage2 = findViewById(R.id.animate_progress_bar2);
        massage1txt = findViewById(R.id.massage1txt);
        massage2txt = findViewById(R.id.massage2txt);
        start = findViewById(R.id.start);
        jet1Plus=findViewById(R.id.jet1plus);
        jet2Plus=findViewById(R.id.jet2plus);
        jet1Minus=findViewById(R.id.jet1minus);
        jet2Minus=findViewById(R.id.jet2minus);
        backBtn=findViewById(R.id.back_btn);
    }

}