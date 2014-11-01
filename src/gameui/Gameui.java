package gameui;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;




import javax.swing.JButton;
import javax.swing.JFrame;

import Othello.OthelloConsole;
import Othello.boardSpace;
import Othello.playableItem;

public class Gameui {
	
	private static int gridSize = 4;
	private int Row = gridSize;
	private int Col = gridSize;
	private ArrayList<ButtonWithCoordinates> guiButtons;
	private boardSpace[][] playField;	
	private ActionListener listener;
	private OthelloConsole othelloconsole;
	
	//Constructor 
	public Gameui(OthelloConsole othelloconsole)
	{
		this.othelloconsole = othelloconsole;
		listener = new MyListener();
		guiButtons = new ArrayList<ButtonWithCoordinates>();
		this.playField = playField;
	}
	
	private JButton getGridButton(int r, int c) {
		int index = r * gridSize + c;
		return guiButtons.get(index);
	}
	
	/*
	 * Here size is in terms of 4x4 i.e. square side
	 */
	public void initializeGrid(int size)
	{
		 JFrame.setDefaultLookAndFeelDecorated(true);
		    JFrame frame = new JFrame("GridLayout Test");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLayout(new GridLayout(Row, Col));
		    int k = 0;
			for (int i=0; i < Row; ++i) {
				for (int j=0; j < Col; ++j) {
		    	ButtonWithCoordinates button = new ButtonWithCoordinates(" ",i, j);
		    	button.setFont(button.getFont().deriveFont(Font.BOLD, 20));
		    	button.addActionListener(listener);
		    	guiButtons.add(button);
		    	frame.add(guiButtons.get(k));
		    	k++;
				}
		    }
		    frame.setSize(500, 500);;
		    frame.setVisible(true);
	}
	
	public void updateGrid(ArrayList<String> listPLayableItems)
	{
	    for(int i=0;i<listPLayableItems.size();i++)
	    {
	    	String temp = listPLayableItems.get(i).toString();
	    	temp = temp.equals(playableItem.EMPTY.toString()) ? "" : temp;
	    	guiButtons.get(i).setText(temp);
	    }
	}
	
	private class MyListener implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
		     System.out.println("Button pressed: " + e.getActionCommand());
		     int row = ((ButtonWithCoordinates)e.getSource()).getCoordX();
		     int col = ((ButtonWithCoordinates)e.getSource()).getCoordY();
		     othelloconsole.setGameuiMoveX(row);
		     othelloconsole.setGameuiMoveY(col);
		     othelloconsole.setGameuiMove(true);
		     //System.out.println("Source - " + ((ButtonWithCoordinates)e.getSource()).getCoordX() + "  -- " + ((ButtonWithCoordinates)e.getSource()).getCoordY());
		  }
		}

}
