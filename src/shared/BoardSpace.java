package shared;

import java.io.Serializable;

import shared.SharedConstants;

public class BoardSpace implements Serializable{

	private SharedConstants.PlayableItem gamePiece; //Either blank, white or black

	private int row, col;
	
	public BoardSpace(int row, int col){
		this.row = row;
		this.col = col;
		clear();
		
	}
	
	/*
	 * This method clears the board spot.
	 */
	public void clear() {
		setGamePiece(SharedConstants.PlayableItem.EMPTY);//this will make the board spot contain no game piece 
		
	}
	
	/*
	 * This method adds an item of type WHite black or empty.
	 */
	public void putItem() {
		switch(getGamePiece()) {
		case WHITE: System.out.print(" O "); break;
		case BLACK: System.out.print(" * "); break;
		case EMPTY: System.out.print("   "); break;
		}
		
	}
	
	 public SharedConstants.PlayableItem getGamePiece(){
		 return this.gamePiece;
		 
	 }

	public void setGamePiece(SharedConstants.PlayableItem gamePiece) {
		this.gamePiece = gamePiece;
	}	

}
