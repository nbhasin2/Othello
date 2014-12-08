package gamestate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.os.Environment;

public class GameStateWrter {

	private GameSateModel gameState;
	
	public GameStateWrter(GameSateModel model) {
		
		this.gameState = model;
	}
	public void writeModel()
	{
		 try{
			 String fileDir = Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+"/";
	         FileOutputStream writeRedoBoard= new FileOutputStream(fileDir+"redoBoard");
	         FileOutputStream writeUndoBoard = new FileOutputStream(fileDir+"undoBoard");
	         FileOutputStream writeCurrentBoard = new FileOutputStream(fileDir+"currentBoard");
	         
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
