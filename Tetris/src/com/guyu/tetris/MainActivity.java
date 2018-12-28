package com.guyu.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
    Button rank1, rank2, rank3, rank4, rank5;
    TextView rank2h,rank3h, rank4h, rank5h, rank6h;
    CacheUtils cacheUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		rank1 = (Button)findViewById(R.id.rank1);
		rank2 = (Button)findViewById(R.id.rank2);
		rank3 = (Button)findViewById(R.id.rank3);
		rank4 = (Button)findViewById(R.id.rank4);
		rank5 = (Button)findViewById(R.id.rank5);
		rank2h = (TextView)findViewById(R.id.rank2h);
		cacheUtils = new CacheUtils(this, "UserInfo");
		String maxString1 = "";
		try {
			maxString1 = cacheUtils.getValue("highestScore" + 1, String.valueOf(0));
		} catch (Exception e) {
			Log.e("Main", e.toString());
		}
		int highestScore1;
		try {
			highestScore1 = Integer.parseInt(maxString1.toString());
		} catch (NumberFormatException e) {
			highestScore1 = 0;
		}
		rank2h.setText("Rank1 Highest: "+highestScore1+"");
		rank3h = (TextView)findViewById(R.id.rank3h);
		String maxString2 = "";
		try {
			maxString2 = cacheUtils.getValue("highestScore" + 2, String.valueOf(0));
		} catch (Exception e) {
			Log.e("Main", e.toString());
		}
		int highestScore2;
		try {
			highestScore2 = Integer.parseInt(maxString2.toString());
		} catch (NumberFormatException e) {
			highestScore2 = 0;
		}
		rank3h.setText("Rank2 Highest: "+highestScore2+"");
		rank4h = (TextView)findViewById(R.id.rank4h);
		String maxString3 = "";
		try {
			maxString3 = cacheUtils.getValue("highestScore" + 3, String.valueOf(0));
		} catch (Exception e) {
			Log.e("Main", e.toString());
		}
		int highestScore3;
		try {
			highestScore3 = Integer.parseInt(maxString3.toString());
		} catch (NumberFormatException e) {
			highestScore3 = 0;
		}
		rank4h.setText("Rank3 Highest: "+highestScore3+"");
		rank5h = (TextView)findViewById(R.id.rank5h);
		String maxString4 = "";
		try {
			maxString4 = cacheUtils.getValue("highestScore" + 4, String.valueOf(0));
		} catch (Exception e) {
			Log.e("Main", e.toString());
		}
		int highestScore4;
		try {
			highestScore4 = Integer.parseInt(maxString4.toString());
		} catch (NumberFormatException e) {
			highestScore4 = 0;
		}
		rank5h.setText("Rank4 Highest: "+highestScore4+"");
		rank6h = (TextView)findViewById(R.id.rank6h);
		String maxString5 = "";
		try {
			maxString5 = cacheUtils.getValue("highestScore" + 5, String.valueOf(0));
		} catch (Exception e) {
			Log.e("Main", e.toString());
		}
		int highestScore5;
		try {
			highestScore5 = Integer.parseInt(maxString5.toString());
		} catch (NumberFormatException e) {
			highestScore5 = 0;
		}
		rank6h.setText("Rank5 Highest: "+highestScore5+"");
		rank1.setOnClickListener(this);
		rank2.setOnClickListener(this);
		rank3.setOnClickListener(this);
		rank4.setOnClickListener(this);
		rank5.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, Gamemain.class);
		switch (v.getId()) {
		    case R.id.rank1:
		        intent.putExtra("rank", 1);
		        break;
		    case R.id.rank2:
		        intent.putExtra("rank", 2);
		        break;
		    case R.id.rank3:
		        intent.putExtra("rank", 3);
		        break;
		    case R.id.rank4:
		        intent.putExtra("rank", 4);
		        break;
		    case R.id.rank5:
		        intent.putExtra("rank", 5);
		        break;
		    default:
                break;
		}
		startActivity(intent);
	}
}
