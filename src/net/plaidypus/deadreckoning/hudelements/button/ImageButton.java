package net.plaidypus.deadreckoning.hudelements.button;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ImageButton extends Button{
	
	Image image;
	Image altImage;
	
	public ImageButton(int x, int y, int bindMethod, Image image) {
		this(x,y,bindMethod,image,null);
	}
	
	public ImageButton(int x, int y, int bindMethod, Image image, Image altimage) {
		super(x, y, bindMethod);
		this.image=image;
		this.altImage=altimage;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		super.update(gc, sbg, delta);
	}
	
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
		
		if(!moused){
			g.drawImage(image, getX(), getY());
		}
		if(moused){
			if(altImage==null){
				g.drawImage(image, getX(), getY());
				g.setColor(new Color(255,255,255,100));
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
			else{
				g.drawImage(altImage, getX(), getY());
			}
		}
		else if (pressed){
			g.setColor(new Color(255,255,255,255));
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}
	}

}
