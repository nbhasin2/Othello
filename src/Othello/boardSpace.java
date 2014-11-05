package Othello;

public class boardSpace {

playableItem gamePiece; //Either blank, white or black

int row, col;

public boardSpace(int row, int col){
	this.row = row;
	this.col = col;
	clear();
	
}

/*
 * This method clears the board spot.
 */
public void clear() {
	gamePiece = playableItem.EMPTY;//this will make the board spot contain no game piece 
	
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

 public playableItem getGamePiece(){
	 return this.gamePiece;
	 
 }


}
