package co.aurasphere.bluepair.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import co.aurasphere.bluepair.Modes;
import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String CUSTOM_MASSAGE_START_STOP = "custom_massage_start_stop";
    public static final String HEATER_CUSTOM_MASSAGE_START_STOP = "heater_custom_massage_start_stop";
    public static final String DRAIN_CUSTOM_MASSAGE_START_STOP = "drain_custom_massage_start_stop";
    public static final String CLEANING_CUSTOM_MASSAGE_START_STOP = "cleaning_custom_massage_start_stop";
    public static final String HYDRO_SEQUENCE_START_STOP = "HYDRO_SEQUENCE_START_STOP";
    public static final String CUSTOM_HYDRO_SEQUENCE_START_STOP = "CUSTOM_HYDRO_SEQUENCE_START_STOP";

    public static final String HYDRO_CUSTOM_MASSAGE_BROADCAST_KEY = "HYDRO_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String AIR_CUSTOM_MASSAGE_BROADCAST_KEY = "AIR_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String WATER_CUSTOM_MASSAGE_BROADCAST_KEY = "WATER_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String NECK_CUSTOM_MASSAGE_BROADCAST_KEY = "NECK_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String OZONE_CUSTOM_MASSAGE_BROADCAST_KEY = "OZONE_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String HEATER_CUSTOM_MASSAGE_BROADCAST_KEY = "HEATER_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY = "DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String CLEANING_DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY = "CLEANING_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String CLEANING_FALL_CUSTOM_MASSAGE_BROADCAST_KEY = "CLEANING_CUSTOM_MASSAGE_BROADCAST_KEY";
    public static final String HYDRO_SEQUENCE_BROADCAST_KEY = "HYDRO_SEQUENCE_BROADCAST_KEY";
    public static final String CUSTOM_HYDRO_SEQUENCE_BROADCAST_KEY = "CUSTOM_HYDRO_SEQUENCE_BROADCAST_KEY";

    BroadcastReceiver hydroSequenceStartStopReciever=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==HYDRO_SEQUENCE_START_STOP) {
                if(intent.getBooleanExtra("isOn", false)){
                    turnOnHydroSequenceTimers();
                }else{
                    stopHydroSequenceTimers();
                }
            }
        }
    };
    BroadcastReceiver customHydroSequenceStartStopReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==CUSTOM_HYDRO_SEQUENCE_START_STOP) {
                if(intent.getBooleanExtra("isOn", false)){
                    turnOnCustomHydroSequenceTimers();
                }else{
                    stopCustomHydroSequenceTimers();
                }
            }
        }
    };
    BroadcastReceiver startStopCustomMassageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==CUSTOM_MASSAGE_START_STOP){
                switch (Operations.valueOf(intent.getStringExtra("key"))){
                    case HYDRO:
                        handleHydro(intent.getBooleanExtra("isOn",false),true);
                        break;
                    case AIR:
                        handleAir(intent.getBooleanExtra("isOn",false));
                        break;
                    case WATER:
                        handleWater(intent.getBooleanExtra("isOn",false));
                        break;
                    case NECK:
                        handleNeck(intent.getBooleanExtra("isOn",false));
                        break;
                    case OZONE:
                        handleOzone(intent.getBooleanExtra("isOn",false));
                        break;
//                    case 17:
//                        handleHeater(data.getBooleanExtra("isOn",false));
//                        break;
//                    case 18:
//                        handleDrain(data.getBooleanExtra("isOn",false));
//                        break;
//                    case 19:
//                        handleCleaning(data.getBooleanExtra("isOn",false));
//                        break;
                }
            }
        }
    };

    BroadcastReceiver heaterBroadcastStartStop=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction()==HEATER_CUSTOM_MASSAGE_START_STOP) {
                handleHeater(intent.getBooleanExtra("isOn", false));
            }
        }
    };
    BroadcastReceiver drainBroadcastStartStop=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CustomActivity.this,"drain start stop recieved",Toast.LENGTH_SHORT).show();
            if(intent.getAction()==DRAIN_CUSTOM_MASSAGE_START_STOP) {

                handleDrain(intent.getBooleanExtra("isOn", false));
            }
        }
    };

    BroadcastReceiver cleaningBroadcastStartStop=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==CLEANING_CUSTOM_MASSAGE_START_STOP) {
                handleCleaning(intent.getBooleanExtra("isOn", false));
            }
        }
    };

    public static final String CUSTOM_MASSAGE_BROADCAST_KEY = "broadcast_for_custom_massage";
    public static final String BROADCAST_MASSAGE_TYPE = "massage_type";
    ArrayList<Boolean> isOnOff=new ArrayList<>(4);

    private ImageView imgHydroMassageSetting,imgAirMassageSetting,imgWaterFallMassageSetting,imgNeckFallSetting,imgChromaLightSetting,imgOzoneSetting,custom_heater_setting;
    private CheckBox airSwitch,neckSwitch,ozoneSwitch,checkBoxWaterFall,checkBoxHeater,checkBoxDrain,checkBoxCleaning;
    private ImageView imgBackBtn,custom_drain_setting;
    private TextView tvHyrdoTimer,tvAirTimer,tvOzoneTimer,tvWaterFallTImer,tvNeckFallTimer,tvChromaLight;
    private LinearLayout tvHydroSequance;
    private String timer = "10";
    private TextView hydroSequenceOnOff, airMassageToggleOnOff, waterFallSequenceOnOff, neckFallSequenceOnOff;
    private String hydroMassageTime,airMassageTimer,waterMassageTimer,neckMassageTimer,chromaTimer,ozoneTimer,heaterTimer,drainTimer,cleaningTimer;
    private CheckBox checkBoxHydroMassage;
    private TextView tvHydroMassageOnOff,tvAirMassageONOFF,tvWaterFallOnOFF,tvNeckFallOnOff,tvChromaLightONOFF,tvHeaterONOFF,checkBoxDrainOnOff,tvCustomCleaningONOFF,tvOzoneOnOff;
    private TextView tvAirMassageToggle,tvNeckFallToggle,tvSequenceWaterFall;
    private TextView tvCustomHeaterTime,tvCustomDrainTime,tvCustomCleaningTime,tvChromaLightTimer;
    private Button chromaSwitch;
    private CHROMESTATE light;
    TextView cleaningDrainTime;

    ArrayList<CountDownTimer> countDownTimers= new ArrayList<>(10);
    ArrayList<Boolean> isAlreadyOn= new ArrayList<>(9);
    ArrayList<String> timings= new ArrayList<>(10);
    CountDownTimer jet1Timer,jet2Timer,jet3Timer,jet4Timer;
    CountDownTimer customHydroSequence,customAirSequence;



    enum CHROMESTATE{
        ON,PAUSE,OFF
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnOff.add(false);
        isOnOff.add(false);
        isOnOff.add(false);
        isOnOff.add(false);
        CountDownTimer sampleInitTimer=new CountDownTimer(0,60*60*1000) {
            @Override public void onTick(long millisUntilFinished) {}
            @Override public void onFinish() {}
        };
        countDownTimers.addAll(Arrays.asList(sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer,sampleInitTimer));
        for (int i = 0; i < 9; i++) {isAlreadyOn.add(false);}
        for (int i = 0; i < 10; i++) {timings.add("10");}
        setContentView(R.layout.activity_custom);
        getSupportActionBar().hide();
        init();


    }


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(startStopCustomMassageReceiver,new IntentFilter(CUSTOM_MASSAGE_START_STOP));
        registerReceiver(heaterBroadcastStartStop,new IntentFilter(HEATER_CUSTOM_MASSAGE_START_STOP));
        registerReceiver(drainBroadcastStartStop,new IntentFilter(DRAIN_CUSTOM_MASSAGE_START_STOP));
        registerReceiver(cleaningBroadcastStartStop,new IntentFilter(CLEANING_CUSTOM_MASSAGE_START_STOP));
        registerReceiver(hydroSequenceStartStopReciever,new IntentFilter(HYDRO_SEQUENCE_START_STOP));
        registerReceiver(customHydroSequenceStartStopReceiver,new IntentFilter(CUSTOM_HYDRO_SEQUENCE_START_STOP));
    }



    private void updateTimes(){
        tvWaterFallTImer.setText(Modes.getModes().getWaterTime());
        //        tvChromaLight.setText(Modes.getModes().getChromaTime());
        tvNeckFallTimer.setText(Modes.getModes().getNeckTime());
        tvOzoneTimer.setText(Modes.getModes().getOzoneTime());
        tvHyrdoTimer.setText(Modes.getModes().getHydroTime());
        tvCustomHeaterTime.setText(Modes.getModes().getHeaterTime());
        tvCustomCleaningTime.setText(Modes.getModes().getCleaningTime());
        tvAirTimer.setText(Modes.getModes().getAirTime());
        tvCustomDrainTime.setText(Modes.getModes().getDrainTime());
        tvChromaLightTimer.setText(Modes.getModes().getChromaTime());
    }
    private void init(){
        cleaningDrainTime=findViewById(R.id.drain_timer);
        hydroSequenceOnOff=findViewById(R.id.custom_hydro_sequence_tv_on_off);
        airMassageToggleOnOff=findViewById(R.id.custom_air_massage_toggle_on_off);
        waterFallSequenceOnOff=findViewById(R.id.custom_water_fall_sequence_on_off);
        neckFallSequenceOnOff=findViewById(R.id.fall_toggle_on_off);

        light = CHROMESTATE.OFF;
        tvCustomDrainTime=findViewById(R.id.custom_drain_timer);
        tvCustomHeaterTime=findViewById(R.id.custom_heater_time);
        tvCustomCleaningTime=findViewById(R.id.custom_cleaning_time);
        checkBoxHydroMassage=findViewById(R.id.custom_check_box_hydro_massage);
        tvHyrdoTimer=findViewById(R.id.custom_hydro_timer);
        tvHydroSequance=findViewById(R.id.hydroseqlay);
        ozoneSwitch=findViewById(R.id.custom_ozone);
        tvHydroMassageOnOff=findViewById(R.id.custom_hydro_on_off);
        tvOzoneTimer=findViewById(R.id.custom_ozone_timer);
        tvWaterFallTImer=findViewById(R.id.custom_water_fall_timer);

//        timer=tvOzoneTimer.getText().toString();
        tvNeckFallTimer=findViewById(R.id.custom_neck_fall_timer);
//        tvChromaLight=findViewById(R.id.custom_activity_chroma_light_timer);
        tvCustomHeaterTime=findViewById(R.id.custom_heater_timer);
//        tvChromaLight.setText(Modes.getModes().getChromaTime());
        tvAirTimer=findViewById(R.id.custom_air_massage);
        tvAirMassageONOFF=findViewById(R.id.custom_air_massage_on_off);





//        findViewById(R.id.massage_toggle).setOnClickListener(this);
        findViewById(R.id.falltogglelay).setOnClickListener(this);
        findViewById(R.id.cleaning_icon).setOnClickListener(this);
        imgHydroMassageSetting=findViewById(R.id.custom_hydro_massage_setting);
        imgHydroMassageSetting.setOnClickListener(this);
        imgAirMassageSetting=findViewById(R.id.custom_air_massage_setting);
        imgAirMassageSetting.setOnClickListener(this);
        imgWaterFallMassageSetting=findViewById(R.id.custom_water_fall_setting);
        imgWaterFallMassageSetting.setOnClickListener(this);
        imgNeckFallSetting=findViewById(R.id.custom_neck_fall_setting);
        imgNeckFallSetting.setOnClickListener(this);
        imgChromaLightSetting=findViewById(R.id.custom_chroma_fall_setting);
        imgChromaLightSetting.setOnClickListener(this);
        imgOzoneSetting=findViewById(R.id.custom_ozone_setting);
        custom_heater_setting=findViewById(R.id.custom_heater_setting);
        custom_drain_setting=findViewById(R.id.custom_drain_setting);
        imgOzoneSetting.setOnClickListener(this);
        custom_heater_setting.setOnClickListener(this);
        custom_drain_setting.setOnClickListener(this);
        imgBackBtn=findViewById(R.id.cutsom_activity_back_btn);
        tvChromaLightTimer=findViewById(R.id.custom_chroma_light_time);

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        hydroMassageTime=tvHyrdoTimer.getText().toString();
        airMassageTimer=tvAirTimer.getText().toString();
        waterMassageTimer=tvWaterFallTImer.getText().toString();
        neckMassageTimer=tvNeckFallTimer.getText().toString();
        chromaTimer=tvChromaLightTimer.getText().toString();
        drainTimer=tvCustomDrainTime.getText().toString();
        heaterTimer=tvCustomHeaterTime.getText().toString();
        ozoneTimer=tvOzoneTimer.getText().toString();
        cleaningTimer=tvCustomCleaningTime.getText().toString();
        tvOzoneOnOff=findViewById(R.id.custom_ozone_timer_on_off);



//        hyrdroSwitch=findViewById(R.id.hydro_on_off);



        // water fall

        assignInitialTimingWithFormat();
        tvWaterFallOnOFF=findViewById(R.id.custom_water_fall_on_off);
        checkBoxWaterFall=findViewById(R.id.checkbox_water_Fall);
        checkBoxWaterFall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    BluetoothOperation.sendCommand("#$CASCADEON"+String.format("%03d",Integer.parseInt(timer))+"$#");
                    tvWaterFallOnOFF.setText("ON");
                    Log.d("TAG", "custom waterfall: "+String.format("%03d",Integer.parseInt(timer)));

                }else{
                    BluetoothOperation.sendCommand("#$CASCADEOFF$#");
                    tvWaterFallOnOFF.setText("OFF");

                }
                handleWater(b);
            }
        });


        ozoneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tvOzoneOnOff.setText("ON");
                    BluetoothOperation.sendCommand("#$OZONEON"+String.format("%03d",Integer.parseInt(timer))+"$#");
                    Log.d("TAG", "custom ozone: "+String.format("%03d",Integer.parseInt(timer)));
                }else{
                    tvOzoneOnOff.setText("OFF");
                    BluetoothOperation.sendCommand("#$OZONEOFF");

                }
                handleOzone(b);
            }
        });

        checkBoxHydroMassage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    BluetoothOperation.sendCommand("#$HYDROON010$#");
