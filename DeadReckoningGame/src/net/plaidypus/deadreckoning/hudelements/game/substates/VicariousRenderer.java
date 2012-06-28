package net.plaidypus.deadreckoning.hudelements.game.substates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.state.HudLayersState;

public class VicariousRenderer extends HudElement{

	HudElement target;
	HudLayersState targetState;
	
	public VicariousRenderer(HudElement target) {
		super(0, 0, HudElement.TOP_LEFT, false);
		this.target = target;
	}
	
	public VicariousRenderer(HudLayersState target){
		super(0, 0, HudElement.TOP_LEFT, false);
		this.targetState = target;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {}

	@Override
	public void makeFrom(Object o) {
		target = (HudElement) o;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {}

	@Override
	public int getWidth() {
		return target.getWidth();
	}

	@Override
	public int getHeight() {
		return target.getHeight();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		if(this.targetState!=null){
			this.targetState.render(gc, sbg, g);
		}
		else if(this.target!=null){
			this.target.render(gc, sbg, g);
		}
	}

}
