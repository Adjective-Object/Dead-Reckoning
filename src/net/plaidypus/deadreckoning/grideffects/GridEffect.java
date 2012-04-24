package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Graphics;

/**
 * The Class GridEffect.
 * 
 * GridEffects are a method of putting animations / etc tied to the grid of a GameBoard
 * they can be stored as above or below the entities in the tile.
 * 
 */
public abstract class GridEffect {
	
	/** a boolean value that, when true, says "this should be deleted"*/
	private boolean kill;
	
	/** The location where the grid effect is located. */
	public Tile location;

	/**
	 * Instantiates a new grid effect.
	 *
	 * @param location the tile to tie the effect to
	 */
	public GridEffect(Tile location) {
		this.location = location;
		this.kill = false;
	}

	/**
	 * Sets the location of the gridEffect.
	 *
	 * @param l the new location
	 */
	public void setLocation(Tile l) {
		this.location = l;
	}

	/**
	 * Checks if is complete / should be killed.
	 *
	 * @return true, if is complete
	 */
	public boolean isComplete() {
		return this.kill;
	}

	/**
	 * Sets the complete / kill value.
	 *
	 * @param comp the new kill value
	 */
	public void setComplete(boolean comp) {
		this.kill = comp;
	}

	/**
	 * Updates the grid effect.
	 *
	 * @param delta the milliseconds passed since the last update call
	 */
	public abstract void update(int delta);

	/**
	 * Renders at the x,y offset (given in terms of tiles offset - number of tiles shifted out of screen).
	 *
	 * @param g the g
	 * @param xoff the xoff in tile offset
	 * @param yoff the yoff in tiel offset
	 */
	public abstract void render(Graphics g, float xoff, float yoff);
}
