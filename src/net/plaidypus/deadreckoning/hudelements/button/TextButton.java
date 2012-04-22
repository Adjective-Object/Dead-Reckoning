package net.plaidypus.deadreckoning.hudelements.button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class TextButton extends Button {

	String text;
	UnicodeFont font;
	Color c, c2, c3;

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

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta);
	}

	@Override
	public void makeFrom(Object o) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public int getWidth() {
		return font.getWidth(text) + 20;
	}

	@Override
	public int getHeight() {
		return font.getHeight(text) + 4;
	}

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
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(text, getX() + 10, getY() + 1);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}

}
