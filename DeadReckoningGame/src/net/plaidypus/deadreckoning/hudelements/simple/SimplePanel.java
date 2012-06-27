package net.plaidypus.deadreckoning.hudelements.simple;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

public class SimplePanel extends HudElement{
	
	int width,height, borderX, borderY;
	
	public SimplePanel(int x, int y, int width, int height, int bindMethod) {
		this(x,y,width,height,15,15,bindMethod);
	}
		
	public SimplePanel(int x, int y, int width, int height, int borderX, int borderY, int bindMethod) {
		super(x, y, bindMethod, false);
		this.width=width;
		this.height=height;
		this.borderX=borderX;
		this.borderY=borderY;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta, hasFocus);
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(DeadReckoningGame.menuColor);
		g.fillRect(getAbsoluteX() - borderX, getAbsoluteY() - borderY,
				getWidth() + borderX * 2, getHeight() + borderY * 2);
		g.setColor(Color.white);
		g.drawRect(getAbsoluteX() - borderX, getAbsoluteY() - borderY,
				getWidth() + borderX * 2, getHeight() + borderY * 2);

	}

}
