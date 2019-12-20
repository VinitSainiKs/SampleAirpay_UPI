package com.example.sampleairpay_upi;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;
import com.airpay.airpaysdk_simplifiedotp.ResponseMessage;
import com.airpay.airpaysdk_simplifiedotp.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;


public class MainActivity extends Activity implements ResponseMessage, OnClickListener {
    EditText emailId, phone_et, firstName, lastName, address, city, state, country, pincode, orderid, amount;
    Button nextButton;
    ResponseMessage resp;
    ArrayList<Transaction> transactionList;
    private String ErrorMessage = "invalid";
    public boolean ischaracter;
    public boolean boolIsError_new = true;
    private ImageView img_down;
    private LinearLayout layout_address;

    private int k = 0;
    public final int PERMISSION_REQUEST_CODE = 101;
    //private EditText VPA_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_activity);

        emailId = (EditText) findViewById(R.id.emailId);
        phone_et = (EditText) findViewById(R.id.phone_et);
        firstName = (EditText) findViewById(R.id.firstName_et);
        lastName = (EditText) findViewById(R.id.lastName_et);
        address = (EditText) findViewById(R.id.address_et);
        city = (EditText) findViewById(R.id.city_et);
        state = (EditText) findViewById(R.id.state_et);
        country = (EditText) findViewById(R.id.country_et);
        pincode = (EditText) findViewById(R.id.pincode_et);
        orderid = (EditText) findViewById(R.id.orderId_et);
        amount = (EditText) findViewById(R.id.amount_et);
        //VPA_et = (EditText)findViewById(R.id.VPA_et);
        layout_address = (LinearLayout) findViewById(R.id.layout_address);
        img_down = (ImageView) findViewById(R.id.img_down);
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        img_down.setImageResource(R.drawable.drop_down);

