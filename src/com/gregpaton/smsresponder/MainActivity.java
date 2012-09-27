package com.gregpaton.smsresponder;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	
	private SMSRecv smsrecv;
	private Button button1;
	private TextView textView1;
	private String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // set up GUI objects
        button1 = (Button)findViewById(R.id.button1);
		textView1 = (TextView)findViewById(R.id.textView1);
		
		Intent intent = new Intent();

	    button1.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v)
	        {
	        	if (textView1.getText() == "mobile")
	        		textView1.setText("hello");
	        	else
	        		textView1.setText("mobile");
	        	//setContentView(textView1);
//            	Toast.makeText(getBaseContext(), 
//                    "Please enter both phone number and message.", 
//                    Toast.LENGTH_SHORT).show();
            	sendSMS("5554", "hi");
	        }
	     });
	    

	    
        registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

            	Toast.makeText(getBaseContext(), 
                    "Receiveeeeeeddddd", 
                    Toast.LENGTH_SHORT).show();
				
	        	if (textView1.getText() == "recv1")
	        		textView1.setText("recv2");
	        	else
	        		textView1.setText("recv1");
	        	
				Bundle bundle = intent.getExtras();
                SmsMessage[] msgs = null;
                String str = "";

                if (bundle != null)
                {
                    //---retrieve the SMS message received---
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                        for (int i = 0; i < msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        str += "SMS from " + msgs[i].getOriginatingAddress();
                        str += " :";
                        str += msgs[i].getMessageBody().toString();
                        str += "\n";
                    }
                    //---display the new SMS message---
                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                }
			}
        }, new IntentFilter("SMS_RECV"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
	    Log.d("YourActivity", "onNewIntent is called!");
	
	    String memberFieldString = intent.getStringExtra("KEY");
	
	    super.onNewIntent(intent);
    } // End of onNewIntent(Intent intent)
    

    private void sendSMS(String phoneNumber, String message)
    {      
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent("SMS_SENT"), 0);
        
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent("SMS_DELIVERED"), 0);
    	      
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);               
    }  
    
    public void receivedSMS(String phoneNumber)
    {
    	textView1.setText(phoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected void onResume() {
	    super.onResume();
		// Notification that the activity is no longer visible
		Log.i(TAG, "onResume");
	}
	

}
