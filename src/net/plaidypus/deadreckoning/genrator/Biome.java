package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Stair;

public abstract class Biome {
	static ArrayList<Biome> biomes;
	
	public abstract GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks) throws SlickException;
	
	public static Biome getRandomBiome(){
		return biomes.get(Utilities.randInt(0,biomes.size()));
	}
	
	public static void init(){
		biomes = new ArrayList<Biome>(0);
		biomes.add(new Hemple(16));
	}
	
}
