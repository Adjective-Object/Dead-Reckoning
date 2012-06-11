package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class StaticImageEntity.
 */
public abstract class StaticImageEntity extends Entity {

	/** The draw. */
	Image draw;

	/**
	 * Instantiates a new static image entity.
	 */
	public StaticImageEntity() {
	}

	/**
	 * Instantiates a new static image entity.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 * @param drawImage
	 *            the draw image
	 */
	public StaticImageEntity(Tile t, int layer, Image drawImage) {
		super(t, layer);
		draw = drawImage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#forceRender(org.newdawn.slick
	 * .Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(draw, x, y);
	}

}
