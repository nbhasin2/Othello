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
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

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
	private Boolean aiRandomCheck, aiMinimaxCheck;
	
	//ui
	private JFrame frame;
	private JMenuBar menuBar;
    private JMenu menu;
    private JMenu aiMenu;
    private JMenuItem item;
    private JRadioButtonMenuItem randomAI;
    private JRadioButtonMenuItem minimaxAI;
    private JLabel scoreLabel;
	
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
	
	private void aiCheckToggle()
	{
		
	}
	
	/*
	 * Here size is in terms of 4x4 i.e. square side
	 */
	public void initializeGrid(int size)
	{
		 
		    frame = new JFrame("GridLayout Test");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLayout(new GridLayout(Row, Col));

	        menuBar = new JMenuBar();
	        menu = new JMenu("Menu");
	        aiMenu = new JMenu("AI");

	        menuBar.add(menu);

//	        menuBar.add(aiMenu);
	        
	        item = new JMenuItem("Exit");
	        randomAI = new JRadioButtonMenuItem("Random", true);
	        minimaxAI = new JRadioButtonMenuItem("Minimax", true);
	        scoreLabel = new JLabel();
	        menuBar.add(scoreLabel);
	        item.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);
	            }
	        });
	        
	        menu.add(item);
	        aiMenu.add(randomAI);
	        aiMenu.add(minimaxAI);
	        frame.setJMenuBar(menuBar);
		    int k = 0;
			for (int i=0; i < Row; ++i) {
				for (int j=0; j < Col; ++j) {
		    	ButtonWithCoordinates button = new ButtonWithCoordinates("",i, j);
		    	button.addActionListener(listener);
		    	guiButtons.add(button);
		    	frame.add(guiButtons.get(k));
		    	k++;
				}
		    }
		    frame.setSize(500,490);
		    frame.setVisible(true);
		    frame.setResizable(false);
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
	        String availableMoveImage;
	        emptyImage = "/GameIcons/resized-images/empty.png";
	        whiteImage = "/GameIcons/resized-images/white-green.png";
	        blackImage = "/GameIcons/resized-images/black-green.png";
	        availableMoveImage = "/GameIcons/resized-images/empty-available-green-yellow.png";
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
 
	    	ArrayList<String> tempAvailableSolutions = new ArrayList<String>();
	    	
    		for(int l=0; l<othelloconsole.getAvailableSolutions(0).size(); l++)
	    	{
	    			tempAvailableSolutions.add(othelloconsole.getAvailableSolutions(0).get(l).split(",")[0]+
	    						","+othelloconsole.getAvailableSolutions(0).get(l).split(",")[1]);
	    	}
//    		System.out.println("Available Sol 0 - "+tempAvailableSolutions.toString());
			for (int k=0; k < guiButtons.size(); k++) {
				String tempVar = guiButtons.get(i).coordX+","+guiButtons.get(i).coordY;
//				System.out.println(tempVar);
				if(tempAvailableSolutions.contains(tempVar))
				{
					img = ImageIO.read(getClass().getResource(availableMoveImage));
				}
		    }

	    	guiButtons.get(i).setContentAreaFilled(true);
	    	guiButtons.get(i).setIcon(new ImageIcon(img)); 
	    	if(othelloconsole.getCurrentScore()!=null)
	    	{
	    		scoreLabel.setText("Score - "+"White : "+othelloconsole.getCurrentScore()[1]+" - Black :	 "+othelloconsole.getCurrentScore()[2]);
	    	}
	    	} catch (IOException ex) {
	    	  }
	    }
	}
	
	private class MyListener implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
//		     System.out.println("Button pressed: " + e.getActionCommand());
		     int row = ((ButtonWithCoordinates)e.getSource()).getCoordX();
		     int col = ((ButtonWithCoordinates)e.getSource()).getCoordY();
		     othelloconsole.setGameuiMoveX(row);
		     othelloconsole.setGameuiMoveY(col);
		     othelloconsole.setGameuiMove(true);
		  }
		}

	public void showPopup(String message)
	{
		JOptionPane.showMessageDialog(frame,
			    message);
	}
}
