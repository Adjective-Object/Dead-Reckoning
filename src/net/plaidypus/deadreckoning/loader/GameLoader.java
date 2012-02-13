package net.plaidypus.deadreckoning.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

public class GameLoader {
	
	public void loadGame(Save s){
		
	}
	
	public void saveGame(GameBoard g, Save saveTarget){
		FileOutputStream saveFile;
		try {
			File f = new File(saveTarget.getLocation()+"floor.map");
			f.createNewFile();
			saveFile=new FileOutputStream(f);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
	}
}
