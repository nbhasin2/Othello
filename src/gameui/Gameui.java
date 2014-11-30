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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import othello.BoardSpace;
import othello.OthelloConsole;
import shared.SharedConstants;

public class Gameui {

	private static int gridSize = 8;
	private int Row = gridSize;
	private int Col = gridSize;
	private ArrayList<ButtonWithCoordinates> guiButtons;
	private BoardSpace[][] playField;	
	private ActionListener listener;
	private Boolean aiRandomCheck, aiMinimaxCheck;
	private String aiType;
	private Controller gameController;

	//ui
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenu aiMenu;
	private JMenuItem item;
	private JRadioButtonMenuItem randomAI;
	private JRadioButtonMenuItem minimaxAI;
	private JButton undoButton;
	private JButton redoButton;
	private JLabel scoreLabel;
	private JCheckBox checkbox;
	private String emptyImage;
	private String whiteImage;
	private String blackImage;
	private String availableMoveImage;
	private Image img;


	//Constructor 
	public Gameui(Controller gameController)
	{
		this.gameController = gameController;
		this.gameController.setOthelloUi(this);
		listener = new MyListener();
		guiButtons = new ArrayList<ButtonWithCoordinates>();
		this.playField = playField;
		initializeGrid();
	}

	/**
	 * This method is use to initialize a square grid gui with 
	 * size as grid size. Mainly deals with adding initial buttons
	 * to the gridlayout in frame.
	 */
	public void initializeGrid()
	{

		emptyImage = SharedConstants.EmptyImage;
		whiteImage = SharedConstants.WhiteImage;
		blackImage = SharedConstants.BlackGreenImage;
		availableMoveImage = SharedConstants.GreenYellowImage;

		frame = new JFrame("Othello");
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
		
		//undo and redo button
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		
		//adding action listener to the button
		undoMoveAddButtonListener(undoButton);
		redoMoveAddButtonListener(redoButton);
		
		checkbox.setSelected(true);
		checkbox.addItemListener(new CheckBoxListener());
		menuBar.add(new JLabel(" | Score - "));
		menuBar.add(scoreLabel);
		menuBar.add(new JLabel(" | Enable Available Moves"));
		menuBar.add(undoButton);
		menuBar.add(redoButton);
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
		ArrayList<String> tempAvailableSolutions = gameController.guiAskForAvailableSolutions();
		for(int i=0;i<listPLayableItems.size();i++)
		{

			img = null;
			try {
				String temp = listPLayableItems.get(i).toString();
				if(temp.equals(SharedConstants.PlayableItem.EMPTY.toString()))
				{		    		  
					img = ImageIO.read(getClass().getResource(emptyImage));  	  
				}
				else if(temp.equals(SharedConstants.PlayableItem.WHITE.toString()))
				{
					img = ImageIO.read(getClass().getResource(whiteImage));


				}else if(temp.equals(SharedConstants.PlayableItem.BLACK.toString()))
				{
					img = ImageIO.read(getClass().getResource(blackImage));
				}

				if(checkbox.isSelected())
				{
					showAvailableMovesInGui(i,tempAvailableSolutions);
				}
				
				guiButtons.get(i).setContentAreaFilled(true);
				guiButtons.get(i).setIcon(new ImageIcon(img)); 
				
				scoreLabel.setText(gameController.guiAskForCurrentScore());
			} catch (IOException ex) {
			}
		}
		
	}
	
	/**
	 * @author Nishant
	 * This method simply checks if the space in grid in a available move
	 * for the player and if yes it will update that tile to yellow green 
	 * image. 
	 * @throws IOException 
	 */
	public void showAvailableMovesInGui(int buttonLocation, ArrayList<String> tempAvailableSolutions) throws IOException
	{

//		for (int k=0; k < guiButtons.size(); k++) {
			String tempVar = guiButtons.get(buttonLocation).getCoordX()+","+guiButtons.get(buttonLocation).getCoordY();

			if(tempAvailableSolutions.contains(tempVar))
			{
				img = ImageIO.read(getClass().getResource(availableMoveImage));
			}   
	}

	
	public void undoMoveAddButtonListener(JButton button)
	{
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameController.undoMove();
			}
		});
	}
	
	
	public void redoMoveAddButtonListener(JButton button)
	{
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameController.redoMove();
			}
		});
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
			gameController.guiButtonClicked(row, col, true); 
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
