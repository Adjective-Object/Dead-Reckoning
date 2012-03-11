package net.plaidypus.deadreckoning.hudelements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Button extends HudElement{
	
	public Button(int x, int y, int bindMethod) {
		super(x, y, bindMethod, true);
	}

	boolean pressed;
	
	public boolean isPressed(){
		return pressed;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta, boolean hasFocus) throws SlickException{
		int mx = gc.getInput().getMouseX(), my = gc.getInput().getMouseY();
		boolean overlap = mx>getX() && mx<getX()+getWidth() && my>getY() && my<getY()+getHeight();
		
		super.update(gc, sbg, delta, hasFocus||overlap );
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)&& overlap || this.hasFocus && gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			pressed=true;
		}
		else{
			pressed=false;
		}
	}
	
}
