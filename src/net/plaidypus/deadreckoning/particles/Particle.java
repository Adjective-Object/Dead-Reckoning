package net.plaidypus.deadreckoning.particles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Particle {

	public boolean toKill;

	/**
	 * allows for easy visual effects independent of tile system.
	 */
	public Particle() {
		toKill = false;
	}

	/**
	 * updates the specific variables of the particle
	 * 
	 * @param gc
	 *            the gamecontainer this is happening in
	 * @param sbg
	 *            the statebasedgame this is happening in
	 * @param delta
	 *            the elapsed milliseconds
	 */
	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);

	/**
	 * renders the particle with a certain x and y offset (camera positioning)
	 * 
	 * @param g
	 * @param xoff
	 * @param yoff
	 */
	public abstract void render(Graphics g, float xoff, float yoff);
}
