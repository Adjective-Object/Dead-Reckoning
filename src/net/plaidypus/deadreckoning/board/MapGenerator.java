package net.plaidypus.deadreckoning.board;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.biome.Biome;
import net.plaidypus.deadreckoning.biome.Temple;

public class MapGenerator {
	
	static ArrayList<ArrayList<Biome>> biomes;
	
	public static GameBoard generateMap(int depth){
		return biomes.get(depth).get(Utilities.randInt(0, biomes.get(depth).size())).makeBoard();
	}
	
	public static void init(){
		biomes= new ArrayList<ArrayList<Biome>>(0);
		biomes.add(new ArrayList<Biome>(0));
		biomes.get(0).add(new Temple());
		
	}
}