//                    Toast.makeText(CustomActivity.this,"Hydro Massage On "+Modes.getModes().getHydroTime(),Toast.LENGTH_SHORT).show();
                    tvHydroMassageOnOff.setText("ON");
                }else{
                    BluetoothOperation.sendCommand("#$HYDROOFF$#");
                    tvHydroMassageOnOff.setText("OFF");
//                    Toast.makeText(CustomActivity.this,"Hydro Massage Off ",Toast.LENGTH_SHORT).show();

                }
                handleHydro(b,true);
            }
        });



        tvHydroSequance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CustomActivity.this, "OnClick", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CustomActivity.this, HydroMessage.class);
                intent.putExtra("isOn",isOnOff.get(0));
                startActivityForResult(intent,0);
//                startActivity(intent);
            }
        });



        airSwitch=findViewById(R.id.air_massage_on_off);

        airSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    BluetoothOperation.sendCommand("#$AIRON010$#");
                    tvAirMassageONOFF.setText("ON");
                }else{

                    BluetoothOperation.sendCommand("#$AIROFF$#");
                    tvAirMassageONOFF.setText("OFF");
                }
                handleAir(b);
            }
        });



        neckSwitch=findViewById(R.id.neck_fall_switch_on_off);
        tvNeckFallOnOff=findViewById(R.id.custom_neck_fall_on_off);


        neckSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    BluetoothOperation.sendCommand("#$NECKON010$#");
                    tvNeckFallOnOff.setText("ON");

                }else{
                    BluetoothOperation.sendCommand("#$NECKOFF#");
                    tvNeckFallOnOff.setText("OFF");


                }
                handleNeck(b);
            }
        });

        chromaSwitch=findViewById(R.id.chroma_switch_on_off);
        tvChromaLightONOFF=findViewById(R.id.custom_chroma_light_on_off);


        chromaSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (light){
                    case OFF:
                        BluetoothOperation.sendCommand("#$CHROMAON$#");
                        tvChromaLightONOFF.setText("PAUSE");
                        light = CHROMESTATE.PAUSE;
                        handleChroma(true,"ON");
                        break;
                    case PAUSE:
                        BluetoothOperation.sendCommand("#$CHROMAPAUSE$#");
                        tvChromaLightONOFF.setText("OFF");
                        light = CHROMESTATE.ON;
                        handleChroma(false,"PAUSE");
                        break;
                    case ON:
                        BluetoothOperation.sendCommand("#$CHROMAOFF$#");
                        tvChromaLightONOFF.setText("ON");
                        light = CHROMESTATE.OFF;
                        handleChroma(false,"OFF");
                        break;
                }
            }
        });

        // heater massage

        checkBoxHeater=findViewById(R.id.custom_heater_on_off);
        tvHeaterONOFF=findViewById(R.id.custom_heater_timer_on_off);


        checkBoxHeater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    BluetoothOperation.sendCommand("#$HEATERON"+String.format("%03d",Integer.parseInt(timer))+"$#");
                    tvHeaterONOFF.setText("ON");
                    Log.d("TAG", "custom heater: "+String.format("%03d",Integer.parseInt(timer)));
                    //startActivity(new Intent(CustomActivity.this,CustomHeaterActivity.class));
                    //finish();
                }else{
                    BluetoothOperation.sendCommand("#$HEATEROFF$#");
                    tvHeaterONOFF.setText("OFF");
                }
                handleHeater(b);
            }
        });

        TextView custom_heater_temp = findViewById(R.id.custom_heater_temp);
        custom_heater_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomActivity.this,CustomHeaterTemp.class));
                //finish();
            }
        });

        checkBoxDrain=findViewById(R.id.custom_check_box_drain);
        checkBoxDrainOnOff=findViewById(R.id.custom_drain_timer_on_off);



        checkBoxDrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    checkBoxDrainOnOff.setText("ON");
                    BluetoothOperation.sendCommand(" #$DRAINON"+String.format("%03d",Integer.parseInt(timer))+"$#");
                    Log.d("TAG", "custom drain: "+String.format("%03d",Integer.parseInt(timer)));
                }else{
                    checkBoxDrainOnOff.setText("OFF");
                    BluetoothOperation.sendCommand("#$DRAINOFF$#");
                }
                handleDrain(b);
            }
        });


        checkBoxCleaning=findViewById(R.id.custom_check_box_cleaning);
        tvCustomCleaningONOFF=findViewById(R.id.checkbox_cleaning_on_off);

        checkBoxCleaning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tvCustomCleaningONOFF.setText("ON");
