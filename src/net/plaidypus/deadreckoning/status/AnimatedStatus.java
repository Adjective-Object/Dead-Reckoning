package net.plaidypus.deadreckoning.status;

import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedStatus.
 */
public abstract class AnimatedStatus extends Status {

	/** The animation. */
	Animation animation;

	/**
	 * Instantiates a new animated status.
	 *
	 * @param source the source
	 * @param tile the tile
	 * @param description the description
	 * @param identifier the identifier
	 * @param effectAnimation the effect animation
	 */
	public AnimatedStatus(InteractiveEntity source, Image tile,
			String description, String identifier, Animation effectAnimation) {
		super(source, tile, description, identifier);
		this.animation = effectAnimation;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#update(net.plaidypus.deadreckoning.entities.LivingEntity, int)
	 */
	@Override
	public void update(LivingEntity target, int delta) {
		animation.update(delta);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#render(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(animation.getCurrentFrame(), x, y);
	}

}
