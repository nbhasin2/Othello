package gameui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javafx.scene.text.Font;

import javax.swing.JButton;
import javax.swing.JFrame;

import Othello.boardSpace;

public class Gameui {
	
	private int Row;
	private int Col;
	public ArrayList<JButton> guiButtons;
	private boardSpace[][] playField;	
	
	//Constructor 
	public Gameui()
	{
		guiButtons = new ArrayList<JButton>();
		this.playField = playField;
	}
	
	/*
	 * Here size is in terms of 4x4 i.e. square side
	 */
	public void initializeGrid(int size)
	{
		 JFrame.setDefaultLookAndFeelDecorated(true);
		    JFrame frame = new JFrame("GridLayout Test");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLayout(new GridLayout(0, 4));
		    for(int i=0;i<size;i++)
		    {
		    	JButton button = new JButton(" ");
		    	guiButtons.add(button);
		    	frame.add(guiButtons.get(i));
		    }
		    frame.pack();
		    frame.setVisible(true);
	}
	
	public void updateGrid(ArrayList<String> listPLayableItems)
	{
	    for(int i=0;i<listPLayableItems.size();i++)
	    {
	    	guiButtons.get(i).setText(listPLayableItems.get(i)+"");
	    }
	}

}
