package artificialIntelegence;
/**
 * 
 * 
 * @author Zacharie
 *
 */
public class AI extends Player {
	
	private AIStrategy strategy;
	/**
	 * Creates a player AI based on a chosen strategy
	 * @param name is the name given to the player
	 * @param is the game the player is playing 
	 * @param is the strategy that the AI will play 
	 */
	public AI(String name, Game game, String strat)
	{
		super(name,game);
		this.setStrategy(strat);
	}
	/**
	 * 
	 * @param String s is the name of the strategy you want the AI to be playing
	 */
	public void setStrategy(String s)
	{
		if(s.equals("random") || s.equals("R"))
		{
			strategy = new RandomStrategy();
		}
		else if(s.equals("minmax") || s.equals("M"))
		{
			strategy = new MinMaxStrategy();
		}
		else
		{
			System.out.println("Error no such strategy");
		}
	}

	/**
	 * will play the turn based on which strategy was chosen
	 * 
	 */
	@Override
	public void playTurn() {
		int row;
		int col;
		do
		{
			strategy.makeMove(getGame());
			row = strategy.getRow();
			col = strategy.getCol();
			
		}while(!(getGame().isValid(row,col)));
		getGame().makeMove(row,col);
	}
	
	
}
