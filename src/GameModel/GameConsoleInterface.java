package GameModel;

import java.util.ArrayList;

public abstract class GameConsoleInterface {

	public GameConsoleInterface()
	{
		
	}
	
	public abstract ArrayList<String> getAvailableSolutions();
	public abstract  int evaluate();
}
