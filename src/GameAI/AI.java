package GameAI;

import java.util.ArrayList;
import java.util.Random;

import Shared.SharedConstants;
import GameModel.GameConsoleInterface;
import Othello.OthelloConsole;


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
		othelloValidMoves = new ArrayList<String>();
		
		this.setAIType(AIType);
		randomGenerator = new Random();
	}
	/*
	public void setTicTacToeBoard(int [][]board)
	{
		this.TicTacToeBoard = board;
	}
	
	public void setOthelloValidMoves(ArrayList<String> validMoves)
	{
		this.othelloValidMoves = validMoves;
	}*/
	
	private String randomStrategy(ArrayList<String> validMoves)
	{
		int index = randomGenerator.nextInt(validMoves.size());
		return validMoves.get(index);
	}

	public String makeMove(GameConsoleInterface game )   
	{
		if(AIType.equals(SharedConstants.AIRandom))
		{
			return randomStrategy(game.getAvailableSolutions());
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
	    ArrayList<String> nextMoves = game.getAvailableSolutions();
	    if (nextMoves.isEmpty() || level == 0) {
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
	        	tempGame.moveSet(currRow, currCol, level%2);
	            if (level%2 != 0) {  // mySeed (computer) is maximizing player
	               currentScoreStr = minimaxStrategy(tempGame,level - 1,alpha,beta);
	               currentScore = Integer.parseInt(currentScoreStr.split(",")[2]);
	               if (currentScore > alpha) 
	               {
	                  alpha= currentScore;
	                  bestRow = currRow;
	  				  bestCol = currCol;
	                  
	               }
	            } else {  // oppSeed is minimizing player
	            	currentScore = Integer.parseInt(minimaxStrategy(tempGame,level - 1,alpha,beta).split(",")[2]);
	               if (currentScore < beta) {
	            	   beta = currentScore;
	            	   bestRow = currRow;
	            	   bestCol = currCol;
	               }
	            }
	           tempGame.undoMove(currRow, currCol, level%2);
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
