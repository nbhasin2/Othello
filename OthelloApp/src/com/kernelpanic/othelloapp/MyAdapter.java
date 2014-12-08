package com.kernelpanic.othelloapp;

import java.util.ArrayList;





import shared.BoardSpace;
import shared.SharedConstants;
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
		

			gridView = new View(context);

			gridView = inflater.inflate(R.layout.grid_item, null);


			ImageView flag = (ImageView) gridView .findViewById(R.id.flag);

			BoardSpace bspaceSingle = boardSpaceList.get(position);

			if (bspaceSingle.getGamePiece().equals(SharedConstants.PlayableItem.BLACK)) {
				flag.setImageResource(R.drawable.black_green);
			} else if (bspaceSingle.getGamePiece().equals(SharedConstants.PlayableItem.WHITE)) {
				flag.setImageResource(R.drawable.white_green);
			} else{
					flag.setImageResource(R.drawable.empty);
			}

		return gridView;
	}

	@Override
	public int getCount() {
		return boardSpaceList.size();
	}

	@Override
	public Object getItem(int position) {
		return boardSpaceList.get(position);
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
	

	// Call this method whenever new data is to be added to existing list.
	public void updateImageList(ArrayList<BoardSpace> newBoardspaceArraylist) {  

//		ArrayList<BoardSpace> bsl = new ArrayList<BoardSpace>();
//		for(BoardSpace bs : newBoardspaceArraylist)
//		{
//			System.out.print(bs.getGamePiece()+"\t");
//			bsl.add(bs);
//		}
//		
	
	       this.boardSpaceList = newBoardspaceArraylist;

	    this.notifyDataSetChanged();
	}

}