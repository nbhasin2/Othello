package gameai;

import java.util.ArrayList;

import shared.SharedConstants;
import gamemodel.GameConsoleInterface;

public class AIMinimax implements  AIStrategy{

	public AIMinimax() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * A recursive function that checks for the best possible solutions based on a given depth to search through
	 * most of the logic was found from this website:
	 * ink https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
	 * 
	 *  
	 * game the game the AI is playing
	 *  
	 * level the depth of which the AI is looking through a higher depth means a better result
	 * 
	 * alpha the best maximum score used for prunning
	 * 
	 * beta the best minimum score used for prunning
	 * @return string coordinate picked that will have minimal risk
	 */
	@Override
	public String makeMove(GameConsoleInterface game) {
		return minimaxStrategy(game,3,Integer.MIN_VALUE +1,Integer.MAX_VALUE -1);
	}
	
	private String minimaxStrategy(GameConsoleInterface game,int level,int alpha,int beta){
		GameConsoleInterface tempGame;
	    int currentScore;
	    String currentScoreStr;
	    int bestRow = -1;
	    int bestCol = -1;
	    int currRow = -1;
	    int currCol = -1;
	    ArrayList<String> nextMoves = game.getAvailableSolutions(level%2);
	    if (nextMoves.isEmpty() || level == 0 || game.isGameOver()) {
	        currentScore = game.evaluate()[0]; 
	        return bestRow + "," + bestCol + "," + currentScore;
	    } 
	    else {
	         for (String move : nextMoves) {
	        	currRow = Integer.parseInt(move.split(",")[0]);
	        	currCol = Integer.parseInt(move.split(",")[1]);
	        	tempGame = (GameConsoleInterface)game;
	        	tempGame.moveSet(currRow, currCol, level);
	        	
	        	if (level%2 != 0) { // since AI starts on 3 if the modulus is not even then it is AI's Turn
	        						//AI is maximizing player
	               currentScoreStr = minimaxStrategy(tempGame,level - 1,alpha,beta);
	               currentScore = Integer.parseInt(currentScoreStr.split(",")[2]);
	               if (currentScore > alpha) 
	               {
	                  alpha= currentScore;
	                  bestRow = currRow;
	  				  bestCol = currCol;
	                  
	               }
	            } else {  //If level is even it is  human's is minimizing player
	            	currentScore = Integer.parseInt(minimaxStrategy(tempGame,level - 1,alpha,beta).split(",")[2]);
	               if (currentScore < beta) {
	            	   beta = currentScore;
	            	   bestRow = currRow;
	            	   bestCol = currCol;
	               }
	            }
	           tempGame.undoMove(currRow, currCol, level);
	           if (alpha >= beta) break;
	         }
	     }
	     return bestRow + "," + bestCol + "," + ((level%2 != 0) ? alpha: beta);
	}
	@Override
	public String toString(){
		return SharedConstants.AIMinimax;
	}

}
