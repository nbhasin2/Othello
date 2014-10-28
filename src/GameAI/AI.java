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
	
	public String randomStrategy(ArrayList<String> validMoves)
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
			return minimaxStrategy(game);
		}
		else
		{
			return "";
		}
	}
	
	
	private String minimaxStrategy(GameConsoleInterface game) {
		
		return null;
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
