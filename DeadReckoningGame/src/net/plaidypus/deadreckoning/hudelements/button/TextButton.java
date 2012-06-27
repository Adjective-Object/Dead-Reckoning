package net.plaidypus.deadreckoning.hudelements.button;

import net.plaidypus.deadreckoning.DeadReckoningGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class TextButton.
 */
public class TextButton extends Button {

	/** The text. */
	String text;

	/** The font. */
	UnicodeFont font;

	/** The c3. */
	Color c, c2, c3;

	/**
	 * Instantiates a new text button.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 * @param normalColor
	 *            the normal color
	 * @param highlightC
	 *            the highlight c
	 * @param pressedC
	 *            the pressed c
	 * @param text
	 *            the text
	 * @param font
	 *            the font
	 * @throws SlickException
	 *             the slick exception
	 */
	
	public TextButton(int x, int y, int bindMethod, String text) throws SlickException{
		this(x,y,bindMethod,
				new Color(30, 50, 70),
				new Color(40, 60, 80),
				new Color(60, 80, 100),
				text,
				DeadReckoningGame.menuSmallFont
				);
	}
	
	public TextButton(int x, int y, int bindMethod, String text, UnicodeFont font) throws SlickException{
		this(x,y,bindMethod,
				new Color(30, 50, 70),
				new Color(40, 60, 80),
				new Color(60, 80, 100),
				text,
				font
				);
	}
	
	public TextButton(int x, int y, int bindMethod, Color normalColor,
			Color highlightC, Color pressedC, String text, UnicodeFont font)
			throws SlickException {
		super(x, y, bindMethod);
		this.text = text;
		this.font = font;
		this.c = normalColor;
		this.c2 = highlightC;
		this.c3 = pressedC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.button.Button#update(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang
	 * .Object)
	 */
	@Override
	public void makeFrom(Object o) {
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
		return font.getWidth(text) + 20;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return font.getHeight(text) + 4;
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
		if (moused) {
			g.setColor(c2);
		}
		if (pressed) {
			g.setColor(c3);
		} else {
			g.setColor(c);
		}
		g.fillRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(text, getAbsoluteX() + 10, getAbsoluteY() + 1);
		g.drawRect(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
	}

}
