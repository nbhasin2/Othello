package gameui;

import javax.swing.*;

@SuppressWarnings("serial")
public class ButtonWithCoordinates extends JButton {

    int coordX;
    int coordY;

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