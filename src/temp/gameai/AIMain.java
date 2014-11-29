package gameai;

import gamemodel.GameConsoleInterface;
/**
 * 
 * @author Zacharie
 * AI funcitons that game will use, depending on the strategies called.
 */

public class AIMain {
	private AIStrategy strategy;
	/**
	 * constructor for AI using strategy
	 */
	public AIMain(AIStrategy strategy){
		this.setAIType(strategy);
	}
	/**
	 * 
	 * @param 
	 * game the game that the AI is playing
	 * @return a string coordinate that return the move it chooses to make based on the set AI 
	 */
	public String makeMove(GameConsoleInterface game )  {
		return strategy.makeMove(game);
	}
	/**
	 * @return the aIType
	 */
	public AIStrategy getAIType() {
		return this.strategy;
	}
	/**
	 * @param strategy the aIType to set
	 */
	public void setAIType(AIStrategy strategy) {
		this.strategy = strategy;
	}
}
