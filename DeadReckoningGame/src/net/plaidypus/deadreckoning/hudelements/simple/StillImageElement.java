package net.plaidypus.deadreckoning.hudelements.simple;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class StillImageElement.
 */
public class StillImageElement extends HudElement {

	/** The img. */
	Image img;

	/**
	 * Instantiates a new still image element.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMeth
	 *            the bind meth
	 */
	public StillImageElement(int x, int y, int bindMeth) {
		super(x, y, bindMeth, false);
	}

	/**
	 * Instantiates a new still image element.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMeth
	 *            the bind meth
	 * @param img
	 *            the img
	 */
	public StillImageElement(int x, int y, int bindMeth, Image img) {
		super(x, y, bindMeth, false);
		this.img = img;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang
	 * .Object)
	 */
	public void makeFrom(Object o) {
		this.img = (Image) (o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		if (img != null) {
			return img.getWidth();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		if (img != null) {
			return img.getHeight();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame,
	 * org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(img, this.getX(), this.getY());
	}

}
