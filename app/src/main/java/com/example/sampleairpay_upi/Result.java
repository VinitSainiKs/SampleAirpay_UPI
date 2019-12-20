package com.example.sampleairpay_upi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.airpay_simplifiedotpunified.Transaction;

//import com.airpay.airpaysdk_simplifiedotp.Transaction;

import com.airpay.airpaysdk_simplifiedotp.Transaction;

import java.util.ArrayList;
import java.util.zip.CRC32;

//import com.airpay.airpaysdk_simplifiedotp.Transaction;


public class Result extends Activity implements OnClickListener {

	private TextView tvstatus;
	private TextView tvmerchantkey;
	private TextView tvstatusmsg;
	private TextView tvmerposttyp;
	private TextView tvamount;
	private TextView tvtxnmode;
	private TextView tvtxnId;
	private TextView tvsecure;
	private TextView tvcustom;
	private TextView tvtxnstatus;
	private TextView tvtxnid;
	private Button btreturn;


	// Response Variable 
	
		private String getStatus = "";
		private String getMerchantKey = "";
		private String getMerchantPostType = "";
		private String getStatusMsg = "";
		private String getTransactionAmt = "";
		private String getTXN_Mode ="";
		private String getMerchantTransactionId ="";
		private String getSecureHash = "";
		private String getCustomVar = "";
		private String getTransactionId = "";
		private String getTransactionStatus = "";
		private String getTushar = "";
		private String getName="";
		private String getAmount="";
		
		private Bundle b;
		
	
		ArrayList<Transaction> transactionList;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		tvstatus=(TextView)findViewById(R.id.tvstatus);
		
		tvmerchantkey=(TextView)findViewById(R.id.tvmerchantkey);
		tvmerposttyp=(TextView)findViewById(R.id.tvmerposttyp);
		tvstatusmsg=(TextView)findViewById(R.id.tvstatusmsg);
		tvamount=(TextView)findViewById(R.id.tvamount);
		tvtxnmode=(TextView)findViewById(R.id.tvtxnmode);
		tvtxnId=(TextView)findViewById(R.id.tvtxnId);
		
		tvsecure=(TextView)findViewById(R.id.tvsecure);
		tvcustom=(TextView)findViewById(R.id.tvcustom);
		tvtxnid=(TextView)findViewById(R.id.tvtxnid);
		tvtxnstatus=(TextView)findViewById(R.id.tvtxnstatus);
		
		btreturn=(Button)findViewById(R.id.btreturn);
		btreturn.setOnClickListener(this);
		
