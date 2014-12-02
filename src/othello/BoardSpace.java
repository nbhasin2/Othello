package othello;

import java.io.Serializable;

import shared.SharedConstants;

public class BoardSpace implements Serializable{

	SharedConstants.PlayableItem gamePiece; //Either blank, white or black

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
		gamePiece = SharedConstants.PlayableItem.EMPTY;//this will make the board spot contain no game piece 
		
	}
	
	/*
	 * This method adds an item of type WHite black or empty.
	 */
	public void putItem() {
		switch(gamePiece) {
		case WHITE: System.out.print(" O "); break;
		case BLACK: System.out.print(" * "); break;
		case EMPTY: System.out.print("   "); break;
		}
		
	}
	
	 public SharedConstants.PlayableItem getGamePiece(){
		 return this.gamePiece;
		 
	 }	

}
