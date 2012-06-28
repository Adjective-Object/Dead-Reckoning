package net.plaidypus.deadreckoning.hudelements.simple;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.HudElement;

public class AnimatedImageElement extends HudElement{

	Animation animation;
	
	public AnimatedImageElement(int x, int y, int bindMethod, Animation animation) {
		super(x, y, bindMethod, false);
		this.animation=animation;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeFrom(Object o) {
		this.animation=(Animation)o;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {}

	@Override
	public int getWidth() {
		return animation.getWidth();
	}

	@Override
	public int getHeight() {
		return animation.getHeight();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(this.animation.getCurrentFrame(),this.getAbsoluteX(),this.getAbsoluteY());
	}

}
