package com.connect.four;

import java.util.ArrayList;

import shared.BoardSpace;
import shared.GameBoard;
import shared.SharedConstants;
/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class GameBoardC4 extends GameBoard {

	public GameBoardC4(Object obj) {
		super(obj, SharedConstants.connectRow,SharedConstants.connectCol);
	
	}
	@Override
	public void boardSetup() {
		playField = new BoardSpace[ROWS][COLS];
		
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j] = new BoardSpace(i, j); //4x4 gameboard where every space is a new boardSpace object 
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
			ArrayList<String> listOfItems = new ArrayList<>();
			System.out.println(playField);
			for(int i = 0; i < ROWS; i++){
				System.out.print("|");
				for(int j = 0; j < COLS; j++){
					playField[i][j].putItem();
					listOfItems.add(playField[i][j].getGamePiece()+"");
					if(j < COLS ) System.out.print("|");
				}

				System.out.println();
			if(i < ROWS  )
				System.out.println("-----------------------------");
			}
			setChanged();
			notifyObservers(listOfItems);	
		}
	}


