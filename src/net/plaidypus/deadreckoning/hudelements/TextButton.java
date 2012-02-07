package net.plaidypus.deadreckoning.hudelements;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

public class TextButton extends HudElement{	
	
	String text;
	UnicodeFont font;
	boolean pressed;
	Color c, c2, c3;
	
	public TextButton(int x, int y, int bindMethod, Color normalColor, Color highlightC, Color pressedC, String text, UnicodeFont font) throws SlickException{
		super(x,y,bindMethod,true);
		this.text=text;
		this.font=font;
		this.c=normalColor;
		this.c2=highlightC;
		this.c3 = pressedC;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		int mx = gc.getInput().getMouseX(), my = gc.getInput().getMouseY();
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)&& mx>getX() && mx<getX()+getWidth() && my>getY() && my<getY()+getHeight()){
			pressed=true;
		}
		else{
			pressed=false;
		}
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		this.font.addNeheGlyphs();
		this.font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		this.font.loadGlyphs();
	}

	@Override
	public int getWidth() {
		return font.getWidth(text)+20;
	}

	@Override
	public int getHeight() {
		return font.getHeight(text)+4;
	}
	
	public boolean isPressed(){
		return pressed;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(hasFocus){
			g.setColor(c2);
		}
		if (pressed){
			g.setColor(c3);
		}
		else{
			g.setColor(c);
		}
		g.fillRect(getX(),getY(),getWidth(),getHeight());
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(text,getX()+10, getY()+1);
		g.drawRect(getX(),getY(),getWidth(),getHeight());
	}
	
	
}
