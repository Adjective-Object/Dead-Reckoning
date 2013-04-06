package net.plaidypus.deadreckoning.hudelements.simple;

import net.plaidypus.deadreckoning.hudelements.button.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TickerBox extends Button{

	int width,height;
	
	boolean ticked=false;
	boolean changeTicked = false;
	
	public TickerBox(int x, int y, int bindMethod) {
		this(x, y, 15,15, bindMethod);
	}
	
	public TickerBox(int x, int y, int width, int height, int bindMethod) {
		super(x, y, bindMethod);
		this.width=width;
		this.height=height;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException{
		super.update(gc, sbg, delta);
		if(this.isPressed()){
			this.ticked=!this.ticked;
			this.changeTicked = true;
		}
	}
	
	@Override
	public void makeFrom(Object o) {
		this.ticked = (Boolean)o;
	}

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
			if(this.ticked){
			g.setColor(Color.red);
			g.setLineWidth(2);
			g.drawLine(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height);
			g.setLineWidth(1);
		}
		g.setColor(Color.black);
		g.drawRect(this.getX(), this.getY(), width, height);
	}

	public boolean isTicked() {
		return this.ticked;
	}
	
	public boolean changeTicked(){
		if(changeTicked){
			changeTicked=false;
			return true;
		}
		return false;
	}

}
