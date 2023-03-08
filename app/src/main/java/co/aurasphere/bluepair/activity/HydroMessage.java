package co.aurasphere.bluepair.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ahp.AnimateHorizontalProgressBar;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class HydroMessage extends AppCompatActivity {

    ImageView backBtn;
    private SeekBar massage2,massage3,massage4;
    int mins1=Integer.parseInt(Modes.getModes().getHydroJet1Time());
    int mins2=Integer.parseInt(Modes.getModes().getHydroJet2Time());
    int mins3=Integer.parseInt(Modes.getModes().getHydroJet3Time());
    int mins4= Integer.parseInt(Modes.getModes().getHydroJet4Time());
    TextView start;
    private boolean isOn = false;
    SeekBar massage1;
    ImageView jet1Plus,jet2Plus,jet3Plus,jet4Plus;
    ImageView jet1Minus, jet2Minus, jet3Minus, jet4Minus;
    TextView massage1txt,massage2txt,massage3txt,massage4txt;
    private Boolean isAlreadyOn=false;
    Intent resultIntent=new Intent();

    BroadcastReceiver hydroSequenceJet1Broadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long currentMillis;
            String[] extractedJet1Time = Modes.getModes().getHydroJet1Time().split(" ");
            String[] extractedJet2Time = Modes.getModes().getHydroJet2Time().split(" ");
            String[] extractedJet3Time = Modes.getModes().getHydroJet3Time().split(" ");
            String[] extractedJet4Time = Modes.getModes().getHydroJet4Time().split(" ");

            Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
            Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));
            Long jet3Sec= Long.valueOf((Integer.parseInt(extractedJet3Time[0])));
            Long jet4Sec= Long.valueOf((Integer.parseInt(extractedJet4Time[0])));
            if(intent.getAction().equals(CustomActivity.HYDRO_SEQUENCE_JET1_BROADCAST_KEY)) {

                currentMillis=Long.parseLong(extractedJet1Time[0]);
                massage1txt.setText(((currentMillis))+"");
                massage1.setProgress((int) (currentMillis));
                if(jet1Sec==0 && jet2Sec==0 && jet3Sec==0 && jet4Sec==0){
                    Log.e("TAG", "onReceive: hydro massage Activity broadcast jet1" );
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    start.setText("START");
                    isOn=false;
                    isAlreadyOn=false;
                    Intent broadcastIntent=new Intent(CustomActivity.HYDRO_SEQUENCE_START_STOP);
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
                    resultIntent.putExtra("isOn",isOn);
                }
            }
        }
    };


    BroadcastReceiver hydroSequenceJet2Broadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long currentMillis;
            String[] extractedJet1Time = Modes.getModes().getHydroJet1Time().split(" ");
            String[] extractedJet2Time = Modes.getModes().getHydroJet2Time().split(" ");
            String[] extractedJet3Time = Modes.getModes().getHydroJet3Time().split(" ");
            String[] extractedJet4Time = Modes.getModes().getHydroJet4Time().split(" ");

            Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
            Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));
            Long jet3Sec= Long.valueOf((Integer.parseInt(extractedJet3Time[0])));
            Long jet4Sec= Long.valueOf((Integer.parseInt(extractedJet4Time[0])));
            if(intent.getAction().equals(CustomActivity.HYDRO_SEQUENCE_JET2_BROADCAST_KEY)) {
                currentMillis=Long.parseLong(extractedJet2Time[0]);
                massage2txt.setText(((currentMillis))+"");
                massage2.setProgress((int) (currentMillis));
                if(jet1Sec==0 && jet2Sec==0 && jet3Sec==0 && jet4Sec==0){
                    Log.e("TAG", "onReceive: hydro massage Activity broadcast jet2 jet1:"+jet1Sec );
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    start.setText("START");
                    isOn=false;
                    isAlreadyOn=false;
                    Intent broadcastIntent=new Intent(CustomActivity.HYDRO_SEQUENCE_START_STOP);
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
                    resultIntent.putExtra("isOn",isOn);
                }
            }
        }
    };
    BroadcastReceiver hydroSequenceJet3Broadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long currentMillis;
            String[] extractedJet1Time = Modes.getModes().getHydroJet1Time().split(" ");
            String[] extractedJet2Time = Modes.getModes().getHydroJet2Time().split(" ");
            String[] extractedJet3Time = Modes.getModes().getHydroJet3Time().split(" ");
            String[] extractedJet4Time = Modes.getModes().getHydroJet4Time().split(" ");

            Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
            Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));
            Long jet3Sec= Long.valueOf((Integer.parseInt(extractedJet3Time[0])));
            Long jet4Sec= Long.valueOf((Integer.parseInt(extractedJet4Time[0])));
            if(intent.getAction().equals(CustomActivity.HYDRO_SEQUENCE_JET3_BROADCAST_KEY)) {
                currentMillis=Long.parseLong(extractedJet3Time[0]);
                massage3txt.setText(((currentMillis))+"");
                massage3.setProgress((int) (currentMillis));

                if(jet1Sec==0 && jet2Sec==0 && jet3Sec==0 && jet4Sec==0){
                    Log.e("TAG", "onReceive: hydro massage Activity broadcast jet3 jet1:"+jet1Sec );
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    start.setText("START");
                    isOn=false;
                    isAlreadyOn=false;
                    Intent broadcastIntent=new Intent(CustomActivity.HYDRO_SEQUENCE_START_STOP);
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
                    resultIntent.putExtra("isOn",isOn);
                }
            }
        }
    };
    BroadcastReceiver hydroSequenceJet4Broadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long currentMillis;
            String[] extractedJet1Time = Modes.getModes().getHydroJet1Time().split(" ");
            String[] extractedJet2Time = Modes.getModes().getHydroJet2Time().split(" ");
            String[] extractedJet3Time = Modes.getModes().getHydroJet3Time().split(" ");
            String[] extractedJet4Time = Modes.getModes().getHydroJet4Time().split(" ");

            Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
            Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));
            Long jet3Sec= Long.valueOf((Integer.parseInt(extractedJet3Time[0])));
            Long jet4Sec= Long.valueOf((Integer.parseInt(extractedJet4Time[0])));
            if(intent.getAction().equals(CustomActivity.HYDRO_SEQUENCE_JET4_BROADCAST_KEY)) {
                currentMillis=Long.parseLong(extractedJet4Time[0]);
                massage4txt.setText(((currentMillis))+"");
                massage4.setProgress((int) (currentMillis));

                if(jet1Sec==0 && jet2Sec==0 && jet3Sec==0 && jet4Sec==0){
                    Log.e("TAG", "onReceive: hydro massage Activity broadcast jet4 jet1:"+jet1Sec );
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    start.setText("START");
                    isOn=false;
                    isAlreadyOn=false;
                    Intent broadcastIntent=new Intent(CustomActivity.HYDRO_SEQUENCE_START_STOP);
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
                    resultIntent.putExtra("isOn",isOn);
                }
            }
        }
    };
    /*
    BroadcastReceiver hydroSequenceBroadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long currentMillis;
            String[] extractedJet1Time = Modes.getModes().getHydroJet1Time().split(" ");
            String[] extractedJet2Time = Modes.getModes().getHydroJet2Time().split(" ");
            String[] extractedJet3Time = Modes.getModes().getHydroJet3Time().split(" ");
            String[] extractedJet4Time = Modes.getModes().getHydroJet4Time().split(" ");

            Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
            Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));
            Long jet3Sec= Long.valueOf((Integer.parseInt(extractedJet3Time[0])));
            Long jet4Sec= Long.valueOf((Integer.parseInt(extractedJet4Time[0])));
            if(intent.getAction().equals(CustomActivity.HYDRO_SEQUENCE_BROADCAST_KEY)) {
                Log.e("TAG", "onReceive: hydro massage Activity broadcast" );
                currentMillis=Long.parseLong(extractedJet1Time[0]);
                massage1txt.setText(((currentMillis))+"");
                massage1.setProgress((int) (currentMillis));

                currentMillis=Long.parseLong(extractedJet2Time[0]);
                massage2txt.setText(((currentMillis))+"");
                massage2.setProgress((int) (currentMillis));

                currentMillis=Long.parseLong(extractedJet3Time[0]);
                massage3txt.setText(((currentMillis))+"");
                massage3.setProgress((int) (currentMillis));

                currentMillis=Long.parseLong(extractedJet4Time[0]);
                massage4txt.setText(((currentMillis))+"");
                massage4.setProgress((int) (currentMillis));

                if(jet1Sec==0 && jet2Sec==0 && jet3Sec==0 && jet4Sec==0){
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    start.setText("START");
                    isOn=false;
                    isAlreadyOn=false;
                    Intent broadcastIntent=new Intent(CustomActivity.HYDRO_SEQUENCE_START_STOP);
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
                    resultIntent.putExtra("isOn",isOn);
                }


            }
        }
    };*/

    @Override
    protected void onStart() {
        super.onStart();
//        registerReceiver(hydroSequenceBroadcast,new IntentFilter(CustomActivity.HYDRO_SEQUENCE_BROADCAST_KEY));
        registerReceiver(hydroSequenceJet1Broadcast,new IntentFilter(CustomActivity.HYDRO_SEQUENCE_JET1_BROADCAST_KEY));
        registerReceiver(hydroSequenceJet2Broadcast,new IntentFilter(CustomActivity.HYDRO_SEQUENCE_JET2_BROADCAST_KEY));
        registerReceiver(hydroSequenceJet3Broadcast,new IntentFilter(CustomActivity.HYDRO_SEQUENCE_JET3_BROADCAST_KEY));
        registerReceiver(hydroSequenceJet4Broadcast,new IntentFilter(CustomActivity.HYDRO_SEQUENCE_JET4_BROADCAST_KEY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "hydromassage onPause: " );
//        unregisterReceiver(hydroSequenceBroadcast);
        unregisterReceiver(hydroSequenceJet1Broadcast);
        unregisterReceiver(hydroSequenceJet2Broadcast);
        unregisterReceiver(hydroSequenceJet3Broadcast);
        unregisterReceiver(hydroSequenceJet4Broadcast);
    }

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
        isAlreadyOn=getIntent().getBooleanExtra("isOn",false);
        massage1 = findViewById(R.id.animate_progress_bar);
        massage2 = findViewById(R.id.animate_progress_bar2);
        massage3 = findViewById(R.id.animate_progress_bar3);
        massage4 = findViewById(R.id.animate_progress_bar4);
        massage1txt = findViewById(R.id.massage1txt);
        massage2txt = findViewById(R.id.massage2txt);
        massage3txt = findViewById(R.id.massage3txt);
        massage4txt = findViewById(R.id.massage4txt);
        start = findViewById(R.id.start);
        massage1txt.setText(mins1+"");
        massage2txt.setText(mins2+"");
        massage3txt.setText(mins3+"");
        massage4txt.setText(mins4+"");
        massage1.setProgress(mins1);
        massage2.setProgress(mins2);
        massage3.setProgress(mins3);
        massage4.setProgress(mins4);
        initialiseViews();

//        Toast.makeText(this, ""+isAlreadyOn, Toast.LENGTH_SHORT).show();

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
                Intent broadcastIntent=new Intent(CustomActivity.HYDRO_SEQUENCE_START_STOP);
                if (isOn){
                    BluetoothOperation.sendCommand("#$HYDROSEQOFF$#");
                    isOn = false;
                    start.setText("START");

                    resultIntent.putExtra("isOn",isOn);
                    setResult(0,resultIntent);
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
                }
                else{
                    BluetoothOperation.sendCommand(getCommand());
                    isOn = true;
                    start.setText("STOP");
                    resultIntent.putExtra("isOn",isOn);
                    setResult(0,resultIntent);
                    Log.e("TAG", "onClick: start is clicked mins1 : "+mins1+" mins2 : "+mins2+" mins3 "+ mins3+" mins4 : "+mins4);
                    Modes.getModes().setHydroJet1Time(String.valueOf(mins1));
                    Modes.getModes().setHydroJet2Time(String.valueOf(mins2));
                    Modes.getModes().setHydroJet3Time(String.valueOf(mins3));
                    Modes.getModes().setHydroJet4Time(String.valueOf(mins4));
                    broadcastIntent.putExtra("isOn",isOn);
                    sendBroadcast(broadcastIntent);
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