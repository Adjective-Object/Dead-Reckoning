package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * The Class AnimationEffect.
 * 
 * A subclass of Grideffect for easily applying animations to the tile grid,
 * looping only if the animation loops
 */
public class AnimationEffect extends GridEffect {

	/** The animation. */
	public Animation animation;

	/**
	 * Instantiates a new animation effect.
	 * 
	 * @param location
	 *            the location tile
	 * @param a
	 *            the animation
	 */
	public AnimationEffect(Tile location, Animation a) {
		super(location);
		this.animation = a;
		a.start();
	}

	/**
	 * updates the animation
	 * 
	 * @see org.newdawn.slick.Animation#update(long)
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#update(int)
	 */
	@Override
	public void update(int delta) {
		this.animation.update(delta);
		if (animation.isStopped()) {
			this.setComplete(true);
		}
	}

	/**
	 * renders the animation at the given tile XY offset and tile coords
	 * 
	 * @see org.newdawn.slick.Animation#getCurrentFrame()
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#render(org.newdawn.slick.Graphics,
	 *      float, float)
	 */
	@Override
	public void render(Graphics g, float xoff, float yoff) {
		g.drawImage(this.animation.getCurrentFrame(), location.getX()
				* DeadReckoningGame.tileSize + xoff, location.getY()
				* DeadReckoningGame.tileSize + yoff);
	}
}