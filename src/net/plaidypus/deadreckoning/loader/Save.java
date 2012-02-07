package net.plaidypus.deadreckoning.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import net.plaidypus.deadreckoning.board.GameBoard;

public class Save {
	String saveLocation;
	String name;
	int depth;
	
	public Save(String saveLocation){
		try {
			BufferedReader r = new BufferedReader(new FileReader(saveLocation+"/saveInformation.txt"));
			name = r.readLine();
			depth = Integer.parseInt(r.readLine());
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		this.saveLocation=saveLocation;
		
	}
	
	public String getName(){
		return this.name;
	}

	public String getLocation() {
		return saveLocation;
	}

	public GameBoard loadMap() {
		return null;//TODO ANGUD
	}
}
