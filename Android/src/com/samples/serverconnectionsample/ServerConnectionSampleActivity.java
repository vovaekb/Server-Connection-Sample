package com.samples.serverconnectionsample;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ServerConnectionSampleActivity extends Activity {
	
	private EditText messageText;
	private Button transmitButton;
	private TextView sendDataText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        messageText = (EditText)findViewById(R.id.messageText);
        
        sendDataText = (TextView)findViewById(R.id.sendDataText);
        
        transmitButton = (Button)findViewById(R.id.transmitButton);
        transmitButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String hostName =  "10.0.2.2";

				int port = 9023; // 80;
				DataOutputStream dataOutputStream;
				DataInputStream dataInputStream;
				
				Socket socket = null;
				try {
					socket = new Socket(InetAddress.getByName(hostName), port);
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					
					if(messageText.getText().toString() != ""){
						String message = messageText.getText().toString();
						
						JSONObject sendData = new JSONObject();
						try {
							sendData.put("message", message);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						String sendString = sendData.toString();
						
						byte dataHeader[] = new byte[6];
						
						byte byteData[] = sendString.getBytes();
						
						dataOutputStream.write(byteData,0, byteData.length);
						dataOutputStream.flush();
					}
					
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String inputLine;
					while((inputLine = input.readLine()) != null){
						sendDataText.setText(inputLine);
					}
					
				} catch(UnknownHostException e){
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, "Connection established", duration);
				toast.show();
				
			}
		});

    }
}