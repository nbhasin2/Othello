package gameai;

import java.util.ArrayList;

import shared.SharedConstants;
import gamemodel.GameConsoleInterface;

public class AIPickMiddleMove implements AIStrategy{

	public AIPickMiddleMove() {
		
	}
	/**
	 *
	 *validMoves a array list of valid moves that it will choose from
	 * @return a string coordinate picked depending on the size of the array of avaible moves
	 * it will pick the one in the middle of the array;
	 */
	@Override
	public String makeMove(GameConsoleInterface game) {
		ArrayList<String> validMoves = game.getAvailableSolutions(-1);
		if(validMoves.size() != 0){
			int index = (validMoves.size()/2);
			return validMoves.get(index);
		}
		else{
			return SharedConstants.INVALID+","+SharedConstants.INVALID;
		}
	}
	@Override
	public String toString(){
		return SharedConstants.AIPickMiddleStrategy;
	}

}
