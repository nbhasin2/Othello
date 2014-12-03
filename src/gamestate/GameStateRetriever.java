package gamestate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.connect.four.boardSpace;

import othello.BoardSpace;

public class GameStateRetriever {
	
	public GameStateRetriever() {
		

	}
	
	@SuppressWarnings("unchecked")
	public GameSateModel retrieveModel()
	{
	
		 ArrayList<BoardSpace[][]> redoBoard= new ArrayList<BoardSpace[][]>();
		 ArrayList<BoardSpace[][]> undoBoard= new ArrayList<BoardSpace[][]>();
		 BoardSpace[][] currentBoard = null;
	        try
	        {
	            FileInputStream fisRedoBoard = new FileInputStream("redoBoard");
	            FileInputStream fisUndoBoard = new FileInputStream("undoBoard");
	            FileInputStream fisCurrentBoard = new FileInputStream("currentBoard");
	            ObjectInputStream oisRedoBoard = new ObjectInputStream(fisRedoBoard);
	            ObjectInputStream oisUndoBoard = new ObjectInputStream(fisUndoBoard);
	            ObjectInputStream oisCurrentBoard = new ObjectInputStream(fisCurrentBoard);
	            
	            redoBoard =(ArrayList<BoardSpace[][]>) oisRedoBoard.readObject();
	            undoBoard =(ArrayList<BoardSpace[][]>) oisUndoBoard.readObject();
	            currentBoard = (BoardSpace[][]) oisCurrentBoard.readObject();
	            
	            oisUndoBoard.close();
	            oisRedoBoard.close();
	            oisCurrentBoard.close();
	            fisRedoBoard.close();
	            fisUndoBoard.close();
	            fisCurrentBoard.close();
	         }catch(IOException ioe){
	             ioe.printStackTrace();
	             
	          }catch(ClassNotFoundException c){
	             System.out.println("Class not found");
	             c.printStackTrace();
	             
	          }
	        
	    GameSateModel model = new GameSateModel(redoBoard,undoBoard,currentBoard);

		return model;
	}
	
	public boolean checkIfFileExist()
	{
		File redofile = new File("redoBoard");
		File undofile = new File("undo");
		File currentFile = new File("currentBoard");
		if(redofile.exists() && !redofile.isDirectory() && undofile.exists() && !undofile.isDirectory() && currentFile.exists() && !currentFile.isDirectory()) { 
			
			return true;
			}
		return false;
	}
}