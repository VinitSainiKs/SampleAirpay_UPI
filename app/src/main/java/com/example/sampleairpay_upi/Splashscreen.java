package com.example.sampleairpay_upi;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Splashscreen extends Activity implements OnClickListener {


	private Button btget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen_activity);

		//this.overridePendingTransition(R.anim.slideup, R.anim.slideup);

		btget=(Button)findViewById(R.id.btget);
		btget.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(v.getId() == R.id.btget)
		{

			finish();
			Intent intent = new Intent(Splashscreen.this,MainActivity.class);
			//intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}


	}


}
