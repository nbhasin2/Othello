package gameui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
	
	private static int gridSize = 8;
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
		    	ButtonWithCoordinates button = new ButtonWithCoordinates("",i, j);
//		    	button.setFont(button.getFont().deriveFont(Font.BOLD, 20));
		    	button.addActionListener(listener);
		    	guiButtons.add(button);
		    	frame.add(guiButtons.get(k));
		    	k++;
				}
		    }
		    frame.setSize(500,490);
			//frame.pack();
		    frame.setVisible(true);
		    frame.setResizable(true);
	}
	
	public void updateGrid(ArrayList<String> listPLayableItems)
	{

	    
	    for(int i=0;i<listPLayableItems.size();i++)
	    {
	    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    	double width = screenSize.getWidth();
	    	double height = screenSize.getHeight();
	    	String emptyImage;
	    	String whiteImage; 
	        String blackImage;
	    	if(width < 1440)
	    	{
	    		emptyImage = "/GameIcons/resized-images/empty.png";
	    		whiteImage = "/GameIcons/resized-images/white-green.png";
	    		blackImage = "/GameIcons/resized-images/black-green.png";
	    	}else
	    	{
		    	 emptyImage = "/GameIcons/empty.png";
		    	 whiteImage = "/GameIcons/white-green.png";
		         blackImage = "/GameIcons/black-green.png";
	    	}
	    	Image img = null;
	    	try {
	    	String temp = listPLayableItems.get(i).toString();
	    	if(temp.equals(playableItem.EMPTY.toString()))
	    	{		    		  
	    		img = ImageIO.read(getClass().getResource(emptyImage));  	  
	    	}
	    	else if(temp.equals(playableItem.WHITE.toString()))
	    	{
	    		img = ImageIO.read(getClass().getResource(whiteImage));
	    		
	    		
	    	}else if(temp.equals(playableItem.BLACK.toString()))
	    	{
	    		img = ImageIO.read(getClass().getResource(blackImage));
	    	}
 
//	    	guiButtons.get(i).setBorder(BorderFactory.createEmptyBorder());
	    	guiButtons.get(i).setContentAreaFilled(true);
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
