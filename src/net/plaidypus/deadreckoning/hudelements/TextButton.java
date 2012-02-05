package net.plaidypus.deadreckoning.hudelements;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class TextButton extends HudElement{	
	
	String text;
	UnicodeFont font;
	boolean moused;
	
	public TextButton(int x, int y, int bindMethod, String text, UnicodeFont font){
		super(x,y,bindMethod,true);
		this.text=text;
		this.font=font;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		int mx = gc.getInput().getMouseX(), my = gc.getInput().getMouseY();
		
		if(this.hasFocus ||
				mx>getX() && mx<getX()+getWidth() && my>getY() && my<getY()+getHeight()){
			moused=true;
		}
		else{
			moused=false;
		}
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {}

	@Override
	public int getWidth() {
		return font.getWidth(text)+20;
	}

	@Override
	public int getHeight() {
		return font.getHeight(text)+4;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(moused){
			g.setColor(new Color(80,60,70));
		}
		else{
			g.setColor(new Color(60,40,50));
		}
		g.fillRect(getX(),getY(),getWidth(),getHeight());
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(text,getX()+10, getY()+2);
		g.drawRect(getX(),getY(),getWidth(),getHeight());
	}
	
	
}
