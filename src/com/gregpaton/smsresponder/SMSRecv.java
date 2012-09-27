package com.gregpaton.smsresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;



public class SMSRecv extends BroadcastReceiver {

	private final String TAG = this.getClass().getSimpleName();
	private String number = null;
	
	@Override 
	public void onReceive(Context context, Intent intent) 
	{
		Log.i(TAG, "onRecv");

        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;      
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);   
                number = msgs[i].getOriginatingAddress(); 
            }

            //abortBroadcast();
            
            // notify main activity that SMS has been received
            Intent intent2open = new Intent(context, MainActivity.class);
            intent2open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2open.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            String name = "NUMBER";
            String value = number;
            intent2open.putExtra(name, value);
            context.startActivity(intent2open);
        }
	}
	
}