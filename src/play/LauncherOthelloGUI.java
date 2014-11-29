package play;

import gameai.*;
import gameui.Controller;
import gameui.Gameui;

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

	private  int buttonAIRandom = 0;
	private  int buttonAIMinimax = 1;
	private  int buttonAIPickMiddle = 2;
	private  int buttonCancelClose = 3;
	private  int buttonResponsenil = -1;
	public static void main(String[] args) {
		LauncherOthelloGUI guiLauncherOthello = new LauncherOthelloGUI();
		guiLauncherOthello.initGuiLauncer();
	}


	/*
	 * This function is the initializer for the othello gui. This will be used inside our console
	 * launcher to provide an option to launch the gui directly.
	 * 
	 */
	public void initGuiLauncer() {
		
		int response = buttonResponsenil;
		OthelloConsole othello = null;
		do{
			String[] options = new String[] {SharedConstants.AIRandom, SharedConstants.AIMinimax,SharedConstants.AIPickMiddleStrategy, SharedConstants.CancelButton};
			response = JOptionPane.showOptionDialog(null, "Select the AI type", "AI",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);
			/*
			 * If game controller and othello gui are not inside each if statement, the frame
			 * will be initialized without any AI and you will be left with a blank game
			 * grid that is uncloseable with the exit button. This is a small known bug where 
			 * duplicating a small amount of code will fix the bug, allowing the frame to close
			 * properly. This is intended as per our design, but we will implement a fix looking
			 * forward to our next milestone.
			 */
			if(response==buttonAIRandom)
			{
				Controller gamecontroller = new Controller();
				Gameui othellogui = new Gameui(gamecontroller);
				othello = new OthelloConsole(new AIRandom());
				gamecontroller.setOthelloModel(othello);
				othello.gameOthelloInitializer(gamecontroller);

			}else if(response==buttonAIMinimax)
			{			
				Controller gamecontroller = new Controller();
				Gameui othellogui = new Gameui(gamecontroller);
				othello = new OthelloConsole(new AIMinimax());
				gamecontroller.setOthelloModel(othello);
				othello.gameOthelloInitializer(gamecontroller);
			}else if(response==buttonAIPickMiddle)
			{			
				Controller gamecontroller = new Controller();
				Gameui othellogui = new Gameui(gamecontroller);
				othello = new OthelloConsole(new AIPickMiddleMove());
				gamecontroller.setOthelloModel(othello);
				othello.gameOthelloInitializer(gamecontroller);
			}
			else if(response==buttonCancelClose)
			{
				System.exit(0);
			}		
			if(othello!=null)
			{
				othello.playOthello();
			}

		}while(response==buttonResponsenil);

	}

}
