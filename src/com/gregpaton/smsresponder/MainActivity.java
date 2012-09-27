package com.gregpaton.smsresponder;

import android.media.AudioManager;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	
	private CheckBox cbStatus;
	private EditText etMessage;
	private String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // set up GUI objects
		cbStatus = (CheckBox)findViewById(R.id.cbStatus);
		etMessage = (EditText)findViewById(R.id.etMessage);
		final AudioManager audio_mngr = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
			    
	    
	    cbStatus.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View v) {
				if (cbStatus.isChecked() == false)
				{
					cbStatus.setText("Available");
					cbStatus.setChecked(false);
					audio_mngr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				}
				else
				{
					cbStatus.setText("Busy");
					cbStatus.setChecked(true);
					audio_mngr.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				}
			}
		});
	    
	    etMessage.setOnKeyListener(new View.OnKeyListener() {   
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_ENTER)) {  
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0);
				}
				return true;
			}
		});
    }

    
    // called when SMS is received 
    @Override
    protected void onNewIntent(Intent intent) {
	    Log.d(TAG, "onNewIntent");
	
	    if (cbStatus.isChecked())
	    {
		    number = intent.getStringExtra("NUMBER");
		    if (number != null)
		    {
		    	sendSMS(number, etMessage.getText().toString());
		    }
	    }	    

		    super.onNewIntent(intent);
    }
    

    private void sendSMS(String phoneNumber, String message)
    {      
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent("SMS_SENT"), 0);
        
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent("SMS_DELIVERED"), 0);
    	      
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);  
        
        //---display the sent SMS message---
        String str = "";
        str += "SMS Sent to " + phoneNumber;
        str += " : " + message + "\n";
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
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
