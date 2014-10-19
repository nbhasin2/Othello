package artificialIntelegence;
/**
 * 
 * 
 * @author Zacharie
 * the interface for strategy
 */
public interface AIStrategy {
	
	
	
	//public void makeMove(Game g);
	
	public int getRow();
	
	public int getCol();

	public void makeMove(Game game);

	
}
