package net.plaidypus.deadreckoning.biome;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;

public abstract class Biome {
	static ArrayList<Biome> biomes;
	
	public abstract GameBoard makeBoard();
	
	public static void init(){
		biomes = new ArrayList<Biome>(0);
		biomes.add(new Temple());
	}
	
	public static GameBoard getRandomBoard(){
		return biomes.get(Utilities.randInt(0, biomes.size())).makeBoard();
	}
}
