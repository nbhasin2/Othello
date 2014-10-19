package artificialIntelegence;
/**
 * 
 * 
 * @author Zacharie
 *
 */
public abstract class Player {
	
	private String name;
	private Game game;
	
	/**
	 * Contrustor for the player
	 * 
	 * @param n is a String class of the name of the player,
	 * @param g is Game class of the game that the player is playing
	 */
	
	public Player (String n,Game g)
	{
		this.name = n;
		this.setGame(g);
	}
	
	/**
	 * playTurn will ask the players to make a move, and it will verify the
	 * validation of the move with the rules of the game or the availability of
	 * the board. 
	 */
	public abstract void playTurn();
	
	/**
	 * Will return the name of the player
	 * @return String representation of the name of the player
	 */
	public String getName()
	{
		return this.name;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
}
