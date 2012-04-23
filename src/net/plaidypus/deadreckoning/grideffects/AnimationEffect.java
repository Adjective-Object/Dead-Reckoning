package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimationEffect.
 */
public class AnimationEffect extends GridEffect {
	
	/** The animation. */
	public Animation animation;

	/**
	 * Instantiates a new animation effect.
	 *
	 * @param location the location
	 * @param a the a
	 */
	public AnimationEffect(Tile location, Animation a) {
		super(location);
		this.animation = a;
		a.start();
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#update(int)
	 */
	public void update(int delta) {
		this.animation.update(delta);
		if (animation.isStopped()) {
			this.setComplete(true);
		}
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#render(org.newdawn.slick.Graphics, float, float)
	 */
	public void render(Graphics g, float xoff, float yoff) {
		g.drawImage(this.animation.getCurrentFrame(), location.getX()
				* DeadReckoningGame.tileSize + xoff, location.getY()
				* DeadReckoningGame.tileSize + yoff);
	}
}