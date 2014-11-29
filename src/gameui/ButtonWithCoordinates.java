package gameui;

import javax.swing.*;
/*
 * By Nishant Bhasin
 * This class is an extension of JButton class and the reason
 * This class was made because it was required to store the coordinates
 * of the buttons in grid to store grid coordinates of the buttons.
 * This way when the person / user clicks on the button I access.
 * the location of the button in the grid and update it accordingly.
 * 
 * int coordX and int coordY are the variables updated in Gameui class
 * and as we populate the Grid with ButtonWithCoordinates we add the 
 * coordintes in the for loop. 
 */

@SuppressWarnings("serial")
public class ButtonWithCoordinates extends JButton {

	private  int coordX;
    private int coordY;

    /**
     * Constructor for the class. This also calls the super class.
     * @param buttonText
     * @param coordX
     * @param coordY
     */
    public ButtonWithCoordinates(String buttonText, int coordX, int coordY) {
        super(buttonText);
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * @return the coordX
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * @return the coordY
     */
    public int getCoordY() {
        return coordY;
    }
}