/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package foundation.ble;

import android.R.integer;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.File.TxtFileUtil;

import foundation.dataService.util.DateService;





/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BLEService extends Service {
    private final static String TAG = BLEService.class.getSimpleName();
  //文件
  	private File file=new File("/sdcard/HeartRates.txt"/**文件路径名**/);
	private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;
    
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;
    
   

	private  static HeartList heartList=new HeartList();
    public static HeartList getHeartList() {
		return heartList;
	}
    
	public static void setHeartList(HeartList heartList) {
		BLEService.heartList = heartList;
	}
	//得到心率集合
	public List<Integer> getHeartRates(){
		List<Integer> heartRates=new ArrayList<Integer>();
		heartRates.addAll(heartList.getHeartRates());
		return heartRates;
	}
	//清空
	public void clear(){
		heartList.clear();
	}
	//心率服务的uuid
    public final static UUID UUID_HEART_RATE_SERVICE=
            UUID.fromString(SampleGattAttributes.HEART_RATE_SERVICE);
    //心率测量的UUID
    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    //时间
  	private  Date collectTime=null;
  	private  List<Integer> heartRates=null;
  	
	private  Timer UpdateTimer=null;//更新时间表
  	private  TimerTask task=null;
 
  
  
	
	private  ITimeout listener=null;
	

	public ITimeout getLis() {
		return listener;
	}

	public  void setLis(ITimeout lis) {
		listener = lis;
	}
	
	public void work(){
		
		 task=new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				collectTime=DateService.getDate();
				
			
				listener.onTimeout(collectTime,heartList.getHeartRates());
				heartList.clear();
				}
			 };
			 UpdateTimer=new Timer();
			 UpdateTimer.schedule(task, 60000, 60000);//第一次更新时间为一分钟，频率为一分钟
		
		
	}
    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            //已经和周边建立连接
            if (newState == BluetoothProfile.STATE_CONNECTED) {
            
                mConnectionState = STATE_CONNECTED;
                //writeState(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } 
            //没和周边建立连接
            else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
               // writeState(intentAction);
            }
        }
        //发现服务
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
            	
                List<BluetoothGattService> gattServices=getSupportedGattServices();
      		  // Loops through available GATT Services.
      		UUID ServiceUuid = null;
      		UUID CharacteristicUuid=null;
              for (BluetoothGattService gattService : gattServices) {
                 
              	ServiceUuid = gattService.getUuid();
              	//取出心率服务
                  if(UUID_HEART_RATE_SERVICE.equals(ServiceUuid)){
                  	 List<BluetoothGattCharacteristic> gattCharacteristics =
                               gattService.getCharacteristics();
                  	 for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                  		 CharacteristicUuid=gattCharacteristic.getUuid();
                  		 final BluetoothGattCharacteristic characteristic =gattCharacteristic;
                  		 //取出心率服务里面的心率测量特征值
                  		 if(UUID_HEART_RATE_MEASUREMENT.equals(CharacteristicUuid)){
                  			 final int charaProp = gattCharacteristic.getProperties();
                  			  if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                                    // If there is an active notification on a characteristic, clear
                                    // it first so it doesn't update the data field on the user interface.
                                    if (mNotifyCharacteristic != null) {
                                        setCharacteristicNotification(
                                                mNotifyCharacteristic, false);
                                        mNotifyCharacteristic = null;
                                    }
                                    readCharacteristic(characteristic);
                                }
                                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                                    mNotifyCharacteristic = characteristic;
                                    setCharacteristicNotification(
                                            characteristic, true);
                                }
                                
                  		 }
                  	 }
                  	
                     
                  }
              }
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }
        //读取特征值
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
            	TxtFileUtil.appendToFile("onChracteristicRead"+"\r\n", file);
            	dealCharacter(characteristic);
            }
        }
        //特征值一改变再次读取并广播
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
        	 
        	 
        	 TxtFileUtil.appendToFile("onChracteristicChanged"+"\r\n", file);
        	 dealCharacter(characteristic);
        }
    };
    //解析特征值得到心率数据,并写入文件中
    private void dealCharacter(final BluetoothGattCharacteristic characteristic) {
        
     
        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
     
        //判断得到的是否是心率的UUID,是的话进行处理,最终将心率特征值转为10进制
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
        	UUID s=characteristic.getUuid();
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            //取得心率的值,并将心率数据转成10进制
            final Integer heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            //添加到心率集合中
            heartList.add(heartRate);
          
            TxtFileUtil.appendToFile("dealChracter"+heartRate+"\r\n", file);
        } 
       
    }

 
    
    
    
    public class LocalBinder extends Binder {
        public BLEService getService() {
        	
            return BLEService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
    	
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
    	
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
    
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
    	
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
     
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
        
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
        	
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
    	
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
        	
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
    	
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
        	
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
    	
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }
}
