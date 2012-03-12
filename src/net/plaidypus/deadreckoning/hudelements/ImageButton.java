package net.plaidypus.deadreckoning.hudelements;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ImageButton extends Button{
	
	Image image;
	
	public ImageButton(int x, int y, int bindMethod, Image image) {
		super(x, y, bindMethod);
		this.image=image;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {}
	
	@Override
	public void makeFrom(Object o) {
		this.image=(Image)(o);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {}

	public int getWidth() {
		if(image!=null){
			return image.getWidth();
		}
		return 0;
	}

	@Override
	public int getHeight() {
		if(image!=null){
			return image.getHeight();
		}
		return 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(image, getX(), getY());
		
		if(hasFocus){
			g.setColor(new Color(0,0,0,100));
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
		else if (pressed){
			g.setColor(new Color(0,0,0,255));
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}
	}

}