		b=getIntent().getExtras();
		try{

			if(b != null)
			{
				Log.e("", "*********Inside onResume bundle ");

				getStatus  = b.getString("getSTATUS");
				getMerchantKey  = b.getString("getMERCHANTKEY");
				getMerchantPostType  = b.getString("getMERCHANTPOSTTYPE");
				getStatusMsg  = b.getString("getSTATUSMSG");
				getTransactionAmt  = b.getString("getTRANSACTIONAMT");
				getTXN_Mode  = b.getString("getTXN_MODE");
				getMerchantTransactionId  = b.getString("getMERCHANTTRANSACTIONID");
				getSecureHash  = b.getString("getSECUREHASH");
				getCustomVar  = b.getString("getCUSTOMVAR");
				getTransactionId  = b.getString("getTRANSACTIONID");
				getTransactionStatus  = b.getString("getTRANSACTIONSTATUS");

				//getTushar  = b.getString("TUSHAR");

				//Log.e("","#################### getTushar "+getTushar);

				Log.e("", "#################### getStatus " + getStatus);
				Log.e("", "#################### getMerchantKey " + getMerchantKey);
				Log.e("", "#################### getMerchantPostType " + getMerchantPostType);
				Log.e("", "#################### getStatusMsg " + getStatusMsg);
				Log.e("", "#################### getTransactionAmt " + getTransactionAmt);
				Log.e("", "#################### getTXN_Mode " + getTXN_Mode);
				Log.e("", "#################### getMerchantTransactionId " + getMerchantTransactionId);
				Log.e("", "#################### getSecureHash " + getSecureHash);
				Log.e("", "#################### getCustomVar " + getCustomVar);
				Log.e("", "#################### getTransactionId " + getTransactionId);
				Log.e("", "#################### getTransactionStatus " + getTransactionStatus);

				
				tvstatus.setText(""+getStatus);
				tvmerchantkey.setText(""+getMerchantKey);
				tvmerposttyp.setText(""+getMerchantPostType);
				tvstatusmsg.setText(""+getStatusMsg);
				tvamount.setText(""+getTransactionAmt);
				tvtxnmode.setText(""+getTXN_Mode);
				tvtxnId.setText(""+getMerchantTransactionId);
				tvsecure.setText(""+getSecureHash);
				tvcustom.setText(""+getCustomVar);
				tvtxnid.setText(""+getTransactionId);
				tvtxnstatus.setText(""+getTransactionStatus);
				
				
			}
			else{
				

			}


		}catch(Exception e)
		{
			Log.e("", "Error OnResume : " + e.getMessage());
		}
		
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	
		b=getIntent().getExtras();
		try{

			if(b != null)
			{
				Log.e("", "*********Inside onResume bundle ");

				getStatus  = b.getString("getSTATUS");
				getMerchantKey  = b.getString("getMERCHANTKEY");
				getMerchantPostType  = b.getString("getMERCHANTPOSTTYPE");
				getStatusMsg  = b.getString("getSTATUSMSG");
				getTransactionAmt  = b.getString("getTRANSACTIONAMT");
				getTXN_Mode  = b.getString("getTXN_MODE");
				getMerchantTransactionId  = b.getString("getMERCHANTTRANSACTIONID");
				getSecureHash  = b.getString("getSECUREHASH");
				getCustomVar  = b.getString("getCUSTOMVAR");
				getTransactionId  = b.getString("getTRANSACTIONID");
				getTransactionStatus  = b.getString("getTRANSACTIONSTATUS");

				//getTushar  = b.getString("TUSHAR");

				//Log.e("","#################### getTushar "+getTushar);

				Log.e("", "#################### getStatus " + getStatus);
				Log.e("", "#################### getMerchantKey " + getMerchantKey);
				Log.e("", "#################### getMerchantPostType " + getMerchantPostType);
				Log.e("", "#################### getStatusMsg " + getStatusMsg);
				Log.e("", "#################### getTransactionAmt " + getTransactionAmt);
				Log.e("", "#################### getTXN_Mode " + getTXN_Mode);
				Log.e("", "#################### getMerchantTransactionId " + getMerchantTransactionId);
				Log.e("", "#################### getSecureHash " + getSecureHash);
				Log.e("", "#################### getCustomVar " + getCustomVar);
				Log.e("", "#################### getTransactionId " + getTransactionId);
				Log.e("", "#################### getTransactionStatus " + getTransactionStatus);

				
				tvstatus.setText(""+getStatus);
				tvmerchantkey.setText(""+getMerchantKey);
				tvmerposttyp.setText(""+getMerchantPostType);
				tvstatusmsg.setText(""+getStatusMsg);
				tvamount.setText(""+getTransactionAmt);
				tvtxnmode.setText(""+getTXN_Mode);
				tvtxnId.setText(""+getMerchantTransactionId);
				tvsecure.setText(""+getSecureHash);
				tvcustom.setText(""+getCustomVar);
				tvtxnid.setText(""+getTransactionId);
				tvtxnstatus.setText(""+getTransactionStatus);
				
				
			}
			else{
				

			}


		}catch(Exception e)
		{
			Log.e("", "Error OnResume : " + e.getMessage());
		}
		super.onResume();
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(Result.this , MainActivity.class);
		startActivity(intent);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(R.id.btreturn == v.getId())
		{
			Intent intent = new Intent(Result.this , MainActivity.class);
			startActivity(intent);
		}
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		try 
		{
			Bundle bundle = data.getExtras();
			transactionList = new ArrayList<Transaction>();
			transactionList = (ArrayList<Transaction>)bundle.getSerializable("DATA");
			
			Log.e("", "Result ---> TransactionList --> " + transactionList.size());
			Log.e("", "Result ---> TransactionList 111 --> " + transactionList);
			if(transactionList != null)
			{
				Toast.makeText(this, transactionList.get(0).getSTATUS() + "\n" + transactionList.get(0).getSTATUSMSG(), Toast.LENGTH_LONG).show();;

				Log.e("", "");
				System.out.println("A="+transactionList.get(0).getSTATUS()); // 200= success
				System.out.println("B="+transactionList.get(0).getMERCHANTKEY());
				System.out.println("C="+transactionList.get(0).getMERCHANTPOSTTYPE());
				System.out.println("D="+transactionList.get(0).getSTATUSMSG()); //  success or fail
				System.out.println("E="+transactionList.get(0).getTRANSACTIONAMT());
				System.out.println("F="+transactionList.get(0).getTXN_MODE());
				System.out.println("G="+transactionList.get(0).getMERCHANTTRANSACTIONID()); // order id
				System.out.println("H="+transactionList.get(0).getSECUREHASH());
				System.out.println("I="+transactionList.get(0).getCUSTOMVAR());
				System.out.println("J="+transactionList.get(0).getTRANSACTIONID());
				System.out.println("K="+transactionList.get(0).getTRANSACTIONSTATUS());
				
				
				
				
		        String transid = transactionList.get(0).getMERCHANTTRANSACTIONID();
		        String apTransactionID = transactionList.get(0).getTRANSACTIONID();
		    	String amount = transactionList.get(0).getTRANSACTIONAMT();
				String transtatus = transactionList.get(0).getTRANSACTIONSTATUS();
				String message = transactionList.get(0).getSTATUSMSG();
				String merchantid= "1" ; //provided by Airpay
				String username="8419743";		//provided by Airpay
				String sParam = transid + ":" + apTransactionID+ ":" + amount + ":" + transtatus + ":" + message + ":" + merchantid +":" + username;
				CRC32 crc = new CRC32();
		        crc.update(sParam.getBytes());
		        String sCRC = ""+crc.getValue();
		        System.out.println("Verified Hash= "+sCRC);
		        
		        
		        

				getStatus  = transactionList.get(0).getSTATUS();
				getMerchantKey  = transactionList.get(0).getMERCHANTKEY();
				getMerchantPostType  = transactionList.get(0).getMERCHANTPOSTTYPE();
				getStatusMsg  = transactionList.get(0).getSTATUSMSG();
				getTransactionAmt  = transactionList.get(0).getTRANSACTIONAMT();
				getTXN_Mode  = transactionList.get(0).getTXN_MODE();
				getMerchantTransactionId  = transactionList.get(0).getMERCHANTTRANSACTIONID();
				getSecureHash  = transactionList.get(0).getSECUREHASH();
				getCustomVar  = transactionList.get(0).getCUSTOMVAR();
				getTransactionId  = transactionList.get(0).getTRANSACTIONID();
				getTransactionStatus  = transactionList.get(0).getTRANSACTIONSTATUS();

				//getTushar  = b.getString("TUSHAR");

				//Log.e("","#################### getTushar "+getTushar);

				Log.e("", "## getStatus " + getStatus);
				Log.e("", "## getMerchantKey " + getMerchantKey);
				Log.e("", "## getMerchantPostType " + getMerchantPostType);
				Log.e("", "## getStatusMsg " + getStatusMsg);
				Log.e("", "## getTransactionAmt " + getTransactionAmt);
				Log.e("", "## getTXN_Mode " + getTXN_Mode);
				Log.e("", "## getMerchantTransactionId " + getMerchantTransactionId);
				Log.e("", "## getSecureHash " + getSecureHash);
				Log.e("", "## getCustomVar " + getCustomVar);
				Log.e("", "## getTransactionId " + getTransactionId);
				Log.e("", "## getTransactionStatus " + getTransactionStatus);

				
				tvstatus.setText(""+getStatus);
				tvmerchantkey.setText(""+getMerchantKey);
				tvmerposttyp.setText(""+getMerchantPostType);
				tvstatusmsg.setText(""+getStatusMsg);
				tvamount.setText(""+getTransactionAmt);
				tvtxnmode.setText(""+getTXN_Mode);
				tvtxnId.setText(""+getMerchantTransactionId);
				tvsecure.setText(""+getSecureHash);
				tvcustom.setText(""+getCustomVar);
				tvtxnid.setText(""+getTransactionId);
				tvtxnstatus.setText(""+getTransactionStatus);

				
				
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