//                    BluetoothOperation.sendCommand("");
//                    startActivity(new Intent(CustomActivity.this,));
                    handleCleaning(b);

                }else {
                    tvCustomCleaningONOFF.setText("OFF");
                    handleCleaning(b);
                    //BluetoothOperation.sendCommand("#$CLEANINGOFF$#");
                }
            }
        });

        tvAirMassageToggle=findViewById(R.id.custom_air_massage_toggle);

        (findViewById(R.id.airtogglelay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomActivity.this,CustomHydroMassage.class);
                intent.putExtra("isOn",isOnOff.get(1));
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });

        /*tvAirMassageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomActivity.this,CustomHydroMassage.class);
                startActivity(intent);
                //finish();
            }
        });*/

        tvSequenceWaterFall=findViewById(R.id.custom_water_fall_sequence);
        (findViewById(R.id.waterfalllay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomActivity.this,CascadeSetOrderActivity.class);
                intent.putExtra("key1","SHOULDER");
                intent.putExtra("isOn",isOnOff.get(2));
//                fall.putExtra("title1","Shoulder Fall");
//                fall.putExtra("title2","Water Fall");
//                fall.putExtra("time",ozoneTimer);
////                    fall.putExtra("key",Operations.NECK);
//                fall.putExtra("key1",Operations.SHOULDER.toString());
//                fall.putExtra("key2",Operations.WATER.toString());
                startActivityForResult(intent,2);
//                startActivity(intent);
            }
        });
        /*tvSequenceWaterFall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomActivity.this,ShoulderAndWaterFallActivity.class);
                intent.putExtra("key1","SHOULDER");
                startActivity(intent);
                            }
        });*/
    }

    private void assignInitialTimingWithFormat() {
//        Long airMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        String[] extractedMinute = Modes.getModes().getNeckTime().split(" ");
        Long millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvNeckFallTimer.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getOzoneTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvOzoneTimer.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getHydroTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvHyrdoTimer.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getHeaterTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvCustomHeaterTime.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getCleaningTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvCustomCleaningTime.setText("2 Sec");
        cleaningDrainTime.setText("2 Sec");

        extractedMinute=Modes.getModes().getWaterTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvWaterFallTImer.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getAirTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvAirTimer.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getDrainTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvCustomDrainTime.setText(((millis)/60000)+" Min");

        extractedMinute=Modes.getModes().getChromaTime().split(" ");
        millis= Long.valueOf((Integer.parseInt(extractedMinute[0])));
        tvChromaLightTimer.setText(((millis)/60000)+" Min");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.custom_hydro_massage_setting:
                Intent hydro_message=new Intent(CustomActivity.this,CustomMassageActivity.class);
                hydro_message.putExtra("title","Hydro Massage");
                hydro_message.putExtra("key",Operations.HYDRO);
                hydro_message.putExtra("time",hydroMassageTime);
                hydro_message.putExtra("image",R.drawable.hydro_massage);
                hydro_message.putExtra("previous_time",timings.get(0));
                hydro_message.putExtra("isAlreadyOn",isAlreadyOn.get(0));
                Log.d("TAG", "onClick: isAlreadyOn"+isAlreadyOn.get(0));
                startActivityForResult(hydro_message,11);
                break;
            case R.id.custom_air_massage_setting:
                Intent airMessage=new Intent(CustomActivity.this,CustomMassageActivity.class);
                airMessage.putExtra("title","Air Massage");
                airMessage.putExtra("time",airMassageTimer);
                airMessage.putExtra("previous_time",timings.get(1));

                airMessage.putExtra("key",Operations.AIR);
                airMessage.putExtra("isAlreadyOn",isAlreadyOn.get(1));

                startActivityForResult(airMessage,12);
                break;
            case R.id.custom_water_fall_setting:
                Intent waterFall=new Intent(CustomActivity.this,CustomMassageActivity.class);
                waterFall.putExtra("title","Water Fall");
                waterFall.putExtra("time",waterMassageTimer);
                waterFall.putExtra("key",Operations.WATER);
                waterFall.putExtra("previous_time",timings.get(2));
                waterFall.putExtra("isAlreadyOn",isAlreadyOn.get(2));

                startActivityForResult(waterFall,13);
                break;

            case R.id.custom_neck_fall_setting:
                Intent neckFall=new Intent(CustomActivity.this,CustomMassageActivity.class);
                neckFall.putExtra("title","Neck Fall");
                neckFall.putExtra("time",neckMassageTimer);
                neckFall.putExtra("key",Operations.NECK);
                neckFall.putExtra("previous_time",timings.get(3));
                neckFall.putExtra("isAlreadyOn",isAlreadyOn.get(3));
                startActivityForResult(neckFall,14);
                break;
//            case R.id.custom_chroma_fall_setting:
//                Intent chromaFall=new Intent(CustomActivity.this,CustomMassageActivity.class);
//                chromaFall.putExtra("title","Chroma Fall");
//                chromaFall.putExtra("key",Operations.CHROMA);
//                chromaFall.putExtra("time",chromaTimer);
//                startActivity(chromaFall);
////                finish();
//                break;

            case R.id.custom_ozone_setting:
                Intent ozone=new Intent(CustomActivity.this,CustomMassageActivity.class);
                ozone.putExtra("title","Ozone");
                ozone.putExtra("key",Operations.OZONE);
                ozone.putExtra("time",ozoneTimer);
                ozone.putExtra("previous_time",timings.get(5));
                ozone.putExtra("isAlreadyOn",isAlreadyOn.get(5));
                startActivityForResult(ozone,16);
//                finish();
                break;

            case R.id.custom_heater_setting:
                Intent heater=new Intent(CustomActivity.this,CustomHeaterActivity.class);
                heater.putExtra("isAlreadyOn",isAlreadyOn.get(6));
                heater.putExtra("previous_time",timings.get(6));
                startActivityForResult(heater,17);
                break;

            case R.id.custom_drain_setting:
                Intent drain=new Intent(CustomActivity.this,CustomDrainActivity.class);
                drain.putExtra("isAlreadyOn",isAlreadyOn.get(7));
                drain.putExtra("previous_time",timings.get(7));
                startActivityForResult(drain,18);
                break;
            case R.id.falltogglelay:
                Intent fall = new Intent(CustomActivity.this,ShoulderAndWaterFallActivity.class);
//                    fall.putExtra("title","Shoulder Fall");
                    fall.putExtra("title1","Shoulder Fall");
                    fall.putExtra("title2","Water Fall");
                    fall.putExtra("time",ozoneTimer);
//                    fall.putExtra("key",Operations.NECK);
                    fall.putExtra("key1",Operations.SHOULDER.toString());
                    fall.putExtra("key2",Operations.WATER.toString());
//                  startActivity(fall);
                     fall.putExtra("isOn",isOnOff.get(3));
                  startActivityForResult(fall,3);
//                finish();
                break;

                case R.id.cleaning_icon:
                    Intent intent=new Intent(CustomActivity.this,ShoulderAndWaterFallActivity.class);
                    intent.putExtra("title1","Fill");
                    intent.putExtra("title2","Drain");
                    intent.putExtra("key1","FILL");
                    intent.putExtra("isAlreadyOn",isAlreadyOn.get(8));
//                    Toast.makeText(this, "is already on in custom "+isAlreadyOn.get(8), Toast.LENGTH_SHORT).show();
                    intent.putExtra("previous_fill_time",timings.get(8));
                    intent.putExtra("previous_fill_time",timings.get(9));
                    startActivityForResult(intent,19);
                //finish();
                break;

//            case R.id.custom_hydro_sequence_tv:
//
//                Intent intent=new Intent(CustomActivity.this,ShoulderAndWaterFallActivity.class);
//                startActivity(intent);
//                break;
//
//            case R.id.custom_air_massage_toggle:
//                Intent toggleAirMassage=new Intent(CustomActivity.this,CustomHydroMassage.class);
////                toggleAirMassage.putExtra("time",);
//                startActivity(toggleAirMassage);
//                break;
//
            default:

                break;
//

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String[] extractedMinute;
//        if(data!=null) {
            Boolean isOn = data.getBooleanExtra("isOn", false);
//        }
//        Toast.makeText(this, "result code is  "+ resultCode, Toast.LENGTH_SHORT).show();
        switch (resultCode){
            case 0 :
                isOnOff.set(0, isOn );
                if(isOn) hydroSequenceOnOff.setText("ON"); else hydroSequenceOnOff.setText("OFF");
                break;
            case 1:
                isOn = data.getBooleanExtra("isOn", false);
                isOnOff.set(1, isOn );
                if(isOn) airMassageToggleOnOff.setText("ON"); else airMassageToggleOnOff.setText("OFF");
                break;
            case 2:
                isOn = data.getBooleanExtra("isOn", false);
                isOnOff.set(2, isOn );
                if(isOn) waterFallSequenceOnOff.setText("ON"); else waterFallSequenceOnOff.setText("OFF");
                break;
            case 3:
                isOn = data.getBooleanExtra("isOn", false);
                isOnOff.set(3, isOn );
                if(isOn) neckFallSequenceOnOff.setText("ON"); else neckFallSequenceOnOff.setText("OFF");
                break;
            case 11:
                handleHydro(data.getBooleanExtra("isOn",false),data.getBooleanExtra("isTimeChanged",false));
                break;
            case 12:
                handleAir(data.getBooleanExtra("isOn",false));
                break;
            case 13:
                handleWater(data.getBooleanExtra("isOn",false));
                break;
            case 14:
                handleNeck(data.getBooleanExtra("isOn",false));
                break;
            case 16:
                handleOzone(data.getBooleanExtra("isOn",false));
                break;
            case 17:
                handleHeater(data.getBooleanExtra("isOn",false));
                break;
            case 18:
                handleDrain(data.getBooleanExtra("isOn",false));
                break;
            case 19:
                handleCleaning(data.getBooleanExtra("isOn",false));
                break;

//            case 19:
//                extractedMinute = Modes.getModes().getCleaningTime().split(" ");
//                Long cleaningMillis = Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
//
//                if(data.getBooleanExtra("isStarted",false)){
//                    CountDownTimer cleaningTimer=new CountDownTimer(cleaningMillis,1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            tvCustomCleaningTime.setText((millisUntilFinished/1000)+" Min");
//                            Modes.getModes().setCleaningTime((millisUntilFinished)+" Min");
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            tvCustomCleaningTime.setText("0 Min");
//                            Modes.getModes().setCleaningTime("0 Min");
//                        }
//                    };
//                    countDownTimers.set(8,cleaningTimer);
//                    countDownTimers.get(8).start();
//                }else{
//                    countDownTimers.get(8).cancel();
//                    Toast.makeText(this, "cleaning is paused", Toast.LENGTH_SHORT).show();
//                }
//                break;


        }
    }




    @Override
    protected void onRestart() {
        super.onRestart();
//        updateTimes();
    }
    private void handleCleaning(boolean isStarted) {
//        Toast.makeText(this, "in clean custom", Toast.LENGTH_SHORT).show();
        String[] extractedFillMinute = Modes.getModes().getCleanFillTime().split(" ");
        String[] extractedDrainMinute = Modes.getModes().getCleanDrainTime().split(" ");
//        Toast.makeText(this, "timing is : "+Modes.getModes().getHydroTime(), Toast.LENGTH_SHORT).show();
//        Long hydroMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long fallSeconds= Long.valueOf((Integer.parseInt(extractedFillMinute[0])));
        Long drainSeconds= Long.valueOf((Integer.parseInt(extractedDrainMinute[0])));

        if(isStarted){
            countDownTimers.get(8).cancel();
            countDownTimers.get(9).cancel();
//            Toast.makeText(this, "in hydro if", Toast.LENGTH_SHORT).show();
            CountDownTimer fallTimer=new CountDownTimer(fallSeconds*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvCustomCleaningTime.setText(((millisUntilFinished)/1000)+" Sec");
                    Modes.getModes().setCleanFillTime(String.valueOf(millisUntilFinished/1000));
                    tvCustomCleaningONOFF.setText("ON");
                    timings.set(8, String.valueOf(millisUntilFinished/1000));
                    isAlreadyOn.set(8,true);
                    Intent cleaningFallBroadCastIntent=new Intent(CLEANING_FALL_CUSTOM_MASSAGE_BROADCAST_KEY);
                    sendBroadcast(cleaningFallBroadCastIntent);

                }

                @Override
                public void onFinish() {
                    tvCustomCleaningTime.setText("0 Sec");
                    Modes.getModes().setCleanFillTime("0");
                    isAlreadyOn.set(8,false);
                    tvCustomCleaningONOFF.setText("OFF");
                    timings.set(8, String.valueOf(0));
                    Intent cleaningFallBroadCastIntent=new Intent(CLEANING_FALL_CUSTOM_MASSAGE_BROADCAST_KEY);
                    sendBroadcast(cleaningFallBroadCastIntent);
                    if(fallSeconds==0 && drainSeconds==0){
                        String stopCommand="#$CLEANINGOFF$#";
                        BluetoothOperation.sendCommand(stopCommand);
                    }
                }
            };
            countDownTimers.set(8,fallTimer);
            countDownTimers.get(8).start();
            CountDownTimer drainTimer=new CountDownTimer(drainSeconds*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    cleaningDrainTime.setText(((millisUntilFinished)/1000)+" Sec");
                    Modes.getModes().setCleanDrainTime(String.valueOf(millisUntilFinished/1000));
                    tvCustomCleaningONOFF.setText("ON");
                    timings.set(9, String.valueOf(millisUntilFinished/1000));
                    isAlreadyOn.set(8,true);
                    Intent cleaningDrainBroadCastIntent=new Intent(CLEANING_DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY);
                    sendBroadcast(cleaningDrainBroadCastIntent);
                }

                @Override
                public void onFinish() {
                    cleaningDrainTime.setText("0 Sec");
                    Modes.getModes().setCleanDrainTime("0 Sec");
                    isAlreadyOn.set(8,false);
                    tvCustomCleaningONOFF.setText("OFF");
                    timings.set(9, String.valueOf(0));
                    Intent cleaningDrainBroadCastIntent=new Intent(CLEANING_DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY);
                    sendBroadcast(cleaningDrainBroadCastIntent);
                    if(fallSeconds==0 && drainSeconds==0){
                        String stopCommand="#$CLEANINGOFF$#";
                        BluetoothOperation.sendCommand(stopCommand);
                    }
                }
            };
            countDownTimers.set(8,fallTimer);
            countDownTimers.set(9,drainTimer);
            countDownTimers.get(8).start();
            countDownTimers.get(9).start();


        }else{
//            Toast.makeText(this, "in hydro else", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "WaterFall is paused", Toast.LENGTH_SHORT).show();
            countDownTimers.get(8).cancel();
            countDownTimers.get(9).cancel();
            isAlreadyOn.set(8,false);
            tvCustomCleaningONOFF.setText("OFF");
            String stopCommand="#$CLEANINGOFF$#";
            BluetoothOperation.sendCommand(stopCommand);
        }
    }

    private void handleHydro(boolean isStarted,boolean isChanged) {
        String[] extractedMinute = Modes.getModes().getHydroTime().split(" ");
//        Toast.makeText(this, "timing is : "+Modes.getModes().getHydroTime(), Toast.LENGTH_SHORT).show();
//        Long hydroMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long hydroMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(0).cancel();
//            Toast.makeText(this, "in hydro if", Toast.LENGTH_SHORT).show();
            CountDownTimer hydroTimer=new CountDownTimer(hydroMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvHyrdoTimer.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setHydroTime((millisUntilFinished)+" Min");
                    tvHydroMassageOnOff.setText("ON");
                    timings.set(0, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Intent hydroBroadCastIntent=new Intent(HYDRO_CUSTOM_MASSAGE_BROADCAST_KEY);
                    hydroBroadCastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.HYDRO.toString());
                    sendBroadcast(hydroBroadCastIntent);
                }

                @Override
                public void onFinish() {
                    tvHyrdoTimer.setText("0 Min");
                    Modes.getModes().setHydroTime("0 Min");
                    isAlreadyOn.set(0,false);
                    tvHydroMassageOnOff.setText("OFF");
                    timings.set(0, String.valueOf(0));
                    Intent hydroBroadCastIntent=new Intent(HYDRO_CUSTOM_MASSAGE_BROADCAST_KEY);
                    hydroBroadCastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.HYDRO.toString());
                    sendBroadcast(hydroBroadCastIntent);
                    BluetoothOperation.sendCommand("#$HYDROOFF$#");

                }
            };
            countDownTimers.set(0,hydroTimer);
            countDownTimers.get(0).start();
            isAlreadyOn.set(0,true);
        }else{
//            Toast.makeText(this, "in hydro else", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "WaterFall is paused", Toast.LENGTH_SHORT).show();
            countDownTimers.get(0).cancel();
            isAlreadyOn.set(0,false);
            tvHydroMassageOnOff.setText("OFF");
            BluetoothOperation.sendCommand("#$HYDROOFF$#");
        }
    }
    private void handleAir(boolean isStarted) {
        String[] extractedMinute = Modes.getModes().getAirTime().split(" ");
//        Long airMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long airMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(1).cancel();
            CountDownTimer airTimer=new CountDownTimer(airMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvAirTimer.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setAirTime((millisUntilFinished)+" Min");
                    tvAirMassageONOFF.setText("ON");
                    timings.set(1, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Intent airBroadcastIntent=new Intent(AIR_CUSTOM_MASSAGE_BROADCAST_KEY);
                    airBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.AIR.toString());
                    sendBroadcast(airBroadcastIntent);
                }

                @Override
                public void onFinish() {
                    tvAirTimer.setText("0 Min");
                    Modes.getModes().setAirTime("0 Min");
                    isAlreadyOn.set(1,false);
                    tvAirMassageONOFF.setText("OFF");
                    timings.set(1, String.valueOf("0"));
                    Intent airBroadcastIntent=new Intent(AIR_CUSTOM_MASSAGE_BROADCAST_KEY);
                    airBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.AIR.toString());
                    sendBroadcast(airBroadcastIntent);
                    BluetoothOperation.sendCommand("#$AIROFF$#");
                }
            };
            countDownTimers.set(1,airTimer);
            countDownTimers.get(1).start();
            isAlreadyOn.set(1,true);
        }else{
            countDownTimers.get(1).cancel();
            isAlreadyOn.set(1,false);
//            Toast.makeText(this, "Air Massage is paused", Toast.LENGTH_SHORT).show();
            tvAirMassageONOFF.setText("OFF");
            BluetoothOperation.sendCommand("#$AIROFF$#");
        }
    }


    private void handleWater(Boolean isStarted){
        String[] extractedMinute = Modes.getModes().getWaterTime().split(" ");
//        Long waterMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long waterMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(2).cancel();
            CountDownTimer waterTimer=new CountDownTimer(waterMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvWaterFallTImer.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setWaterTime((millisUntilFinished)+" Min");
                    tvWaterFallOnOFF.setText("ON");
                    timings.set(2, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Intent waterBroadcastIntent=new Intent(WATER_CUSTOM_MASSAGE_BROADCAST_KEY);
                    waterBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.WATER.toString());
                    sendBroadcast(waterBroadcastIntent);
                }

                @Override
                public void onFinish() {
                    tvWaterFallTImer.setText("0 Min");
                    Modes.getModes().setWaterTime("0 Min");
                    isAlreadyOn.set(2,false);
                    tvWaterFallOnOFF.setText("OFF");
                    timings.set(2, String.valueOf("0"));
                    Intent waterBroadcastIntent=new Intent(WATER_CUSTOM_MASSAGE_BROADCAST_KEY);
                    waterBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.WATER.toString());
                    sendBroadcast(waterBroadcastIntent);
                    BluetoothOperation.sendCommand("#$CASCADEOFF$#");
                }
            };
            countDownTimers.set(2,waterTimer);
            countDownTimers.get(2).start();
            isAlreadyOn.set(2,true);
        }else{
//            Toast.makeText(this, "WaterFall is paused", Toast.LENGTH_SHORT).show();
            countDownTimers.get(2).cancel();
            isAlreadyOn.set(2,false);
            tvWaterFallOnOFF.setText("OFF");
            BluetoothOperation.sendCommand("#$CASCADEOFF$#");
        }
    }
    private void handleNeck(Boolean isStarted){
        String[] extractedMinute = Modes.getModes().getNeckTime().split(" ");
//        Long neckMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long neckMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(3).cancel();
            CountDownTimer neckTimer=new CountDownTimer(neckMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvNeckFallTimer.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setNeckTime((millisUntilFinished)+" Min");
                    tvNeckFallOnOff.setText("ON");
                    timings.set(3, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Intent neckBroadcastIntent=new Intent(NECK_CUSTOM_MASSAGE_BROADCAST_KEY);
                    neckBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.NECK.toString());
                    sendBroadcast(neckBroadcastIntent);
                }

                @Override
                public void onFinish() {
                    tvNeckFallTimer.setText("0 Min");
                    Modes.getModes().setNeckTime("0 Min");
                    isAlreadyOn.set(3,false);
                    tvNeckFallOnOff.setText("OFF");
                    timings.set(3, String.valueOf("0"));
                    Intent neckBroadcastIntent=new Intent(NECK_CUSTOM_MASSAGE_BROADCAST_KEY);
                    neckBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.NECK.toString());
                    sendBroadcast(neckBroadcastIntent);
                    BluetoothOperation.sendCommand("#$NECKOFF$#");
                }
            };
            countDownTimers.set(3,neckTimer);
            countDownTimers.get(3).start();
            isAlreadyOn.set(3,true);
        }else{
            countDownTimers.get(3).cancel();
            isAlreadyOn.set(3,false);
//            Toast.makeText(this, "Neck Fall is paused", Toast.LENGTH_SHORT).show();
            tvNeckFallOnOff.setText("OFF");
            BluetoothOperation.sendCommand("#$NECKOFF$#");
        }
    }
    private void handleOzone(Boolean isStarted){
        String[] extractedMinute = Modes.getModes().getOzoneTime().split(" ");
//        Long ozoneMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long ozoneMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(5).cancel();
            CountDownTimer ozoneTimer=new CountDownTimer(ozoneMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvOzoneTimer.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setOzoneTime((millisUntilFinished)+" Min");
                    tvOzoneOnOff.setText("ON");
                    timings.set(5, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Intent ozoneBroadcastIntent=new Intent(OZONE_CUSTOM_MASSAGE_BROADCAST_KEY);
                    ozoneBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.OZONE.toString());
                    sendBroadcast(ozoneBroadcastIntent);
                }

                @Override
                public void onFinish() {
                    tvOzoneTimer.setText("0 Min");
                    Modes.getModes().setOzoneTime("0 Min");
                    isAlreadyOn.set(5,false);
                    tvOzoneOnOff.setText("OFF");
                    timings.set(5, String.valueOf("0"));
                    Intent ozoneBroadcastIntent=new Intent(OZONE_CUSTOM_MASSAGE_BROADCAST_KEY);
                    ozoneBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.OZONE.toString());
                    sendBroadcast(ozoneBroadcastIntent);
                    BluetoothOperation.sendCommand("#$OZONEOFF$#");
                }
            };
            countDownTimers.set(5,ozoneTimer);
            countDownTimers.get(5).start();
            isAlreadyOn.set(5,true);
        }else{
            countDownTimers.get(5).cancel();
            isAlreadyOn.set(5,false);
//            Toast.makeText(this, "Ozone is paused", Toast.LENGTH_SHORT).show();
            tvOzoneOnOff.setText("OFF");
            BluetoothOperation.sendCommand("#$OZONEOFF$#");
        }
    }
    private void handleHeater(Boolean isStarted){
        String[] extractedMinute = Modes.getModes().getHeaterTime().split(" ");
//        Long heaterMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long heaterMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

//        Toast.makeText(this, "heater is : "+isStarted, Toast.LENGTH_SHORT).show();
        if(isStarted){
            countDownTimers.get(6).cancel();
            CountDownTimer heaterTimer=new CountDownTimer(heaterMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvCustomHeaterTime.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setHeaterTime((millisUntilFinished)+" Min");
                    tvHeaterONOFF.setText("ON");
                    timings.set(6, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Intent heaterBroadcastIntent=new Intent(HEATER_CUSTOM_MASSAGE_BROADCAST_KEY);
                    heaterBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.HEATER.toString());
                    sendBroadcast(heaterBroadcastIntent);
                }

                @Override
                public void onFinish() {
                    tvCustomHeaterTime.setText("0 Min");
                    Modes.getModes().setHeaterTime("0 Min");
                    isAlreadyOn.set(6,false);
                    tvHeaterONOFF.setText("OFF");
                    timings.set(6, String.valueOf("0"));
                    Intent heaterBroadcastIntent=new Intent(HEATER_CUSTOM_MASSAGE_BROADCAST_KEY);
                    heaterBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.HEATER.toString());
                    sendBroadcast(heaterBroadcastIntent);
                    BluetoothOperation.sendCommand("#$HEATEROFF$#");
                }
            };
            countDownTimers.set(6,heaterTimer);
            countDownTimers.get(6).start();
            isAlreadyOn.set(6,true);
        }else{
            countDownTimers.get(6).cancel();
            isAlreadyOn.set(6,false);
//            Toast.makeText(this, "heater is paused", Toast.LENGTH_SHORT).show();
            tvHeaterONOFF.setText("OFF");
            BluetoothOperation.sendCommand("#$HEATEROFF$#");
        }
    }
    private void handleChroma(Boolean isStarted, String switchState){
        String[] extractedMinute = Modes.getModes().getChromaTime().split(" ");
//        Long drainMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long chromaMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(4).cancel();
            CountDownTimer chromaTimer=new CountDownTimer(chromaMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d("TAG", "onTick: "+millisUntilFinished/1000);
                    tvChromaLightTimer.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setChromaTime((millisUntilFinished)+" Min");
                    tvChromaLightONOFF.setText(switchState);
                    timings.set(4, String.valueOf((millisUntilFinished+ 60000)/60000));
                }

                @Override
                public void onFinish() {
                    tvChromaLightTimer.setText("0 Min");
                    Modes.getModes().setChromaTime("0 Min");
                    isAlreadyOn.set(4,false);
                    tvChromaLightONOFF.setText("OFF");
                    timings.set(4,"0");

                }
            };
            countDownTimers.set(4,chromaTimer);
            countDownTimers.get(4).start();
            isAlreadyOn.set(4,true);
        }else{
            countDownTimers.get(4).cancel();
            isAlreadyOn.set(4,false);
//            Toast.makeText(this, "drain is paused", Toast.LENGTH_SHORT).show();
            tvChromaLightONOFF.setText(switchState);
        }
    }
    private void handleDrain(Boolean isStarted){

        String[] extractedMinute = Modes.getModes().getDrainTime().split(" ");
//        Long drainMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long drainMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])));

        if(isStarted){
            countDownTimers.get(7).cancel();
            CountDownTimer drainTimer=new CountDownTimer(drainMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d("TAG", "onTick: "+millisUntilFinished/1000);
                    tvCustomDrainTime.setText(((millisUntilFinished+ 60000)/60000)+" Min");
                    Modes.getModes().setDrainTime((millisUntilFinished)+" Min");
                    checkBoxDrainOnOff.setText("ON");
                    timings.set(7, String.valueOf((millisUntilFinished+ 60000)/60000));
                    Log.e("TAG", "onReceive: drain currentmillis "+millisUntilFinished );
                    Intent drainBroadcastIntent=new Intent(DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY);
                    drainBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.DRAIN.toString());
                    sendBroadcast(drainBroadcastIntent);
                }

                @Override
                public void onFinish() {
                    tvCustomDrainTime.setText("0 Min");
                    Modes.getModes().setDrainTime("0 Min");
                    isAlreadyOn.set(7,false);
                    checkBoxDrainOnOff.setText("OFF");
                    timings.set(7, String.valueOf(0));
                    Intent drainBroadcastIntent=new Intent(DRAIN_CUSTOM_MASSAGE_BROADCAST_KEY);
                    drainBroadcastIntent.putExtra(BROADCAST_MASSAGE_TYPE,Operations.DRAIN.toString());
                    sendBroadcast(drainBroadcastIntent);
                    BluetoothOperation.sendCommand("#$DRAINOFF$#");
                }


            };
            countDownTimers.set(7,drainTimer);
            countDownTimers.get(7).start();
            isAlreadyOn.set(7,true);
        }else{
            countDownTimers.get(7).cancel();
            isAlreadyOn.set(7,false);
//            Toast.makeText(this, "drain is paused", Toast.LENGTH_SHORT).show();
            checkBoxDrainOnOff.setText("OFF");
            BluetoothOperation.sendCommand("#$DRAINOFF$#");
        }
    }
    //    class IsOn{
