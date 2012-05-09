package net.plaidypus.deadreckoning.generator;

import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarFile;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Stair;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Biome.
 */
public abstract class Biome {
	
	/** The biomes. */
	static ArrayList<Biome> biomes = new ArrayList<Biome>(0);

	protected String parentMod;
	
	/**
	 * Make board.
	 *
	 * @param depth the depth
	 * @param floorLinks the floor links
	 * @return the game board
	 * @throws SlickException the slick exception
	 */
	public abstract GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks)
			throws SlickException;

	/**
	 * Gets the random biome.
	 *
	 * @return the random biome
	 */
	public static Biome getRandomBiome() {
		return biomes.get(Utilities.randInt(0, biomes.size()));
	}

	public static void addBiome(Biome b) {
		Biome.biomes.add(b);
	}
	
	public static ArrayList<Biome> getBiomes(){
		return biomes;
	}
	
	public String getURLLoader(){
		return this.parentMod;
	}
	
	public void setLoader(String newMod){
		this.parentMod=newMod;
	}
	
	/**
	 * Initializes the biome code, adding initializing the required
	 * classes of each biome.
	 *
	 * needs to be called after the biome list has been populated
	 *
	 * @throws SlickException the slick exception
	 */
	public abstract  void init() throws SlickException;
}
