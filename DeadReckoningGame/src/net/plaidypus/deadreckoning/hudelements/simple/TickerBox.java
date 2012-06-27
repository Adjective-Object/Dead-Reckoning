package net.plaidypus.deadreckoning.hudelements.simple;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.button.Button;

public class TickerBox extends Button{

	int width,height;
	
	public TickerBox(int x, int y, int bindMethod) {
		this(x, y, 15,15, bindMethod);
	}
	
	public TickerBox(int x, int y, int width, int height, int bindMethod) {
		super(x, y, bindMethod);
		this.width=width;
		this.height=height;
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
		g.setColor(Color.white);
		g.fillRect(this.getX(), this.getY(), width, height);
		g.setColor(Color.black);
		g.drawRect(this.getX(), this.getY(), width, height);
		g.setColor(DeadReckoningGame.menuHighlightColor);
		g.drawLine(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height);
	}

}