//        Integer itemPos
//    }


    private void turnOnHydroSequenceTimers(){

        String[] extractedJet1Time = Modes.getModes().getHydroJet1Time().split(" ");
        String[] extractedJet2Time = Modes.getModes().getHydroJet2Time().split(" ");
        String[] extractedJet3Time = Modes.getModes().getHydroJet3Time().split(" ");
        String[] extractedJet4Time = Modes.getModes().getHydroJet4Time().split(" ");
//        Toast.makeText(this, "timing is : "+Modes.getModes().getHydroTime(), Toast.LENGTH_SHORT).show();
//        Long hydroMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
        Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));
        Long jet3Sec= Long.valueOf((Integer.parseInt(extractedJet3Time[0])));
        Long jet4Sec= Long.valueOf((Integer.parseInt(extractedJet4Time[0])));

        final long[] jet1Seconds = new long[1];
        final long[] jet2Seconds = new long[1];
        final long[] jet3Seconds = new long[1];
        final long[] jet4Seconds = new long[1];
        jet1Timer=new CountDownTimer(jet1Sec*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Modes.getModes().setHydroJet1Time(String.valueOf(millisUntilFinished/1000));
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                Log.e("TAG", "onTick: jet1 "+millisUntilFinished/1000);
                jet1Seconds[0]=millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                Modes.getModes().setHydroJet1Time("0");
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                jet1Seconds[0]=0;
                if(jet1Seconds[0]==0 && jet2Seconds[0]==0 && jet3Seconds[0]==0 && jet4Seconds[0]==0){
                    stopHydroSequenceTimers();
                }
            }
        };
        jet1Timer.start();

        jet2Timer=new CountDownTimer(jet2Sec*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Modes.getModes().setHydroJet2Time(String.valueOf(millisUntilFinished/1000));
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                Log.e("TAG", "onTick: jet2 "+millisUntilFinished/1000);
                jet2Seconds[0]=millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                Modes.getModes().setHydroJet2Time("0");
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                jet2Seconds[0]=0;
                if(jet1Seconds[0]==0 && jet2Seconds[0]==0 && jet3Seconds[0]==0 && jet4Seconds[0]==0){
                    stopHydroSequenceTimers();
                }
            }
        };
        jet2Timer.start();

        jet3Timer=new CountDownTimer(jet3Sec*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Modes.getModes().setHydroJet3Time(String.valueOf(millisUntilFinished/1000));
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                Log.e("TAG", "onTick: jet3 "+millisUntilFinished/1000);
                jet3Seconds[0]=millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                Modes.getModes().setHydroJet3Time("0");
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                jet3Seconds[0]=0;
                if(jet1Seconds[0]==0 && jet2Seconds[0]==0 && jet3Seconds[0]==0 && jet4Seconds[0]==0){
                    stopHydroSequenceTimers();
                }
            }
        };
        jet3Timer.start();

        jet4Timer=new CountDownTimer(jet4Sec*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Modes.getModes().setHydroJet4Time(String.valueOf(millisUntilFinished/1000));
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                Log.e("TAG", "onTick: jet4 "+millisUntilFinished/1000);
                jet4Seconds[0]=millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                Modes.getModes().setHydroJet4Time("0");
                Intent hydroSequenceBroadcastIntent=new Intent(HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(hydroSequenceBroadcastIntent);
                jet4Seconds[0]=0;
                if(jet1Seconds[0]==0 && jet2Seconds[0]==0 && jet3Seconds[0]==0 && jet4Seconds[0]==0){
                    stopHydroSequenceTimers();
                }
            }
        };
        jet4Timer.start();
        isOnOff.set(0, true );
        hydroSequenceOnOff.setText("ON");
    }
    private void stopHydroSequenceTimers(){
        if(jet1Timer!=null && jet2Timer!=null && jet3Timer!=null && jet4Timer!=null) {
            jet1Timer.cancel();
            jet2Timer.cancel();
            jet3Timer.cancel();
            jet4Timer.cancel();
            isOnOff.set(0, false );
            hydroSequenceOnOff.setText("OFF");
        }
    }

    private void turnOnCustomHydroSequenceTimers(){

        String[] extractedJet1Time = Modes.getModes().getCustomSequenceHydro().split(" ");
        String[] extractedJet2Time = Modes.getModes().getCustomSequenceAir().split(" ");

//        Toast.makeText(this, "timing is : "+Modes.getModes().getHydroTime(), Toast.LENGTH_SHORT).show();
//        Long hydroMillis= Long.valueOf((Integer.parseInt(extractedMinute[0])+1)*60*1000);
        Long jet1Sec= Long.valueOf((Integer.parseInt(extractedJet1Time[0])));
        Long jet2Sec= Long.valueOf((Integer.parseInt(extractedJet2Time[0])));

        final long[] hydroSeconds = new long[1];
        final long[] airSeconds = new long[1];

        customHydroSequence=new CountDownTimer(jet1Sec*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Modes.getModes().setCustomSequenceHydro(String.valueOf(millisUntilFinished/1000));
                Intent customHydroSequenceBroadcastIntent=new Intent(CUSTOM_HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(customHydroSequenceBroadcastIntent);
                hydroSeconds[0] =millisUntilFinished/1000;

            }

            @Override
            public void onFinish() {
                Modes.getModes().setCustomSequenceHydro("0");
                Intent customHydroSequenceBroadcastIntent=new Intent(CUSTOM_HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(customHydroSequenceBroadcastIntent);
                hydroSeconds[0]=0;
                if(hydroSeconds[0]==0 && airSeconds[0]==0){
                    stopCustomHydroSequenceTimers();
                }
            }
        };
        customHydroSequence.start();

        customAirSequence=new CountDownTimer(jet2Sec*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Modes.getModes().setCustomSequenceAir(String.valueOf(millisUntilFinished/1000));
                Intent customHydroSequenceBroadcastIntent=new Intent(CUSTOM_HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(customHydroSequenceBroadcastIntent);
                airSeconds[0]=millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                Modes.getModes().setCustomSequenceAir("0");
                Intent customHydroSequenceBroadcastIntent=new Intent(CUSTOM_HYDRO_SEQUENCE_BROADCAST_KEY);
                sendBroadcast(customHydroSequenceBroadcastIntent);
                airSeconds[0]=0;
                if(hydroSeconds[0]==0 && airSeconds[0]==0){
                    stopCustomHydroSequenceTimers();
                }
            }
        };
        customAirSequence.start();


        isOnOff.set(1, true );
        airMassageToggleOnOff.setText("ON");
    }
    private void stopCustomHydroSequenceTimers(){
        if(customAirSequence!=null && customHydroSequence!=null) {
            Log.e("TAG", "stopCustomHydroSequenceTimers: is triggered");
            customAirSequence.cancel();
            customHydroSequence.cancel();

            isOnOff.set(1, false );
            airMassageToggleOnOff.setText("OFF");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(startStopCustomMassageReceiver);
        unregisterReceiver(heaterBroadcastStartStop);
        unregisterReceiver(drainBroadcastStartStop);
        unregisterReceiver(cleaningBroadcastStartStop);
        unregisterReceiver(hydroSequenceStartStopReciever);
        unregisterReceiver(customHydroSequenceStartStopReceiver);
    }
}