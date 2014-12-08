package com.kernelpanic.othelloapp;

import gameai.AIIdiot;
import gameai.AIMinimax;
import gameai.AIPickMiddleMove;
import gameai.AIRandom;
import gameui.Controller;
import gameui.Gameui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;














import othello.OthelloConsole;
import shared.BoardSpace;
import shared.SharedConstants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class OthelloGameActivity extends Activity{

	private GridView gridView;
	private Button btnRedo;
	private Button btnUndo;
	private Button btnSave;
	private Button btnLoad;

	private int Row = 8;
	private int Col = 8;
	private OthelloConsole othello;
	private Controller gamecontroller;
	private volatile ArrayList<BoardSpace> tempItemList = new ArrayList<BoardSpace>();
	private BoardSpace[][] playField;
	private MyAdapter myAdapter;
	private OthelloGameActivity activity = this;
	private String aiTypeString = SharedConstants.AIMinimax;
	private Runnable myRunnable;
	private Thread thread;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnRedo = (Button) findViewById(R.id.redoButton);
		btnUndo = (Button) findViewById(R.id.undoButton);
		btnSave = (Button) findViewById(R.id.saveButton);
		btnLoad = (Button) findViewById(R.id.loadButton);
		
		
		Intent intent = getIntent();
		
		if( intent.getExtras() != null)
		{
			aiTypeString = intent.getExtras().getString(SharedConstants.AITypeintent);
		}
		
		  myRunnable = new Runnable(){

		     public void run(){
		 		gamecontroller = new Controller();
				
		 		if(aiTypeString.equals(SharedConstants.AIIdiot))
		 		{
		 			othello = new OthelloConsole(new AIIdiot());
		 		}
		 		if(aiTypeString.equals(SharedConstants.AIMinimax))
		 		{
		 			othello = new OthelloConsole(new AIMinimax());
		 		}
		 		if(aiTypeString.equals(SharedConstants.AIPickMiddleStrategy))
		 		{
		 			othello = new OthelloConsole(new AIPickMiddleMove());
		 		}
		 		if(aiTypeString.equals(SharedConstants.AIRandom))
		 		{
		 			othello = new OthelloConsole(new AIRandom());
		 		}
				
				gamecontroller.setOthelloModel(othello);
				othello.gameOthelloInitializer(gamecontroller);
				othello.playOthello();
				playField = othello.getBoard().makeDeepCopy();
				gamecontroller.setActivity(activity);

				for(int i = 0; i < Row; i++){
					for(int j = 0; j < Col; j++){
						
						tempItemList.add(playField[i][j]);
					}
				}
		      
		     }
		   };

		   thread = new Thread(myRunnable);
		   thread.start();
		   
		
		myAdapter = new MyAdapter(this, tempItemList);

		gridView = (GridView) findViewById(R.id.gridView);

		gridView.setAdapter(myAdapter);
		

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				int[] values = new int[2]; 
		           v.getLocationOnScreen(values);
		           
		           int row_no=position/Col;
		            int col_no=position%Col;
		          gamecontroller.guiButtonClicked(row_no, col_no, true);

			}
		});
		
		initButtons();

	}
	
	
	public void initButtons()
	{
		//redo code
		btnRedo.setText("Redo");
		btnRedo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gamecontroller.redoMove();
			}
		});
		
		btnUndo.setText("Undo");
		btnUndo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gamecontroller.undoMove();
			}
		});
		
		btnLoad.setText("Load");
		btnLoad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gamecontroller.loadMove();
			}
		});


		btnSave.setText("Save");
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gamecontroller.saveMove();
			}
		});
	}

	/*
	 * This method is used to update the grid
	 */
	public void updateGrid(BoardSpace[][] bspace)
	{
		tempItemList.clear();
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				
				tempItemList.add(bspace[i][j]);
			}
		}
		myAdapter.updateImageList(tempItemList);

	}
	
	/**
	 * Game Over Popoup
	 */
	public void showPopup(){

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Game Over")
        .setMessage(this.othello.getScoreString())
        .setCancelable(false)
        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
		

	@Override
	public void onBackPressed() {
		thread.interrupt();
	    this.finish();
	    return;
	}

}