package com.stampQR.stampqrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class ProcessData extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
    IntentFilter intentFilter;
    InputStream inputStream;
    OutputStream outputStream;
    RxThread rxThread;
    String rxData = "";

    ImageView qrImage;
    TextView plainText;
    TextView btDevices;

    TextView encoderResponse;
    Button connectBtn;
    Button createStampBtn;

    String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // get from net 00001101-0000-1000-8000-00805F9B34FB
    String imageUrl = "http://18.133.86.241:8000/images/";
    String encodedText;
    StringBuilder sb;
    Queue stringQueue;

    Boolean dataTransferComplete = Boolean.FALSE;
    Boolean transferringData = Boolean.FALSE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_data);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        rxThread = new RxThread();
        sb=new StringBuilder();
        stringQueue = new LinkedList();

        registerReceiver(BtReceiver,intentFilter);
        qrImage = findViewById(R.id.processQrImage);
        plainText = findViewById(R.id.processPlainText);
        encoderResponse = findViewById(R.id.showEncoderResponse);
        btDevices = findViewById(R.id.showDevices);
        connectBtn = findViewById(R.id.connectToEncoderBtn);
        createStampBtn = findViewById(R.id.createStampBtn);
        //createStampBtn.setEnabled(false);

        plainText.setText((String)getIntent().getSerializableExtra("plainText"));
        String imageName = (String)getIntent().getSerializableExtra("imageName");
        encodedText = (String)getIntent().getSerializableExtra("encodedText");
        Glide.with(this).asBitmap().load(imageUrl+imageName).into(qrImage);

        //encodedText="hello-world-example";
        //String [] dataChunksArray = encodedText.split("-"); //
        String [] dataChunksArray = encodedText.split("\n");

        for (String s: dataChunksArray) {
            stringQueue.add(s+";");
        }

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("MissingPermission")
            public void onClick(View view) {
                Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                for(BluetoothDevice device:devices){
                    //sb.append(device.getName());
                    //sb.append("\n");
                        if(device.getName().contains("HC-05")){
                            bluetoothDevice=device;
                        }
                    }
                    //btDevices.setText("");
                    //btDevices.setText(sb.toString());
                    //sb.setLength(0);
                    //if encoder found, connect to it.
                    if(!bluetoothDevice.equals(null)){
                        try{
                            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                            bluetoothSocket.connect();
                            inputStream = bluetoothSocket.getInputStream();
                            outputStream = bluetoothSocket.getOutputStream();
                            rxThread.start();
                            Toast.makeText(view.getContext(), "Connected to encoder!", Toast.LENGTH_SHORT).show();
                            //createStampBtn.setEnabled(true);
                        }catch(Exception e){
                            System.out.println(e);
                            Toast.makeText(view.getContext(), "Could not connect to encoder! "+e, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        });


        createStampBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Prep data, can only send 50 characters at one time

                        try{
                            //start sending data by sending the first line
                            //outputStream.write("Start;".getBytes());
                            sendData(outputStream,stringQueue);
                            transferringData=Boolean.TRUE;
                            Toast.makeText(view.getContext(), "Sending data to encoder!", Toast.LENGTH_SHORT).show();
                            //System.out.println(encodedText);
                        }catch(Exception e){
                            Toast.makeText(view.getContext(), "Failed to send data to encoder!", Toast.LENGTH_SHORT).show();
                        }
                    }

        });


    }

    @Override
    public void onBackPressed() {
        if(!transferringData){
            super.onBackPressed();
        }
        if(dataTransferComplete){
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            super.onBackPressed();
        }else if(transferringData){
            Toast.makeText(getApplicationContext(), "Data transfer not complete, please wait!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendData(OutputStream o, Queue<String> stringQueue) throws IOException {
        Context c = getApplicationContext();
       String dataToSend = stringQueue.remove();
           o.write(dataToSend.getBytes());
        System.out.println(dataToSend);
           if(stringQueue.isEmpty()){
               o.write("Done;".getBytes());
               dataTransferComplete=Boolean.TRUE;
               transferringData=Boolean.FALSE;
               Toast.makeText(c, "Data transfer complete!", Toast.LENGTH_SHORT).show();
           }
    }

//***************************************RxThread*******************************************************
    class RxThread extends Thread{
        public Boolean isRunning;
        byte[] rx = new byte[1];
        View view;
        RxThread(){
            isRunning=true;
        }
        RxThread(View v){
        isRunning=true; view = v;
    }
        @Override
        public void run(){
            while(isRunning){
                try{
                    if(inputStream.available()>0){
                        inputStream.read(rx);
                        rxData = new String(rx);
                        System.out.println(rxData);
                    }
                    //if we get a "P"(rocessed) back from arduion send next line or send "Done" to indicate no more data.

                    if(rxData.equals("P")){
                        try {
                            if (!stringQueue.isEmpty()) {
                                sendData(outputStream, stringQueue);
                            }
                            System.out.println(rxData);
                        }catch (IOException e){
                            System.out.println(e);
                        }

                    }

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(!rxData.equals("")){
                                //encoderResponse.setText(rxData);
                                System.out.println("Response: "+rxData);
                                rxData="";
                            }
                        }
                    });

                    Thread.sleep(10);
                }catch(Exception e){
                    System.out.println("Process rx data: "+e.getStackTrace()[0]+" "+e.getStackTrace()[1]);
                }
            }
        }
    }

    BroadcastReceiver BtReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            switch (intent.getAction()){
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                        runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        //createStampBtn.setEnabled(true);
                    }
                });
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                        rxThread.isRunning=false;
                    break;
            }
        }
    };

    public void sendData(String [] data){
        int arrayLength = data.length;



    }


}