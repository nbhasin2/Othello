package gamestate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.os.Environment;

import com.connect.four.boardSpace;

import shared.BoardSpace;

public class GameStateRetriever {
	
	private GameSateModel myModel;
	public GameStateRetriever() {
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void retrieveModel()
	{
	
		 ArrayList<BoardSpace[][]> redoBoard= new ArrayList<BoardSpace[][]>();
		 ArrayList<BoardSpace[][]> undoBoard= new ArrayList<BoardSpace[][]>();
		 BoardSpace[][] currentBoard = null;
	        try
	        {
	        	String fileDir = Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+"/";
	            FileInputStream fisRedoBoard = new FileInputStream(fileDir+"redoBoard");
	            FileInputStream fisUndoBoard = new FileInputStream(fileDir+"undoBoard");
	            FileInputStream fisCurrentBoard = new FileInputStream(fileDir+"currentBoard");
	            ObjectInputStream oisRedoBoard = new ObjectInputStream(fisRedoBoard);
	            ObjectInputStream oisUndoBoard = new ObjectInputStream(fisUndoBoard);
	            ObjectInputStream oisCurrentBoard = new ObjectInputStream(fisCurrentBoard);
	            
	            redoBoard =(ArrayList<BoardSpace[][]>) oisRedoBoard.readObject();
	            undoBoard =(ArrayList<BoardSpace[][]>) oisUndoBoard.readObject();
	            currentBoard = (BoardSpace[][]) oisCurrentBoard.readObject();
	            System.out.println("retreive model undo is"+" "+undoBoard);
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
	        
	   setMyModel(new GameSateModel(redoBoard,undoBoard,currentBoard));
	    
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

	public GameSateModel getMyModel() {
		return myModel;
	}

	public void setMyModel(GameSateModel myModel) {
		this.myModel = myModel;
	}
}
