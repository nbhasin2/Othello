package gameui;
/**
 * @author Nishant
 * This class deals with all the gui elements. For this particular milestone 
 * This class is small and not tightly coupled with other components.
 * 
 * It has reference to the Othello Console class that acts as our controller and is mainly
 * used to get available moves that is shown in yellow coloured boxes. 
 * 
 * There 
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import Othello.BoardSpace;
import Othello.OthelloConsole;
import Othello.PlayableItem;

public class Gameui {
	
	private static int gridSize = 8;
	private int Row = gridSize;
	private int Col = gridSize;
	private ArrayList<ButtonWithCoordinates> guiButtons;
	private BoardSpace[][] playField;	
	private ActionListener listener;
	private OthelloConsole othelloconsole;
	private Boolean aiRandomCheck, aiMinimaxCheck;
	private String aiType;
	
	//ui
	private JFrame frame;
	private JMenuBar menuBar;
    private JMenu menu;
    private JMenu aiMenu;
    private JMenuItem item;
    private JRadioButtonMenuItem randomAI;
    private JRadioButtonMenuItem minimaxAI;
    private JLabel scoreLabel;
    private JCheckBox checkbox;
   
	
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
	
	/**
	 * This method is use to initialize a square grid gui with 
	 * size as grid size. Mainly deals with adding initial buttons
	 * to the gridlayout in frame.
	 */
	public void initializeGrid()
	{
		 
		    frame = new JFrame("GridLayout Test");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLayout(new GridLayout(Row, Col));

	        menuBar = new JMenuBar();
	        menu = new JMenu("Menu");
	        aiMenu = new JMenu("AI");

	        menuBar.add(menu);
	        
	        item = new JMenuItem("Exit");
	        randomAI = new JRadioButtonMenuItem("Random", true);
	        minimaxAI = new JRadioButtonMenuItem("Minimax", true);
	        scoreLabel = new JLabel();
	        checkbox = new JCheckBox();
	        checkbox.setSelected(true);
	        checkbox.addItemListener(new CheckBoxListener());
	        menuBar.add(new JLabel(" | Score - "));
	        menuBar.add(scoreLabel);
	        menuBar.add(new JLabel(" | Enable Available Moves"));
	        menuBar.add(checkbox);
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
	
	/**
	 * This method takes in listPlayableItems params and updates the 
	 * grid when user makes a move. 
	 * @param listPLayableItems
	 */
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
	    	if(temp.equals(PlayableItem.EMPTY.toString()))
	    	{		    		  
	    		img = ImageIO.read(getClass().getResource(emptyImage));  	  
	    	}
	    	else if(temp.equals(PlayableItem.WHITE.toString()))
	    	{
	    		img = ImageIO.read(getClass().getResource(whiteImage));
	    		
	    		
	    	}else if(temp.equals(PlayableItem.BLACK.toString()))
	    	{
	    		img = ImageIO.read(getClass().getResource(blackImage));
	    	}
 
	    	if(checkbox.isSelected())
	    	{
		    	ArrayList<String> tempAvailableSolutions = new ArrayList<String>();
		    	
	    		for(int l=0; l<othelloconsole.getAvailableSolutions(0).size(); l++)
		    	{
		    			tempAvailableSolutions.add(othelloconsole.getAvailableSolutions(0).get(l).split(",")[0]+
		    						","+othelloconsole.getAvailableSolutions(0).get(l).split(",")[1]);
		    	}
				for (int k=0; k < guiButtons.size(); k++) {
					String tempVar = guiButtons.get(i).coordX+","+guiButtons.get(i).coordY;
					if(tempAvailableSolutions.contains(tempVar))
					{
						img = ImageIO.read(getClass().getResource(availableMoveImage));
					}
			    }
	    	}

	    	guiButtons.get(i).setContentAreaFilled(true);
	    	guiButtons.get(i).setIcon(new ImageIcon(img)); 
	    	if(othelloconsole.getCurrentScore()!=null)
	    	{
	    		scoreLabel.setText("White : "+othelloconsole.getCurrentScore()[1]+" - Black :	 "+othelloconsole.getCurrentScore()[2]);
	    	}
	    	} catch (IOException ex) {
	    	  }
	    }
	}
	
	/**
	 * Action listener for checkbox button
	 * @author Nishant
	 *
	 */
	private class MyListener implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
		     int row = ((ButtonWithCoordinates)e.getSource()).getCoordX();
		     int col = ((ButtonWithCoordinates)e.getSource()).getCoordY();
		     othelloconsole.setGameuiMoveX(row);
		     othelloconsole.setGameuiMoveY(col);
		     othelloconsole.setGameuiMove(true);
		  }
		}

	/**
	 * This method is used to show the popup message when user wins.
	 * Practically this can be used to show any message.
	 * @param message
	 */
	public void showPopup(String message)
	{
		JOptionPane.showMessageDialog(frame,
			    message);
	}
	
	/**
	 * This will be used later. More functionality will be added later.
	 * @author Nishant
	 *	
	 */
	 private class CheckBoxListener implements ItemListener{
	        public void itemStateChanged(ItemEvent e) {
	            if(e.getSource()==checkbox){
	                if(checkbox.isSelected()) {
	                    System.out.println("one has been selected");
	                } else {System.out.println("nothing");}
	            }
	        }
	    }
}
