package net.plaidypus.deadreckoning.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;

import net.plaidypus.deadreckoning.biome.Biome;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

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

	public void loadGame(GameplayElement state) throws IOException, SlickException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		BufferedReader r = new BufferedReader(new FileReader(saveLocation+"/"+"floor"+depth+".map"));
		state.setBoard(loadBoard(state,r));
		loadEntities(state.getBoard(),r);
	}
	
	public static GameBoard loadBoard(GameplayElement g, BufferedReader r) throws IOException, SlickException, ClassNotFoundException {
		GameBoard b = new GameBoard(g);
		
		b.width=(r.read()-48)*10+r.read()-48;
		r.readLine();
		b.height=(r.read()-48)*10+r.read()-48;
		r.readLine();
		System.out.println("Map Dimensions: "+b.width+","+b.height);
		b.board = new Tile[b.width][b.height];
		for(int y=0; y<b.height; y++){
			for(int x=0; x<b.width; x++){
				int q=(r.read()-48)*10+r.read()-48; // converts 2 characters into the integer equivalent (base 10)
				b.board[x][y]=new Tile(b,x,y,q);
			}
			r.readLine();
		}
		System.out.println(b.board.length);
		for(int i=0; i<b.board.length; i++){
			System.out.println(b.board[i].length);
		}
		
		return b;
		
	}
	
	public static ArrayList<Entity> loadEntities(GameBoard target, BufferedReader r) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		ClassLoader c = ClassLoader.getSystemClassLoader();
		String definition = r.readLine();
		ArrayList<Entity> entities = new ArrayList<Entity> (0);
		while (definition!=null){
			String[] defInfo = definition.split(":");
			Class<? extends Entity> clas = c.loadClass(defInfo[0]).asSubclass(Entity.class);
			clas.newInstance().makeFromString(target, defInfo);
			definition = r.readLine();
		}
		return entities;
	}

	public static Save makeNewSave(String fileLocation, String nameofSave) throws IOException {
		BufferedWriter b = new BufferedWriter(new FileWriter( fileLocation + "/saveInformation.txt"));
		b.write(nameofSave);
		b.newLine();
		b.write("0");
		
		for(int i=0; i<2; i++){
			
		}
		
		Save s = new Save(fileLocation);
		return s;
	}

	
	
}
