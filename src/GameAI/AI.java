package GameAI;

import java.util.ArrayList;
import java.util.Random;

import Shared.SharedConstants;

public class AI {
	private String GameType;
	private Random randomGenerator;
	private String AIType;
	/*
	 * constructor for AI using strategy
	 */
	public AI(String GameType, String AIType)
	{
		this.GameType = GameType;
		this.setAIType(AIType);
		randomGenerator = new Random();
	}
	
	public String randomAI(int [][]board)
	{
		if(GameType == SharedConstants.TicTacToe)
		{
			return ticTacToeRandom(board);
		}	
		else if(GameType == SharedConstants.Othello)
		{
			return othelloRandom(board);
		}else
		{
			return SharedConstants.ErrorMessage;
		}
		
	}
	
	/*
	 * Random cordinate generator
	 * for Tic Tac Toe game.
	 * Eg : 1 1, 2 2, 3 3 etc.
	 */
	public String ticTacToeRandom(int [][]board)
	{
		
		ArrayList<String> freeSpaces = new ArrayList<String>();
//		System.out.println("freespaces 1" +freeSpaces);
		int row = SharedConstants.ROWS;
		int col = SharedConstants.COLS;
		for (row = 0; row < SharedConstants.ROWS; ++row) {
			for (col = 0; col < SharedConstants.COLS; ++col) {
				if(board[row][col] == SharedConstants.EMPTY)
				{
					freeSpaces.add(row+"-"+col);
//					System.out.println("freespaces "+freeSpaces);
//					System.out.println("row - "+ row + " col - " +col);
//					System.out.println(row+"-"+col);
				}
			}
			
		}
//		debug 
//		System.out.println("FreeSpaces - " + freeSpaces.size() +"\n"+freeSpaces + " " + randomGenerator.nextInt(freeSpaces.size()));
		int index = randomGenerator.nextInt(freeSpaces.size());
		return freeSpaces.get(index);
	}
	
	/*
	 * Random coordinate generator
	 * for Othello game.
	 * 
	 */
	public String othelloRandom(ArrayList<String> validMoves)
	{
		int index = randomGenerator.nextInt(validMoves.size());
		return validMoves.get(index);
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
