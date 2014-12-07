package com.kernelpanic.othelloapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.javacodegeeks.android.androidgridviewexample.R;

import othellomodel.*;
import othellomodel.gameai.AIRandom;
import othellomodel.gameui.AndroidButtonWithCoordinates;
import othellomodel.gameui.Controller;
import othellomodel.gameui.Gameui;
import othellomodel.othello.BoardSpace;
import othellomodel.othello.OthelloConsole;
import othellomodel.shared.SharedConstants.PlayableItem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements Observer{

	GridView gridView;
	int Row = 8;
	int Col = 8;
	private OthelloConsole othello;
	Controller gamecontroller;
	ArrayList<BoardSpace> tempItemList = new ArrayList<BoardSpace>();
	private BoardSpace[][] playField;
	private MyAdapter myAdapter;
	private MainActivity activity = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		gamecontroller = new Controller();
//		Gameui othellogui = new Gameui(gamecontroller);
		othello = new OthelloConsole(new AIRandom());
		gamecontroller.setOthelloModel(othello);
		gamecontroller.setActivity(this);
		othello.gameOthelloInitializer(gamecontroller);
		playField = othello.getBoard().getPlayField();
		
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				
				tempItemList.add(playField[i][j]);//4x4 gameboard where every space is a new boardSpace object 
			}
		}
		
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
		            Toast.makeText(getBaseContext(), 
		                    "pic" + (position + 1) + " selected" + "ID" + id + "ROW - "+ row_no+" COL - "+col_no, 
		                    Toast.LENGTH_SHORT).show();
		          gamecontroller.guiButtonClicked(row_no, col_no, true);

			}
		});

	}

	public void updateGrid(BoardSpace[][] bspace)
	{
//		playField = bspace;
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				
				tempItemList.add(bspace[i][j]);//4x4 gameboard where every space is a new boardSpace object 
			}
		}
		myAdapter.setBoardSpaceList(tempItemList);
		myAdapter.notifyDataSetChanged();
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}

}