        img_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layout_address.getVisibility() == View.VISIBLE) {
                    layout_address.setVisibility(View.GONE);
                    img_down.setImageResource(R.drawable.drop_down);


                } else {

                    layout_address.setVisibility(View.VISIBLE);
                    layout_address.animate().alpha(1.0f);
                    img_down.setImageResource(R.drawable.drop_up);

                }
            }
        });

        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission()) {

                } else {
                    requestPermission();

                }
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error Mesg : " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }


    }


    private boolean checkPermission() {
        int read_sms = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS);
        int send_sms = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        int access_network_state = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE);

        if (read_sms == PackageManager.PERMISSION_GRANTED && send_sms == PackageManager.PERMISSION_GRANTED && access_network_state == PackageManager.PERMISSION_GRANTED) {
            Log.e("PERMISSSION GRANTED", "PERMISSSION");

            return true;
        } else {
            Log.e("PERMISSSION Denied", "PERMISSSION Denied");

            return false;
        }
    }


    private void requestPermission() {
        Log.e("requestPermission", "requestPermission");
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_NETWORK_STATE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied, Now you can't access permission.", Toast.LENGTH_LONG).show();

                }


                break;


        }
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                //.setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                        quit();
                    }
                }).create().show();
    }

    public void quit() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
    }

    public void callback(ArrayList<Transaction> data, boolean flag) {
        if (data != null) {
        }
    }

    private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    public void onClick(View v) {
        if (v.getId() == R.id.nextButton) {

            if (emailId.getText().toString().equalsIgnoreCase("") && phone_et.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Email Address Or Phone No", Toast.LENGTH_LONG).show();
            } else if (!emailId.getText().toString().equalsIgnoreCase("") && phone_et.getText().toString().equalsIgnoreCase("") && !checkEmail(emailId.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Email", Toast.LENGTH_LONG).show();
                emailId.setError("Please Enter Valid Email");
            } else if (emailId.getText().toString().equalsIgnoreCase("") && !phone_et.getText().toString().equalsIgnoreCase("") && phone_et.getText().toString().trim().length() < 5) {
                Toast.makeText(getApplicationContext(), "Phone No should be minimum 5 digit", Toast.LENGTH_LONG).show();
                phone_et.setError("Phone No should be minimum 5 digit");
            } else if (emailId.getText().toString().equalsIgnoreCase("") && !phone_et.getText().toString().equalsIgnoreCase("") && !PhoneNumberUtils.isGlobalPhoneNumber(phone_et.getText().toString().trim()) || phone_et.getText().toString().trim().contains(".") || phone_et.getText().toString().trim().contains("-")) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Phone No", Toast.LENGTH_LONG).show();
                phone_et.setError("Please Enter Valid Phone No");
            } else if (emailId.getText().toString().equalsIgnoreCase("") && !phone_et.getText().toString().equalsIgnoreCase("") && Float.parseFloat(phone_et.getText().toString().trim()) == 0) {
                Toast.makeText(getApplicationContext(), "Phone No should not be zero", Toast.LENGTH_LONG).show();
                phone_et.setError("Phone No should not be zero");
            } else if (!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("") && !checkEmail(emailId.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Email", Toast.LENGTH_LONG).show();
                emailId.setError("Please Enter Valid Email");
            } else if (!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("") && phone_et.getText().toString().trim().length() < 5) {
                Toast.makeText(getApplicationContext(), "Phone No should be minimum 5 digit", Toast.LENGTH_LONG).show();
                phone_et.setError("Phone No should be minimum 5 digit");
            } else if (!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("") && !PhoneNumberUtils.isGlobalPhoneNumber(phone_et.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Phone No", Toast.LENGTH_LONG).show();
                phone_et.setError("Please Enter Valid Phone No");
            } else if (!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("") && !PhoneNumberUtils.isGlobalPhoneNumber(phone_et.getText().toString().trim()) || phone_et.getText().toString().trim().contains(".") || phone_et.getText().toString().trim().contains("-")) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Phone No", Toast.LENGTH_LONG).show();
                phone_et.setError("Please Enter Valid Phone No");
            } else if (!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("") && Float.parseFloat(phone_et.getText().toString().trim()) == 0) {
                Toast.makeText(getApplicationContext(), "Phone No should not be zero", Toast.LENGTH_LONG).show();
                phone_et.setError("Phone No should not be zero");
            }
            //if(Float.parseFloat(phone_et.getText().toString().trim()) == 0)       Float.parseFloat(phone_et.getText().toString().trim()) == 0
            else if (firstName.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_LONG).show();
                firstName.setError("Please Enter First Name");

            } else if (lastName.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_LONG).show();
                lastName.setError("Please Enter Last Name");

            } else if (orderid.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Order Id", Toast.LENGTH_LONG).show();
                orderid.setError("Please Enter Order Id");

            } else if (amount.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Amount", Toast.LENGTH_LONG).show();
                amount.setError("Please Enter Amount");
            } else if (!isAlpha(firstName.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid First Name", Toast.LENGTH_LONG).show();
                firstName.setError("Please Enter Valid First Name");
            } else if (!isAlpha(lastName.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Last Name", Toast.LENGTH_LONG).show();
                lastName.setError("Please Enter Valid Last Name");

            } else {

					 /*if(amount.getText().toString().contains("0")) {
                         if(Float.parseFloat(amount.getText().toString().trim()) == 0)
						 {
							 Toast.makeText(getApplicationContext(), "Amount should not be zero", Toast.LENGTH_LONG).show();
							 boolIsError_new = true;
							 amount.setError("Amount should not be zero");
						 }
						 else {
								boolIsError_new = false;

						 }
					 }
					 else*/
                if (amount.getText().toString().trim().contains(".")) {
                    String value = amount.getText().toString().trim();

                    value = value.replace(".", "##");
                    if (value.contains("##")) {
                        String[] arr = value.split("##");

                        if (arr.length > 1) {
                            if (arr[1].length() > 2) {
                                Toast.makeText(getApplicationContext(), "Please Enter Valid Amount", Toast.LENGTH_LONG).show();
                                amount.setError("Please Enter Valid Amount");
                                boolIsError_new = true;


                            } else {
                                if (Float.parseFloat(amount.getText().toString().trim()) == 0) {
                                    Toast.makeText(getApplicationContext(), "Amount should not be zero", Toast.LENGTH_LONG).show();
                                    boolIsError_new = true;
                                    amount.setError("Amount should not be zero");


                                } else {
                                    boolIsError_new = false;
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter an Amount upto 2 Decimal Places", Toast.LENGTH_LONG).show();
                            amount.setError("Please Enter an Amount upto 2 Decimal Places");
                            boolIsError_new = true;


                        }
                    }
                } else {
                    if (Float.parseFloat(amount.getText().toString().trim()) == 0) {
                        Toast.makeText(getApplicationContext(), "Amount should not be zero", Toast.LENGTH_LONG).show();
                        boolIsError_new = true;
                        amount.setError("Amount should not be zero");


                    } else {
                        boolIsError_new = false;
                    }

                }


                if (boolIsError_new == false) {
                    Intent myIntent = new Intent(this, AirpayActivity.class);

                    Bundle b = new Bundle();

                    // Please enter Merchant configuration value

                    
                    // Live Merchant Details - Merchant Id - 
                    b.putString("USERNAME", "");
                    b.putString("PASSWORD", "");
                    b.putString("SECRET", "");
                    b.putString("MERCHANT_ID", "");

                    
                    b.putString("EMAIL", emailId.getText().toString().trim());

                    // This is for dynamic phone no value code - Uncomment it
                    b.putString("PHONE", "" + phone_et.getText().toString().trim());
					/*//  Please enter phone no value
					b.putString("PHONE", "");*/
                    b.putString("FIRSTNAME", firstName.getEditableText().toString().trim());
                    b.putString("LASTNAME", lastName.getEditableText().toString().trim());
                    b.putString("ADDRESS", address.getEditableText().toString().trim());
                    b.putString("CITY", city.getEditableText().toString().trim());
                    b.putString("STATE", state.getEditableText().toString().trim());
                    b.putString("COUNTRY", country.getEditableText().toString().trim());
                    b.putString("PIN_CODE", pincode.getEditableText().toString().trim());
                    b.putString("ORDER_ID", orderid.getEditableText().toString().trim());
                    b.putString("AMOUNT", amount.getEditableText().toString().trim());
                    b.putString("CURRENCY", "356");
                    b.putString("ISOCURRENCY", "INR");
                    b.putString("CHMOD", "");
                    b.putString("CUSTOMVAR", "");
                    b.putString("TXNSUBTYPE", "");
                    b.putString("WALLET", "0");

                    // Live Success URL Merchant Id -
                    b.putString("SUCCESS_URL", "");

                    
                    b.putParcelable("RESPONSEMESSAGE", (Parcelable) resp);

                    myIntent.putExtras(b);
                    startActivityForResult(myIntent, 120);

                }

            }

            ////

            emailId.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                public void afterTextChanged(Editable edt) {
                    if (emailId.getText().length() > 0) {
                        emailId.setError(null);
                    }
                }

            });

            phone_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                public void afterTextChanged(Editable edt) {
                    if (phone_et.getText().length() > 0) {
                        phone_et.setError(null);
                    }
                }

            });


            firstName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                public void afterTextChanged(Editable edt) {
                    if (firstName.getText().length() > 0) {
                        firstName.setError(null);
                    }
                }

            });

            lastName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                public void afterTextChanged(Editable edt) {
                    if (lastName.getText().length() > 0) {
                        lastName.setError(null);
                    }
                }

            });

            orderid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                public void afterTextChanged(Editable edt) {
                    if (orderid.getText().length() > 0) {
                        orderid.setError(null);
                    }
                }

            });

            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                public void afterTextChanged(Editable edt) {
                    if (amount.getText().length() > 0) {
                        amount.setError(null);
                    }
                }

            });

            ////

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bundle bundle = data.getExtras();
            transactionList = new ArrayList<Transaction>();
            transactionList = (ArrayList<Transaction>) bundle.getSerializable("DATA");
            if (transactionList != null) {
                Toast.makeText(this, transactionList.get(0).getSTATUS() + "\n" + transactionList.get(0).getSTATUSMSG(), Toast.LENGTH_LONG).show();

                if (transactionList.get(0).getSTATUS() != null) {
                    Log.e("STATUS -> ", "=" + transactionList.get(0).getSTATUS());

                }
                if (transactionList.get(0).getMERCHANTKEY() != null) {
                    Log.e("MERCHANT KEY -> ", "=" + transactionList.get(0).getMERCHANTKEY());

                }
                if (transactionList.get(0).getMERCHANTPOSTTYPE() != null) {
                    Log.e("MERCHANT POST TYPE ", "=" + transactionList.get(0).getMERCHANTPOSTTYPE());
                }
                if (transactionList.get(0).getSTATUSMSG() != null) {
                    Log.e("STATUS MSG -> ", "=" + transactionList.get(0).getSTATUSMSG()); //  success or fail
                }
                if (transactionList.get(0).getTRANSACTIONAMT() != null) {
                    Log.e("TRANSACTION AMT -> ", "=" + transactionList.get(0).getTRANSACTIONAMT());

                }
                if (transactionList.get(0).getTXN_MODE() != null) {
                    Log.e("TXN MODE -> ", "=" + transactionList.get(0).getTXN_MODE());
                }
                if (transactionList.get(0).getMERCHANTTRANSACTIONID() != null) {
                    Log.e("MERCHANT_TXN_ID -> ", "=" + transactionList.get(0).getMERCHANTTRANSACTIONID()); // order id

                }
                if (transactionList.get(0).getSECUREHASH() != null) {
                    Log.e("SECURE HASH -> ", "=" + transactionList.get(0).getSECUREHASH());
                }
                if (transactionList.get(0).getCUSTOMVAR() != null) {
                    Log.e("CUSTOMVAR -> ", "=" + transactionList.get(0).getCUSTOMVAR());
                }
                if (transactionList.get(0).getTRANSACTIONID() != null) {
                    Log.e("TXN ID -> ", "=" + transactionList.get(0).getTRANSACTIONID());
                }
                if (transactionList.get(0).getTRANSACTIONSTATUS() != null) {
                    Log.e("TXN STATUS -> ", "=" + transactionList.get(0).getTRANSACTIONSTATUS());
                }
                if (transactionList.get(0).getTXN_DATE_TIME() != null) {
                    Log.e("TXN_DATETIME -> ", "=" + transactionList.get(0).getTXN_DATE_TIME());
                }
                if (transactionList.get(0).getTXN_CURRENCY_CODE() != null) {
                    Log.e("TXN_CURRENCY_CODE -> ", "=" + transactionList.get(0).getTXN_CURRENCY_CODE());
                }
                if (transactionList.get(0).getTRANSACTIONVARIANT() != null) {
                    Log.e("TRANSACTIONVARIANT -> ", "=" + transactionList.get(0).getTRANSACTIONVARIANT());
                }
                if (transactionList.get(0).getCHMOD() != null) {
                    Log.e("CHMOD -> ", "=" + transactionList.get(0).getCHMOD());
                }
                if (transactionList.get(0).getBANKNAME() != null) {
                    Log.e("BANKNAME -> ", "=" + transactionList.get(0).getBANKNAME());
                }
                if (transactionList.get(0).getCARDISSUER() != null) {
                    Log.e("CARDISSUER -> ", "=" + transactionList.get(0).getCARDISSUER());
                }
                if (transactionList.get(0).getFULLNAME() != null) {
                    Log.e("FULLNAME -> ", "=" + transactionList.get(0).getFULLNAME());
                }
                if (transactionList.get(0).getEMAIL() != null) {
                    Log.e("EMAIL -> ", "=" + transactionList.get(0).getEMAIL());
                }
                if (transactionList.get(0).getCONTACTNO() != null) {
                    Log.e("CONTACTNO -> ", "=" + transactionList.get(0).getCONTACTNO());
                }
                if (transactionList.get(0).getMERCHANT_NAME() != null) {
                    Log.e("MERCHANT_NAME -> ", "=" + transactionList.get(0).getMERCHANT_NAME());
                }
                if (transactionList.get(0).getSETTLEMENT_DATE() != null) {
                    Log.e("SETTLEMENT_DATE -> ", "=" + transactionList.get(0).getSETTLEMENT_DATE());
                }
                if (transactionList.get(0).getSURCHARGE() != null) {
                    Log.e("SURCHARGE -> ", "=" + transactionList.get(0).getSURCHARGE());
                }
                if (transactionList.get(0).getBILLEDAMOUNT() != null) {
                    Log.e("BILLEDAMOUNT -> ", "=" + transactionList.get(0).getBILLEDAMOUNT());
                }
                if (transactionList.get(0).getISRISK() != null) {
                    Log.e("ISRISK -> ", "=" + transactionList.get(0).getISRISK());
                }

                String transid = transactionList.get(0).getMERCHANTTRANSACTIONID();
                String apTransactionID = transactionList.get(0).getTRANSACTIONID();
                String amount = transactionList.get(0).getTRANSACTIONAMT();
                String transtatus = transactionList.get(0).getTRANSACTIONSTATUS();
                String message = transactionList.get(0).getSTATUSMSG();

                String merchantid = ""; // Please enter Merchant Id
                String username = "";        // Please enter Username
                String sParam = transid + ":" + apTransactionID + ":" + amount + ":" + transtatus + ":" + message + ":" + merchantid + ":" + username;
                CRC32 crc = new CRC32();
                crc.update(sParam.getBytes());
                String sCRC = "" + crc.getValue();
                Log.e("Verified Hash ==", "Verified Hash= " + sCRC);

                if (sCRC.equalsIgnoreCase(transactionList.get(0).getSECUREHASH())) {
                    Log.e("Airpay Secure ->", " Secure hash mismatched");
                } else {
                    Log.e("Airpay Secure ->", " Secure hash matched");
                }


                //Log.e("Remaining Params-->>","Remaining Params-->>"+transactionList.get(0).getMyMap());

                // This code is to get remaining extra value pair.
                for (String key : transactionList.get(0).getMyMap().keySet()) {
                    Log.e("EXTRA-->>", "KEY: " + key + " VALUE: " + transactionList.get(0).getMyMap().get(key));
                    String extra_param= transactionList.get(0).getMyMap().get("PRI_ACC_NO_START"); // To replace key value as you want
                    Log.e("Extra Param -->","="+extra_param);
                    transactionList.get(0).getMyMap().get(key);
                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Error Message --- >>>", "Error Message --- >>> " + e.getMessage());
        }
    }

    private String getProtocolDomain(String sURL) {
        int k = sURL.indexOf("/",sURL.indexOf("://") + 3);
        return sURL.substring(0, k);
    }

    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }


}