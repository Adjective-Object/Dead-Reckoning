package net.plaidypus.deadreckoning.hudelements.simple;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class TextElement.
 */
public class TextElement extends HudElement {

	/** The text. */
	String text;
	
	/** The color. */
	Color color;
	
	/** The font. */
	UnicodeFont font;

	/**
	 * Instantiates a new text element.
	 *
	 * @param x the x
	 * @param y the y
	 * @param bindMethod the bind method
	 * @param text the text
	 * @param color the color
	 * @param f the f
	 */
	public TextElement(int x, int y, int bindMethod, String text, Color color,
			UnicodeFont f) {
		super(x, y, bindMethod, false);
		this.text = text;
		this.font = f;
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang.Object)
	 */
	@Override
	public void makeFrom(Object o) {
		this.text = (String) o;
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
		return font.getWidth(text);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return font.getHeight(text);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, getX(), getY());
	}

}
