package net.plaidypus.deadreckoning.hudelements.simple;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.HudElement;

public class FillerBar extends HudElement{

	public float position = 0, destination=1.0F;
	public Color color;
	public int width, height;
	
	public FillerBar(int x, int y, Color color) {
		this(x,y,HudElement.TOP_LEFT,50,9,color);
	}
	
	public FillerBar(int x, int y, int bindMethod, int width, int height, Color color) {
		super(x, y, bindMethod, false);
		this.color=color;
		this.width=width;
		this.height=height;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {;
		this.position+= (this.destination-this.position)/100*delta;
	}
	
	public void setDestination(float newValue){
		this.destination=newValue;
	}
	
	public void setPosition(float newValue){
		this.position=newValue;
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
		g.setColor(color);
		g.fillRect(this.getAbsoluteX(), this.getAbsoluteY(), width*position, height);
	}

}
