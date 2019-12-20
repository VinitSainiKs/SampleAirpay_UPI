package com.example.sampleairpay_upi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

//import com.example.airpaysdk_simplifiedotp.AirpayActivity;

//import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;

import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Tushar on 11/21/2015.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    private String web_otp_value ="";
    private String axis_msg_otp = "";
    private Handler handler;
    private String sbi_msg_otp="";
    private String axis_final_value="";
    private ArrayList<String> arrayList = new ArrayList<String>();
    private String otp_rep ="";
    private String message_final ="";

    // Delay AUTO OTP
  /*private CountDownTimer countDownTimer;
    private final long startTime =2 * 1000;
    private final long interval = 1 * 1000;*/



    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {
                String message="";

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++)
                {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                     message += currentMessage.getDisplayMessageBody();
                    Log.e("SmsReceiver", "senderNum: " + senderNum + "; message: @@@" + message);
                    message_final = message.replace(".","");
                    Log.e("SmsReceiver", "message_final: @@@111" + message_final);
                } // end for loop


                if(message_final.contains(" "))
                {
                    String[] arr=message_final.split(" ");



                    for(int i=0;i<arr.length;i++)
                    {

                        if(arr[i].length() == 6)
                        {
                            arrayList.add(arr[i]);
                        }

                    }

                    for (int i = 0; i < arrayList.size(); i++) {
                        boolean digitsOnly = TextUtils.isDigitsOnly(arrayList.get(i));

                        if(digitsOnly == true)
                        {


                            try{
                                Log.e("OTP UN FINAL ==>","OTP UN FINAL ==>"+arrayList.get(i));


                                Log.e("SMS OTP UNIFIED 22 --> ",""+arrayList.get(i));
                                AirpayActivity.Otpfiled(arrayList.get(i));

                            /*    countDownTimer = new MyCountDownTimer(startTime, interval);
                                 countDownTimer.start();*/

                            }
                            catch(Exception e)
                            {
                                Log.e("ERROR IN TRY RECEIVER->",""+e.getMessage());
                            }

                        }

                    }

                }

            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

    /*public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {


        }

        @Override

        public void onTick(long millisUntilFinished) {
        }

    }*/
}