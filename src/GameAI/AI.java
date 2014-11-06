package GameAI;

import java.util.ArrayList;
import java.util.Random;

import Shared.SharedConstants;
import GameModel.GameConsoleInterface;
import Othello.OthelloConsole;
/**
 * 
 * @author Zacharie
 * AI funcitons that game will use, depending on the strategies called.
 */

public class AI {
	private String GameType;
	private Random randomGenerator;
	private String AIType;
	private int [][] TicTacToeBoard;
	private ArrayList<String> othelloValidMoves;
	
	
	
	/*
	 * constructor for AI using strategy
	 */
	public AI( String AIType)
	{
		
		if(AIType == SharedConstants.AIRandom)
		{
			long ranSeed = System.nanoTime();
			randomGenerator = new Random(ranSeed);
		}

		this.setAIType(AIType);
		
	}
	public String makeMove(GameConsoleInterface game )   
	{
		if(AIType.equals(SharedConstants.AIRandom))
		{
			return randomStrategy(game.getAvailableSolutions(-1));
		}
		else if(AIType.equals(SharedConstants.AIMinimax))
		{
			return minimaxStrategy(game,3,Integer.MIN_VALUE+1,Integer.MAX_VALUE-1);
		}
		else
		{
			return "";
		}
	}
	
	private String randomStrategy(ArrayList<String> validMoves)
	{
		int index = randomGenerator.nextInt(validMoves.size());
		return validMoves.get(index);
	}
	
	private String minimaxStrategy(GameConsoleInterface game,int level,int alpha,int beta)   
	{
		
		GameConsoleInterface tempGame;
		
		// mySeed is maximizing; while oppSeed is minimizing
	   
	    int currentScore;
	    String currentScoreStr;
	    int bestRow = -1;
	    int bestCol = -1;
	    int currRow = -1;
	    int currCol = -1;
	    ArrayList<String> nextMoves = game.getAvailableSolutions(level%2);
	    if (nextMoves.isEmpty() || level == 0 || game.isGameOver()) {
	    	// Game over or depth reached, evaluate score
	        currentScore = game.evaluate(); 
	        return bestRow + "," + bestCol + "," + currentScore;
	    } 
	    else 
	    {
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

	/**
	 * @return the aIType
	 */
	public String getAIType() {
		return AIType;
	}

	/**
	 * @param aIType the aIType to set
	 */
	public void setAIType(String aIType) {
		AIType = aIType;
	}
}
