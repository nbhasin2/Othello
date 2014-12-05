package tictactoe;


import gameui.Controller;
import gameui.Gameui;
import shared.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;



/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class GameBoardTic  extends GameBoard{
	


	public GameBoardTic(Object obj) {
		super(obj, SharedConstants.ticTacRow,SharedConstants.ticTacCol,"TicTac");
	
	}
	@Override
	public void boardSetup() {
		BoardSpaceTic[][] playfield = new BoardSpaceTic[ROWS][COLS];
		this.setPlayField(playfield);
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j] = new BoardSpaceTic(i, j); //4x4 gameboard where every space is a new boardSpace object 
			}
		}
		
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].clear(); //Initialize every space to empty
			}
		}
	}
	@Override
	public void printBoard() {
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].putItem();
				if(j < COLS-1 ) System.out.print("|");
			}

			System.out.println();
		if(i < ROWS-1  )
			System.out.println("--------------");
		}
	}
}
