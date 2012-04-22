package net.plaidypus.deadreckoning.hudelements.simple;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class TextElement extends HudElement {

	String text;
	Color color;
	UnicodeFont font;

	public TextElement(int x, int y, int bindMethod, String text, Color color,
			UnicodeFont f) {
		super(x, y, bindMethod, false);
		this.text = text;
		this.font = f;
		this.color = color;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	@Override
	public void makeFrom(Object o) {
		this.text = (String) o;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public int getWidth() {
		return font.getWidth(text);
	}

	@Override
	public int getHeight() {
		return font.getHeight(text);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, getX(), getY());
	}

}
