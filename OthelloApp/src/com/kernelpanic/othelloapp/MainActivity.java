package com.kernelpanic.othelloapp;

import shared.SharedConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView topText;
	private Button minimaxBtn, randomBtn, middleBtn, idiotBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		topText = (TextView) findViewById(R.id.textViewTop);
		topText.setText("Pick a strategy for Othello" );
		
		minimaxBtn = (Button) findViewById(R.id.Minimax);
		randomBtn = (Button) findViewById(R.id.Random);
		middleBtn = (Button) findViewById(R.id.Middle);
		idiotBtn = (Button) findViewById(R.id.Idiot);
		
		minimaxBtn.setText(shared.SharedConstants.AIMinimax);
		randomBtn.setText(shared.SharedConstants.AIRandom);
		middleBtn.setText(shared.SharedConstants.AIPickMiddleStrategy);
		idiotBtn.setText(shared.SharedConstants.AIIdiot);
		final Intent i = new Intent(this, OthelloGameActivity.class);
		
		minimaxBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				i.putExtra(SharedConstants.AITypeintent, SharedConstants.AIMinimax);
				startActivity(i); 
				
			}
		});
		
		randomBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				i.putExtra(SharedConstants.AITypeintent, SharedConstants.AIRandom);
				startActivity(i); 
				
			}
		});
		
		middleBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				i.putExtra(SharedConstants.AITypeintent, SharedConstants.AIPickMiddleStrategy);
				startActivity(i); 
				
			}
		});
		
		idiotBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				i.putExtra(SharedConstants.AITypeintent, SharedConstants.AIIdiot);
				startActivity(i); 
				
			}
		});
		
		
		
	}



}
