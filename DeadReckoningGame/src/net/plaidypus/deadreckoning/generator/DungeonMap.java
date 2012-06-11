package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Stair;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class DungeonMap.
 */
public class DungeonMap {

	/** The depth. */
	private int depth;

	/**
	 * Instantiates a new dungeon map.
	 * 
	 * @param depthDungeon
	 *            the depth dungeon
	 */
	public DungeonMap(int depthDungeon) {
		setDepth(depthDungeon);
	}

	/**
	 * Make board.
	 * 
	 * @param depth
	 *            the depth
	 * @return the game board
	 * @throws SlickException
	 *             the slick exception
	 */
	public GameBoard makeBoard(int depth) throws SlickException {
		ArrayList<Stair> topass = new ArrayList<Stair>(0);
		if (depth != 0) {
			topass.add(new Stair(null, Tile.LAYER_PASSIVE_MAP, "floor"
					+ (depth - 1) + ".map", Stair.UP));
		}
		if (depth != this.depth) {
			topass.add(new Stair(null, Tile.LAYER_PASSIVE_MAP, "floor"
					+ (depth + 1) + ".map", Stair.DOWN));
		}
		return Biome.getRandomBiome().makeBoard(depth, topass);
	}

	/**
	 * Gets the floor name.
	 * 
	 * @param depth
	 *            the depth
	 * @return the floor name
	 */
	public String getFloorName(int depth) {
		return "floor" + depth + ".map";
	}

	/**
	 * Sets the depth.
	 * 
	 * @param depth
	 *            the new depth
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * Gets the depth.
	 * 
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}
}
