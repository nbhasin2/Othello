package gameai;

import java.util.ArrayList;
import java.util.Random;

import shared.SharedConstants;
import gamemodel.GameConsoleInterface;

public class AIRandom implements AIStrategy {
	private Random randomGenerator;
	
	public AIRandom() {
	
	}
	/**
	 * 
	 * 
	 * validMoves a array list of valid moves that it will choose from
	 * @return a string coordinate picked randomly from the valid moves
	 */
	@Override
	public String makeMove(GameConsoleInterface game) {
		ArrayList<String> validMoves = game.getAvailableSolutions(-1);
		if(validMoves.size() != 0){
			long ranSeed = System.nanoTime();
			randomGenerator = new Random(ranSeed);
			int index = randomGenerator.nextInt(validMoves.size());
			return validMoves.get(index);
		}
		else{
			return "-1,-1";
		}
	}
	@Override
	public String toString(){
		return SharedConstants.AIRandom;
	}

}
