package gameui;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JFrame;

import Othello.boardSpace;

public class Gameui {
	
	private static int gridSize = 4;
	private int Row = gridSize;
	private int Col = gridSize;
	private ArrayList<ButtonWithCoordinates> guiButtons;
	private boardSpace[][] playField;	
	private ActionListener listener;

	
	//Constructor 
	public Gameui()
	{
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
	    	guiButtons.get(i).setText(listPLayableItems.get(i)+"");
	    }
	}
	
	private class MyListener implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
		     System.out.println("Button pressed: " + e.getActionCommand());
		     System.out.println("Source - " + ((ButtonWithCoordinates)e.getSource()).getCoordX() + "  -- " + ((ButtonWithCoordinates)e.getSource()).getCoordY());
//		     ((JButton)e.getSource()).setEnabled(false);
		  }
		}

}
