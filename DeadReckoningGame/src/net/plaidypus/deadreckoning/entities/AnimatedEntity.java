package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedEntity.
 */
public abstract class AnimatedEntity extends Entity {

	/** The sprite. */
	Animation sprite;

	/**
	 * Instantiates a new animated entity.
	 * 
	 * @param location
	 *            the location
	 * @param layer
	 *            the layer
	 * @param imageRef
	 *            the image ref
	 * @throws SlickException
	 *             the slick exception
	 */
	public AnimatedEntity(String imageRef)
			throws SlickException {
		super();
		int[] frames = new int[] { 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 0, 1, 1, 1, 2,
				1, 3, 1, 4, 1 };
		int[] durations = new int[] { 120, 120, 120, 120, 120, 120, 120, 120,
				120, 120 };

		sprite = new Animation(new SpriteSheet(new Image(imageRef),
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize, 0),
				frames, durations);
		sprite.setAutoUpdate(true);
	}

	/**
	 * Render.
	 * 
	 * @return the image
	 */
	public Image render() {
		return sprite.getCurrentFrame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.
	 * GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		sprite.update(delta);
	}

}
