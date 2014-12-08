package gamestate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import shared.SharedConstants;

public class GameStateWrter {

	private GameSateModel gameState;
	
	public GameStateWrter(GameSateModel model) {
		
		this.gameState = model;
	}
	public void writeModel(String code)
	{
		 try{
	         FileOutputStream writeRedoBoard= new FileOutputStream(SharedConstants.SAVEDLOCATION+"redoBoard"+code);
	         FileOutputStream writeUndoBoard = new FileOutputStream(SharedConstants.SAVEDLOCATION+"undoBoard"+code);
	         FileOutputStream writeCurrentBoard = new FileOutputStream(SharedConstants.SAVEDLOCATION+"currentBoard"+code);
	         
	         ObjectOutputStream oosredoBoard = new ObjectOutputStream(writeRedoBoard);
	         ObjectOutputStream oosundoBoard = new ObjectOutputStream(writeUndoBoard);
	         ObjectOutputStream ooscurrentBoard = new ObjectOutputStream(writeCurrentBoard);
	    
	         oosredoBoard.writeObject(gameState.getRedoBoard());
	         oosundoBoard.writeObject(gameState.getUndoBoard());
	         ooscurrentBoard.writeObject(gameState.getCurrentBoard());
	         
	         oosredoBoard.close();
	         oosundoBoard.close();
	         ooscurrentBoard.close();
	         
	         writeRedoBoard.close();
	         writeUndoBoard.close();
	         writeCurrentBoard.close();
	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }
	}
}
