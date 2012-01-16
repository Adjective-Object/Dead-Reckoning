package net.plaidypus.deadreckoning.hudelements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StillImageElement extends HudElement{
	
	Image img;
	
	public StillImageElement(int x, int y, int bindMeth) {
		super(x,y,bindMeth,false);
		// TODO Auto-generated constructor stub
	}
	
	public void makeFrom(Object o){
		this.img=(Image)(o);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public int getWidth() {
		return img.getWidth();
	}

	@Override
	public int getHeight() {
		return img.getHeight();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawImage(img,this.getX(),this.getY());
	}

}
