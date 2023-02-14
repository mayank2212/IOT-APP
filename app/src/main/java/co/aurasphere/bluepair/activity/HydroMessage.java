package co.aurasphere.bluepair.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daasuu.ahp.AnimateHorizontalProgressBar;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class HydroMessage extends AppCompatActivity {

    ImageView backBtn;
    private SeekBar massage2,massage3,massage4;
    int mins1=5,mins2=5,mins3=5,mins4=5;
    TextView start;
    private boolean isOn = false;
    SeekBar massage1;
    ImageView jet1Plus,jet2Plus,jet3Plus,jet4Plus;
    ImageView jet1Minus, jet2Minus, jet3Minus, jet4Minus;
    TextView massage1txt,massage2txt,massage3txt,massage4txt;
    private Boolean isAlreadyOn=false;
    Intent resultIntent=new Intent();

    @Override
    protected void onStop() {
        super.onStop();

//        onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydro_message);
        getSupportActionBar().hide();
        initialiseViews();
        massage1 = findViewById(R.id.animate_progress_bar);
        massage2 = findViewById(R.id.animate_progress_bar2);
        massage3 = findViewById(R.id.animate_progress_bar3);
        massage4 = findViewById(R.id.animate_progress_bar4);
        massage1txt = findViewById(R.id.massage1txt);
        massage2txt = findViewById(R.id.massage2txt);
        massage3txt = findViewById(R.id.massage3txt);
        massage4txt = findViewById(R.id.massage4txt);
        start = findViewById(R.id.start);

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
        massage3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar progress"+i,Toast.LENGTH_LONG).show();
                mins3 = i;
                massage3txt.setText(i+"");
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
        massage4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                Toast.makeText(CustomMassageActivity.this ,"Seek Bar progress"+i,Toast.LENGTH_LONG).show();
                mins4 = i;
                massage4txt.setText(i+"");
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
                try {
                    if(mins1<180) {
                        massage1.setProgress(++mins1);
                    }
                }catch(Exception e){
                    Log.d("TAG", "error in setting jet1 progress"+ e.getLocalizedMessage());
                }
                Log.d("TAG", "Jet 1 is is clicked: plus");
            }
        });
        jet2Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins2<180) {
                    massage2.setProgress(++mins2);
                }
            }
        });
        jet3Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins3<180) {
                    massage3.setProgress(++mins3);
                }
            }
        });
        jet4Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins4<180) {
                    massage4.setProgress(++mins4);
                }
            }
        });jet1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins1>0) {
                    massage1.setProgress(--mins1);
                }
                Log.d("TAG", "Jet 1 is is clicked: minus");
            }
        });jet2Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins2>0) {
                    massage2.setProgress(--mins2);
                }
            }
        });jet3Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins3>0) {
                    massage3.setProgress(--mins3);
                }
            }
        });jet4Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mins4>0) {
                    massage4.setProgress(--mins4);
                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOn){
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    isOn = false;
                    start.setText("START");

                    resultIntent.putExtra("isOn",isOn);
                    setResult(0,resultIntent);
                }
                else{
                    BluetoothOperation.sendCommand(getCommand());
                    isOn = true;
                    start.setText("STOP");
                    resultIntent.putExtra("isOn",isOn);
                    setResult(0,resultIntent);
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
        jet1Plus=findViewById(R.id.jet1plus);
        jet2Plus=findViewById(R.id.jet2plus);
        jet3Plus=findViewById(R.id.jet3plus);
        jet4Plus=findViewById(R.id.jet4plus);
        jet1Minus=findViewById(R.id.jet1minus);
        jet2Minus=findViewById(R.id.jet2minus);
        jet3Minus=findViewById(R.id.jet3minus);
        jet4Minus=findViewById(R.id.jet4minus);
        backBtn=findViewById(R.id.back_btn);
    }

    private String getCommand() {

        Log.d("TAG", "Hydro jet1 time: is "+String.format("%03d", mins1));
        Log.d("TAG", "Hydro jet2 time: is "+String.format("%03d", mins2));
        Log.d("TAG", "Hydro jet3 time: is "+String.format("%03d", mins3));
        Log.d("TAG", "Hydro jet4 time: is "+String.format("%03d", mins4));

        return "#$HYDROSEQH1"+String.format("%03d", mins1)+"H2"+String.format("%03d", mins2)+"H3"+String.format("%03d", mins3)+"H4"+String.format("%03d", mins4)+"$#";
    }


}