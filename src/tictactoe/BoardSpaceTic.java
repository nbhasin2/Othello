package tictactoe;
import shared.BoardSpace;

public class BoardSpaceTic extends BoardSpace  {

	public BoardSpaceTic(int row,int col) {
		super(row,col);
	}
	
	@Override
	public void putItem() {
		switch(getGamePiece()) {
		case WHITE: System.out.print(" O "); break;
		case BLACK: System.out.print(" X "); break;
		case EMPTY: System.out.print("   "); break;
		}
	}

}
