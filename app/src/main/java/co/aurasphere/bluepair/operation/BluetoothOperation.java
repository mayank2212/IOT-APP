package co.aurasphere.bluepair.operation;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class BluetoothOperation {

    private static final String TAG = BluetoothOperation.class.getSimpleName();

    public static Boolean isDeviceConnected(){
        try{
            List<BleDevice> list =  BleManager.getInstance().getAllConnectedDevice();
            Log.e(TAG, "isDeviceConnected: "+list.size() );
            if (list.isEmpty()){
                return false;
            }
            return list != null;
        }
        catch (Exception e){
            return false;
        }
    }

    public static void sendCommand(String command){
        try {
            List<BleDevice> list =  BleManager.getInstance().getAllConnectedDevice();
            //UUID uuid = BleManager.getInstance().getBluetoothGatt(list.get(0)).getServices().get(1).getUuid();
            UUID uuid = getUUid(list.get(0));

            Log.e("TAG", "onClick: 1 uuid "+BleManager.getInstance().getBluetoothGatt(list.get(0)).getServices().get(3).getUuid().toString());
            Log.e("TAG", "onClick: 2 uuid "+uuid.toString());
            String hex = toHex(command);
            BleManager.getInstance().write(
                    list.get(0),
                    BleManager.getInstance().getBluetoothGatt(list.get(0)).getServices().get(2).getUuid().toString(),
                    uuid.toString(),
                    //"2324485944524f4f4e3031302423"
                    HexUtil.hexStringToBytes(hex),
                    new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                            Log.e(TAG, "onWriteSuccess: " );
                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                            Log.e(TAG, "onWriteFailure: "+exception.getDescription() );
                        }
                    });
        }
        catch (Exception e){
            Log.e(TAG, "Exception " );
            e.printStackTrace();
        }
    }

    private static UUID getUUid(BleDevice bleDevice) {
        for (BluetoothGattService bluetoothGattService:BleManager.getInstance().getBluetoothGatt(bleDevice).getServices()){
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic:bluetoothGattService.getCharacteristics()){
                int charaProp = bluetoothGattCharacteristic.getProperties();
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                        return bluetoothGattCharacteristic.getUuid();
                }
            }
        }
        return BleManager.getInstance().getBluetoothGatt(bleDevice).getServices().get(2).getCharacteristics().get(2).getUuid();
    }


    public static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(StandardCharsets.UTF_8)));
    }
}
