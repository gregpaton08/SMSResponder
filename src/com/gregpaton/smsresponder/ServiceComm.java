package com.gregpaton.smsresponder;

import android.app.Service;
import android.content.IntentFilter;

//public class ServiceComm extends Service
//{
//    private SMS mSMSreceiver;
//    private IntentFilter mIntentFilter;
//
//    @Override
//    public void onCreate()
//    {
//        super.onCreate();
//
//        //SMS event receiver
//        mSMSreceiver = new SMSreceiver();
//        mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        registerReceiver(mSMSreceiver, mIntentFilter);
//    }
//
//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//
//        // Unregister the SMS receiver
//        unregisterReceiver(mSMSreceiver);
//    }
//}