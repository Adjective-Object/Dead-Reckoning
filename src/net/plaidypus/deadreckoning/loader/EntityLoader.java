package net.plaidypus.deadreckoning.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Chest;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Torch;
import net.plaidypus.deadreckoning.entities.Wall;

public class EntityLoader {
	
	public static HashMap<String,Entity> makerArray;
	
	public static void init() throws SlickException {
		makerArray = new HashMap<String,Entity>(0);
		makerArray.put("Torch",new Torch("Torch"));
		makerArray.put("Wall",new Wall("Wall"));
		makerArray.put("Chest",new Chest("Chest"));
	}
	
	public static ArrayList<Entity> loadEntititiesFromSave(GameBoard loadOnto, BufferedReader r) throws IOException{
		ArrayList<Entity> entities = new ArrayList<Entity>(0);
		String entityDefinition = "";
		while(entityDefinition!=null){
			System.out.println(entityDefinition);
			if(!entityDefinition.equals("")){
				entities.add(loadEntityFromString(loadOnto,entityDefinition));
			}
			entityDefinition = r.readLine();
		}
		return entities;
	}
	
	private static Entity loadEntityFromString(GameBoard b, String entityDefinition){
		return makerArray.get(entityDefinition.split(":")[0]).makeFromString(b,entityDefinition.split(":"));
	}
}
