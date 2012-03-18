package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Stair;

public abstract class Biome {
	static ArrayList<Biome> biomes;
	Class[] requiredClasses;
	
	public abstract GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks) throws SlickException;
	
	public static Biome getRandomBiome(){
		return biomes.get(Utilities.randInt(0,biomes.size()));
	}
	
	public static void init() throws SlickException{
		biomes = new ArrayList<Biome>(0);
		biomes.add(new Hemple(16));
		
		ClassLoader c = ClassLoader.getSystemClassLoader();
		for(int i=0; i<biomes.size(); i++){
			for(int e = 0; e< biomes.get(i).requiredClasses.length; e++){
				try{
					Class<? extends Entity> clas = biomes.get(i).requiredClasses[e].asSubclass(Entity.class);
					clas.newInstance().init();
				}
				catch (InstantiationException error) {
					error.printStackTrace();
				} catch (IllegalAccessException error) {
					error.printStackTrace();
				}
				
			}
		}
	}
	
}
