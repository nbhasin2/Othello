package com.kernelpanic.othelloapp;

import java.util.ArrayList;

import com.javacodegeeks.android.androidgridviewexample.R;

import othellomodel.com.connect.four.boardSpace;
import othellomodel.othello.BoardSpace;
import othellomodel.shared.SharedConstants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<BoardSpace> boardSpaceList;

	public MyAdapter(Context context, ArrayList<BoardSpace> bSpace) {
		this.context = context;
		this.boardSpaceList = bSpace;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			gridView = inflater.inflate(R.layout.grid_item, null);


			ImageView flag = (ImageView) gridView .findViewById(R.id.flag);

			BoardSpace mobile = boardSpaceList.get(position);

			if (mobile.getGamePiece().equals(SharedConstants.PlayableItem.BLACK)) {
				flag.setImageResource(R.drawable.black_green);
			} else if (mobile.getGamePiece().equals(SharedConstants.PlayableItem.WHITE)) {
				flag.setImageResource(R.drawable.white_green);
			} else{
					flag.setImageResource(R.drawable.empty);
			}

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

	@Override
	public int getCount() {
		return boardSpaceList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public ArrayList<BoardSpace> getBoardSpaceList() {
		return boardSpaceList;
	}

	public void setBoardSpaceList(ArrayList<BoardSpace> boardSpaceList) {
		this.boardSpaceList = boardSpaceList;
	}

}