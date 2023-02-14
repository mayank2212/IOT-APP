package co.aurasphere.bluepair.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothHeadset;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;

import java.lang.reflect.Method;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.operation.BluetoothOperation;
import it.beppi.knoblibrary.Knob;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class HomePageActivity extends AppCompatActivity {
    private static final String TAG = "tsg";
    private TextView tvBluetoothConnect,default_select,custom_set;
    ConstraintLayout constraintLayout;
    private boolean isSameValue0 = false;
    private boolean isSameValue100 = false;
     private ImageButton imgGo;
    private TextView tvOnOff;

    enum state{
        NONE,DEF,CUST
    }

    private state stateobj = state.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().hide();
        tvBluetoothConnect = findViewById(R.id.blue_tooth_connect);
        imgGo=findViewById(R.id.imgButton);
        default_select=findViewById(R.id.default_select);
        default_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDefault();
            }
        });
        custom_set=findViewById(R.id.custom_set);
        custom_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCustom();
            }
        });

        imgGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (stateobj){
                    case DEF:
                        goToDefault();
                        break;
                    case CUST:
                        goToCustom();
                        break;
                    case NONE:
                        Toast.makeText(HomePageActivity.this,"Bar is not at far end.",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        //tvBluetoothConnect=findViewById(R.id.default_select);

        tvBluetoothConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, BluetoothActivity.class));

            }
        });

//        CircularSeekBar seekBar = (CircularSeekBar) findViewById(R.id.seek_bar);
//        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
//                Log.e(TAG, "onProgressChanged: "+progress );
//                if (progress > 35){
//                    //startActivity(new Intent(HomePageActivity.this,DefalutActivity.class));
//                }
//                if(progress < -35){
//
//                }
//            }
//
//            @Override
//            public void onStopTrackingTouch(CircularSeekBar seekBar) {
//            }
//
//            @Override
//            public void onStartTrackingTouch(CircularSeekBar seekBar) {
//            }
//        });

//        Knob knob = (Knob) findViewById(R.id.knob);
//        knob.setOnStateChanged(new Knob.OnStateChanged() {
//            @Override
//            public void onState(int state) {
//                Log.e(TAG, "onState: "+state);
//
//                if (BluetoothOperation.isDeviceConnected()){
//
//                    if (state==65){
//                        startActivity(new Intent(HomePageActivity.this,CustomActivity.class));
//                        BluetoothOperation.sendCommand("#$CUSTOMSET$#");
//
//                    }
//                    if (state==135){
//                        //                        startActivity(new Intent(HomePageActivity.this,DefalutActivity.class));
//                        BluetoothOperation.sendCommand("#$DEFAULTSET$#");
//                    }
//
//                }else{
//                    Toast.makeText(HomePageActivity.this, "Device is not connected", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


//        imgGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if (BluetoothOperation.isDeviceConnected()){
//                    startActivity(new Intent(HomePageActivity.this,CustomActivity.class));
//                    finish();
//
//                }else{
//                    Toast.makeText(HomePageActivity.this, "Device does not connected ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        constraintLayout = findViewById(R.id.constraintLayout2);
        tvOnOff=findViewById(R.id.home_page_on_off);

        final CircularSeekBar seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float v, boolean b) {
                constraintLayout.animate().rotation(circularSeekBar.getProgress() - 40).setDuration(0);
                int progress = (int) circularSeekBar.getProgress();
                System.out.println(circularSeekBar.getProgress());
                if (progress == 0 && !isSameValue0) {
                    //TODO :: ADD ACTIVITY INTENT WHEN POINTER IS AT LEFT MOST SIDE
                    stateobj = state.DEF;
                    //Toast.makeText(HomePageActivity.this, "0", Toast.LENGTH_SHORT).show();
                    isSameValue0 = true;
                } else if (progress == 260 && !isSameValue100) {
                    //TODO :: ADD ACTIVITY INTENT WHEN POINTER IS AT RIGHT MOST SIDE
                    stateobj = state.CUST;
                    //Toast.makeText(HomePageActivity.this, "260", Toast.LENGTH_SHORT).show();
                    isSameValue100 = true;
                }
                if (progress > 10) {
                    isSameValue0 = false;
                }
                if (progress < 250) {
                    isSameValue100 = false;
                }
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar circularSeekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar circularSeekBar) {

            }
        });
    }

    void goToDefault(){
        if (BluetoothOperation.isDeviceConnected()){
            BluetoothOperation.sendCommand("#$DEFAULTSET$#");
            tvOnOff.setText("OFF");
        }
        else{
            Toast.makeText(HomePageActivity.this, "Device does not connected ", Toast.LENGTH_SHORT).show();
            tvOnOff.setText("ON");

        }
    }

    void goToCustom(){
        //startActivity(new Intent(HomePageActivity.this,CustomActivity.class));
        if (BluetoothOperation.isDeviceConnected()){
            tvOnOff.setText("OFF");
            startActivity(new Intent(HomePageActivity.this,CustomActivity.class));
            BluetoothOperation.sendCommand("#$CUSTOMSET$#");
        }
        else{
            Toast.makeText(HomePageActivity.this, "Device does not connected ", Toast.LENGTH_SHORT).show();
            tvOnOff.setText("ON");

        }
    }

    private static void getUUid(BleDevice bleDevice) {
        int x = 0;
        for (BluetoothGattService bluetoothGattService : BleManager.getInstance().getBluetoothGatt(bleDevice).getServices()) {
            int y = 0;
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService.getCharacteristics()) {
                int charaProp = bluetoothGattCharacteristic.getProperties();
                Log.e(TAG, "getUUid: " + x + " " + y + " " + bluetoothGattCharacteristic.getUuid().toString());
                y++;
            }
            Log.e(TAG, "getUUid: " + x + " " + bluetoothGattService.getUuid().toString());
            x++;
        }


    }


    public boolean isBluetoothHeadsetConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED) {
            Toast.makeText(HomePageActivity.this, "Device is  connected ", Toast.LENGTH_SHORT).show();

//            return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
//                    && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
//            BluetoothOperation.sendCommand("#$DEFAULTSET$#");
//
//            BluetoothOperation.sendCommand("#$HYDROON010$#");


        } else {
            Toast.makeText(HomePageActivity.this, "Device is not connected ", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            boolean connected = (boolean) m.invoke(device, (Object[]) null);
            return connected;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //tvOnOff.setText("OFF / OFF");
        if (BluetoothOperation.isDeviceConnected()){
            tvBluetoothConnect.setText("Disconnect");
        }
        else{
            tvBluetoothConnect.setText("Connect");
        }
    }
}