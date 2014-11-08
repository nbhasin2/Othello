package play;

import javax.swing.JOptionPane;
/**
 * @author Nishant
 * This class is gui launcher for Othello Game. You can also use console but 
 * since we wanted to have a java executable thats why this class was created.
 * 
 * Testing for this class was done manually. No unit test were introduced as this is
 * gui and what we did was checked what happens when you click on random, minimax or cancel 
 * and compared the expected behavior. If you click on minimax it should start the minimax
 * and if you click on random then strategy for the game should be random move by AI.
 * Cancel is used to exit the game. The red button doesn't do anything. 
 */


import othello.OthelloConsole;
import shared.SharedConstants;

public class LauncherOthelloGUI {
	
	public static void main(String[] args) {
		int response = -1;
		OthelloConsole othello = null;
		do{
		String[] options = new String[] {"Random", "Minimax", "Cancel"};
		response = JOptionPane.showOptionDialog(null, "Select the AI type", "AI",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options, options[0]);
		if(response==0)
		{
			othello = new OthelloConsole(SharedConstants.AIRandom);
		}else if(response==1)
		{
			othello = new OthelloConsole(SharedConstants.AIMinimax);
		}else if(response==2)
		{
			System.exit(0);
		}		
		if(othello!=null)
		{
			othello.playOthello();
		}
		}while(response==-1);
	}

}
