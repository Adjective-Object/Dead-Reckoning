package net.plaidypus.deadreckoning;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Statue;
import net.plaidypus.deadreckoning.genrator.DungeonMap;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

public class Save {
	String saveLocation;
	String name;
	String currentMap;
	
	public Save(String saveLocation){
		try {
			BufferedReader r = new BufferedReader(new FileReader(saveLocation+"/saveInformation.txt"));
			name = r.readLine();
			currentMap = r.readLine();
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
		BufferedReader r = new BufferedReader(new FileReader(saveLocation+"/"+currentMap));
		state.setBoard(loadBoard(state,saveLocation,r));
		loadEntities(state.getBoard(),r);
	}
	
	public static GameBoard loadBoard(GameplayElement g, String saveLocation, BufferedReader r) throws IOException, SlickException, ClassNotFoundException {
		GameBoard b = new GameBoard(g,saveLocation);
		b.depth=r.read();
		r.readLine();
		b.width=r.read();
		r.readLine();
		b.height=r.read();
		r.readLine();
		b.board = new Tile[b.width][b.height];
		for(int y=0; y<b.height; y++){
			for(int x=0; x<b.width; x++){
				int q=r.read();
				b.board[x][y]=new Tile(b,x,y,q);
			}
			r.readLine();
		}
		
		return b;
	}
	
	public static ArrayList<Entity> loadEntities(GameBoard target, BufferedReader r) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		ClassLoader c = ClassLoader.getSystemClassLoader();
		String definition = r.readLine();
		ArrayList<Entity> entities = new ArrayList<Entity> (0);
		while (definition!=null){
			String[] defInfo = definition.split(":");
			try {
				Class<? extends Entity> clas = c.loadClass(defInfo[0]).asSubclass(Entity.class);
				clas.newInstance().init();
				clas.newInstance().makeFromString(target, defInfo);
			}
			catch (Exception e) {
				entities.add(new Statue().makeFromString(target,defInfo));
				e.printStackTrace();
			}
			definition = r.readLine();
		}
		return entities;
	}
	
	public static void saveBoard(GameBoard b, BufferedWriter r) throws IOException{
		r.write(b.depth);r.newLine();
		r.write(b.width);r.newLine();
		r.write(b.height);r.newLine();
		
		for(int y=0; y<b.height; y++){
			for(int x=0; x<b.width; x++){
				r.write(b.getTileAt(x, y).getTileFace());
			}
			r.newLine();
		}
	}
	
	public static void saveEntities(GameBoard b, BufferedWriter r) throws IOException{
		for(int i=0; i<b.ingameEntities.size(); i++){
			System.out.println(b.ingameEntities.get(i));
			r.write(b.ingameEntities.get(i).saveToString());
			r.newLine();
		}
	}

	public static Save makeNewSave(String fileLocation, String nameofSave) throws IOException, SlickException {
		new File(fileLocation).mkdir();
		File director = new File(fileLocation + "/saveInformation.txt");
		System.out.println(director.getCanonicalPath());
		BufferedWriter r = new BufferedWriter(new FileWriter(director));
		r.write(nameofSave);
		r.newLine();
		r.write("floor0.map");
		r.close();
		
		DungeonMap map = new DungeonMap(16);
		
		
		for(int i=0; i<map.getDepth(); i++){
			File floorFile = new File(fileLocation+map.getFloorName(i));
			floorFile.createNewFile();
			r = new BufferedWriter(new FileWriter(floorFile));
			
			GameBoard gameBoard = map.makeBoard(i);
			
			Save.saveBoard(gameBoard, r);
			Save.saveEntities(gameBoard, r);
			r.close();
		}
		
		Save s = new Save(fileLocation);
		return s;
	}

	public static GameBoard loadGame(GameplayElement game, String saveLocation, String targetFloor) {
		try {
			BufferedReader r =   new BufferedReader( new FileReader( saveLocation+"/"+targetFloor ));
			GameBoard b = loadBoard(game, saveLocation, r);
			loadEntities(b,r);
			return b;
		} catch (Exception e){
			return null;
		}
	}

	
	
}
