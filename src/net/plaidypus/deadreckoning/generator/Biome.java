package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

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
	static ArrayList<Biome> biomes;
	
	/** The required classes. */
	Class<Entity>[] requiredClasses;

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

	/**
	 * Inits the.
	 *
	 * @throws SlickException the slick exception
	 */
	public static void init() throws SlickException {
		biomes = new ArrayList<Biome>(0);
		biomes.add(new Hemple(16));
		
		for (int i = 0; i < biomes.size(); i++) {
			for (int e = 0; e < biomes.get(i).requiredClasses.length; e++) {
				try {
					Class<? extends Entity> clas = biomes.get(i).requiredClasses[e]
							.asSubclass(Entity.class);
					clas.newInstance().init();
				} catch (InstantiationException error) {
					error.printStackTrace();
				} catch (IllegalAccessException error) {
					error.printStackTrace();
				}

			}
		}
	}

}
