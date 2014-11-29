package gameui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import othello.OthelloConsole;

public class Controller extends Object implements Observer{

	private OthelloConsole othelloModel;
	private Gameui othelloUi;
	private boolean isGUI = false; 
	
	/**
	 * @author Nishant
	 * @param model
	 * @param othelloGui
	 * 
	 * The constructor of Controller has access to both
	 * othelloconsole class that acts as the model and 
	 * Gamui that is the gui from where we get updates (button listener).
	 * Once we get updates from GameUi, controler queries the model for
	 * available moves and updates UI accordingly.
	 */
	public Controller(OthelloConsole model, Gameui othelloGui)
	{
		othelloModel = model;
		othelloUi = othelloGui;
	}
	
	/*
	 * An empty constructor to initialize othelloModel and OthelloUi 
	 */
	public Controller()
	{
		othelloModel = null;
		othelloUi = null;
	}
	
	/**
	 * @author Nishant
	 * 
	 * This method is called when gui button is clicked by the 
	 * GameUI console. Here controller talks with model 
	 * and updates the variable accordingly. 
	 * An observer could also have been used but since we have 
	 * access to othello model we can easily change the value of
	 * X Y (row,col) moves.
	 */
	public void guiButtonClicked(int row, int col, boolean value)
	{
		if(othelloModel!=null)
		{
			othelloModel.setGameuiMoveX(row);
			othelloModel.setGameuiMoveY(col);
			othelloModel.setGameuiMove(true);
		}
	}
	
	
	/**
	 * @author Nishant
	 * null
	 * @return
	 * 
	 * GameUi asks for current score from controller to update 
	 * the GUI with value. Say the score is unavailable due to 
	 * any reason we send an error string otherwise we check
	 * othello model being equal to null and do the same thing.
	 * If the model is not null we get the result and return 
	 * a string with both score for white and black. 
	 * This also checks if the game is over and sends message to 
	 * othello gui to show the popup.
	 */
	public String guiAskForCurrentScore()
	{
		if(othelloModel==null)
			return "|";
		
		if(othelloModel.getCurrentScore()!=null)
		{
			return"White : "+othelloModel.getCurrentScore()[1]+" - Black :	 "+othelloModel.getCurrentScore()[2];
		}else
		{
			return"|";
		}
	}
	
	/**
	 * @author Nishant
	 * @return 
	 * 
	 * This method gets called when gui wants to know available solutions.
	 * The reason gui asks for available solution is so that it can print out 
	 * the the yellow green tiles on the board for user to easily make his
	 * move. 
	 */
	public ArrayList<String> guiAskForAvailableSolutions()
	{
		ArrayList<String> tempAvailableSolutions = new ArrayList<String>();

		if(othelloModel==null)
			return tempAvailableSolutions;
		
		for(int l=0; l<othelloModel.getAvailableSolutions(0).size(); l++)
		{
			tempAvailableSolutions.add(othelloModel.getAvailableSolutions(0).get(l).split(",")[0]+
					","+othelloModel.getAvailableSolutions(0).get(l).split(",")[1]);
		}
		
		return tempAvailableSolutions;
	}
	
	/**
	 * @author Nishant
	 * This simply checks the model to see if the game is over
	 * and if tru simply shows the get score string 
	 * and send message to othelloUi saying it has won.
	 */
	public void checkHasGameOver()
	{
		if(othelloModel.isGameOver())
		{
			othelloUi.showPopup(othelloModel.getScoreString());
		}
	}
	
	/**
	 * @author Nishant
	 * This method is used to undo a move and redraw the gui
	 * on the board.
	 */
	public void undoMove()
	{
		othelloModel.undoMove();
	}
	
	/**
	 * @author Nishant
	 * This method is used to redo a move if a person has not 
	 * made a move after undo.
	 */
	public void redoMove()
	{
		othelloModel.redoMove();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof ArrayList<?>)
		{
			ArrayList<String> listOfItems = (ArrayList<String>)arg1;
			if(othelloUi!=null&&listOfItems!=null)
			{
				othelloUi.updateGrid(listOfItems);
			}
		}
		
		if(arg1 instanceof String)
		{
			checkHasGameOver();
		}
	}

	/**
	 * @return the othelloModel
	 */
	public OthelloConsole getOthelloModel() {
		return othelloModel;
	}

	/**
	 * @param othelloModel the othelloModel to set
	 */
	public void setOthelloModel(OthelloConsole othelloModel) {
		this.othelloModel = othelloModel;
	}

	/**
	 * @return the othelloUi
	 */
	public Gameui getOthelloUi() {
		return othelloUi;
	}

	/**
	 * @param othelloUi the othelloUi to set
	 */
	public void setOthelloUi(Gameui othelloUi) {
		this.othelloUi = othelloUi;
	}

	/**
	 * @return the isGUI
	 */
	public boolean isGUI() {
		return isGUI;
	}

	/**
	 * @param isGUI the isGUI to set
	 */
	public void setGUI(boolean isGUI) {
		this.isGUI = isGUI;
	}

}
