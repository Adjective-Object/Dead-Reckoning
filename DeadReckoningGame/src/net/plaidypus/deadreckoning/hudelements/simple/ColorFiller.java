package net.plaidypus.deadreckoning.hudelements.simple;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorFiller.
 */
public class ColorFiller extends HudElement {

	/** The color. */
	Color color;

	/**
	 * Instantiates a new color filler.
	 *
	 * @param c the c
	 */
	public ColorFiller(Color c) {
		super(0, 0, HudElement.TOP_LEFT, false);
		this.color = c;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang.Object)
	 */
	@Override
	public void makeFrom(Object o) {

	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(this.color);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
	}
}
