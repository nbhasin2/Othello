package gameui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		 
		    JFrame frame = new JFrame("GridLayout Test");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLayout(new GridLayout(Row, Col));

	        JMenuBar menuBar = new JMenuBar();
	        JMenu menu = new JMenu("Menu");

	        menuBar.add(menu);
	        JMenuItem item = new JMenuItem("Exit");
	        
	        item.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);
	            }
	        });
	        menu.add(item);
	        frame.setJMenuBar(menuBar);
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
		    frame.setSize(1000,980);;
		    frame.setVisible(true);
		    frame.setResizable(false);
	}
	
	public void updateGrid(ArrayList<String> listPLayableItems)
	{

	    
	    for(int i=0;i<listPLayableItems.size();i++)
	    {
	    	Image img = null;
	    	try {
	    	String temp = listPLayableItems.get(i).toString();
	    	if(temp.equals(playableItem.EMPTY.toString()))
	    	{		    		  
	    		img = ImageIO.read(getClass().getResource("/GameIcons/empty.png"));  	  
	    	}
	    	else if(temp.equals(playableItem.WHITE.toString()))
	    	{
	    		img = ImageIO.read(getClass().getResource("/GameIcons/white-green.png"));
	    		
	    		
	    	}else if(temp.equals(playableItem.BLACK.toString()))
	    	{
	    		img = ImageIO.read(getClass().getResource("/GameIcons/black-green.png"));
	    	}
    		img.getScaledInstance(img.getWidth(null)/2, img.getHeight(null)/2, Image.SCALE_AREA_AVERAGING);
	    	guiButtons.get(i).setIcon(new ImageIcon(img));  
	    	} catch (IOException ex) {
	    	  }
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
