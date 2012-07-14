package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/**
 * The Class Biome.
 */
public abstract class Biome extends DeadReckoningComponent {
 
	static HashMap<String,Biome> biomes = new HashMap<String,Biome>(0);
	protected SpriteSheet tileImage;
	
	protected String parentMod;

	public int minDepth=-1, maxDepth=-1;
	
	/**
	 * Make board.
	 * 
	 * @param depth
	 *            the depth
	 * @param floorLinks
	 *            the floor links
	 * @return the game board
	 * @throws SlickException
	 *             the slick exception
	 */
	public abstract GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks)
			throws SlickException;

	/**
	 * Gets the random biome.
	 * 
	 * @return the random biome
	 */
	public static Biome getRandomBiome() {
		Set<String> keys = biomes.keySet();
		Iterator<String> o = keys.iterator();
		int n= Utilities.randInt(0,keys.size());
		for (int i=0; i<n; i++){
			o.next();
		}
		return biomes.get(o.next());
	}

	public static void addBiome(String path,Biome b) {
		Biome.biomes.put(path,b);
	}

	public static HashMap<String,Biome> getBiomes() {
		return biomes;
	}

	public String getParentMod() {
		return this.parentMod;
	}

	public void setParentMod(String newMod) {
		this.parentMod = newMod;
	}

	public SpriteSheet getTileImage() {
		return this.tileImage;
	}

	public int getNullTileValue() {
		return 0;
	}

	public static Biome getBiome(String path) {
		return biomes.get(path);
	}
}
