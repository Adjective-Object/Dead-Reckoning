package net.plaidypus.deadreckoning.hudelements;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ColorFiller extends HudElement{
	
	Color color;
	
	public ColorFiller(Color c){
		super(0,0,HudElement.TOP_LEFT,false);
		this.color=c;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
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
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(this.color);
		g.fillRect(0, 0, gc.getWidth(),gc.getHeight());
	}
}
