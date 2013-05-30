package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.GameBoard;
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
	public GameBoard makeBoard(int depth) throws SlickException { //TODO actual mapping of dungeon
		ArrayList<Stair> topass = new ArrayList<Stair>(0);
		if (depth != 0) {
			topass.add(new Stair("floor"
					+ (depth - 1) + ".map", Stair.UP));
		}
		if (depth != this.depth) {
			topass.add(new Stair("floor"
					+ (depth + 1) + ".map", Stair.DOWN));
		}
		Biome b = Biome.getRandomBiome();
		while(!(
				(b.minDepth<depth || b.minDepth==-1) &&
				(b.maxDepth>depth||b.maxDepth==-1)
				)){
			b = Biome.getRandomBiome();
		}
		if(this.depth==depth){
			b=Biome.getBiome("core.biomes.ThroneRoom");
		}
		return b.makeBoard(depth, topass);
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
