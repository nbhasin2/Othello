package Othello;

public class boardSpace {

playableItem gamePiece; //Either blank, white or black

int row, col;

public boardSpace(int row, int col){
	this.row = row;
	this.col = col;
	clear();
	
}

public void clear() {
	gamePiece = playableItem.EMPTY;//this will make the board spot contain no game piece 
	
}

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
