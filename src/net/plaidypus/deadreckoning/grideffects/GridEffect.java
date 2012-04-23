package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class GridEffect.
 */
public abstract class GridEffect {
	
	/** The kill. */
	private boolean kill;
	
	/** The location. */
	public Tile location;

	/**
	 * Instantiates a new grid effect.
	 *
	 * @param location the location
	 */
	public GridEffect(Tile location) {
		this.location = location;
		this.kill = false;
	}

	/**
	 * Sets the location.
	 *
	 * @param l the new location
	 */
	public void setLocation(Tile l) {
		this.location = l;
	}

	/**
	 * Checks if is complete.
	 *
	 * @return true, if is complete
	 */
	public boolean isComplete() {
		return this.kill;
	}

	/**
	 * Sets the complete.
	 *
	 * @param comp the new complete
	 */
	public void setComplete(boolean comp) {
		this.kill = comp;
	}

	/**
	 * Update.
	 *
	 * @param delta the delta
	 */
	public abstract void update(int delta);

	/**
	 * Render.
	 *
	 * @param g the g
	 * @param xoff the xoff
	 * @param yoff the yoff
	 */
	public abstract void render(Graphics g, float xoff, float yoff);
}
