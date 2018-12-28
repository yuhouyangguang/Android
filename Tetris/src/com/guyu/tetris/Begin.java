package com.guyu.tetris;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Begin extends Activity {
    Button start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_begin);
		start = (Button)findViewById(R.id.button);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				gotoRank();
			}
		});
	}
	
	public void gotoRank() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
