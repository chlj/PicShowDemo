package com.pic.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Administrator
 * 
 */
public class TextViewClickChangeColorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);

		TextView add_cart_layout = (TextView) findViewById(R.id.add_cart_layout);
		add_cart_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		findViewById(R.id.txt1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Pic1Activity.class));

			}
		});
		findViewById(R.id.txt2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Pic2Activity.class));

			}
		});
	}

}
