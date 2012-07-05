package net.plaidypus.deadreckoning.status;

import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedStatus.
 * 
 * Made for Statuses that apply animated effects to their target Entities
 */
public abstract class AnimatedStatus extends Status {

	/** The animation. */
	Animation animation;

	/**
	 * Instantiates a new animated status.
	 * 
	 * @param sourceID
	 *            the source's ID
	 * @param tile
	 *            the tile image
	 * @param description
	 *            the description
	 * @param identifier
	 *            the int identifier of the status
	 * @param effectAnimation
	 *            the animation applies over the target Entity
	 */
	public AnimatedStatus(Integer sourceID, Image tile,
			String description, String identifier, Animation effectAnimation) {
		super(sourceID, tile, description, identifier);
		this.animation = effectAnimation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.status.Status#update(net.plaidypus.deadreckoning
	 * .entities.LivingEntity, int)
	 */
	@Override
	public void update(LivingEntity target, int delta) {
		animation.update(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.status.Status#render(org.newdawn.slick.Graphics
	 * , int, int)
	 */
	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(animation.getCurrentFrame(), x, y);
	}

}
